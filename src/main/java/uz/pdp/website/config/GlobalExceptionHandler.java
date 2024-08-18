package uz.pdp.website.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.pdp.website.dto.BaseResponse;
import uz.pdp.website.exception.DataNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public BaseResponse dataNotFoundException(DataNotFoundException e) {
        return BaseResponse.builder()
                .message(e.getMessage())
                .status(404)
                .success(false)
                .build();
    }


}
