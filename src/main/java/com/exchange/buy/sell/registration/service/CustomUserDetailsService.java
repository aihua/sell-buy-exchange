package com.exchange.buy.sell.registration.service;

import com.exchange.buy.sell.market.domain.Privilege;
import com.exchange.buy.sell.market.domain.Role;
import com.exchange.buy.sell.market.domain.UserEntity;
import com.exchange.buy.sell.market.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by oleht on 13.07.2018
 */
@Service("userDetailsService")
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        try {
            UserEntity user = userRepository.findByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException(
                        "No user found with username: " + email);
            }

            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword().toLowerCase(),
                    user.isEnabled(),
                    accountNonExpired,
                    credentialsNonExpired,
                    accountNonLocked,
                    getAuthorities(user.getRoles()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private final Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private final List<String> getPrivileges(final Collection<Role> roles) {
        final List<String> privileges = new ArrayList<String>();
        final List<Privilege> collection = new ArrayList<Privilege>();
        for (final Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (final Privilege item : collection) {
            privileges.add(item.getName());
        }

        return privileges;
    }

    private final List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (final String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

}
