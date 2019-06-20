package com.prodev.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.prodev.myapplication.R;
import com.prodev.myapplication.SharedPrefManager;
import com.prodev.myapplication.adapter.recycler_adap;
import com.prodev.myapplication.models.model;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    RecyclerView rec;
    recycler_adap reca ;
    RecyclerView.LayoutManager manager;
    ArrayList<model> modelRecyclerArrayList = new ArrayList<>();
    model modelRecycler;
    FloatingActionButton afab,bfab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){

                finish();
                Intent j = new Intent(MainActivity.this, Registration.class);
                startActivity(j);


        }



        rec = (RecyclerView)findViewById(R.id.recycler);
        manager = new LinearLayoutManager(this);
        rec.setLayoutManager(manager);

        afab=findViewById(R.id.action_a);
        bfab = findViewById(R.id.action_b);




        try {
            JSONArray jarray = new JSONArray(loadJSONFromAsset());


            for (int i = 0; i < jarray.length(); i++) {
                modelRecycler =new model();

                JSONObject obj = jarray.getJSONObject(i);
                // Log.d("Details-->", jo_inside.getString("formule"));
                String company_name = obj.getString("Company Name");
                String industry = obj.getString("Industry");
                String symbol =obj.getString("Symbol");
                String isin=obj.getString("ISIN Code");

                //Add your values in your `ArrayList` as below:

                modelRecycler.setCompany(company_name);
                modelRecycler.setIndusty(industry);
                modelRecycler.setSymbol(symbol);
                modelRecycler.setIsin(isin);

                modelRecyclerArrayList.add(modelRecycler);
                reca =new recycler_adap(modelRecyclerArrayList,getApplicationContext());


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        rec.setAdapter(reca);

        afab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rec.scrollToPosition(0);
            }
        });

        bfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rec.scrollToPosition(reca.getItemCount()-1);
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



    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getApplicationContext().getAssets().open("json_file.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
