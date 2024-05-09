package com.dimstyl.dietitianhub.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    CLIENT(1L), DIETITIAN(2L);

    private final Long id;

    public String getRole() {
        return this.name();
    }
}
