package com.tamakara.litebooth.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoPageQueryFormDTO {
    private String queryMode;
    private Long orderId;
    private String queryEmail;
    private String queryPassword;
    private Long pageNum;
    private Long pageSize;
}
