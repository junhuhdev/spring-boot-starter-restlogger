package huh.enterprises.restlogger.engine;

import io.vavr.control.Option;
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

//    @Override
//    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
//        ClientHttpResponse response = null;
//        try {
//            restLogService.logOutgoingRequest(request, new String(body, UTF_8));
//            response = execution.execute(request, body);
//            restLogService.logIncomingResponse(request, response, StreamUtils.copyToString(response.getBody(), UTF_8));
//        } catch (Exception e) {
//            String temp = "";
//            String pelle = "";
////            restLogService.logIncomingResponse(request, response, StreamUtils.copyToString(response.getBody(), UTF_8));
//        }
//        return response;
//    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        Option<ClientHttpResponse> optResponse = Option.none();
        try {
            optResponse = Option.of(execution.execute(request, body));
        } finally {
            restLogService.logOutgoingRequest(request, new String(body, UTF_8));
            optResponse.toTry().andThenTry(response -> restLogService.logIncomingResponse(request, response, StreamUtils.copyToString(response.getBody(), UTF_8)));
        }
        return optResponse.getOrNull();
    }

}
