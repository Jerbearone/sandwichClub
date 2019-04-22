package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);
        //linking java strings to textViews.

        setTitle(sandwich.getMainName());
        TextView alsoKnownAsTextView = findViewById(R.id.also_known_tv);
        //converting the List<String> to String
        // that will be passed into the also_known_as_tv text view.
        StringBuilder alsoKnownAsStringBuilder = new StringBuilder();
        for (String s : sandwich.getAlsoKnownAs()){
            alsoKnownAsStringBuilder.append(s);
            alsoKnownAsStringBuilder.append("|");
        }
        //deleting the delimiter(newline) so there is not an extra newline printed in the view.
        alsoKnownAsStringBuilder.deleteCharAt(alsoKnownAsStringBuilder.length()-1);
        String alsoKnownAsString = alsoKnownAsStringBuilder.toString();
        alsoKnownAsTextView.setText(alsoKnownAsString);

        TextView originTextView = findViewById(R.id.origin_tv);
        originTextView.setText(sandwich.getPlaceOfOrigin());
        TextView descriptionTextView = findViewById(R.id.description_tv);
        descriptionTextView.setText(sandwich.getDescription());

        StringBuilder ingredientsStringBuilder = new StringBuilder();
        for (String s : sandwich.getIngredients()){
            ingredientsStringBuilder.append(s);
            ingredientsStringBuilder.append(", ");
        }
        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);
        //deleting the last character so that the delimiter(comma) doesn't appear at the end.
        ingredientsStringBuilder.deleteCharAt(ingredientsStringBuilder.length()-2);
        String ingredientsString = ingredientsStringBuilder.toString();
        ingredientsTextView.setText(ingredientsString);


    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

    }
}
