package huh.enterprises.restlogger.engine;

import org.springframework.boot.web.servlet.DispatcherType;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Logging requests (GET methods)
 * The GET methods don’t contain body
 * so we’ll use a HandlerInterceptor for this case.
 */
public class RestLogGetMethodInterceptor implements HandlerInterceptor {

    private final RestLogService loggingService;

    public RestLogGetMethodInterceptor(RestLogService loggingService) {
        this.loggingService = loggingService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (DispatcherType.REQUEST.name().equals(request.getDispatcherType().name())
                && request.getMethod().equals(HttpMethod.GET.name())) {
            loggingService.logIncomingRequest(request, null);
        }
        return true;
    }

}
