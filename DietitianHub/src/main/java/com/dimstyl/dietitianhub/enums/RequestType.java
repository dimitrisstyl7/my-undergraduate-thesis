package com.dimstyl.dietitianhub.enums;

public enum RequestType {

    WEB, API;

    public static RequestType byUri(String uri) {
        return uri.startsWith("/api/") ? API : WEB;
    }

}
