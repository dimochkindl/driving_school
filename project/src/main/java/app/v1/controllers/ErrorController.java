package app.v1.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @GetMapping
    public String handleError(HttpServletRequest request, Model model) {
        // Получаем атрибут с именем javax.servlet.error.status_code
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error-404"; // Создайте страницу error-404.jsp в папке resources/templates/
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error-500"; // Создайте страницу error-500.jsp в папке resources/templates/
            }
        }
        return "error"; // Создайте страницу error.jsp в папке resources/templates/
    }
}

