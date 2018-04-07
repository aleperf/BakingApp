package com.example.aleperf.bakingapp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;


@Entity(tableName = "RECIPES_TABLE")
public class Recipe {

    @PrimaryKey
    public int id;
    public String name;
    public List<Ingredient> ingredients;
    public List<Step> steps;
    public int servings;
    public String image;

    public Recipe(int id, String name, List<Ingredient> ingredients, List<Step> steps, int servings, String image){
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;

    }




    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    public static class Ingredient{
        float quantity;
        String measure;
        String ingredient;

        public Ingredient(float quantity, String measure, String ingredient){
            this.quantity = quantity;
            this.measure = measure;
            this.ingredient = ingredient;
        }

        public float getQuantity() {
            return quantity;
        }

        public String getMeasure() {
            return measure;
        }

        public String getIngredient() {
            return ingredient;
        }
    }

    public static class Step {
        int id;
        String shortDescription;
        String description;
        String videoURL;
        String thumbnailURL;

        public int getId() {
            return id;
        }

        public String getShortDescription() {
            return shortDescription;
        }

        public String getDescription() {
            return description;
        }

        public String getVideoURL() {
            return videoURL;
        }

        public String getThumbnailURL() {
            return thumbnailURL;
        }
    }
}
