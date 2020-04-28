package com.waa.dormart.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class Role {

    public static class RoleBuilder {
        private Role role;

        public RoleBuilder() {
            this.role = new Role();
        }

        public RoleBuilder withName(String name) {
            this.role.setName(name);
            return this;
        }

        public Role build() {
            return this.role;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    private String name;

    @OneToMany(mappedBy = "role")
    private List<User> users;

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public static RoleBuilder create() {
        return new RoleBuilder();
    }
}
