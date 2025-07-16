package org.springframework.boot.result;

public record ResponseWrapper<T>(boolean success, String message, T data) {

	public static <T> ResponseWrapper<T> success(T data, String message) {
		return new ResponseWrapper<>(true, message, data);
	}

	public static <T> ResponseWrapper<T> failure(String message) {
		return new ResponseWrapper<>(false, message, null);
	}
}