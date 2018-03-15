package com.example.aleperf.bakingapp.RecipeTypeConverterTests;

import com.example.aleperf.bakingapp.model.Recipe.Step;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import com.example.aleperf.bakingapp.database.RecipeTypeConverter;

import java.util.List;

public class StepsTypeConverterTest {

    private static final int ID_1 = 0;
    private static final int ID_5 = 4;
    private static final int ID_6 = 5;
    private static  final int ID_7 = 6;

    private static final String SHORT_DESC_1 = "Recipe Introduction";
    private static final String SHORT_DESC_2 = "Starting prep";
    private static final String SHORT_DESC_3 = "Prep the cookie crust.";
    private static final String SHORT_DESC_4 = "Press the crust into baking form.";

    private static final String DESCRIPTION_1 ="Recipe Introduction";
    private static final String DESCRIPTION_4 = "3. Press the cookie crumb mixture into the prepared " +
            "pie pan and bake for 12 minutes. Let crust cool to room temperature.";

    private static final String VIDEO_URL_1 = "";


    private final static String NUTELLA_STEPS = "[\n" +
            "      {id\": 0,\n" +
            "        \"shortDescription\": \"Recipe Introduction\",\n" +
            "        \"description\": \"Recipe Introduction\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 1,\n" +
            "        \"shortDescription\": \"Starting prep\",\n" +
            "        \"description\": \"1. Preheat the oven to 350\\u00b0F. Butter a 9\\\" deep dish pie pan.\",\n" +
            "        \"videoURL\": \"\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 2,\n" +
            "        \"shortDescription\": \"Prep the cookie crust.\",\n" +
            "        \"description\": \"2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 3,\n" +
            "        \"shortDescription\": \"Press the crust into baking form.\",\n" +
            "        \"description\": \"3. Press the cookie crumb mixture into the prepared pie pan and bake for 12 minutes. Let crust cool to room temperature.\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9cb_4-press-crumbs-in-pie-plate-creampie/4-press-crumbs-in-pie-plate-creampie.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 4,\n" +
            "        \"shortDescription\": \"Start filling prep\",\n" +
            "        \"description\": \"4. Beat together the nutella, mascarpone, 1 teaspoon of salt, and 1 tablespoon of vanilla on medium speed in a stand mixer or high speed with a hand mixer until fluffy.\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd97a_1-mix-marscapone-nutella-creampie/1-mix-marscapone-nutella-creampie.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 5,\n" +
            "        \"shortDescription\": \"Finish filling prep\",\n" +
            "        \"description\": \"5. Beat the cream cheese and 50 grams (1/4 cup) of sugar on medium speed in a stand mixer or high speed with a hand mixer for 3 minutes. Decrease the speed to medium-low and gradually add in the cold cream. Add in 2 teaspoons of vanilla and beat until stiff peaks form.\",\n" +
            "        \"videoURL\": \"\",\n" +
            "        \"thumbnailURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda20_7-add-cream-mix-creampie/7-add-cream-mix-creampie.mp4\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 6,\n" +
            "        \"shortDescription\": \"Finishing Steps\",\n" +
            "        \"description\": \"6. Pour the filling into the prepared crust and smooth the top. Spread the whipped cream over the filling. Refrigerate the pie for at least 2 hours. Then it's ready to serve!\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda45_9-add-mixed-nutella-to-crust-creampie/9-add-mixed-nutella-to-crust-creampie.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      }\n" +
            "    ]";



    @Test
    public void verifyCorrectListSize(){
        List<Step> steps = RecipeTypeConverter.convertJsonStringToSteps(NUTELLA_STEPS);
        assertEquals(7, steps.size());
        String json = RecipeTypeConverter.convertStepListToJson(steps);
    }

    @Test
    public void nullStringReturnEmptyList() {
        List<Step> ingredients = RecipeTypeConverter.convertJsonStringToSteps(null);
        assertEquals(0, ingredients.size());
    }

    @Test
    public void emptyStringReturnEmptyList() {
        List<Step> steps = RecipeTypeConverter.convertJsonStringToSteps("");
        assertEquals(0, steps.size());
    }

    @Test
    public void stepIdIsCorrect(){
        List<Step> steps = RecipeTypeConverter.convertJsonStringToSteps(NUTELLA_STEPS);
        assertEquals(ID_1, steps.get(0).getId());
        assertEquals(ID_5, steps.get(4).getId());
        assertEquals(ID_6, steps.get(5).getId());
        assertEquals(ID_7, steps.get(6).getId());

    }

    @Test
    public void stepShortDescriptionIsCorrect(){
        List<Step> steps = RecipeTypeConverter.convertJsonStringToSteps(NUTELLA_STEPS);
        assertEquals(SHORT_DESC_1, steps.get(0).getShortDescription());
        assertEquals(SHORT_DESC_2, steps.get(1).getShortDescription());
        assertEquals(SHORT_DESC_3, steps.get(2).getShortDescription());
        assertEquals(SHORT_DESC_4, steps.get(3).getShortDescription());
    }

    @Test
    public void stepDescriptionIsCorrect(){
        List<Step> steps = RecipeTypeConverter.convertJsonStringToSteps(NUTELLA_STEPS);
        assertEquals(DESCRIPTION_1, steps.get(0).getDescription());
        assertEquals(DESCRIPTION_4, steps.get(3).getDescription());
    }



}
