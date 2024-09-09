package gr.unipi.thesis.dimstyl.exceptionHandlers;

import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime timestamp, int statusCode, String message, String redirectUrl) {
}
