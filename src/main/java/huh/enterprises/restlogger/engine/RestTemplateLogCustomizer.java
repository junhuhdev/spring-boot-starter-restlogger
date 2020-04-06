package huh.enterprises.restlogger.engine;

import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class RestTemplateLogCustomizer implements RestTemplateCustomizer {

    private final RestLogService restLogService;
    private final RestTemplateErrorHandler restTemplateErrorHandler;

    public RestTemplateLogCustomizer(RestLogService restLogService, RestTemplateErrorHandler restTemplateErrorHandler) {
        this.restLogService = restLogService;
        this.restTemplateErrorHandler = restTemplateErrorHandler;
    }

    @Override
    public void customize(RestTemplate restTemplate) {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        //Since we log the response, we need to use the BufferingClient to read the response more than once.
        BufferingClientHttpRequestFactory clientHttpRequestFactory = new BufferingClientHttpRequestFactory(simpleClientHttpRequestFactory);
        restTemplate.setErrorHandler(restTemplateErrorHandler);
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        restTemplate.getInterceptors().add(new RestTemplateLogger(restLogService));
    }
}
