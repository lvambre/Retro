package com.hoadri.retro.models;

import com.hoadri.retro.models.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String buyerUsername;

    @OneToOne
    private Item item;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
