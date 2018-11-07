package com.paulorobertomartins.cleanarch.gateways;

public interface EntityGateway<T> {

    T persist(final T entity);
}
