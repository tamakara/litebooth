import {defineStore} from 'pinia'
import {ref} from 'vue'
import {fetchShopInfoVO} from "@/api/shop";
import {fetchItemCardPageVO} from "@/api/item";
import {fetchGroupListVO} from "@/api/group";
import {GroupVO, ItemCardPageVO, ShopInfoVO} from "@/types";

export const useHomeStore = defineStore('home', () => {
    const shopInfo = ref<ShopInfoVO>({
        logoFileId: 0,
        logoFileUrl: '',
        logoTitle: '',
        homeTitle: '',
        homeSubtitle: '',
        homeAnnouncement: '',
    })
    const groupList = ref<GroupVO[]>([])
    const itemCardQueryForm = ref({
        keyword: '',
        groupId: 0,
        pageNum: 1,
        pageSize: 20
    })
    const itemCardPage = ref<ItemCardPageVO>({
        records: [],
        pageNum: 1,
        pageSize: 20,
        total: 0
    })
    const loading = ref(false)

    const fetchShopInfo = async () => {
        loading.value = true
        try {
            shopInfo.value = await fetchShopInfoVO()
        } finally {
            loading.value = false
        }
    }

    const fetchGroupList = async () => {
        loading.value = true
        try {
            groupList.value = await fetchGroupListVO()
        } finally {
            loading.value = false
        }
    }

    const fetchItemCardPage = async () => {
        loading.value = true
        try {
            itemCardPage.value = await fetchItemCardPageVO(itemCardQueryForm.value)
        } finally {
            loading.value = false
        }
    }

    return {
        shopInfo,
        groupList,
        itemCardQueryForm,
        itemCardPage,
        loading,
        fetchShopInfoVO: fetchShopInfo,
        fetchGroupListVO: fetchGroupList,
        fetchItemCardPageVO: fetchItemCardPage
    }
})
