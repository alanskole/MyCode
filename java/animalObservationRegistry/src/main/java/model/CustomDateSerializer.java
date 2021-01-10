package model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomDateSerializer extends StdSerializer<LocalDateTime> {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public CustomDateSerializer() {
        this(null);
    }

    public CustomDateSerializer(Class t) {
        super(t);
    }

    @Override
    public void serialize (LocalDateTime value, JsonGenerator gen, SerializerProvider arg2)
        throws IOException, JsonProcessingException {
        gen.writeString(formatter.format(value));
    }
}
