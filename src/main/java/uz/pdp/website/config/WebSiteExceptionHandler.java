package uz.pdp.website.config;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.pdp.website.exception.AlreadyExistsException;
import uz.pdp.website.exception.DataNotFoundException;
import uz.pdp.website.service.user.UserService;

@ControllerAdvice
@RequiredArgsConstructor
public class WebSiteExceptionHandler extends ResponseEntityExceptionHandler {
    private final UserService userService;

    @ExceptionHandler(value = DataNotFoundException.class)
    public String handleDataNotFoundException(DataNotFoundException e, Model model){
        model.addAttribute("exception", e.getMessage());
        return "login";
    }
}
