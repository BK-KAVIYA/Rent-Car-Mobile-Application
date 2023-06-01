package com.example.sourthenlankacarrental;

public class DynamicItemList {
    private String title;
    private String description;
    private int review;
    private String image;

    public DynamicItemList() {
    }

    public DynamicItemList(String title, String description, int review, String image) {
        this.title = title;
        this.description = description;
        this.review = review;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
