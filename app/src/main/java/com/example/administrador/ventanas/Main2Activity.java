package com.example.administrador.ventanas;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import Clases.Usuario;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{

    TextView tv_Nombre;
    TextView tv_Apellido;
    TextView tv_Email;
    TextView tv_Clave;
    TextView tv_Servicio;
    Button   btn_Servicio;


    Usuario usuario = new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toast.makeText(this,"Insertando datos del usuario",Toast.LENGTH_LONG).show();

        tv_Nombre = (TextView)findViewById(R.id.tvNombre);
        tv_Apellido = (TextView)findViewById(R.id.tvApellido);
        tv_Email = (TextView)findViewById(R.id.tvEmail);
        tv_Clave = (TextView)findViewById(R.id.tvClave);

        tv_Servicio = (TextView)findViewById(R.id.tvServicio);
        btn_Servicio = (Button)findViewById(R.id.btnServicio);

        Bundle b = this.getIntent().getExtras();

        usuario.setUsnombre(b.getString("NOMBRE"));
        usuario.setUsapelldo(b.getString("APELLIDO"));
        usuario.setUsemail(b.getString("EMAIL"));
        usuario.setUsclave(b.getString("CLAVE"));

        tv_Nombre.setText("Nombre: " + usuario.getUsnombre() +"");

        tv_Apellido.setText("Apellido: " +usuario.getUsapelldo()+"");
        tv_Email.setText("Email: " + usuario.getUsemail() +"");
        tv_Clave.setText("Clave: " + usuario.getUsclave() +"");

        btn_Servicio.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnServicio:
               //new  JSONTask().execute("http://jsonparsing.parseapp.com/jsonData/moviesDemoItem.txt");
                new  JSONTask().execute("http://jsonparsing.parseapp.com/jsonData/moviesDemoList.txt");

                break;
        }
    }


    public class  JSONTask  extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String...params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                String finalJson =  buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray  parentArray = parentObject.getJSONArray("movies");

                StringBuffer finalBufferData = new StringBuffer();

                for(int i=0; i<parentArray.length();i++ ){
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    String movieName = finalObject.getString("movie");
                    int year = finalObject.getInt("year");
                    finalBufferData.append(movieName + "-" + year + "\n");
                }
                return finalBufferData.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null){
                    connection.disconnect();
                }
                try {if (reader!=null){
                    reader.close();
                }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            tv_Servicio.setText(result);
        }
    }

}





