package com.tamakara.litebooth.domain.vo.user;

import com.tamakara.litebooth.domain.vo.order.OrderVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderListVO {
    private List<OrderVO> orders;
    private Long pageNumber;
    private Long pageSize;
    private Long total;
}
