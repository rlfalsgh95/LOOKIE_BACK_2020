package kr.or.connect.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The requested information could not be found.")
public class InformationNotFoundException extends RuntimeException{
}