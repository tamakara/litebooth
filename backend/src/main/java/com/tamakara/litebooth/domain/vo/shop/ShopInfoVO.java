package com.tamakara.litebooth.domain.vo.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopInfoVO {
    private Long logoFileId;
    private String logoFileUrl;
    private String logoTitle;
    private String homeTitle;
    private String homeSubtitle;
    private String homeAnnouncement;
}
