package com.dimstyl.dietitianhub.utilities;

import com.dimstyl.dietitianhub.enums.RequestType;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class RequestTypeUtil {

    public static RequestType getRequestType(String uri) {
        return RequestType.byUri(uri);
    }

}
