package com.example.sergio.demodebug.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sergio.demodebug.POJO.User;
import com.example.sergio.demodebug.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter {
    private List<User> userList= new ArrayList<>();
    LayoutInflater layoutInflater;
    Context context;

    public MyAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public User getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView==null ){
            convertView = layoutInflater.inflate(R.layout.item,parent,false);
            viewHolder= new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        User user = userList.get(position);
        viewHolder.textView.setText(user.getName());
        Picasso.get().load(user.getUsername()).into(viewHolder.imageView);

        return convertView;
    }
    public class ViewHolder{
        ImageView imageView;
        TextView textView;

        public ViewHolder(View item) {
            this.imageView = item.findViewById(R.id.imageView);
            this.textView = item.findViewById(R.id.txtUserName);
        }


    }
}
