package com.prodev.myapplication.ui;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.prodev.myapplication.R;
import com.prodev.myapplication.SharedPrefManager;
import com.prodev.myapplication.adapter.rec_adap2;
import com.prodev.myapplication.adapter.rec_adap3;
import com.prodev.myapplication.api.api_interface;
import com.prodev.myapplication.models.model;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.util.ArrayList;
import java.util.Iterator;

import static java.security.AccessController.getContext;

public class my_profile extends AppCompatActivity {
    RecyclerView rec3;
    TextView tx;
    ProgressDialog pg;
    RecyclerView.LayoutManager manager;
    rec_adap3 reca3;
    ArrayList<model> list  =new ArrayList<>();
    String comp;
    String current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        tx = findViewById(R.id.phone_tx);
        tx.setText(SharedPrefManager.getInstance(this).getUsername());
        pg = new ProgressDialog(this);
        pg.setMessage("Loading...");
        pg.show();
        manager=new LinearLayoutManager(this);
        rec3  =findViewById(R.id.recycler3);
        rec3.setLayoutManager(manager);


        //reca3 = new rec_adap3();
volley();
retrofit();

    }
    public void retrofit(){

    }

    private void volley() {
        String url="http://project007.coolpage.biz/login/items/fetch_to_profile.php?mobile="+
                SharedPrefManager.getInstance(this).getUsername();
        final RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        final JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONArray> (){

                    @Override
                    public void onResponse(final JSONArray responses) {

                        try{
                            // Loop through the array elements



 ;
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(api_interface.JSONURL)
                                        .addConverterFactory(ScalarsConverterFactory.create())
                                        .build();

                                api_interface api = retrofit.create(api_interface.class);
                            for(int i=0;i<responses.length();i++){
                                //Call<String> call = api.getString();
                                final Call<String > call3 = api.getProducts("TIME_SERIES_DAILY",
                                        responses.getJSONObject(i).getString("company")
                                        ,"5min","J0N70CI83WLLS1BR");
                                final int finalI = i;
                                call3.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call2, retrofit2.Response<String> response) {
                                        // Log.i("Responsestring", response.body().toString());
                                        //Toast.makeText()
                                        if (response.isSuccessful()) {
                                            if (response.body() != null) {
                                                Log.i("onSuccess", response.body().toString());

                                                try {
    JSONObject obj = new JSONObject(response.body());
    JSONObject obj3 = obj.getJSONObject("Time Series (Daily)");
    JSONObject obj4 = obj3.getJSONObject(obj3.keys().next());



    //Getting all the keys inside json object with key- pages
        //getting string values with keys- pageid and title
                                                    current=obj4.getString("4. close");
       //current = obj4.getString("4. close");
                                                    Log.e("my_profile",""+current);
                                                    String current2= String.valueOf(Double.parseDouble(current)*
                                                            Double.parseDouble(responses.getJSONObject(finalI).getString("stock"))*
                                                    Double.parseDouble(responses.getJSONObject(finalI).getString("unit")));
                                                    Log.e("my_profile",""+current2);
            double diff=Double.parseDouble(current2)-Double.parseDouble(responses.getJSONObject(finalI).getString("totalP"));
                                                    Log.e("my_profile",""+diff);
            double perc = (diff*100)/Double.parseDouble(responses.getJSONObject(finalI).getString("totalP"));
                                                    Log.e("my_profile",""+perc);
                                                    list.add(new model(responses.getJSONObject(finalI).getString("company"),
                                                            responses.getJSONObject(finalI).getString("dateP"),
                                                            responses.getJSONObject(finalI).getString("costP"),
                                                            responses.getJSONObject(finalI).getString("stock"),
                                                            responses.getJSONObject(finalI).getString("unit"),
                                                            responses.getJSONObject(finalI).getString("totalP"),
                                                            current,current2,String.valueOf(diff),
                                        String.valueOf(perc)
                                                    ));
pg.dismiss();
                                                    reca3 = new rec_adap3(list,my_profile.this);
                                                    rec3.setAdapter(reca3);

}catch (Exception e){}
                                            } else {
                                                Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> call2, Throwable t) {

                                    }
                                });

                               // Toast.makeText(my_profile.this, ""+current, Toast.LENGTH_SHORT).show();


                              // tot = tot +  (Integer.parseInt(response.getJSONObject(i).getString("price"))*Integer.parseInt(response.getJSONObject(i).getString("qty")));

                            }
                            //Toast.makeText(MainActivity.this, ""+list.size(), Toast.LENGTH_SHORT).show();

                               // pg.dismiss();
                                /*reca3 = new rec_adap3(list, my_profile.this);
                                rec3.setAdapter(reca3);*/


                        }catch (JSONException e){
                            e.printStackTrace();
                        }


                    }

                },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                //Toast.makeText(this,error.toString(),Toast.LENGTH_LONG).show();
            }

        });

rq.add(jar);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
