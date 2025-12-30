import type {GroupListVO} from "@/api/group";
import type {ItemCreateOrUpdateFormDTO} from "@/api/item";

export interface ItemProps {
  formInline: ItemCreateOrUpdateFormDTO;
  groupList: GroupListVO;
  coverFileUrl: string;
}


