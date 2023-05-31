package com.example.sourthenlankacarrental;

import android.widget.ImageView;

public class DynamicRVModel {
    String name,description;
    int id, is_deleted, vehicele_id,rating;
    String imageUrl;
    ImageView imageView;


    public DynamicRVModel() {
    }

    public DynamicRVModel(String description, int id, int is_deleted, int vehicele_id, int rating) {
        this.description = description;
        this.id = id;
        this.is_deleted = is_deleted;
        this.vehicele_id = vehicele_id;
        this.rating = rating;
    }

    public DynamicRVModel(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(int is_deleted) {
        this.is_deleted = is_deleted;
    }

    public int getVehicele_id() {
        return vehicele_id;
    }

    public void setVehicele_id(int vehicele_id) {
        this.vehicele_id = vehicele_id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public String toString() {
        return "DynamicRVModel{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", is_deleted=" + is_deleted +
                ", vehicele_id=" + vehicele_id +
                ", rating=" + rating +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageView=" + imageView +
                '}';
    }
}
