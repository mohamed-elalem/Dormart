package com.waa.dormart.models;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
public class User implements UserDetails {

    public static class UserBuilder implements ModelBuilder<User> {
        private User user;

        private UserBuilder() {
            this(new User());
        }

        private UserBuilder(User user) {
            this.user = user;
            this.user.setEnabled(false);
        }

        public UserBuilder withName(String name) {
            user.setName(name);
            return this;
        }

        public UserBuilder withEmail(String email) {
            user.setEmail(email);
            return this;
        }

        public UserBuilder withPassword(String password) {
            user.setPassword(password);
            return this;
        }

        public UserBuilder withRole(Role role) {
            user.setRole(role);
            return this;
        }

        public UserBuilder withEnabled(Boolean enabled) {
            user.setEnabled(enabled);
            return this;
        }

        public UserBuilder withActive(Boolean active) {
            user.setActive(active);
            return this;
        }

        public User build() {
            return user;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "{model.notBlank.error}")
    @Size(min = 3, max = 60, message = "{model.size.error}")
    private String name;

    @NotBlank(message = "{model.notBlank.error}")
    @Email(message = "{model.email.error}")
    private String email;

    @NotBlank(message = "{model.size.error}")
    private String password;

    @Transient
    private String passwordConfirmation;

    @Size(min = 6, max = 80, message = "{model.size.error}")
    @Transient
    private String rawPassword;

    @ManyToOne
    private Role role;

    @ColumnDefault("false")
    private Boolean enabled;

    @NotNull
    private Boolean active;

    @OneToMany
    @Fetch(FetchMode.SUBSELECT)
    private List<Product> products;

    @ManyToMany(mappedBy = "following", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<User> followers = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="following_follower",
            joinColumns={@JoinColumn(name="follower_id")},
            inverseJoinColumns={@JoinColumn(name="following_id")})
    private Set<User> following = new HashSet<>();

    @OneToMany(mappedBy = "reviewer")
    private List<Review> reviews;

    public User() {
        this.enabled = false;
        this.active = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(getRole().getName()));
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.email;
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
        return enabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getRawPassword() {
        return rawPassword;
    }

    public void setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Set<User> getFollowing() {
        return following;
    }

    public void setFollowing(Set<User> following) {
        this.following = following;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

    public Boolean isFollowing(User user) {
        return getFollowing().contains(user);
    }

    public Boolean isFollowedBy(User user) {
        return getFollowers().contains(user);
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId().equals(user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public static UserBuilder create() {
        return new UserBuilder();
    }


}
