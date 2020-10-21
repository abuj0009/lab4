package com.example.lab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class  ChatAdapter extends BaseAdapter {

    private List<Message> elements;
    private Context context;
    private LayoutInflater inflater;

    public ChatAdapter(List<Message> messageModels, Context context) {
        this.elements = messageModels;
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return elements.size();
    }

    @Override
    public Object getItem(int position) {
        return elements.get(position);
    }

    @Override
    public long getItemId(int position) {
        System.out.println("Delete id --> "+elements.get(position).getId());
        return elements.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            if (elements.get(position).isSent()){
                convertView = inflater.inflate(R.layout.activity_main_send, null);

            }else {
                convertView = inflater.inflate(R.layout.activity_main_receive, null);
            }
            TextView  messageTxt = (TextView)convertView.findViewById(R.id.textViewMessage);
            messageTxt.setText(elements.get(position).message);
        }
        return convertView;
    }
}
