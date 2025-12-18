package com.tamakara.litebooth.domain.vo.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoPageVO {
    private List<OrderInfoVO> records;
    private Long pageNum;
    private Long pageSize;
    private Long total;
}
