package huh.enterprises.restlogger.engine.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.util.Collection;
import java.util.Collections;
import java.util.StringJoiner;

public class HeaderFormatter {

    public static String formatResponseHeaders(ContentCachingResponseWrapper response) {
        StringJoiner sj = new StringJoiner(", ", "headers=[", "]");
        response.getHeaderNames().forEach(headerName ->
                response.getHeaders(headerName).forEach(headerValue ->
                        sj.add(String.format("%s: %s", headerName, headerValue))));
        return sj.toString();
    }

}
