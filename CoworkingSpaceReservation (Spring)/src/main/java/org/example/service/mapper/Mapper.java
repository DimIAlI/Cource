package org.example.service.mapper;

public interface Mapper<T, E> {

    T mapTo(E elem);
}
