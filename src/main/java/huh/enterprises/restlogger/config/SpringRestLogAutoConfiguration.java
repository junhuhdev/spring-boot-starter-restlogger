package huh.enterprises.restlogger.config;

import huh.enterprises.restlogger.engine.RestLogGetMethodInterceptor;
import huh.enterprises.restlogger.engine.RestLogRequestControllerAdvicer;
import huh.enterprises.restlogger.engine.RestLogResponseControllerAdvicer;
import huh.enterprises.restlogger.engine.RestLogService;
import huh.enterprises.restlogger.engine.RestLogServiceImpl;
import huh.enterprises.restlogger.engine.RestTemplateErrorHandler;
import huh.enterprises.restlogger.engine.RestTemplateLogCustomizer;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore(RestTemplateAutoConfiguration.class)
public class SpringRestLogAutoConfiguration {

    @Bean
    public RestLogService restLogService() {
        return new RestLogServiceImpl();
    }

    @Bean
    public RestLogGetMethodInterceptor restLogGetMethodInterceptor(RestLogService restLogService) {
        return new RestLogGetMethodInterceptor(restLogService);
    }

    @Bean
    @ConditionalOnMissingBean
    public RestLogResponseControllerAdvicer restLogResponseAdapter(RestLogService restLogService) {
        return new RestLogResponseControllerAdvicer(restLogService);
    }

    @Bean
    @ConditionalOnMissingBean
    public RestLogRequestControllerAdvicer restLogRequestControllerAdvicer(RestLogService restLogService) {
        return new RestLogRequestControllerAdvicer(restLogService);
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplateErrorHandler restTemplateErrorHandler(RestLogService restLogService) {
        return new RestTemplateErrorHandler(restLogService);
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplateLogCustomizer restTemplateLogCustomizer(RestLogService restLogService, RestTemplateErrorHandler restTemplateErrorHandler) {
        return new RestTemplateLogCustomizer(restLogService, restTemplateErrorHandler);
    }

}
