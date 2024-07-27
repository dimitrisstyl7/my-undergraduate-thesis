package com.dimstyl.dietitianhub.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {

    CLIENT(1, "CLIENT"), DIETITIAN(2, "DIETITIAN");

    private final int id;
    private final String role;

}
