package com.timon.rhouse.security;

import com.timon.rhouse.domain.Landlord;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public class LandlordDetailsService  implements UserDetails {

    private Landlord landlord;

    public LandlordDetailsService(Landlord landlord) {
        this.landlord = landlord;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(landlord.getRoles());
        return Arrays.asList(authority);
    }

    @Override
    public String getPassword() {
        return landlord.getPassword();
    }

    @Override
    public String getUsername() {
        return landlord.getUsername();
    }

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
