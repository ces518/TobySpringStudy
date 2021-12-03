package me.june.mvc.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ResponseStatusExceptionResolver
 * 예외를 특정 HTTP 응답 상태코드로 전환해 준다.
 */
@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE, reason = "서비스 일시 중지")
public class NotInServiceException extends RuntimeException {
}
