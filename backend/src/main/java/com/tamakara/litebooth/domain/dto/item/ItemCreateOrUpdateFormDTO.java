package com.tamakara.litebooth.domain.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCreateOrUpdateFormDTO {
    private Long id;
    private Boolean isActive;
    private String name;
    private Double price;
    private Long groupId;
    private Long coverFileId;
    private Long coverFileUrl;
    private String description;
}
