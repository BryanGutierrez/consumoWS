package com.example.bryan.ConsumoSW;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetJson extends AsyncTask<Void,Void,Void> {

    String data = "";
    String dataParsed="";
    String singleParsed="";

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=Tepic,mx&APPID=0906362826d2cfea265ed029381a7e31");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new
                    InputStreamReader(inputStream));
            String line ="";
            while (line!=null){
                line = bufferedReader.readLine();
                data = data+line;
            }
            JSONObject jsonObject = new JSONObject(data);
            JSONArray weather = new JSONArray(jsonObject.getString("weather"));
            JSONObject weather2;
            String description="";
            for(int i=0;i<weather.length();i++){
                weather2 = (JSONObject)weather.get(i);
                description= weather2.getString("description");
            }
            JSONObject main = new JSONObject(jsonObject.getString("main"));
            JSONObject sys = new JSONObject(jsonObject.getString("sys"));
            singleParsed = "Ciudad: " + jsonObject.get("name") + "\n"
                            + "País: "+ sys.getString("country") +"\n"
                            + "Descripción: "+ description+"\n"
                            + "Temperatura : "+ main.getString("temp_min")+"\n"
                            + "Humedad : " + main.getString("humidity")+"\n";

            dataParsed = dataParsed + singleParsed + "\n";

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        MainActivity.textView.setText(this.dataParsed);
    }

}
