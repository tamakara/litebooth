export type ShopInfoUpdateFormDTO = {
  logoFileId: number;
  logoTitle: string;
  homeTitle: string;
  homeSubtitle: string;
  homeAnnouncement: string;
};

export type ShopInfoVO = {
  logoFileId: number;
  logoFileUrl: string;
  logoTitle: string;
  homeTitle: string;
  homeSubtitle: string;
  homeAnnouncement: string;
};

export type FormItemProps = ShopInfoUpdateFormDTO;
