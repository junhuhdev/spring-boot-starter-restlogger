package huh.enterprises.restlogger.config;

import huh.enterprises.restlogger.engine.RestLogGetMethodInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
    private final RestLogGetMethodInterceptor logInterceptor;

    @Autowired
    public WebConfig(RestLogGetMethodInterceptor logInterceptor) {
        this.logInterceptor = logInterceptor;
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor);
    }

    @Bean
    public RestTemplate restTemplate(@Qualifier("restTemplateLogCustomizer") RestTemplateCustomizer restTemplateLogCustomizer) {
        return new RestTemplateBuilder()
                .customizers(restTemplateLogCustomizer)
                .build();
    }

}
