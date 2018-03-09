package com.example.aleperf.bakingapp;

import java.util.List;



public class Recipe {

    public int id;
    public String name;
    public List<Ingredient> ingredients;
    public List<Step> steps;
    public int servings;
    public String image;

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

    public class Ingredient{
        float quantity;
        String measure;
        String ingredient;

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

    public class Step{
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
