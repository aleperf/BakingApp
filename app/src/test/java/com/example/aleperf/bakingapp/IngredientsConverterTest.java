package com.example.aleperf.bakingapp;



import com.example.aleperf.bakingapp.model.Recipe;
import com.example.aleperf.bakingapp.model.Recipe.Ingredient;


import static org.junit.Assert.assertEquals;
import org.junit.Test;


import com.example.aleperf.bakingapp.database.RecipeTypeConverter;

import java.util.ArrayList;
import java.util.List;


public class IngredientsConverterTest {

    private final static float QUANTITY_1 = 2f;
    private final static float QUANTITY_2 = 6f;
    private final static float QUANTITY_3 = 0.5f;
    private final static float QUANTITY_4 = 1.5f;
    private final static float DELTA = 0.0001f;

    private final static String MEASURE_1 = "CUP";
    private final static String MEASURE_2 = "TBLSP";
    private final static String MEASURE_5 = "TBLSP";
    private final static String MEASURE_6 = "K";
    private final static String MEASURE_9 = "OZ";

    private final static String INGREDIENT_1 = "Graham Cracker crumbs";
    private final static String INGREDIENT_2 = "unsalted butter, melted";
    private final static String INGREDIENT_7 = "Mascapone Cheese(room temperature)";
    private final static String INGREDIENT_8 = "heavy cream(cold)";

    private final static Ingredient INGREDIENT_A = new Ingredient(2f, "CUP","Graham Cracker crumbs" );
    private final static Ingredient INGREDIENT_B = new Ingredient(6f, "TBLSP", "unsalted butter, melted");
    private final static Ingredient INGREDIENT_C = new Ingredient(1.5f, "TSP","granulated sugar");

    private final String JSON_MOCK = "[{\"quantity\":2.0,\"measure\":\"CUP\",\"ingredient\":\"Graham Cracker crumbs\"},{\"quantity\":6.0,\"measure\":\"TBLSP\",\"ingredient\":\"unsalted butter, melted\"},{\"quantity\":1.5,\"measure\":\"TSP\",\"ingredient\":\"granulated sugar\"}]";





    private final static String NUTELLA_PIE_INGREDIENTS_JSON = "[\n" +
            "      {\n" +
            "        \"quantity\": 2,\n" +
            "        \"measure\": \"CUP\",\n" +
            "        \"ingredient\": \"Graham Cracker crumbs\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 6,\n" +
            "        \"measure\": \"TBLSP\",\n" +
            "        \"ingredient\": \"unsalted butter, melted\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 0.5,\n" +
            "        \"measure\": \"CUP\",\n" +
            "        \"ingredient\": \"granulated sugar\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 1.5,\n" +
            "        \"measure\": \"TSP\",\n" +
            "        \"ingredient\": \"salt\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 5,\n" +
            "        \"measure\": \"TBLSP\",\n" +
            "        \"ingredient\": \"vanilla\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 1,\n" +
            "        \"measure\": \"K\",\n" +
            "        \"ingredient\": \"Nutella or other chocolate-hazelnut spread\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 500,\n" +
            "        \"measure\": \"G\",\n" +
            "        \"ingredient\": \"Mascapone Cheese(room temperature)\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 1,\n" +
            "        \"measure\": \"CUP\",\n" +
            "        \"ingredient\": \"heavy cream(cold)\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 4,\n" +
            "        \"measure\": \"OZ\",\n" +
            "        \"ingredient\": \"cream cheese(softened)\"\n" +
            "      }\n" +
            "    ]";

    @Test
    public void verifyCorrectListSize() {
        List<Recipe.Ingredient> ingredients = RecipeTypeConverter.convertJsonStringToIngredients(NUTELLA_PIE_INGREDIENTS_JSON);
        assertEquals(9, ingredients.size());
    }

    @Test
    public void nullStringReturnEmptyList() {
        List<Recipe.Ingredient> ingredients = RecipeTypeConverter.convertJsonStringToIngredients(null);
        assertEquals(0, ingredients.size());
    }

    @Test
    public void emptyStringReturnEmptyList() {
        List<Recipe.Ingredient> ingredients = RecipeTypeConverter.convertJsonStringToIngredients("");
        assertEquals(0, ingredients.size());
    }

    @Test
    public void verifyCorrectIngredientQuantity() {
        List<Recipe.Ingredient> ingredients = RecipeTypeConverter.convertJsonStringToIngredients(NUTELLA_PIE_INGREDIENTS_JSON);
        assertEquals(QUANTITY_1, ingredients.get(0).getQuantity(), DELTA);
        assertEquals(QUANTITY_2, ingredients.get(1).getQuantity(), DELTA);
        assertEquals(QUANTITY_3, ingredients.get(2).getQuantity(), DELTA);
        assertEquals(QUANTITY_4, ingredients.get(3).getQuantity(), DELTA);

    }

    @Test
    public void verifyCorrectIngredientMeasure(){
        List<Recipe.Ingredient> ingredients = RecipeTypeConverter.convertJsonStringToIngredients(NUTELLA_PIE_INGREDIENTS_JSON);
        assertEquals(MEASURE_1, ingredients.get(0).getMeasure());
        assertEquals(MEASURE_2, ingredients.get(1).getMeasure());
        assertEquals(MEASURE_5, ingredients.get(4).getMeasure());
        assertEquals(MEASURE_6, ingredients.get(5).getMeasure());
        assertEquals(MEASURE_9, ingredients.get(8).getMeasure());
    }

    @Test
    public void verifyCorrectIngredientField(){
        List<Recipe.Ingredient> ingredients = RecipeTypeConverter.convertJsonStringToIngredients(NUTELLA_PIE_INGREDIENTS_JSON);
        assertEquals(INGREDIENT_1, ingredients.get(0).getIngredient());
        assertEquals(INGREDIENT_2, ingredients.get(1).getIngredient());
        assertEquals(INGREDIENT_7, ingredients.get(6).getIngredient());
        assertEquals(INGREDIENT_8, ingredients.get(7).getIngredient());

    }

    @Test
    public void verifyCorrectConversionOfListToJson(){
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(INGREDIENT_A);
        ingredients.add(INGREDIENT_B);
        ingredients.add(INGREDIENT_C);
        String json = RecipeTypeConverter.convertIngredientListToJson(ingredients);
        assertEquals(JSON_MOCK, json);


    }

}
