package com.sandeep.indplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private static final String KEY_STATE = "state";
    private static final String KEY_CITIES = "cities";
    Spinner stateSpinner;
    Spinner citiesSpinner;
    EditText InputPhoneNumber, InputEmailID;
    private String cities_url = "http://api.androiddeft.com/cities/cities_array.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stateSpinner = findViewById(R.id.stateSpinner);
        citiesSpinner = findViewById(R.id.citiesSpinner);
        InputPhoneNumber = findViewById(R.id.phone_number);
        InputEmailID = findViewById(R.id.email_id);
        loadStateCityDetails();

        Button submitButton = findViewById(R.id.buttonSubmit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = InputPhoneNumber.getText().toString();
                if (isValidPhone(phone)) {
                    validateEmail();
                } else {

                    Toast.makeText(v.getContext(), "Error:Invalid Phone number .", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void validateEmail() {
        String email = InputEmailID.getText().toString();
        if (isValidEmail(email)) {
            storeData();

        } else {
            Toast.makeText(this, "Error: Invalid Email Id.", Toast.LENGTH_LONG).show();
        }
    }

    private void storeData() {
        final String phone = InputPhoneNumber.getText().toString();
        final String email = InputEmailID.getText().toString();
        final State state = (State) stateSpinner.getSelectedItem();
        final String city = citiesSpinner.getSelectedItem().toString();

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(phone).exists())) {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone", phone);
                    userdataMap.put("email", email);
                    userdataMap.put("state", state.getStateName());
                    userdataMap.put("city", city);

                    RootRef.child("Users").child(phone).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this,
                                                "Your data has been saved successfully ", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Network Error:Please try after some time...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(MainActivity.this, "This " + phone + " already exists.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public static boolean isValidEmail(String email) {
        String expression = "^[\\w\\.]+@([\\w]+\\.)+[A-Z]{2,7}$";
        CharSequence inputString = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }


    public static boolean isValidPhone(String phone) {
        String expression = "^([0-9\\+]|\\(\\d{1,3}\\))[0-9\\-\\. ]{9,13}$";
        CharSequence inputString = phone;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }


    private void loadStateCityDetails() {
        final List<State> statesList = new ArrayList<>();
        final List<String> states = new ArrayList<>();
        JsonArrayRequest jsArrayRequest = new JsonArrayRequest
                (Request.Method.GET, cities_url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray responseArray) {
                        try {
                            for (int i = 0; i < responseArray.length(); i++) {
                                JSONObject response = responseArray.getJSONObject(i);
                                String state = response.getString(KEY_STATE);
                                JSONArray cities = response.getJSONArray(KEY_CITIES);
                                List<String> citiesList = new ArrayList<>();
                                for (int j = 0; j < cities.length(); j++) {
                                    citiesList.add(cities.getString(j));
                                }
                                statesList.add(new State(state, citiesList));
                                states.add(state);

                            }
                            final StateAdapter stateAdapter = new StateAdapter(MainActivity.this,
                                    R.layout.state_list, R.id.spinnerText, statesList);
                            stateSpinner.setAdapter(stateAdapter);

                            stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    State cityDetails = stateAdapter.getItem(position);
                                    List<String> cityList = cityDetails.getCities();
                                    ArrayAdapter citiesAdapter = new ArrayAdapter<>(MainActivity.this,
                                            R.layout.city_list, R.id.citySpinnerText, cityList);
                                    citiesSpinner.setAdapter(citiesAdapter);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    public void fetchdata(View view) {
        startActivity(new Intent(MainActivity.this, ContentActivity.class));
    }
}
