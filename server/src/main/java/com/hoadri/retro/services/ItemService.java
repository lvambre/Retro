package com.hoadri.retro.services;

import com.hoadri.retro.models.Item;
import com.hoadri.retro.models.enums.*;
import com.hoadri.retro.repositories.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * A service for managing items related logic
 * Provides methods to interact with the ItemRepository and perform operations on items
 * Acts as a bridge between the controller and the repository to ensure validations and rules before saving data
 */
@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    /**
     * Retrieves all items on Retro
     * @return A list of all Items on Retro
     */
    public List<Item> fetchAllItems() {
        return itemRepository.findAll();
    }

    /**
     * Retrieves all items for sale on Retro
     * @return A list of Item for sale on Retro
     */
    public List<Item> fetchItemsInSale() {
        return itemRepository.findByAvailable(true);
    }

    /**
     * Retrieves all items corresponding to a given category
     * @param category The Item's category
     * @return A list of Item of the given category
     */
    public List<Item> fetchItemsByCategory(Category category) {
        return itemRepository.findByCategory(category);
    }

    /**
     * Retrieves one specific item with its id
     * @param id The Item's id to retrieve
     * @return The Item found
     */
    public Item fetchOneItem(UUID id) {
        Optional<Item> item = itemRepository.findById(id);
        return item.orElse(null);
    }

    /**
     * Adds a new item to sell on Retro
     * @return The Item put on sale
     */
    public Item newItem(Item item) {
        itemRepository.save(item);
        return item;
    }

    /**
     * Changes item from available to not available (item sold)
     * @param id The Item's id to change to sold
     * @return True if change was successful
     */
    public boolean soldItem(UUID id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Item not found"));
        item.setAvailable(false);
        itemRepository.save(item);
        return true;
    }

    /**
     *  Updates an item in sale
     * @param id Item's attribute
     * @param name Item's attribute
     * @param description Item's attribute
     * @param sellerUsername Item's attribute
     * @param price Item's attribute
     * @param imagePaths Item's attribute
     * @return True if update was successful
     */
    public boolean updateItem(
            UUID id,
            String name,
            String description,
            String sellerUsername,
            double price,
            boolean available,
            boolean women,
            boolean men,
            Brand brand,
            Category category,
            Condition condition,
            List<Color> colors,
            Size size,
            List<String> imagePaths) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Item not found"));
        item.setName(name);
        item.setDescription(description);
        item.setSellerUsername(sellerUsername);
        item.setPrice(price);
        item.setAvailable(available);
        item.setWomen(women);
        item.setMen(men);
        item.setBrand(brand);
        item.setCategory(category);
        item.setCondition(condition);
        item.setColors(colors);
        item.setSize(size);
        item.setImagePaths(imagePaths);
        itemRepository.save(item);
        return true;
    }

    /**
     * Deletes one specific item with its id
     * @param id The Item's id to delete
     * @return True if item was deleted successfully
     */
    public boolean deleteItem(UUID id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Item not found"));
        itemRepository.delete(item);
        return true;
    }

    /**
     * Saves the image paths to the item
     * @param id The Item's id
     * @param imagePaths The images' paths
     */
    public void saveImagePaths(UUID id, List<String> imagePaths) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.getImagePaths().addAll(imagePaths);
            itemRepository.save(item);
        } else {
            throw new EntityNotFoundException("Item not found");
        }
    }
}