export type ShopInfoUpdateFormDTO = {
  logo: string;
  logoTitle: string;
  homeTitle: string;
  homeSubtitle: string;
  homeAnnouncement: string;
};

export type ShopInfoUpdateFormVO = {
  logo: string;
  logoUrl: string;
  logoTitle: string;
  homeTitle: string;
  homeSubtitle: string;
  homeAnnouncement: string;
};

export type FormItemProps = ShopInfoUpdateFormDTO;
