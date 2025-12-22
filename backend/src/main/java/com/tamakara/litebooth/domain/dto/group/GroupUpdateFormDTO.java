package com.tamakara.litebooth.domain.dto.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupUpdateFormDTO {
    private Long id;
    private String name;
}
