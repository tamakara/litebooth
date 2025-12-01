package com.tamakara.litebooth.domain.vo.order;

import com.tamakara.litebooth.domain.entity.Order;
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
    private String status;
    private String queryEmail;
    private String queryPassword;
    private String itemName;
    private Double itemPrice;
    private Long quantity;
    private String payMethod;
    private Double amount;
    private List<String> contentList;
    private Instant createdAt;
    private Instant updatedAt;

    public OrderInfoVO(Order order, List<String> contentList) {
        this.id = order.getId().toString();
        this.status = order.getStatus();
        this.queryEmail = order.getQueryEmail();
        this.queryPassword = order.getQueryPassword();
        this.itemName = order.getItemName();
        this.itemPrice = order.getItemPrice();
        this.quantity = order.getQuantity();
        this.payMethod = order.getPayMethod();
        this.amount = order.getAmount();
        this.contentList = contentList;
        this.createdAt = order.getCreatedAt();
        this.updatedAt = order.getUpdatedAt();
    }
}
