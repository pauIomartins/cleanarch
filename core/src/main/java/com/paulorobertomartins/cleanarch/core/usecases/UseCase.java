package com.paulorobertomartins.cleanarch.core.usecases;

import java.util.function.Consumer;

@FunctionalInterface
public interface UseCase<T, P> {

    void execute(final T request, Consumer<P> consumer);
}