package org.example.model.storage;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import org.example.model.CustomerManager;

public class CustomerKeyDeserializer extends KeyDeserializer {
    @Override
    public Object deserializeKey(String key, DeserializationContext ctxt){
        return CustomerManager.getInstance().buildCustomer(key);
    }
}
