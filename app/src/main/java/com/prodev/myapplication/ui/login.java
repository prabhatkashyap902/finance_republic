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
import com.prodev.myapplication.SharedPrefManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    EditText ed1,ed2;
    Button bt;
    TextView tx;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ed1 = findViewById(R.id.mob_log);
        ed2 = findViewById(R.id.pass_log);
        bt =findViewById(R.id.log_bt);
        tx= findViewById(R.id.log_tx);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed1.getText().toString().trim().equals("") || ed2.getText().toString().trim().equals(""))
                {
                    Toast.makeText(login.this, "oops! you forgot to enter!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    userLogin();
                }
            }
        });

        tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(login.this, Registration.class));
            }
        });


    }



    private void userLogin(){
        final String username = ed1.getText().toString().trim();
        final String password = ed2.getText().toString().trim();

        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                SharedPrefManager.getInstance(getApplicationContext())
                                        .userLogin(
                                                obj.getString("mobile"),
                                                "jjj"
                                        );
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(getApplicationContext(), "Some Fault is there, Please restart the app!", Toast.LENGTH_LONG).show();
                    }
                }
        ){
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
