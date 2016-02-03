package com.example.nagakrishna.ase_lab2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class Weather extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private Boolean CELCIUS = false;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContext = getBaseContext();


//        Button btnArea = (Button)findViewById(R.id.btnWeather_area);
//        btnArea.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        CallAPI ob = new CallAPI();
//                        new CallAPI().execute("api.openweathermap.org/data/2.5/weather?q=" + Area);
//
//                    }
//                }
//        );
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        if (CELCIUS == false) {
//            getMenuInflater().inflate(R.menu.menu_main, menu);
//        }
//        else {
//            getMenuInflater().inflate(R.menu.menu_farehheitsacle, menu);
//        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

//        if (id == R.id.celciusScale)
//        {
//            CELCIUS = true;
//        }
//
//        if(id == R.id.farenheitScale)
//        {
//            CELCIUS = false;
//        }

        return super.onOptionsItemSelected(item);
    }

    private void hideKeyboard(View editableView) {
        InputMethodManager imm = (InputMethodManager)mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editableView.getWindowToken(), 0);
    }

    public void ButtonClick(final View v)
    {
        final EditText txtLocation = (EditText) findViewById(R.id.txtWeather_area);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideKeyboard(txtLocation);
            }
        });


        final TextView txtLabelTemperature = (TextView)findViewById(R.id.labelTemperature);
        final TextView txtTemperature = (TextView)findViewById(R.id.txtWeather_temp);
        final TextView texLabelTemperature_min = (TextView)findViewById(R.id.labelTemperature_min);
        final TextView txtTemperature_min = (TextView)findViewById(R.id.txtWeather_temp_min);
        final TextView texLabelTemperature_max = (TextView)findViewById(R.id.labelTemperature_max);
        final TextView txtTemperature_max = (TextView)findViewById(R.id.txtWeather_temp_max);
        final TextView  txtLabelWindSpeed = (TextView)findViewById(R.id.labelWindSpeed);
        final TextView txtWindSpeed = (TextView)findViewById(R.id.txtWindSpeed);
        final TextView txtLabelPressure = (TextView)findViewById(R.id.labelPressure);
        final TextView txtPressure = (TextView)findViewById(R.id.txtPressure);
        final TextView txtLabelHumidity = (TextView)findViewById(R.id.labelHumidity);
        final TextView txtHumididty = (TextView)findViewById(R.id.txtHumidity);
        final TextView txtLablePlace = (TextView)findViewById(R.id.labelPlace);
        final TextView txtPlace = (TextView)findViewById(R.id.txtPlace);
        String Area = txtLocation.getText().toString();
        Area = Area.replaceAll(" ", "%20");
        CallAPI callAPI = new CallAPI(new WeatherServiceListener() {
            @Override
            public void servicesuccess(String output) {
                double temperature = 0, temp_min, temp_max;
            try
            {

                JSONObject data = new JSONObject(output.toString());
                JSONObject main1 = null;
                String temp=null, stemp_min=null, stemp_max=null;

                main1 = data.getJSONObject("main");
                DecimalFormat df = new DecimalFormat("#.#");
//                if (CELCIUS == false) {
//                    temperature = (((main1.optDouble("temp") * 1.8)) - 459.67);
//                    temp_min = (((main1.optDouble("temp_min") * 1.8)) - 459.67);
//                    temp_max = (((main1.optDouble("temp_max") * 1.8)) - 459.67);
//                    temp = df.format(temperature);
//                    stemp_min = df.format(temp_min);
//                    stemp_max = df.format(temp_max);
//                    temp = temp + "°F";
//                }
//                else
//                {
                    temperature = (main1.optDouble("temp") - 273);
                    temp_min = (main1.optDouble("temp_min") - 273);
                    temp_max = (main1.optDouble("temp_max") - 273);
                    temp = df.format(temperature);
                    stemp_min = df.format(temp_min);
                    stemp_max = df.format(temp_max);
                    temp = temp + " °C";
                    stemp_max = stemp_max + " °C";
                    stemp_min = stemp_min + " °C";
              //  }

                double pressure = main1.getDouble("pressure");
                double humidity = main1.getDouble("humidity");
                JSONObject wind = data.getJSONObject("wind");
                double windSpeed = wind.getDouble("speed");
                String place = data.getString("name");
                txtLabelTemperature.setVisibility(v.VISIBLE);
                txtTemperature.setText(String.valueOf(temp));
                texLabelTemperature_min.setVisibility(v.VISIBLE);
                txtTemperature_min.setText(String.valueOf(stemp_min));
                texLabelTemperature_max.setVisibility(v.VISIBLE);
                txtTemperature_max.setText(String.valueOf(stemp_max));
                txtLabelWindSpeed.setVisibility(v.VISIBLE);
                String swind = windSpeed + " m/s";
                txtWindSpeed.setText(String.valueOf(swind));
                txtLabelPressure.setVisibility(v.VISIBLE);
                String sPressure = pressure + " hpa";
                txtPressure.setText(String.valueOf(sPressure));
                txtLabelHumidity.setVisibility(v.VISIBLE);
                String shumidity = humidity + " %" ;
                txtHumididty.setText(String.valueOf(shumidity));
                txtLablePlace.setVisibility(v.VISIBLE);
                txtPlace.setText(String.valueOf(place));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        });
        callAPI.execute("http://api.openweathermap.org/data/2.5/weather?q=" + Area + "&appid=44db6a862fba0b067b1930da0d769e98");
    }
    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Weather Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.nagakrishna.ase_lab2/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Weather Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.nagakrishna.ase_lab2/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}

class CallAPI extends AsyncTask<String, String, String>
{
    private WeatherServiceListener listener;
    public CallAPI(WeatherServiceListener listener)
    {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        String urlString = params[0];
        InputStream inputStream;
        StringBuilder result = null;

        try
        {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            inputStream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            //return e.getMessage();
        }
        return  result.toString();

    }

    protected void onPostExecute(String result)
    {
        listener.servicesuccess(result);
    }
}


