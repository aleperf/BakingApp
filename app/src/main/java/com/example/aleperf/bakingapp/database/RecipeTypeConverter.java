package com.example.aleperf.bakingapp.database;


import android.arch.persistence.room.TypeConverter;

import com.example.aleperf.bakingapp.model.Recipe;
import com.example.aleperf.bakingapp.model.Recipe.Ingredient;
import com.example.aleperf.bakingapp.model.Recipe.Step;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class RecipeTypeConverter {

    static Gson gson = new Gson();

    @TypeConverter
    public static List<Ingredient> convertJsonStringToIngredients(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            return new ArrayList<>();
        }

        Type recipeListType = new TypeToken<List<Ingredient>>() {
        }.getType();

        return gson.fromJson(jsonString, recipeListType);
    }

    @TypeConverter
    public static String convertIngredientListToJson(List<Ingredient> ingredients) {

        return gson.toJson(ingredients);
    }

    @TypeConverter
    public static List<Step> convertJsonStringToSteps(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            return new ArrayList<>();
        }

        Type recipeListType = new TypeToken<List<Ingredient>>() {
        }.getType();

        return gson.fromJson(jsonString, recipeListType);
    }

    @TypeConverter
    String convertStepListToJson(List<Step> steps) {
        return gson.toJson(steps);
    }

}

