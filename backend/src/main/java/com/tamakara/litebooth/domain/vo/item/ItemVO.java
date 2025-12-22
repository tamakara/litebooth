package com.tamakara.litebooth.domain.vo.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemVO {
    private String id;
    private Boolean isActive;
    private String name;
    private Double price;
    private String group;
    private String cover;
    private String coverUrl;
    private String description;
    private Instant createdAt;
}
