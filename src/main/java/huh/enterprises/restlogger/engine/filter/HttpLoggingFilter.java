//package huh.enterprises.restlogger.engine;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StreamUtils;
//import org.springframework.web.util.ContentCachingRequestWrapper;
//import org.springframework.web.util.ContentCachingResponseWrapper;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Map;
//
//import static java.nio.charset.StandardCharsets.UTF_8;
//
///**
// * https://www.baeldung.com/spring-http-logging
// */
//@Slf4j(topic = "REST")
//@Component
//public class HttpLoggingFilter implements Filter {
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
//        try {
//            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
//            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
//
//            ContentCachingRequestWrapper bufferedRequest = new ContentCachingRequestWrapper(httpServletRequest);
//            ContentCachingResponseWrapper bufferedResponse = new ContentCachingResponseWrapper(httpServletResponse);
//
//            final StringBuilder logMessage = new StringBuilder(
//                    "REST Request - ").append("[HTTP METHOD:")
//                    .append(bufferedRequest.getMethod())
//                    .append("] [PATH INFO:")
//                    .append(bufferedRequest.getServletPath())
//                    .append("] [REQUEST BODY:")
////                    .append(bufferedRequest.getRequest().getcon)
//                    .append("] [REMOTE ADDRESS:")
//                    .append(bufferedRequest.getRemoteAddr()).append("]");
//
//            chain.doFilter(bufferedRequest, bufferedResponse);
//            logMessage.append(" [RESPONSE:").append(StreamUtils.copyToString(bufferedResponse.getContentInputStream(), UTF_8)).append("]");
//            log.debug(logMessage.toString());
//        } catch (Throwable a) {
//            log.error(a.getMessage());
//        }
//    }
//}
