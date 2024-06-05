package com.dimstyl.dietitianhub.exceptionHandlers;

import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime timestamp, int statusCode, String message, String redirectUrl) {
}
