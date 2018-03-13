package com.example.aleperf.bakingapp;
import com.example.aleperf.bakingapp.model.Recipe;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import com.example.aleperf.bakingapp.database.IngredientConverter;

import java.util.List;


public class IngredientConverterTest {

    @Mock
    Recipe.Ingredient ingredient;

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
    public void verifyCorrectListSize(){
        List<Recipe.Ingredient> ingredients = IngredientConverter.convertJsonStringToIngredient(NUTELLA_PIE_INGREDIENTS_JSON);
        assertEquals(9, ingredients.size());
    }

    @Test
    public void nullStringReturnEmptyList(){
        List<Recipe.Ingredient> ingredients = IngredientConverter.convertJsonStringToIngredient(null);
        assertEquals(0, ingredients.size());
    }

    @Test
    public void emptyStringReturnEmptyList(){
        List<Recipe.Ingredient> ingredients = IngredientConverter.convertJsonStringToIngredient("");
        assertEquals(0, ingredients.size());
    }
}
