package huh.enterprises.restlogger.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.net.URI;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class RestTemplateErrorHandler extends DefaultResponseErrorHandler {

    private final RestLogService restLogService;

    @Autowired
    public RestTemplateErrorHandler(RestLogService restLogService) {
        this.restLogService = restLogService;
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        restLogService.logIncomingResponse(url, method, response, StreamUtils.copyToString(response.getBody(), UTF_8));
        super.handleError(url, method, response);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        super.handleError(response);
    }
}
