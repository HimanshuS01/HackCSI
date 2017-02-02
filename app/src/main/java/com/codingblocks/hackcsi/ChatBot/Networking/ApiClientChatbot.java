package com.codingblocks.ChatBot.Networking;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sachin on 9/4/2016.
 */
public class ApiClientChatbot {
    private static ApiInterfaceChatbot mService;

    public static ApiInterfaceChatbot getInterface() {
        if (mService == null) {
            Gson gson = new GsonBuilder().create();
            //.excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC)
            //.create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://edu-chatbot.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            mService = retrofit.create(ApiInterfaceChatbot.class);
        }
        return mService;
    }
}
