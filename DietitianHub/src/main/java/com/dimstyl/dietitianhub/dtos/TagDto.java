package com.dimstyl.dietitianhub.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TagDto {

    Integer id;
    Integer categoryId;
    String name;

}
