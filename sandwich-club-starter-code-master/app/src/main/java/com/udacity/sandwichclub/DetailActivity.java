package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private Sandwich getSandwich;

    private TextView mOriginFrom;
    private TextView mAlsoKnownAs;
    private TextView mDescription;
    private TextView mIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mOriginFrom = findViewById(R.id.origin_tv);
        mAlsoKnownAs = findViewById(R.id.also_known_tv);
        mDescription = findViewById(R.id.description_tv);
        mIngredients = findViewById(R.id.ingredients_tv);

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

        try {
            getSandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }

        if (getSandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(getSandwich.getImage())
                .into(ingredientsIv);

        setTitle(getSandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        mOriginFrom.setText(getSandwich.getPlaceOfOrigin());
        
        int otherNamesSize = getSandwich.getAlsoKnownAs().size();
        for (int i = 0; i < otherNamesSize; i++) {
            mAlsoKnownAs.append(getSandwich.getAlsoKnownAs().get(i) + "\n");
        }

        mDescription.setText(getSandwich.getDescription());

        int ingredientsSize = getSandwich.getIngredients().size();
        for (int j = 0; j < ingredientsSize; j++) {
            mIngredients.append(getSandwich.getIngredients().get(j) + "\n");
        }
    }
}
