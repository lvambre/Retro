package com.hoadri.retro.managers;

import com.hoadri.retro.models.Item;
import com.hoadri.retro.models.RetroUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class RetroUserDetails implements UserDetails {
    private final RetroUser retroUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        if ("admin".equals(retroUser.getUsername()))
            roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return roles;
    }

    @Override
    public String getPassword() {
        return retroUser.getPassword();
    }

    @Override
    public String getUsername() {
        return retroUser.getUsername();
    }

    public String getEmail() { return retroUser.getEmail(); }

    public String getPhoneNumber() { return retroUser.getPhoneNumber(); }

    public String getAddress() { return retroUser.getAddress(); }

    public String getProfilePicturePath() { return retroUser.getProfilePicturePath(); }

    public String getDescription() { return retroUser.getDescription(); }

    public List<Item> getItemsOrdered() { return retroUser.getItemsOrdered(); }

    public List<Item> getItemsSold() { return retroUser.getItemsSold(); }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
