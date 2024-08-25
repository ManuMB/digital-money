package com.manumb.digital_money_service.business.users;

import com.manumb.digital_money_service.business.accounts.Account;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(unique = true)
    private String dni;

    @Column(unique = true)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(unique = true)
    private String cvu;

    @Column(unique = true)
    private String alias;

    private String password;

    private Boolean enabled = false;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String fullName, String dni, String email, String phoneNumber, String cvu, String alias, String password) {
        this.id = id;
        this.fullName = fullName;
        this.dni = dni;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.cvu = cvu;
        this.alias = alias;
        this.password = password;
    }

    public User(Long id, String fullName, String dni, String email, String phoneNumber, String cvu, String alias, String password, Boolean enabled) {
        this.id = id;
        this.fullName = fullName;
        this.dni = dni;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.cvu = cvu;
        this.alias = alias;
        this.password = password;
        this.enabled = enabled;
    }

    public User(String fullName, String dni, String email, String phoneNumber, String cvu, String alias, String password) {
        this.fullName = fullName;
        this.dni = dni;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.cvu = cvu;
        this.alias = alias;
        this.password = password;
    }

    public User(String fullName, String dni, String email, String phoneNumber) {
        this.fullName = fullName;
        this.dni = dni;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCvu() {
        return cvu;
    }

    public void setCvu(String cvu) {
        this.cvu = cvu;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
