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
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class sigin extends AppCompatActivity {
    EditText etUsername,etPassword;
    static String id,name,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
           }

    class login extends AsyncTask<Void, Void, String> {
        ProgressDialog Dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Dialog = new ProgressDialog(sigin.this);
            Dialog.setMessage("Processing Your Request Please Wait");
            Dialog.setIndeterminate(false);
            Dialog.setCancelable(false);
            Dialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Dialog.dismiss();



            try {
             /*   Toast.makeText(sigin.this, s,
                        Toast.LENGTH_LONG).show();*/
                JSONObject jsonObject = new JSONObject(s);

                JSONArray jsonArray = jsonObject.getJSONArray("resultArray");


                    JSONObject row = jsonArray.getJSONObject(0);





                if(row.getString("error").equals("notfind")){
                    Toast.makeText(sigin.this, "No Results Found",
                            Toast.LENGTH_LONG).show();
                }
                else{

                   id=row.getString("ID");
                    name=row.getString("Name");
                    email=row.getString("Email");

                    Intent intent = new Intent(getBaseContext(),userhome.class);
                    startActivity(intent);}

            } catch (JSONException e) {
                Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

            }


        }

        @Override
        protected String doInBackground(Void... v) {

            HashMap<String, String> post = new HashMap<>();
            post.put("user", etUsername.getText().toString());
            post.put("password", etPassword.getText().toString());
            ServerPostRequest sr = new ServerPostRequest();
            String page=sr.ServerAddress+"/naviau/Login.php";
            String s = sr.sendPostRequest(page, post);
            return s;



        }
    }


    public void sigin(View v){
        if(etUsername.getText().toString().equals("") || etPassword.getText().toString().equals("")){
            Toast.makeText(getBaseContext(), "Please Fill All fields", Toast.LENGTH_LONG).show();
        }
        else {
            login p = new login();
            p.execute();
        }

    }
    public void newuser(View v){
        Intent intent = new Intent(getBaseContext(),signup.class);
        startActivity(intent);


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
