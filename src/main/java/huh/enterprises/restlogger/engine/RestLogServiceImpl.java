package huh.enterprises.restlogger.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * (1) REQ_IN
 * (2) REQ_OUT
 * (3) RESP_IN
 * (4) RESP_OUT
 */
public class RestLogServiceImpl implements RestLogService {
    private static final Logger logger = LoggerFactory.getLogger("REST");
    private static final Marker marker = MarkerFactory.getMarker("REST");

    @Override
    public void logIncomingRequest(HttpServletRequest request, Object body) {
        StringBuilder sb = new StringBuilder();
        Map<String, String> parameters = buildParametersMap(request);
        String newLine = System.getProperty("line.separator");
        sb.append(RestLogRequestType.REQ_IN);
        sb.append(newLine).append(newLine);
        sb.append("   Address: ").append(request.getRequestURL());
        sb.append(newLine);
        sb.append("   HttpMethod: ").append(request.getMethod());
        sb.append(newLine);
        sb.append("   Content-Type: ").append(request.getContentType());
        sb.append(newLine);
        sb.append("   Headers: ").append(buildHeadersMap(request));
        if (!parameters.isEmpty()) {
            sb.append(newLine);
            sb.append("parameters=[").append(parameters).append("] ");
        }
        try {
            if (body != null) {
                sb.append(newLine);
                sb.append("   Payload: ");
                sb.append(CustomObjectWriter.getObjectWriter().withDefaultPrettyPrinter().writeValueAsString(body));
            }
        } catch (IOException e) {

        }
        sb.append(newLine);
        logger.info(marker, sb.toString());
    }

    @Override
    public void logOutgoingRequest(HttpRequest request, String body) {
        StringBuilder sb = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        sb.append(RestLogRequestType.REQ_OUT);
        sb.append(newLine).append(newLine);
        sb.append("   Address: ").append(request.getURI());
        sb.append(newLine);
        sb.append("   HttpMethod: ").append(request.getMethod());
        sb.append(newLine);
        sb.append("   Content-Type: ").append(request.getHeaders().getContentType());
        sb.append(newLine);
        sb.append("   Headers: ").append(request.getHeaders());
        try {
            if (body != null) {
                sb.append(newLine);
                sb.append("   Payload: ");
                sb.append(CustomObjectWriter.getObjectWriter().writeValueAsString(body));
            }
        } catch (Exception e) {

        }
        sb.append(newLine);
        logger.info(marker, sb.toString());
    }

    @Override
    public void logIncomingResponse(URI url, HttpMethod method, ClientHttpResponse response, String body) {
        try {
            StringBuilder sb = new StringBuilder();
            String newLine = System.getProperty("line.separator");
            sb.append(RestLogRequestType.RESP_IN);
            sb.append(newLine).append(newLine);
            sb.append("   Address: ").append(url);
            sb.append(newLine);
            sb.append("   HttpMethod: ").append(method);
            sb.append(newLine);
            sb.append("   Content-Type: ").append(response.getHeaders().getContentType());
            sb.append(newLine);
            sb.append("   ResponseCode: ").append(response.getStatusCode().value());
            sb.append(newLine);
            sb.append("   Headers: ").append(response.getHeaders());
            if (body != null) {
                sb.append(newLine);
                sb.append("   Payload: ");
                sb.append(body);
            }
            sb.append(newLine);
            logger.info(marker, sb.toString());
        } catch (IOException e) {

        }
    }

    @Override
    public void logIncomingResponse(HttpRequest request, ClientHttpResponse response, String body) {
        try {
            StringBuilder sb = new StringBuilder();
            String newLine = System.getProperty("line.separator");
            sb.append(RestLogRequestType.RESP_IN);
            sb.append(newLine).append(newLine);
            sb.append("   Address: ").append(request.getURI());
            sb.append(newLine);
            sb.append("   HttpMethod: ").append(request.getMethod());
            sb.append(newLine);
            sb.append("   Content-Type: ").append(response.getHeaders().getContentType());
            sb.append(newLine);
            sb.append("   ResponseCode: ").append(response.getStatusCode().value());
            sb.append(newLine);
            sb.append("   Headers: ").append(response.getHeaders());
            if (body != null) {
                sb.append(newLine);
                sb.append("   Payload: ");
                sb.append(body);
            }
            sb.append(newLine);
            logger.info(marker, sb.toString());
        } catch (IOException e) {

        }
    }

    @Override
    public void logOutgoingResponse(HttpServletRequest request, HttpServletResponse response, Object body) {
        StringBuilder sb = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        sb.append(RestLogRequestType.RESP_OUT);
        sb.append(newLine).append(newLine);
        sb.append("   Address: ").append(request.getRequestURL());
        sb.append(newLine);
        sb.append("   HttpMethod: ").append(request.getMethod());
        sb.append(newLine);
        sb.append("   Content-Type: ").append(request.getContentType());
        sb.append(newLine);
        sb.append("   ResponseCode: ").append(response.getStatus());
        sb.append(newLine);
        sb.append("   Headers: ").append(buildHeadersMap(response));
        try {
            if (body != null) {
                sb.append(newLine);
                sb.append("   Payload: ");
                sb.append(CustomObjectWriter.getObjectWriter().withDefaultPrettyPrinter().writeValueAsString(body));
            }
        } catch (IOException e) {

        }
        sb.append(newLine);
        logger.info(marker, sb.toString());
    }

    private Map<String, String> buildParametersMap(HttpServletRequest httpServletRequest) {
        Map<String, String> resultMap = new HashMap<>();
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = httpServletRequest.getParameter(key);
            resultMap.put(key, value);
        }

        return resultMap;
    }

    private Map<String, String> buildHeadersMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }

    private Map<String, String> buildHeadersMap(HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();

        Collection<String> headerNames = response.getHeaderNames();
        for (String header : headerNames) {
            map.put(header, response.getHeader(header));
        }

        return map;
    }

}
