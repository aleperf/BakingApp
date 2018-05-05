package com.example.aleperf.bakingapp.utils;

import android.net.Uri;

import com.example.aleperf.bakingapp.R;
import com.example.aleperf.bakingapp.model.Recipe;
import com.example.aleperf.bakingapp.model.Recipe.Step;

public class RecipeUtilities {

    private final String CUP = "CUP";
    private final String TABLE_SPOON = "TBLSP";
    private final String TEA_SPOON = "TSP";
    private final String KILOGRAM = "K";
    private final String GRAM = "G";
    private final String OZ = "OZ";
    private final String UNIT = "UNIT";
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

    public static String[] getNormalizedMeasure(float measure, String unit){
        //TODO normalize measure units to be less "robotic"
        String[] results = new String[2];
        return results;
    }


}
