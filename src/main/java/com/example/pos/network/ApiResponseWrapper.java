package com.example.pos.network;

/** The public response contract required by the examination. */
public record ApiResponseWrapper<T>(T data, ApiStatus status) {

    public static <T> ApiResponseWrapper<T> success(T data, String message) {
        return new ApiResponseWrapper<>(data, ApiStatus.success(message));
    }

    public static <T> ApiResponseWrapper<T> error(String message, int httpStatus) {
        return new ApiResponseWrapper<>(null, new ApiStatus(String.valueOf(httpStatus), message));
    }

    public record ApiStatus(String code, String message) {
        public static ApiStatus success(String message) {
            return new ApiStatus("200", message);
        }
    }
}
