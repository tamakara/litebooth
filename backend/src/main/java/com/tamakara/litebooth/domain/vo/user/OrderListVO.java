package com.tamakara.litebooth.domain.vo.user;

import com.tamakara.litebooth.domain.vo.order.OrderInfoVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderListVO {
    private List<OrderInfoVO> orders;
    private Long pageNumber;
    private Long pageSize;
    private Long total;
}
