package com.hoadri.retro.services;

import com.hoadri.retro.dtos.ItemDTO;
import com.hoadri.retro.models.Item;
import com.hoadri.retro.models.RetroUser;
import com.hoadri.retro.models.enums.*;
import com.hoadri.retro.repositories.ItemRepository;
import com.hoadri.retro.repositories.RetroUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * A service for managing items related logic
 * Provides methods to interact with the ItemRepository and perform operations on items
 * Acts as a bridge between the controller and the repository to ensure validations and rules before saving data
 */
@Service
@RequiredArgsConstructor
public class ItemService {
    @Autowired
    private final ItemRepository itemRepository;
    @Autowired
    private final RetroUserRepository retroUserRepository;

    /**
     * Retrieves all items on Retro
     * @return A list of all ItemDTOs on Retro
     */
    public List<ItemDTO> fetchAllItems() {
        return itemRepository.findAll().stream().map(
                item ->
                        new ItemDTO(
                                item.getId(),
                                item.getName(),
                                item.getDescription(),
                                item.getSeller().getUsername(),
                                item.getPrice(),
                                item.isAvailable(),
                                item.isWomen(),
                                item.isMen(),
                                item.getBrand(),
                                item.getCategory(),
                                item.getColors(),
                                item.getCondition(),
                                item.getSize(),
                                item.getImagePaths()
                        )).collect(Collectors.toList());
    }

    /**
     * Retrieves all items for sale on Retro
     * @return A list of ItemDTOs for sale on Retro
     */
    public List<ItemDTO> fetchItemsInSale() {
        return itemRepository.findByAvailable(true).stream().map(
                item ->
                        new ItemDTO(
                                item.getId(),
                                item.getName(),
                                item.getDescription(),
                                item.getSeller().getUsername(),
                                item.getPrice(),
                                item.isAvailable(),
                                item.isWomen(),
                                item.isMen(),
                                item.getBrand(),
                                item.getCategory(),
                                item.getColors(),
                                item.getCondition(),
                                item.getSize(),
                                item.getImagePaths()
                        )).collect(Collectors.toList());
    }

    /**
     * Retrieves all items corresponding to a given category
     * @param category The Item's category
     * @return A list of Item of the given category
     */
    public List<ItemDTO> fetchItemsByCategory(Category category) {
        return itemRepository.findByCategory(category).stream().map(
                item ->
                        new ItemDTO(
                                item.getId(),
                                item.getName(),
                                item.getDescription(),
                                item.getSeller().getUsername(),
                                item.getPrice(),
                                item.isAvailable(),
                                item.isWomen(),
                                item.isMen(),
                                item.getBrand(),
                                item.getCategory(),
                                item.getColors(),
                                item.getCondition(),
                                item.getSize(),
                                item.getImagePaths()
                        )).collect(Collectors.toList());
    }

    /**
     * Retrieves one specific item with its id
     * @param id The Item's id to retrieve
     * @return The Item found
     */
    public ItemDTO fetchOneItem(UUID id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        return optionalItem.map(item -> new ItemDTO(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getSeller().getUsername(),
                item.getPrice(),
                item.isAvailable(),
                item.isWomen(),
                item.isMen(),
                item.getBrand(),
                item.getCategory(),
                item.getColors(),
                item.getCondition(),
                item.getSize(),
                item.getImagePaths()
        )).orElse(null);
    }

    /**
     * Adds a new item to sell on Retro
     * @return The Item put on sale
     */
    public boolean newItem(ItemDTO itemDTO, String username) {
        RetroUser retroUser = retroUserRepository.findByUsername(username);
        if(retroUser == null) return false;
        Item item = new Item(
                itemDTO.name(),
                itemDTO.description(),
                retroUser,
                itemDTO.price(),
                true,
                itemDTO.women(),
                itemDTO.men(),
                itemDTO.brand(),
                itemDTO.category(),
                itemDTO.colors(),
                itemDTO.condition(),
                itemDTO.size(),
                itemDTO.imagePaths());
        itemRepository.save(item);
        return true;
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
     * Updates an item in sale
     * @param id Item's attribute
     * @param name Item's attribute
     * @param description Item's attribute
     * @param sellerUsername Item's attribute
     * @param price Item's attribute
     * @param available Item's attribute
     * @param women Item's attribute
     * @param men Item's attribute
     * @param brand Item's attribute
     * @param category Item's attribute
     * @param colors Item's attribute
     * @param condition Item's attribute
     * @param size Item's attribute
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
            List<Color> colors,
            Condition condition,
            Size size,
            List<String> imagePaths) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Item not found"));
        item.setName(name);
        item.setDescription(description);
        RetroUser retroUser = retroUserRepository.findByUsername(sellerUsername);
        item.setSeller(retroUser);
        item.setPrice(price);
        item.setAvailable(available);
        item.setWomen(women);
        item.setMen(men);
        item.setBrand(brand);
        item.setCategory(category);
        item.setColors(colors);
        item.setCondition(condition);
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