package com.prodev.myapplication.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.prodev.myapplication.R;
import com.prodev.myapplication.SharedPrefManager;
import com.prodev.myapplication.adapter.rec_adap2;
import com.prodev.myapplication.api.api_interface;
import com.prodev.myapplication.models.model;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.util.ArrayList;
import java.util.Iterator;

public class Main2Activity extends AppCompatActivity {
    TextView tx1,tx2;
    RecyclerView rec2;
    RecyclerView.LayoutManager layoutManager;
    rec_adap2 reca2;
    String name;
    ProgressDialog pd;
    model modelRecycler;
    ArrayList<model> modelRecyclerArrayList=new ArrayList<>();
    FloatingActionButton fab;
    ConstraintLayout lay_cons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        name= getIntent().getStringExtra("name");

        pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        tx1 =findViewById(R.id.tx1);
        tx2 = findViewById(R.id.tx2);
        rec2= findViewById(R.id.recycle2);
        fab=(FloatingActionButton) findViewById(R.id.add_fab);
        lay_cons=findViewById(R.id.lay_cons);

        layoutManager =new LinearLayoutManager(this);
        rec2.setLayoutManager(layoutManager);

        data();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reca2.getItemCount()>0){
                    showCustomDialog();
                }
                else{
                    Snackbar snackbar = Snackbar
                            .make(lay_cons, "Sorry no Data Found!", Snackbar.LENGTH_LONG);

                    snackbar.show();
                }
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.logout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this, login.class));
                break;

            case R.id.profile:
                startActivity(new Intent(this,my_profile.class));

        }
        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void showCustomDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);
        final TextView rate,total;
        final EditText ed1,ed2;


        rate=(TextView) dialogView.findViewById(R.id.rate_id);
        total=(TextView)dialogView.findViewById(R.id.total_tx);
        ed1=(EditText)dialogView.findViewById(R.id.stock_ed);
        ed2=(EditText) dialogView.findViewById(R.id.unit_ed);

        dialogBuilder.setTitle("Want to buy "+name+" Stocks!");
        //rate.setText("ooo");
        rate.setText(modelRecyclerArrayList.get(0).getClose());


        ed2.addTextChangedListener(new TextWatcher() {
                                       @Override
                                       public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                       }

                                       @Override
                                       public void onTextChanged(CharSequence s, int start, int before, int count) {
                                           if(!ed1.getText().toString().equalsIgnoreCase("")&&!ed2.getText().toString().equalsIgnoreCase(""))
                                               total.setText(String.valueOf(Double.parseDouble(rate.getText().toString())*
                                                       Double.parseDouble(ed1.getText().toString())*Double.parseDouble(s.toString())));

                                       }

                                       @Override
                                       public void afterTextChanged(Editable s) {
                                           if(!ed1.getText().toString().equalsIgnoreCase("") &&!ed2.getText().toString().equalsIgnoreCase(""))
                                           total.setText(String.valueOf(Double.parseDouble(rate.getText().toString())*
                                                   Double.parseDouble(ed1.getText().toString())*Double.parseDouble(s.toString())));

                                       }
                                   });

        ed1.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                if(!ed1.getText().toString().equalsIgnoreCase("")&&!ed2.getText().toString().equalsIgnoreCase(""))
                                                    total.setText(String.valueOf(Double.parseDouble(rate.getText().toString())*
                                                            Double.parseDouble(ed2.getText().toString())*Double.parseDouble(s.toString())));

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if(!ed1.getText().toString().equalsIgnoreCase("") &&!ed2.getText().toString().equalsIgnoreCase(""))
                                                    total.setText(String.valueOf(Double.parseDouble(rate.getText().toString())*
                                                            Double.parseDouble(ed2.getText().toString())*Double.parseDouble(s.toString())));

                                            }
                                        });


                dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if(!ed1.getText().toString().equalsIgnoreCase("")&&!ed2.getText().toString().equalsIgnoreCase("")){
                           if(Integer.parseInt(ed1.getText().toString())<15) {
                               Toast.makeText(Main2Activity.this, "Total is " + total.getText(), Toast.LENGTH_SHORT).show();

                               String url  = "http://project007.coolpage.biz/login/items/add_in_db.php?mobile="
                                       + SharedPrefManager.getInstance(getApplicationContext()).getUsername()+
                                       "&company="+name+
                                       "&dateP="+modelRecyclerArrayList.get(0).getDate()+
                                       "&costP="+modelRecyclerArrayList.get(0).getClose()+
                                       "&stock="+ed1.getText().toString()+
                                       "&unit="+ed2.getText().toString()+
                                       "&totalP="+total.getText();


                               final RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
                               StringRequest sr=new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
                                   @Override
                                   public void onResponse(String response) {
                                       Toast.makeText(Main2Activity.this,"Your Stock has been added to database",Toast.LENGTH_LONG).show();

                                   }
                               }, new com.android.volley.Response.ErrorListener() {
                                   @Override
                                   public void onErrorResponse(VolleyError error) {
                                       Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                                   }
                               });

                               rq.add(sr);



                           }
                           else Toast.makeText(Main2Activity.this,"Stock must be Less than or equal to 15",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(Main2Activity.this,"Enter some values for stock and unit!",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void data() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(api_interface.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        api_interface api = retrofit.create(api_interface.class);

        //Call<String> call = api.getString();
        final Call<String > call3 = api.getProducts("TIME_SERIES_DAILY",name,"5min","J0N70CI83WLLS1BR");
        call3.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call2, Response<String> response) {
                // Log.i("Responsestring", response.body().toString());
                //Toast.makeText()
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());

                        String jsonresponse = response.body().toString();
                        // Log.i("Done",modelRecyclerArrayList.toString());

                        pd.dismiss();
                        writeRecycler(jsonresponse);



                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call2, Throwable t) {

            }
        });
    }

    public void writeRecycler(String response){

        try {
            //getting the whole json object from the response

            JSONObject obj = new JSONObject(response);
            JSONObject obj2= obj.getJSONObject("Meta Data");
            JSONObject obj3=obj.getJSONObject("Time Series (Daily)");
            JSONObject obj4=null;


            tx1.setText(name);
            tx2.setText(obj2.getString("3. Last Refreshed"));



            //Getting all the keys inside json object with key- pages
            Iterator<String> keys= obj3.keys();
            while (keys.hasNext())
            {modelRecycler = new model();
                String date = (String)keys.next();
                obj4 = obj3.getJSONObject(date);
                //getting string values with keys- pageid and title
                String open = obj4.getString("1. open");
                String close = obj4.getString("4. close");


                modelRecycler.setDate(date);
                modelRecycler.setOpen(open);
                modelRecycler.setClose(close);

                modelRecyclerArrayList.add(modelRecycler);
                reca2 = new rec_adap2(modelRecyclerArrayList,this);
               // reca2.notifyDataSetChanged();

            }
                // Log.i("Done",modelRecyclerArrayList.get(0).toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        rec2.setAdapter(reca2);
    }
}
