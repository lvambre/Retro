package com.hoadri.retro.controllers;

import com.hoadri.retro.models.Item;
import com.hoadri.retro.models.enums.Category;
import com.hoadri.retro.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A Rest controller for managing items related endpoints
 * Provides REST API methods for operations on Items
 * Handles HTTP requests related to items and interacts with the ItemService to perform necessary operations on the data
 */
@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    @Autowired
    private final ItemService itemService;

    /**
     * Retrieves all items on Retro
     *
     * @return A list of all the Item
     */
    @GetMapping(value = "items", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Item>> getItems() {
        try {
            return new ResponseEntity<>(itemService.fetchAllItems(), HttpStatus.OK);
        } catch (final Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves all items for sale on Retro
     *
     * @return A list of all the Item for sale
     */
    @GetMapping(value = "itemsForSale", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Item>> getItemsInSale() {
        try {
            return new ResponseEntity<>(itemService.fetchItemsInSale(), HttpStatus.OK);
        } catch (final Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves all items corresponding to a given category
     * @param categoryString The Item's category
     * @return A list of Item of the given category
     */
    @GetMapping(value = "sortBy/{categoryString}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Item>> getItemsByCategory(@PathVariable String categoryString) {
        try {
           Category category = Category.valueOf(categoryString.toUpperCase());
            return new ResponseEntity<>(itemService.fetchItemsByCategory(category), HttpStatus.OK);
        } catch (final Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
    }


    /**
     * Retrieves one specific item with its id
     *
     * @param id The Item's id to retrieve
     * @return The Item found
     */
    @GetMapping(value = "get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> getItem(@PathVariable UUID id) {
        try {
            Item item = itemService.fetchOneItem(id);
            return item != null
                    ? new ResponseEntity<>(item, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (final Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
    }

    /**
     * Adds a new item for sale on Retro
     *
     * @return The Item put for sale
     */
    @PostMapping(value = "newItem", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> newItem(@RequestBody Item item) {
        try {
            return new ResponseEntity<>(itemService.newItem(item), HttpStatus.OK);
        } catch (final Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
    }

    /**
     * Changes item from available to not available (item sold)
     *
     * @param id The Item's id to change to sold
     * @return ResponseEntity with a success message or an error message as a String
     */
    @PatchMapping(value = "soldItem/{id}")
    public ResponseEntity<String> soldItem(@PathVariable UUID id) {
        try {
            return itemService.soldItem(id)
                    ? new ResponseEntity<>("Item sold successfully", HttpStatus.OK)
                    : new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
        } catch (final Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
    }

    /**
     * Updates an item for sale
     *
     * @param updatedItem The updated Item
     * @return ResponseEntity with a success message or an error message as a String
     */
    @PatchMapping(value = "updateItem", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateItem(@RequestBody Item updatedItem) {
        try {
            return itemService.updateItem(
                    updatedItem.getId(),
                    updatedItem.getName(),
                    updatedItem.getDescription(),
                    updatedItem.getSeller(),
                    updatedItem.getPrice(),
                    updatedItem.isAvailable(),
                    updatedItem.isWomen(),
                    updatedItem.isMen(),
                    updatedItem.getBrand(),
                    updatedItem.getCategory(),
                    updatedItem.getCondition(),
                    updatedItem.getColors(),
                    updatedItem.getSize(),
                    updatedItem.getImagePaths())
                    ? new ResponseEntity<>("Item updated successfully", HttpStatus.OK)
                    : new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
        } catch (final Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
    }

    /**
     * Deletes one specific item with its id
     *
     * @param id The Item's id to delete
     * @return ResponseEntity with a success message or an error message as a String
     */
    @DeleteMapping(value = "deleteItem/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable UUID id) {
        try {
            return itemService.deleteItem(id)
                    ? new ResponseEntity<>("Item deleted successfully", HttpStatus.OK)
                    : new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
        } catch (final Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
    }

    /**
     * Uploads images to a specific item
     * @param id The Item's id
     * @param files The files to upload
     * @return ResponseEntity with a success message or an error message as a String
     */
    @PostMapping("uploadImages/{id}")
    public ResponseEntity<String> uploadImages(@PathVariable UUID id, @RequestParam("files") MultipartFile[] files) {
        try {
            if(files.length > 5) {
                return new ResponseEntity<>("Maximum 5 images allowed", HttpStatus.BAD_REQUEST);
            }

            Item item = itemService.fetchOneItem(id);
            if(item == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<String> imagePaths = new ArrayList<>();

            String uploadDir = "C:/retro/images/";
            File dir = new File(uploadDir);
            if(!dir.exists()) {
                dir.mkdirs();
            }

            for (MultipartFile file : files) {
                String fileName = id.toString() + "_" + file.getOriginalFilename();
                File dest = new File(uploadDir + fileName);
                file.transferTo(dest);
                imagePaths.add(dest.getPath());
            }

            itemService.saveImagePaths(id, imagePaths);

            return new ResponseEntity<>("Images uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

