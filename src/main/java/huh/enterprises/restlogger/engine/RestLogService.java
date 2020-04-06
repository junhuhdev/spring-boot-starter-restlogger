package huh.enterprises.restlogger.engine;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;

public interface RestLogService {
    void logIncomingRequest(HttpServletRequest httpServletRequest, Object body);

    void logOutgoingRequest(HttpRequest request, String body);

    void logIncomingResponse(URI url, HttpMethod method, ClientHttpResponse response, String body);

    void logIncomingResponse(HttpRequest request, ClientHttpResponse response, String body);

    void logOutgoingResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body);

}
