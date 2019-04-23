package com.leite.gabriel.teamselection;

import java.io.Serializable;

public class Model implements Serializable {

    private String name;
    private int image_drawable;

    public int getImage_drawable() {
        return image_drawable;
    }

    public void setImage_drawable(int image_drawable) {
        this.image_drawable = image_drawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
