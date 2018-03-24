package com.example.power.naviau;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class search_loction extends AppCompatActivity {
     EditText serchTxt;
    static  ArrayList<String> ID,Name,Description, Type,Latitude,Longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_loction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search a Location");
        toolbar.setNavigationIcon(R.drawable.back1);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(), userhome.class);
                startActivity(myIntent);
                finish();
            }
        });
        serchTxt = (EditText) findViewById(R.id.SearchTxt);

    }
    class search extends AsyncTask<Void, Void, String> {
        ProgressDialog Dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Dialog = new ProgressDialog(search_loction.this);
            Dialog.setMessage("Processing Your Request Please Wait");
            Dialog.setIndeterminate(false);
            Dialog.setCancelable(false);
            Dialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Dialog.dismiss();
           ID = new ArrayList<String>();
             Name = new ArrayList<String>();
            Description = new ArrayList<String>();
            Type = new ArrayList<String>();
            Latitude = new ArrayList<String>();
             Longitude = new ArrayList<String>();


            try {
              /*  Toast.makeText(search_loction.this, s,
                        Toast.LENGTH_LONG).show();*/
                JSONObject jsonObject = new JSONObject(s);

                JSONArray jsonArray = jsonObject.getJSONArray("resultArray");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject row = jsonArray.getJSONObject(i);


                    ID.add(row.getString("ID"));
                    Name.add(row.getString("Name"));
                    Description.add(row.getString("Description"));
                    Type.add(row.getString("Type"));
                    Latitude.add(row.getString("Latitude"));
                    Longitude.add(row.getString("Longitude"));

                }
                if(ID.size()==0){
                    Toast.makeText(search_loction.this, "No Results Found",
                            Toast.LENGTH_LONG).show();
                }
               else{ Intent intent = new Intent(getBaseContext(),MapsActivity.class);
                startActivity(intent);}

            } catch (JSONException e) {
                Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
            }


        }

        @Override
        protected String doInBackground(Void... v) {
            HashMap<String, String> post = new HashMap<>();
            post.put("Search", serchTxt.getText().toString());
            ServerPostRequest sr = new ServerPostRequest();
            String page=sr.ServerAddress+"/naviau/Search.php";
            String s = sr.sendPostRequest(page, post);
            return s;



        }
    }


public void serach(View v){
    search p = new search();
    p.execute();


}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_all, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.search_id:

                Intent intent = new Intent(getBaseContext(),search_loction.class);
                startActivity(intent);
                finish();
                break;

        }


        return super.onOptionsItemSelected(item);
    }


}
