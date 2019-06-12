package app.validation;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

class ApiError {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String message;

    private String debugMessage;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }
}