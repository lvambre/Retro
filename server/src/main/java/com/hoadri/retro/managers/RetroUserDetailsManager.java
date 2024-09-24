package com.hoadri.retro.managers;

import com.hoadri.retro.models.RetroUser;
import com.hoadri.retro.repositories.RetroUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

@Component
public class RetroUserDetailsManager implements UserDetailsManager {
    private final RetroUserRepository retroUserRepository;

    public RetroUserDetailsManager(RetroUserRepository retroUserRepository, PasswordEncoder passwordEncoder) {
        this.retroUserRepository = retroUserRepository;
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
