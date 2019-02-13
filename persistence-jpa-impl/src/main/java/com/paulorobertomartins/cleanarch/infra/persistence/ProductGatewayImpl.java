package com.paulorobertomartins.cleanarch.infra.persistence;

import com.paulorobertomartins.cleanarch.core.entities.Product;
import com.paulorobertomartins.cleanarch.gateways.ProductGateway;
import com.paulorobertomartins.cleanarch.infra.persistence.entities.ProductEntity;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Optional;

@RequiredArgsConstructor
@Named
public class ProductGatewayImpl implements ProductGateway {

    private final EntityManager entityManager;

    @Override
    public Optional<Product> findById(final long productId) {

        final ProductEntity productEntity = entityManager.find(ProductEntity.class, productId);

        if (productEntity == null) {
            return Optional.empty();
        } else {
            return Optional.of(new Product(productEntity.getId(), productEntity.getDescription(), productEntity.getEan()));
        }
    }

    @Override
    public Optional<Product> findByEan(final String productEan) {

        final Query query = entityManager.createQuery("SELECT p FROM ProductEntity p WHERE p.ean = :ean");
        query.setParameter("ean", productEan);

        try {
            final ProductEntity productEntity = (ProductEntity) query.getSingleResult();
            return Optional.of(new Product(productEntity.getId(), productEntity.getDescription(), productEntity.getEan()));
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Product persist(final Product entity) {

        final ProductEntity productEntity = new ProductEntity();
        productEntity.setEan(entity.getEan());
        productEntity.setDescription(entity.getDescription());

        entityManager.persist(productEntity);
        entityManager.flush();

        return new Product(productEntity.getId(), productEntity.getDescription(), productEntity.getEan());
    }
}
