package com.example.aleperf.bakingapp.utils;

import android.net.Uri;

import com.example.aleperf.bakingapp.model.Recipe;
import com.example.aleperf.bakingapp.model.Recipe.Step;

import retrofit2.http.Url;

public class StepFieldsValidator {

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
}