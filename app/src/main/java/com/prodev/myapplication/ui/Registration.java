package com.prodev.myapplication.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.prodev.myapplication.Constants.Constants;
import com.prodev.myapplication.R;
import com.prodev.myapplication.RequestHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    EditText ed1,ed2;
    Button bt;
    TextView tx;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ed1 = findViewById(R.id.mob_reg);
        ed2 = findViewById(R.id.pass_reg);
        bt= findViewById(R.id.reg_bt);
        tx = findViewById(R.id.reg_tx);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed1.getText().toString().trim().equals("")|| ed2.getText().toString().equals("")){
                    Toast.makeText(Registration.this, "Oops! you forgot to enter the details!", Toast.LENGTH_SHORT).show();
                }
                else{
                        registerUser();
                }

            }
        });

        tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Registration.this, login.class));
            }
        });


    }


    public void registerUser() {
        final String username = ed1.getText().toString().trim();
        final String password = ed2.getText().toString().trim();


        progressDialog.setMessage("Registering user...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            // Log.i("tagconvertstr", "["+response+"]");
                            JSONObject jsonObject = new JSONObject(response);


                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                            //Toast.makeText(Registration.this, "Account Created!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Registration.this,login.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Registration.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), " Connect to Internet!", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mobile", username);
                params.put("password", password);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }




}
