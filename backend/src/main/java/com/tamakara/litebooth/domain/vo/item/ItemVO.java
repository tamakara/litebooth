package com.tamakara.litebooth.domain.vo.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemVO {
    private Long id;
    private Long groupId;
    private String groupName;
    private Boolean isActive;
    private String name;
    private Double price;
    private Long coverFileId;
    private String coverFileUrl;
    private String description;
    private Instant createdAt;
}
