package com.example.covid19;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CovidDataFragment extends Fragment {
    public static final String LOG_TAG = CovidDataFragment.class.getSimpleName();
    private static final String USGS_REQUEST_URL =
            "https://api.covid19india.org/data.json";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        final ArrayList<CovidCase>[] covidCases = new ArrayList[]{new ArrayList<>()};

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {

                covidCases[0] = runing();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //UI Thread work here
                        CovidAdapter adapter = new CovidAdapter(getContext(), covidCases[0]);
                        ListView covidListView = (ListView) getView().findViewById(R.id.list);
                        covidListView.setAdapter(adapter);
                    }
                });
            }
        });

        return inflater.inflate(R.layout.coviddata_fragment, container, false);
    }

    public ArrayList<CovidCase> runing() {
        URL url = createUrl(USGS_REQUEST_URL);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
        }
        catch (IOException e) {
            // TODO Handle the IOException
        }

        ArrayList<CovidCase> covidCase = extractFeatureFromJson(jsonResponse);
        return covidCase;
    }
    private URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }
    //
//        /**
//         * Make an HTTP request to the given URL and return a String as the response.
//         */
    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);
        } catch (IOException e) {
            // TODO: Handle the exception
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private ArrayList<CovidCase> extractFeatureFromJson(String earthquakeJSON) {
        ArrayList<CovidCase> covidCases = new ArrayList<>();
        try {
            JSONObject jsonRootObject = new JSONObject(earthquakeJSON);

            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = jsonRootObject.optJSONArray("statewise");

            //Iterate the jsonArray and print the info of JSONObjects
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject states = jsonArray.getJSONObject(i);
                long active = states.getLong("active");
                long confirmed = states.getLong("confirmed");
                long deaths = states.getLong("deaths");
                long recovered = states.getLong("recovered");
                String stateName = states.getString("state");

                covidCases.add(new CovidCase(active,confirmed,deaths,recovered,stateName));


            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the COVID JSON results", e);
        }
        return covidCases;
    }


}
