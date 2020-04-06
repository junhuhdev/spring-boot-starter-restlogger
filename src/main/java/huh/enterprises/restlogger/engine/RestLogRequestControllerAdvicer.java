package huh.enterprises.restlogger.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;

/**
 * Logging requests (POST, PUT, PATCH, DELETE …)
 * For others methods that contains a body, we’ll use RequestBodyAdviceAdapter.
 */
@ControllerAdvice
public class RestLogRequestControllerAdvicer extends RequestBodyAdviceAdapter {

    private final RestLogService loggingService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    public RestLogRequestControllerAdvicer(RestLogService loggingService) {
        this.loggingService = loggingService;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage,
            MethodParameter parameter, Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType) {

        loggingService.logIncomingRequest(httpServletRequest, body);

        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

}
