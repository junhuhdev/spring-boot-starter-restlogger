package huh.enterprises.restlogger.engine.filter;

import huh.enterprises.restlogger.engine.CustomObjectWriter;
import huh.enterprises.restlogger.engine.RestLogRequestType;
import huh.enterprises.restlogger.engine.utils.HeaderFormatter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
@Slf4j(topic = "RequestAndResponseLoggingFilter")
public class LogFormatter {
    private static final List<MediaType> VISIBLE_TYPES = Arrays.asList(
            MediaType.valueOf("text/*"),
            MediaType.APPLICATION_FORM_URLENCODED,
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_XML,
            MediaType.valueOf("application/*+json"),
            MediaType.valueOf("application/*+xml"),
            MediaType.MULTIPART_FORM_DATA);

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
