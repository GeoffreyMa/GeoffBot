package com.example.geoffbot.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.geoffbot.Models.ChatModel;
import com.example.geoffbot.R;
import com.github.library.bubbleview.BubbleTextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter{
    private List<ChatModel> list_chat_models;
    private Context context;
    private LayoutInflater layoutInflater;

    public CustomAdapter(List<ChatModel> list_chat_models, Context context) {
        this.list_chat_models = list_chat_models;
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list_chat_models.size();
    }

    @Override
    public Object getItem(int position) {
        return list_chat_models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null)
        {
            if(list_chat_models.get(position).isSend)
                view = layoutInflater.inflate(R.layout.right_messages,null);
            else
                view = layoutInflater.inflate(R.layout.left_messages,null);

            BubbleTextView bubbleViewTextMessage = (BubbleTextView)view.findViewById(R.id.text_message);
            bubbleViewTextMessage.setText(list_chat_models.get(position).message);
        }
        return view;
    }

}
