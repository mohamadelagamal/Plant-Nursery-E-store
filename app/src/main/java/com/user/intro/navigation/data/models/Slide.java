package com.user.intro.navigation.data.models;

import java.security.SecureRandom;

public class Slide {
    public int image;
    public String title;
    public String description;

    public Slide(int image, String title, String description) {
        this.image = image;
        this.title = title;
        this.description = description;
    }
}
