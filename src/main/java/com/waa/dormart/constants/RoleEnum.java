package com.waa.dormart.constants;

public enum RoleEnum {
    BUYER("ROLE_BUYER", "Buyer"),
    SELLER("ROLE_SELLER", "Seller"),
    ADMIN("ROLE_ADMIN", "Admin");

    private String role;
    private String friendlyName;

    RoleEnum(String role, String friendlyName) {
        this.role = role;
        this.friendlyName = friendlyName;
    }

    public String roleName() {
        return this.role;
    }

    public String roleFriendlyName() {
        return friendlyName;
    }

    public String toString() {
        return this.role;
    }
}
