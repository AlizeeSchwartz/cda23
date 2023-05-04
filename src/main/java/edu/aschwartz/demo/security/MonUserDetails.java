package edu.aschwartz.demo.security;

import edu.aschwartz.demo.model.Utilisateur;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MonUserDetails implements UserDetails {
    private Utilisateur utilisateur;
    public MonUserDetails(Utilisateur utilisateur){
        this.utilisateur = utilisateur;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       /* List<GrantedAuthority> roles = new ArrayList<>();
        if(utilisateur.isAdmin()){
            roles.add(new SimpleGrantedAuthority("ROLE_ADMINISTRATEUR"));
        } else {
            roles.add(new SimpleGrantedAuthority("ROLE_UTILISATEUR"));
        }
        return roles;*/
        return List.of(new SimpleGrantedAuthority(utilisateur.getRole().getName()));
    }

    @Override
    public String getPassword() {
        return utilisateur.getPassword();
    }

    @Override
    public String getUsername() {
        return utilisateur.getEmail();
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
