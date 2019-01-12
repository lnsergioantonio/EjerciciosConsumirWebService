package com.example.sergio.demodebug.parser;

import com.example.sergio.demodebug.POJO.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserJsonParser {
    public static List<User> parse(String content){
        try {
            JSONArray jsonArray = new JSONArray(content);
            List<User> userList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                User user = new User();

                user.setId(jsonObject.getInt("usuarioid"));
                user.setName(jsonObject.getString("nombre"));
                user.setUsername(jsonObject.getString("twitter"));

                userList.add(user);
            }
            return  userList;
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }

    }
}
