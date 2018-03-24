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

import java.util.HashMap;

public class signup extends AppCompatActivity {
    EditText etUsername,etPassword,eEmail,etPasswordC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Register");

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPasswordC = (EditText) findViewById(R.id.etPasswordC);
        eEmail = (EditText) findViewById(R.id.eEmail);
    }

    class Register extends AsyncTask<Void, Void, String> {
        ProgressDialog Dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Dialog = new ProgressDialog(signup.this);
            Dialog.setMessage("Processing Your Request Please Wait");
            Dialog.setIndeterminate(false);
            Dialog.setCancelable(false);
            Dialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Dialog.dismiss();
            Toast.makeText(signup.this, s,
                    Toast.LENGTH_LONG).show();
              }

        @Override
        protected String doInBackground(Void... v) {

            HashMap<String, String> post = new HashMap<>();
            post.put("user", etUsername.getText().toString());
            post.put("password", etPassword.getText().toString());
            post.put("email", eEmail.getText().toString());
            ServerPostRequest sr = new ServerPostRequest();
            String page=sr.ServerAddress+"/naviau/Register.php";
            String s = sr.sendPostRequest(page, post);
            return s;



        }
    }


    public void signup(View v){
        if(etUsername.getText().toString().equals("") || etPassword.getText().toString().equals("")|| eEmail.getText().toString().equals("")){
            Toast.makeText(getBaseContext(), "Please Fill All fields", Toast.LENGTH_LONG).show();
        }
        else if(!etPassword.getText().toString().equals(etPasswordC.getText().toString())){
            Toast.makeText(getBaseContext(), "Passwords Not Match", Toast.LENGTH_LONG).show();
        }
        else {
            Register p = new Register();
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
