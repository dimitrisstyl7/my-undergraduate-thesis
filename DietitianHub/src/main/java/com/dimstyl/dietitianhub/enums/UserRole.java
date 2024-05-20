package com.dimstyl.dietitianhub.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    CLIENT(1), DIETITIAN(2);

    private final Integer id;

    public String getRole() {
        return this.name();
    }
}
