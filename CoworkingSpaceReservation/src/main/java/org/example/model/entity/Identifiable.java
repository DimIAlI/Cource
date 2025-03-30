package org.example.model.entity;

import java.io.Serializable;

public interface Identifiable<T extends Serializable> {
    void setId(T id);

    T getId();
}
