package com.tamakara.litebooth.domain.dto.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopInfoUpdateFormDTO {
    private String logo;
    private String logoTitle;
    private String homeTitle;
    private String homeSubtitle;
    private String homeAnnouncement;
}
