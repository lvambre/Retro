package com.hoadri.retro.repositories;

import com.hoadri.retro.models.RetroUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetroUserRepository extends JpaRepository<RetroUser, String> {
    RetroUser findByUsername(String username);
    Boolean existsByUsername(String username);
}
