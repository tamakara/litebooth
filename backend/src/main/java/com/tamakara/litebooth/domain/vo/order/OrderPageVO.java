package com.tamakara.litebooth.domain.vo.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderPageVO {
    private List<OrderVO> records;
    private Long total;
    private Long size;
    private Long current;
}

