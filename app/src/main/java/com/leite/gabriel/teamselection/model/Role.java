package com.leite.gabriel.teamselection.model;

public enum Role {
    GO("Goleiro"),
    ZA("Zagueiro"),
    ME("Meio"),
    AT("Atacante"),
    SP("Sem Posição");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return role.equals(otherName);
    }

    public String toValue() {
        return this.name();
    }

    @Override public String toString() {
        return role;
    }
}
