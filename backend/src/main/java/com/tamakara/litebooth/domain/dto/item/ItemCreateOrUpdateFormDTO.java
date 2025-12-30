package com.tamakara.litebooth.domain.dto.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCreateOrUpdateFormDTO {
    private Long id;
    private Boolean isActive;
    @NotBlank(message = "商品名称不能为空")
    private String name;
    @NotNull(message = "商品价格不能为空")
    private Double price;
    private Long groupId;
    private Long coverFileId;
    private Long coverFileUrl;
    private String description;
}
