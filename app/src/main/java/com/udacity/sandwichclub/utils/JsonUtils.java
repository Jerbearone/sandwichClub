package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        //Turning the json that was passed into this from strings.xml into seperate strings and
        //arrays.
        JSONObject preParsedSandwich = new JSONObject(json);
        JSONObject preParsedName = preParsedSandwich.getJSONObject("name");
        String parsedName = preParsedName.getString("mainName");
        JSONArray preParsedAlsoKnownAs = preParsedSandwich.getJSONObject("name").getJSONArray("alsoKnownAs");

        List<String> parsedAlsoKnownAs = new ArrayList<String>();
        if (preParsedAlsoKnownAs.length() > 0) {
            for (int i=0; i<preParsedAlsoKnownAs.length(); i++) {
                parsedAlsoKnownAs.add( preParsedAlsoKnownAs.getString(i) );
            }

        }
        else parsedAlsoKnownAs.add(" unknown..");


        String parsedPlaceOfOrigin = preParsedSandwich.getString("placeOfOrigin");

        String parsedDescription = preParsedSandwich.getString("description");
        String parsedImage = preParsedSandwich.getString("image");
        JSONArray preParsedIngredients = preParsedSandwich.getJSONArray("ingredients");

        List<String> parsedIngredients = new ArrayList<String>();
        for (int i=0; i<preParsedIngredients.length(); i++) {
            parsedIngredients.add(preParsedIngredients.getString(i) );
        }

        Sandwich parsedSandwich = new Sandwich(parsedName, parsedAlsoKnownAs, parsedPlaceOfOrigin,
                parsedDescription, parsedImage, parsedIngredients);


        return parsedSandwich;
    }
}
