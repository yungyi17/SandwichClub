package com.udacity.sandwichclub.utils;

import android.util.Log;
import android.widget.Toast;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String LOG_TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        final String SNDH_NAME = "name";
        final String SNDH_MAIN_NAME = "mainName";
        final String SNDH_ALSO_KNOWN_AS = "alsoKnownAs";
        final String SNDH_PLACE_OF_ORIGIN = "placeOfOrigin";
        final String SNDH_DESCRIPTION = "description";
        final String SNDH_IMAGE = "image";
        final String SNDH_INGREDIENTS = "ingredients";

        JSONObject jsonData = new JSONObject(json);
        JSONObject sndhName = jsonData.getJSONObject(SNDH_NAME);

        String sMainName = sndhName.getString(SNDH_MAIN_NAME);
        List<String> sKnownAsList = new ArrayList<>();

        JSONArray sKnownAsArray = sndhName.getJSONArray(SNDH_ALSO_KNOWN_AS);
        //Log.d(LOG_TAG, "Array Length: " + sKnownAsArray.length());

        if (sKnownAsArray.length() > 0) {
            for (int i = 0; i < sKnownAsArray.length(); i++) {
                //Log.d(LOG_TAG,  "VALUES: " + sKnownAsArray.getString(i));
                sKnownAsList.add(sKnownAsArray.getString(i));
            }
        }

        String sPlaceOfOrigin = jsonData.getString(SNDH_PLACE_OF_ORIGIN);
        String sDescription = jsonData.getString(SNDH_DESCRIPTION);
        String sImage = jsonData.getString(SNDH_IMAGE);

        List<String> sIngredients = new ArrayList<>();
        JSONArray sIngredientsArray = jsonData.getJSONArray(SNDH_INGREDIENTS);
        if (sIngredientsArray.length() > 0) {
            for (int j = 0; j < sIngredientsArray.length(); j++) {
                sIngredients.add(sIngredientsArray.getString(j));
            }
        }

        Sandwich mSandwich = new Sandwich(sMainName,
                sKnownAsList, sPlaceOfOrigin, sDescription, sImage, sIngredients);

        mSandwich.setMainName(sMainName);
        mSandwich.setAlsoKnownAs(sKnownAsList);
        mSandwich.setPlaceOfOrigin(sPlaceOfOrigin);
        mSandwich.setDescription(sDescription);
        mSandwich.setImage(sImage);
        mSandwich.setIngredients(sIngredients);

        return mSandwich;
    }
}
