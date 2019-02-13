package com.paulorobertomartins.cleanarch.infra.persistence.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "movement")
public class MovementEntity {

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "address_id_from", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AddressEntity addressEntityFrom;

    @JoinColumn(name = "address_id_to", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AddressEntity addressEntityTo;

    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ProductEntity productEntity;

    @Basic(optional = false)
    @Column(name = "quantity")
    private BigDecimal quantity;

    @Basic(optional = false)
    @Column(name = "type")
    private String type;
}
