package com.example.geoffbot.Helper;

import android.app.DownloadManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HttpDataHandler {
    static String stream = null;
    private static final String URL = "https://wsapi.simsimi.com/190410/talk";
    private RequestQueue queue;
    public HttpDataHandler(RequestQueue queue) {
        this.queue = queue;
    }

    public String sendRequest(JSONObject body) throws InterruptedException {
        final Object lock = new Object();
        final Map<String, String> message = new HashMap<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, URL, body,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    System.out.println("response = [" + response + "]");
                                    message.put("response", response.getString("atext"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                synchronized (lock) {
                                    lock.notify();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println("error = [" + error + "]");
                                synchronized (lock) {
                                    lock.notify();
                                }
                            }
                        }) {
            @Override
            public Map<String, String> getHeaders(){
                HashMap<String, String> headers = new HashMap<>();
                        headers.put("x-api-key", "WeW1WytH1m3xBriWqtVC2TCpq2+aPjr2jNBsP3aA");
                return headers;
            }
        };
        System.out.println(jsonObjectRequest);
        queue.add(jsonObjectRequest);
        synchronized (lock) {
            lock.wait();
        }
        return message.get("response");
    }
}
