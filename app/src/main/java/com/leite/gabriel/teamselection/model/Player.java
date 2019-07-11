package com.leite.gabriel.teamselection.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.text.TextUtils;

@Entity
public class Player {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int image;
    private String role;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return (TextUtils.isEmpty(role)) ? Role.SP.name() : role; }
    public void setRole(String role) { this.role = role; }

    public int getImage() { return image; }
    public void setImage(int image) { this.image= image; }
}
