package com.tamakara.litebooth.domain.vo.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopInfoUpdateFormVO {
    private String logo;
    private String logoUrl;
    private String logoTitle;
    private String homeTitle;
    private String homeSubtitle;
    private String homeAnnouncement;
}
