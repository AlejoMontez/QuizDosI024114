package com.example.gabri.quizdosi024114;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gabri.quizdosi024114.Model.User;
import com.example.gabri.quizdosi024114.Parser.JsonUser;
import com.example.gabri.quizdosi024114.URL.HttpManager;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    ProgressBar progressBar;
    Button button;
    TextView textView;

    List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar =(ProgressBar)findViewById(R.id.id_pb_data);
        textView =(TextView) findViewById(R.id.id_tv_data);

        if(isOnline()) {
            TaskUsers taskUsers = new TaskUsers();
            taskUsers.execute("https://jsonplaceholder.typicode.com/users");

        }
        else {
            Toast.makeText(this, "No hay conexion a internet", Toast.LENGTH_SHORT).show();
        }
    }

    // Metodo para validar la conexion a internet
    public boolean isOnline() {
        // OBTENIENDO EL SERVICIO DE LA CONECTIVIDAD
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // DE ConnectivityManager OBTENGO SI ESTA O NO ACTIVA LA RED
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        // SI HAY CONEXION
        if(networkInfo != null) {
            return true;
        }
        else {
            return false;
        }
    }

    // Evento del boton


    public void processData(){

        for (User str : userList){
            textView.append(str.getName() + str.getUsername() + str.getEmail() + str.getStreet() +"\n");
        }
    }

    public class TaskUsers extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String content = null;
            try {
                content = HttpManager.getDataJson(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            processData();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                userList = JsonUser.getData(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            processData();

            progressBar.setVisibility(View.GONE);
        }
    }
}
