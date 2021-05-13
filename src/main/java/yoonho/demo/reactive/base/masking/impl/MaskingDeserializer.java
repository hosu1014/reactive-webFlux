package yoonho.demo.reactive.base.masking.impl;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class MaskingDeserializer extends StdDeserializer<String> {
    private static final long serialVersionUID = 1L;

    public MaskingDeserializer() {
        super(String.class);
    }

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        // un-masking logic here.
        return p.getValueAsString();
    }
}

