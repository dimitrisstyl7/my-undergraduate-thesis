package gr.unipi.thesis.dimstyl.enums;

public enum RequestType {

    WEB, WEB_API, API;

    public static RequestType byUri(String uri) {
        return uri.startsWith("/api/") ? API : WEB;
    }

}
