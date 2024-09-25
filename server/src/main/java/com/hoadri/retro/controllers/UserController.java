package com.hoadri.retro.controllers;

import com.hoadri.retro.dtos.PasswordChangeRequest;
import com.hoadri.retro.dtos.UserCredentialsDTO;
import com.hoadri.retro.dtos.UserLogInDTO;
import com.hoadri.retro.dtos.UserPublicDTO;
import com.hoadri.retro.services.UserService;
import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

/**
 * A Rest controller for managing user authentication related endpoints
 * Provides REST API methods for operations on (Retro) Users
 * Handles HTTP requests related to users and interacts with the UserService to perform necessary operations on the users
 */
@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * Signs up a new Retro user
     * @param user The new user
     */
    @PostMapping(value = "signUp", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void signUp(@RequestBody final UserCredentialsDTO user) {
        if(!userService.signUp(
                user.username(),
                user.password(),
                user.email(),
                user.phoneNumber(),
                user.address(),
                user.profilePicturePath(),
                user.description())
        )
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
    }

    /**
     * Signs in a Retro user
     * @param user The user to sign in
     */
    @PostMapping(value = "signIn", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void signIn(@RequestBody final UserLogInDTO user) {
        try {
            if (!userService.signIn(user.username(), user.password()))
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Already signed in");
        }
        catch (final ServletException ex) {
            if (ex.getCause() instanceof BadCredentialsException)
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong username or password");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
    }

    /**
     * Signs out the logged-in user
     */
    @PostMapping(value = "signOut")
    public void signOut() {
        try {
            userService.signOut();
        }
        catch (final ServletException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
    }

    /**
     * Deletes a Retro user
     * @param username The Retro user's username
     */
    @DeleteMapping(value = "delete/{username}")
    public void delete(@PathVariable String username) {
        if (!userService.delete(username))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist");
    }

    /**
     * Allows the logged-in user to change their password
     * @param passwordChangeRequest An entity with both old and new password
     * @return ResponseEntity with a success message or an error message as a String
     */
    @PostMapping(value = "changePassword", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest) {
        try {
            return userService.changePassword(passwordChangeRequest.oldPassword(), passwordChangeRequest.newPassword())
                    ? new ResponseEntity<>("Password changed successfully", HttpStatus.OK)
                    : new ResponseEntity<>("Password change failed", HttpStatus.UNAUTHORIZED);
        } catch (final Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves all Retro users
     * @return A list of all the users
     */
    @GetMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserPublicDTO>> getUsers() {
        try {
            return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
        } catch (final Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
    }
}

