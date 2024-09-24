package com.hoadri.retro.models;

import com.hoadri.retro.models.enums.*;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entity representing an item
 * Maps to a database table and contains information about items for sale on Retro
 */
@Data
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    private RetroUser seller;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "available", nullable = false)
    private boolean available;

    // An item can be both sold for women and men
    @Column(name = "women", nullable = false)
    private boolean women;

    @Column(name = "men", nullable = false)
    private boolean men;

    @Enumerated(EnumType.STRING)
    @Column(name = "brand", nullable = false)
    private Brand brand;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Column(name = "colors", nullable = false)
    private List<Color> colors;

    @Enumerated(EnumType.STRING)
    @Column(name = "`condition`", nullable = false)
    private Condition condition;

    @Enumerated(EnumType.STRING)
    @Column(name = "size", nullable = false)
    private Size size;

    @ElementCollection
    private List<String> imagePaths = new ArrayList<>();

    public Item() {
    }

    /**
     * Copy constructor
     */
    public Item(UUID id,
                String name,
                String description,
                RetroUser seller,
                double price,
                boolean available,
                boolean women,
                boolean men,
                Brand brand,
                Category category,
                Condition condition,
                List<Color> colors,
                Size size,
                List<String> imagePaths
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.seller = seller;
        this.price = price;
        this.available = available;
        this.women = women;
        this.men = men;
        this.brand = brand;
        this.category = category;
        this.condition = condition;
        this.colors = colors;
        this.size = size;
        this.imagePaths = imagePaths;
    }
}
