package com.example.aleperf.bakingapp.utils;

import android.content.Context;
import android.net.Uri;

import com.example.aleperf.bakingapp.R;
import com.example.aleperf.bakingapp.model.Recipe;
import com.example.aleperf.bakingapp.model.Recipe.Step;

public class RecipeUtilities {

    private static  final String CUP = "CUP";
    private static final String TABLE_SPOON = "TBLSP";
    private static final String TEA_SPOON = "TSP";
    private static final String KILOGRAM = "K";
    private static final String GRAM = "G";
    private static final String OZ = "OZ";
    private static final String UNIT = "UNIT";
    private final static int[] recipeImagesDefault = {R.drawable.intro_pie, R.drawable.intro_brownies,
            R.drawable.intro_yellow_cake, R.drawable.intro_cheese_cake};

    private final static String MP4_EXTENSION = "mp4";

    private static boolean isValidUrlVideo(String videoUrl) {
        if (videoUrl.length() == 0) {
            return false;
        }
        if (videoUrl.endsWith(MP4_EXTENSION)) {
            return true;
        }
        return false;
    }


    public static Uri getVideoUri(Step step) {
        String urlString;
        if (isValidUrlVideo(step.getVideoURL())) {
            urlString = step.getVideoURL();
        } else if (isValidUrlVideo(step.getThumbnailURL())) {
            urlString = step.getThumbnailURL();
        } else {
            return null;
        }

        return Uri.parse(urlString);

    }

    /**
     * Return a default drawable id depending on the position of an item in an adapter
     * @param position the position of an item in an adapter
     * @return a drawable id.
     */

    public static int getImageDefaultId(int position){
       return recipeImagesDefault[position % recipeImagesDefault.length];
    }

    public static String getNormalizedMeasure(Context context, float quantity, String measure){
        String quantityString = String.valueOf(quantity);
        if(quantityString.contains(".0")){
            quantityString = String.valueOf(Math.round(quantity));
        }
        String formatString;
        switch(measure){
            case CUP:
                formatString = context.getString(R.string.cups);
                break;
            case TABLE_SPOON:
                formatString = context.getString(R.string.table_spoons);
                break;
            case TEA_SPOON:
                formatString = context.getString(R.string.tea_spoon);
                break;
            case KILOGRAM:
                formatString = context.getString(R.string.kilograms);
                break;
            case GRAM:
                formatString= context.getString(R.string.grams);
                break;
            case OZ:
                formatString = context.getString(R.string.ounce);
                break;
            case UNIT:
                formatString = context.getString(R.string.unit);
                break;
            default:
                formatString = measure + context.getString(R.string.default_measure);
                }

        return String.format(formatString, quantityString);
    }

    public static String createTagForFragment(int recipePosition, String recipeTitle){
        return recipeTitle + recipePosition ;
    }


}
