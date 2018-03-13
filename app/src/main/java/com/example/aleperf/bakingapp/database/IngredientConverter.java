package com.example.aleperf.bakingapp.database;


import android.arch.persistence.room.TypeConverter;

import com.example.aleperf.bakingapp.model.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class IngredientConverter {

    static Gson gson = new Gson();

    @TypeConverter
    public static List<Recipe> convertJsonStringToRecipe(String jsonString) {
        if (jsonString == null) {
            return new ArrayList<Recipe>();
        }

        Type recipeListType = new TypeToken<List<Recipe>>() {
        }.getType();

        return gson.fromJson(jsonString, recipeListType);
    }

    @TypeConverter
    public static String convertRecipeListToJsonString(List<Recipe> recipes) {

        return gson.toJson(recipes);
    }

}
