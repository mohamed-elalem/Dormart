package com.waa.dormart.constants;

public enum RoleEnum {
    BUYER("ROLE_BUYER"),
    SELLER("ROLE_SELLER"),
    ADMIN("ROLE_ADMIN");

    private String role;

    RoleEnum(String role) {
        this.role = role;
    }

    public String roleName() {
        return this.role;
    }

    public String toString() {
        return this.role;
    }
}
