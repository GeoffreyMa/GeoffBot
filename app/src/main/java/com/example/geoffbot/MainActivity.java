package com.example.geoffbot;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.AsyncTask;
//import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.geoffbot.Adapter.CustomAdapter;
import com.example.geoffbot.Helper.HttpDataHandler;
import com.example.geoffbot.Models.ChatModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    EditText editText;
    List<ChatModel> list_chat = new ArrayList<>();
    FloatingActionButton floatingActionButton;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listMessages);
        editText = (EditText)findViewById(R.id.sendMessages);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                ChatModel model = new ChatModel(text,true); //Usuario envia message
                list_chat.add(model);
                new SimsimiAPI().execute(list_chat);

                //Eliminar mensajes de usuario
                editText.setText("");
            }
        });
        queue = Volley.newRequestQueue(this);
    }
    private class SimsimiAPI extends AsyncTask<List<ChatModel>,Void,String> {


        List<ChatModel> models;
        String text = editText.getText().toString();

        @Override
        protected String doInBackground(List<ChatModel>... params) {
            String response = null;
            String url = "https://wsapi.simsimi.com/190410/talk";
            models = params [0];
            HttpDataHandler httpDataHandler = new HttpDataHandler(queue);
            try {
                JSONObject body = new JSONObject(String.format("{\"utext\": \"%s\", \"lang\": \"en\"}", models.get(0).message));
                return httpDataHandler.sendRequest(body);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            System.out.println(s);

            ChatModel chatModel = new ChatModel(s,false); //simsimi
            models.add(chatModel);
            CustomAdapter adapter = new CustomAdapter(models,getApplicationContext());
            listView.setAdapter(adapter);
        }
    }


}
