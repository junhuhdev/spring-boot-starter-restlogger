package huh.enterprises.restlogger.engine.filter;

import huh.enterprises.restlogger.engine.RestLogRequestType;
import huh.enterprises.restlogger.engine.utils.HeaderFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
@Slf4j(topic = "RequestAndResponseLoggingFilter")
public class LogFormatter {

    public void request(ContentCachingRequestWrapper request) {
        try {
            StringBuilder sb = new StringBuilder();
            String newLine = System.getProperty("line.separator");
            sb.append(RestLogRequestType.REQ_IN);
            sb.append(newLine).append(newLine);
            sb.append("   Address: ").append(request.getRequestURL());
            sb.append(newLine);
            sb.append("   HttpMethod: ").append(request.getMethod());
            sb.append(newLine);
            sb.append("   Content-Type: ").append(request.getHeaders("Content-Type"));
            sb.append(newLine);
            sb.append("   Headers: ").append(HeaderFormatter.formatRequestHeaders(request));
            sb.append(newLine);
            sb.append("   Payload: ");
            sb.append(StreamUtils.copyToString(request.getInputStream(), UTF_8));
            sb.append(newLine);
            log.info(sb.toString());
        } catch (Exception e) {

        }
    }

    public void response(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) {
        try {
            StringBuilder sb = new StringBuilder();
            String newLine = System.getProperty("line.separator");
            sb.append(RestLogRequestType.RESP_OUT);
            sb.append(newLine).append(newLine);
            sb.append("   Address: ").append(request.getRequestURL());
            sb.append(newLine);
            sb.append("   HttpMethod: ").append(request.getMethod());
            sb.append(newLine);
            sb.append("   Content-Type: ").append(response.getHeaders("Content-Type"));
            sb.append(newLine);
            sb.append("   ResponseCode: ").append(response.getStatus());
            sb.append(newLine);
            sb.append("   Headers: ").append(HeaderFormatter.formatResponseHeaders(response));
            sb.append(newLine);
            sb.append("   Payload: ");
            sb.append(StreamUtils.copyToString(response.getContentInputStream(), UTF_8));
            sb.append(newLine);
            log.info(sb.toString());
        } catch (IOException e) {

        }
    }

}
