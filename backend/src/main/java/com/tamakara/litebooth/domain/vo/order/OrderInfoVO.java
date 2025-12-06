package com.tamakara.litebooth.domain.vo.order;

import com.tamakara.litebooth.domain.entity.Order;
import com.tamakara.litebooth.domain.enums.OrderStatus;
import com.tamakara.litebooth.domain.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoVO {
    private String id;
    private OrderStatus status;
    private String queryEmail;
    private String queryPassword;
    private String itemName;
    private Double itemPrice;
    private Long quantity;
    private PaymentMethod paymentMethod;
    private Double amount;
    private List<String> contentList;
    private Instant createdAt;
    private Instant paymentAt;

    public OrderInfoVO(Order order, List<String> contentList) {
        this.id = order.getId().toString();
        this.status = order.getStatus();
        this.queryEmail = order.getQueryEmail();
        this.queryPassword = order.getQueryPassword();
        this.itemName = order.getItemName();
        this.itemPrice = order.getItemPrice();
        this.quantity = order.getQuantity();
        this.paymentMethod = order.getPaymentMethod();
        this.amount = order.getAmount();
        this.contentList = contentList;
        this.createdAt = order.getCreatedAt();
        this.paymentAt = order.getPaymentAt();
    }
}
