package com.example.administrador.ventanas;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.ArrayList;
import java.util.List;

import models.MovieModel;

public class ConsumirRest1Activity extends AppCompatActivity  implements View.OnClickListener{

    ListView lvMovies;
    TextView tv_Servicio;
    Button btn_Servicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumir_rest1);
        lvMovies = (ListView)findViewById(R.id.lvMovies);


        btn_Servicio = (Button)findViewById(R.id.btnServicio);
        btn_Servicio.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnServicio:
                new JSONTask().execute("http://jsonparsing.parseapp.com/jsonData/moviesDemoList.txt");
                break;
        }
    }

    public class  JSONTask  extends AsyncTask<String,String,List<MovieModel>> {

        @Override
        protected List<MovieModel> doInBackground(String...params) {
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
                JSONArray parentArray = parentObject.getJSONArray("movies");

               //StringBuffer finalBufferData = new StringBuffer();

                List<MovieModel> movieModelList = new ArrayList<>();


                for(int i=0; i<parentArray.length();i++ ){

                    JSONObject finalObject = parentArray.getJSONObject(i);

                    MovieModel movieModel = new MovieModel();
                    movieModel.setMovie(finalObject.getString("movie"));
                    movieModel.setYear(finalObject.getInt("year"));
                    movieModel.setRating((float)finalObject.getDouble("rating"));
                    movieModel.setDuration(finalObject.getString("duration"));
                    movieModel.setDirector(finalObject.getString("director"));
                    movieModel.setTagline(finalObject.getString("tagline"));
                    movieModel.setImage(finalObject.getString("image"));
                    movieModel.setStory(finalObject.getString("story"));

                    List<MovieModel.Cast> castList = new ArrayList<>();

                    for (int j=0;j<finalObject.getJSONArray("cast").length();j++){
                        MovieModel.Cast cast = new MovieModel.Cast();
                        cast.setName(finalObject.getJSONArray("cast").getJSONObject(j).getString("name"));
                        castList.add(cast);
                    }
                    movieModel.setCastList(castList);
                    movieModelList.add(movieModel);

                    //finalBufferData.append(movieName + "-" + year + "\n");
                }
                return movieModelList;

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
        protected void onPostExecute(List<MovieModel> result) {
            super.onPostExecute(result);

        }
    }

}
