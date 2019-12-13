package com.example.cinematrix;

public class Movie {

    private int id;
    private String title;
    private String releaseDate;
    private String description;
    private float rating;
    private String image;
    private int posterImage;

    public Movie() {
    }

    public Movie(int id, String title, int posterImage) {
        this.id = id;
        this.title = title;
        this.posterImage = posterImage;
    }

    public Movie(int id, String title, String image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }

    public Movie(int id, String title, String releaseDate, String description, float rating, String image, int posterImage) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.description = description;
        this.rating = rating;
        this.image = image;
        this.posterImage = posterImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(int posterImage) {
        this.posterImage = posterImage;
    }
}
