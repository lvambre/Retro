package com.hoadri.retro.repositories;

import com.hoadri.retro.models.Item;
import com.hoadri.retro.models.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * A repository interface for managing Item entities
 * Extends JpaRepository and provides methods for performing operations on Item objects in the database
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {
    List<Item> findByAvailable(boolean available);
    List<Item> findByCategory(Category category);
}
