package com.paulorobertomartins.cleanarch.core.usecases.impl;

import com.paulorobertomartins.cleanarch.core.entities.Address;
import com.paulorobertomartins.cleanarch.core.usecases.GetStockByAddress;
import com.paulorobertomartins.cleanarch.core.usecases.exceptions.AddressLabelEmptyException;
import com.paulorobertomartins.cleanarch.core.usecases.exceptions.InvalidAddressException;
import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.GetStockByAddressRequest;
import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.GetStockResponse;
import com.paulorobertomartins.cleanarch.gateways.AddressGateway;
import com.paulorobertomartins.cleanarch.gateways.StockGateway;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Named
@Transactional
@RequiredArgsConstructor
public class GetStockByAddressImpl implements GetStockByAddress {

    private final AddressGateway addressGateway;
    private final StockGateway stockGateway;

    @Override
    public void execute(GetStockByAddressRequest request, Consumer<List<GetStockResponse>> consumer) {

        if (request.getAddressLabel() == null || request.getAddressLabel().trim().isEmpty()) {
            throw new AddressLabelEmptyException();
        }
        final Address address = addressGateway.findByLabel(request.getAddressLabel())
                .orElseThrow(InvalidAddressException::new);

        consumer.accept(stockGateway.findByAddress(address).stream().map(s ->
                GetStockResponse.builder()
                        .id(s.getId())
                        .addressLabel(s.getAddress().getLabel())
                        .productEan(s.getProduct().getEan())
                        .quantity(s.getQuantity())
                        .build()
        ).collect(Collectors.toList()));
    }
}
