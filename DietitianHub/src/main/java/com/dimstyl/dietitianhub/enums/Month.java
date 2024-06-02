package com.dimstyl.dietitianhub.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Month {

    Jan(1), Feb(2), Mar(3), Apr(4), May(5), Jun(6), Jul(7), Aug(8), Sep(9), Oct(10), Nov(11), Dec(12);

    private final int monthNumber;

    public static String byNumber(int monthNumber) {
        return Month.values()[monthNumber - 1].name();
    }

}
