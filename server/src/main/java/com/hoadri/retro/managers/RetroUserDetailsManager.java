package com.hoadri.retro.managers;

import com.hoadri.retro.models.RetroUser;
import com.hoadri.retro.repositories.RetroUserRepository;
import com.hoadri.retro.security.SecurityConfiguration;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Component
public class RetroUserDetailsManager implements UserDetailsManager {
    private final RetroUserRepository retroUserRepository;
    private final PasswordEncoder passwordEncoder;

    public RetroUserDetailsManager(RetroUserRepository retroUserRepository, PasswordEncoder passwordEncoder) {
        this.retroUserRepository = retroUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(UserDetails user) {
        if(user instanceof RetroUserDetails retroUserDetails) {
            RetroUser retroUser = new RetroUser();
            retroUser.setUsername(retroUserDetails.getUsername());
            retroUser.setPassword(retroUserDetails.getPassword());
            retroUser.setEmail(retroUserDetails.getEmail());
            retroUser.setPhoneNumber(retroUserDetails.getPhoneNumber());
            retroUser.setAddress(retroUserDetails.getAddress());
            retroUser.setProfilePicturePath(retroUserDetails.getProfilePicturePath());
            retroUser.setDescription(retroUserDetails.getDescription());
            retroUser.setItemsOrdered(retroUserDetails.getItemsOrdered());
            retroUser.setItemsSold(retroUserDetails.getItemsSold());
            retroUserRepository.save(retroUser);
        } else {
            throw new IllegalArgumentException("UserDetails must be an instance of RetroUserDetails");
        }
    }

    @Override
    public void updateUser(UserDetails user) {
        RetroUser retroUser = retroUserRepository.findByUsername(user.getUsername());
        retroUser.setUsername(user.getUsername());
        retroUser.setPassword(user.getPassword());
        retroUserRepository.save(retroUser);
    }

    @Override
    public void deleteUser(String username) {
        retroUserRepository.delete(retroUserRepository.findByUsername(username));
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        Authentication currentLoggedInUser = SecurityContextHolder.getContext().getAuthentication();

        if (currentLoggedInUser == null) {
            throw new IllegalStateException("No authenticated user found.");
        }

        String username = currentLoggedInUser.getName();
        RetroUser retroUser = retroUserRepository.findByUsername(username);

        if (!passwordEncoder.matches(oldPassword, retroUser.getPassword())) {
            throw new IllegalArgumentException("Wrong old password.");
        }

        retroUser.setPassword(passwordEncoder.encode(newPassword));

        retroUserRepository.save(retroUser);
    }

    @Override
    public boolean userExists(String username) {
        return retroUserRepository.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RetroUser retroUser = retroUserRepository.findByUsername(username);
        if(retroUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new RetroUserDetails(retroUser);
    }
}
