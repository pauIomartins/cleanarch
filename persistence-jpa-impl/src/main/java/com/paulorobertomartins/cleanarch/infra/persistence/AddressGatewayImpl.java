package com.paulorobertomartins.cleanarch.infra.persistence;

import com.paulorobertomartins.cleanarch.core.entities.Address;
import com.paulorobertomartins.cleanarch.gateways.AddressGateway;
import com.paulorobertomartins.cleanarch.infra.persistence.entities.AddressEntity;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Optional;

@RequiredArgsConstructor
@Named
public class AddressGatewayImpl implements AddressGateway {

    private final EntityManager entityManager;

    @Override
    public Optional<Address> findById(final long addressId) {

        final AddressEntity addressEntity = entityManager.find(AddressEntity.class, addressId);

        if (addressEntity == null) {
            return Optional.empty();
        } else {
            return Optional.of(new Address(addressEntity.getId(), addressEntity.getLabel()));
        }
    }

    @Override
    public Optional<Address> findByLabel(final String addressLabel) {

        final Query query = entityManager.createQuery("SELECT a FROM AddressEntity a WHERE a.label = :label");
        query.setParameter("label", addressLabel);

        try {
            final AddressEntity addressEntity = (AddressEntity) query.getSingleResult();
            return Optional.of(new Address(addressEntity.getId(), addressEntity.getLabel()));
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Address persist(final Address entity) {

        final AddressEntity addressEntity = new AddressEntity();
        addressEntity.setLabel(entity.getLabel());

        entityManager.persist(addressEntity);
        entityManager.flush();

        return new Address(addressEntity.getId(), addressEntity.getLabel());
    }
}
