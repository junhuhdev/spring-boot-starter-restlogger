package huh.enterprises.restlogger.engine;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class CustomObjectWriter {

    private CustomObjectWriter() {

    }

    public static ObjectWriter getObjectWriter() {
        ObjectMapper mapper = new ObjectMapper();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        mapper.setDateFormat(df);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return mapper.writer().with(new MinimalPrettyPrinter());
    }

}
