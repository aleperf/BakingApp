package com.example.aleperf.bakingapp;

import java.util.List;

/**
 * Created by Tundra on 09/03/2018.
 */

public class Recipe {

    public int id;
    public String name;
    List<Ingredient> ingredients;
    List<Step> steps;
    int servings;
    String image;

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
        int quantity;
        String measure;
        String ingredient;

        public int getQuantity() {
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
        String videoUrl;
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

        public String getVideoUrl() {
            return videoUrl;
        }

        public String getThumbnailURL() {
            return thumbnailURL;
        }
    }
}
