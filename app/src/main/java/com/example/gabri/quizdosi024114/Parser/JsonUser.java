package com.example.gabri.quizdosi024114.Parser;

import com.example.gabri.quizdosi024114.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabri on 19/04/2018.
 */

public class JsonUser {

    public static List<User> getData(String content) throws JSONException {

        JSONArray jsonArray = new JSONArray(content);
        List<User> postList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++){

            JSONObject item = jsonArray.getJSONObject(i);


            User user = new User();
            user.setName(item.getString("name"));
            user.setUsername(item.getString("username"));
            user.setEmail(item.getString("email"));

            JSONObject itemData = item.getJSONObject("address");
            user.setStreet(itemData.getString("street"));


            postList.add(user);

        }

        return postList;
    }
}
