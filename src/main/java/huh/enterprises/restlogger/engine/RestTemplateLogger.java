package huh.enterprises.restlogger.engine;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class RestTemplateLogger implements ClientHttpRequestInterceptor {

    private final RestLogService restLogService;

    public RestTemplateLogger(RestLogService restLogService) {
        this.restLogService = restLogService;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        restLogService.logOutgoingRequest(request, new String(body, UTF_8));
        ClientHttpResponse response = execution.execute(request, body);
        restLogService.logIncomingResponse(request, response, StreamUtils.copyToString(response.getBody(), UTF_8));
        return response;
    }

}
