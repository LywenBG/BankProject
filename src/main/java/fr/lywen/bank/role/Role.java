package fr.lywen.bank.role;

import java.util.Arrays;

public enum Role
{
    ADMIN(10, "Administrateur"),
    MOD(20, "Modérateur"),
    BASIC(60, "Basic");

    private int id;
    private String name;

    Role(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Role getRole(int id)
    {
        return Arrays.stream(Role.values()).filter(role -> role.getId() == id).findAny().orElse(BASIC);
    }
}
