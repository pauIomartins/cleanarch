package com.paulorobertomartins.cleanarch.infra.persistence;

import com.paulorobertomartins.cleanarch.core.entities.Address;
import com.paulorobertomartins.cleanarch.core.entities.Product;
import com.paulorobertomartins.cleanarch.core.entities.Stock;
import com.paulorobertomartins.cleanarch.gateways.StockGateway;
import com.paulorobertomartins.cleanarch.infra.persistence.entities.AddressEntity;
import com.paulorobertomartins.cleanarch.infra.persistence.entities.ProductEntity;
import com.paulorobertomartins.cleanarch.infra.persistence.entities.StockEntity;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Named
public class StockGatewayImpl implements StockGateway {

    private final EntityManager entityManager;

    @Override
    public Stock create(final Stock stock) {

        final StockEntity stockEntity = createStockEntity(stock);

        entityManager.persist(stockEntity);
        entityManager.flush();

        return new Stock(stockEntity.getId(),
                stock.getAddress(),
                stock.getProduct(),
                stockEntity.getQuantity()
        );
    }

    @Override
    public Stock update(final Stock stock) {

        final StockEntity stockEntity = entityManager.find(StockEntity.class, stock.getId());

        stockEntity.setQuantity(stock.getQuantity());

        entityManager.merge(stockEntity);
        entityManager.flush();

        return stock;
    }

    @Override
    public Stock createOrUpdate(final Stock stock) {
        if (stock.getId() == null) {
            return this.create(stock);
        } else {
            return this.update(stock);
        }
    }

    @Override
    public List<Stock> findAll() {

        final TypedQuery<StockEntity> query = entityManager.createQuery(buildQuery(null, null), StockEntity.class);

        return extractResult(query);
    }

    @Override
    public List<Stock> findByAddress(final Address address) {
        final TypedQuery<StockEntity> query = entityManager.createQuery(buildQuery(address, null), StockEntity.class);
        query.setParameter("address_id", address.getId());

        return extractResult(query);
    }

    @Override
    public List<Stock> findByProduct(final Product product) {
        final TypedQuery<StockEntity> query = entityManager.createQuery(buildQuery(null, product), StockEntity.class);
        query.setParameter("product_id", product.getId());

        return extractResult(query);
    }

    @Override
    public Optional<Stock> findByAddressAndProduct(final Address address, final Product product) {

        final TypedQuery<StockEntity> query = entityManager.createQuery(buildQuery(address, product), StockEntity.class);
        query.setParameter("address_id", address.getId());
        query.setParameter("product_id", product.getId());

        try {
            StockEntity stockEntity = query.getSingleResult();
            return Optional.of(new Stock(stockEntity.getId(),
                    address,
                    product,
                    stockEntity.getQuantity()));
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public void remove(final Stock stock) {
        entityManager.remove(entityManager.find(StockEntity.class, stock.getId()));
        entityManager.flush();
    }

    private StockEntity createStockEntity(final Stock stock) {
        final StockEntity stockEntity = new StockEntity();
        stockEntity.setAddressEntity(entityManager.find(AddressEntity.class, stock.getAddress().getId()));
        stockEntity.setProductEntity(entityManager.find(ProductEntity.class, stock.getProduct().getId()));
        stockEntity.setQuantity(stock.getQuantity());
        return stockEntity;
    }

    private List<Stock> extractResult(final TypedQuery<StockEntity> query) {
        return query.getResultList()
                .stream()
                .map(entity -> new Stock(entity.getId(),
                        new Address(entity.getAddressEntity().getId(), entity.getAddressEntity().getLabel()),
                        new Product(entity.getProductEntity().getId(), entity.getProductEntity().getDescription(), entity.getProductEntity().getEan()),
                        entity.getQuantity()))
                .collect(Collectors.toList());
    }

    private String buildQuery(final Address address, final Product product) {

        final StringBuilder sb = new StringBuilder();
        sb.append("SELECT s ");
        sb.append("  FROM StockEntity s ");
        sb.append("  JOIN FETCH s.addressEntity a ");
        sb.append("  JOIN FETCH s.productEntity p ");

        String clause = " WHERE ";
        if (address != null) {
            sb.append(clause).append(" a.id = :address_id ");
            clause = " AND ";
        }
        if (product != null) {
            sb.append(clause).append(" p.id = :product_id ");
        }
        return sb.toString();
    }
}
