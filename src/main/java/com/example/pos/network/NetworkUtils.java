package com.example.pos.network;

import com.example.pos.constant.MessageConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/** Converts internal service results into the common HTTP response format. */
public final class NetworkUtils {
    private NetworkUtils() {
    }

    public static <T> ResponseEntity<ApiResponseWrapper<T>> wrap(ResponseData<T> response) {
        return ResponseEntity.status(response.httpStatus())
                .body(new ApiResponseWrapper<>(response.data(),
                        new ApiResponseWrapper.ApiStatus(
                                String.valueOf(response.httpStatus().value()), response.message())));
    }

    public static <T> ResponseEntity<ApiResponseWrapper<List<T>>> wrap(ResponseDataPack<T> response) {
        return ResponseEntity.status(response.httpStatus())
                .body(new ApiResponseWrapper<>(response.data(),
                        new ApiResponseWrapper.ApiStatus(
                                String.valueOf(response.httpStatus().value()), response.message())));
    }

    public static <T> ResponseEntity<ApiResponseWrapper<T>> error(String message, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus)
                .body(ApiResponseWrapper.error(message, httpStatus.value()));
    }

    public static ResponseEntity<ApiResponseWrapper<Void>> handleException(Exception exception) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (exception instanceof ResponseStatusException responseStatusException) {
            status = HttpStatus.valueOf(responseStatusException.getStatusCode().value());
        }
        String message = exception.getMessage() == null
                ? MessageConstants.UNEXPECTED_ERROR
                : exception.getMessage();
        return error(message, status);
    }
}
