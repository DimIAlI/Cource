package org.example.model.storage;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.example.model.Customer;

import java.io.IOException;

public class CustomerKeySerializer extends JsonSerializer<Customer> {
    @Override
    public void serialize(Customer value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getLogin());
    }
}
