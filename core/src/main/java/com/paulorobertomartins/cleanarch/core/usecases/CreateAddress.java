package com.paulorobertomartins.cleanarch.core.usecases;

import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.CreateAddressRequest;
import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.CreateAddressResponse;

@FunctionalInterface
public interface CreateAddress extends UseCase<CreateAddressRequest, CreateAddressResponse> {
}