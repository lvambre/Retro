package com.hoadri.retro.services;

import com.hoadri.retro.dtos.UserPublicDTO;
import com.hoadri.retro.managers.RetroUserDetails;
import com.hoadri.retro.managers.RetroUserDetailsManager;
import com.hoadri.retro.models.RetroUser;
import com.hoadri.retro.repositories.RetroUserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final PasswordEncoder passwordEncoder;
    private final RetroUserRepository retroUserRepository;
    @Qualifier("retroUserDetailsManager")
    private final UserDetailsManager userDetailsManager;
    private final HttpServletRequest httpServletRequest;

    public boolean signUp(
            final String username,
            final String password,
            final String email,
            final String phoneNumber,
            final String address,
            final String profilePicturePath,
            final String description) {
        if(userDetailsManager.userExists(username)) {
            return false;
        }
        RetroUser retroUser = new RetroUser();
        retroUser.setUsername(username);
        retroUser.setPassword(passwordEncoder.encode(password));
        retroUser.setEmail(email);
        retroUser.setPhoneNumber(phoneNumber);
        retroUser.setAddress(address);
        retroUser.setProfilePicturePath(profilePicturePath);
        retroUser.setDescription(description);
        retroUser.setItemsOrdered(new ArrayList<>());
        retroUser.setItemsSold(new ArrayList<>());
        final UserDetails user = new RetroUserDetails(retroUser);
        userDetailsManager.createUser(user);
        return true;
    }

    public boolean signIn(final String username, final String password) throws ServletException {
        final HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            return false;
        }
        httpServletRequest.login(username, password);
        httpServletRequest.getSession(true);
        return true;
    }

    public void signOut() throws ServletException {
        httpServletRequest.logout();
    }

    public boolean delete(String username) {
        if(!userDetailsManager.userExists(username))
            return false;
        userDetailsManager.deleteUser(username);
        return true;
    }

    public boolean changePassword(String oldPassword, String newPassword) {
        try {
            userDetailsManager.changePassword(oldPassword, newPassword);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    public List<UserPublicDTO> fetchAllUsers() {
        List<RetroUser> retroUsers = retroUserRepository.findAll();
        List<UserPublicDTO> userPublicDTOS = new ArrayList<>();
        for(RetroUser retroUser : retroUsers) {
            userPublicDTOS.add(new UserPublicDTO(
                    retroUser.getUsername(),
                    retroUser.getProfilePicturePath(),
                    retroUser.getDescription()));
        }
        return userPublicDTOS;
    }
}
