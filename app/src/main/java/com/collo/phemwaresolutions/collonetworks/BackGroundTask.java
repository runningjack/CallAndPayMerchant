package com.collo.phemwaresolutions.collonetworks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import static android.R.attr.data;

/**
 * Created by EntralogIT on 2017-03-16.
 */


public class BackGroundTask extends AsyncTask<Object, Object, JSONArray> {

    String url = null;
    String method = null;
    static InputStream is = null;
    static JSONArray jObj = null;
    static String json = "";
    private JSONArray jsonData;
    private JSONObject jsonObj;


    public BackGroundTask(String mapApiUrl, String _method) {
        this.url = mapApiUrl;
        this.method = _method;

    }

    public BackGroundTask(Context context) {
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

//    @Override
//    protected void onPostExecute(String s) {
//        mDialog.dismiss();
//    }

    @Override
    protected JSONArray doInBackground(Object... params) {
        // TODO Auto-generated method stub
        StringBuilder result = new StringBuilder();
        // Making HTTP request
        try {
            URL url1 = new URL(url);
            if (method.equals("POST")) {
                // request method is POST
                HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
                conn.setReadTimeout(30000);
                conn.setConnectTimeout(30000);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("FirstName", "Benedicta");
                    obj.put("LastName", "Adesanya");
                    obj.put("MiddleName", "Ikpori");

                    wr.writeBytes(obj.toString());
                    Log.e("JSON Input", obj.toString());
                    wr.flush();
                    wr.close();
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }


                conn.connect();
                is = conn.getInputStream();

            } else if (method == "GET") {
                HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
                conn.setReadTimeout(30000);
                conn.setConnectTimeout(30000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                is = conn.getInputStream();
                Log.e("JSON Input", "Its A GET");
// read input stream returned by request into a string using StringBuilder
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                is.close();
                json = result.toString();
            }
            jsonData = new JSONArray(json);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // return JSONObject (this is a class variable and null is returned if something went bad)
        return jsonData;

    }
}