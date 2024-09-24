package com.hoadri.retro.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class RetroUser {
    @Id
    @Column(name = "user", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "profile_picture_path", nullable = false)
    private String profilePicturePath;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany
    private List<Item> itemsOrdered;

    @OneToMany
    private List<Item> itemsSold;
}
