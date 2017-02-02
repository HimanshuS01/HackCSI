package com.codingblocks.ChatBot.ChatbotFiles;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.Toast;

import com.codingblocks.ChatBot.Networking.ApiClientChatbot;
import com.codingblocks.ChatBot.GamePart.GameInitActivity;
import com.codingblocks.customnavigationdrawer.R;
import com.roger.catloadinglibrary.CatLoadingView;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChatActivity extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100 ;
    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;
    private ImageView mic_button;

    CatLoadingView mView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_PROGRESS);

        super.onCreate(savedInstanceState);
        mView = new CatLoadingView();

        setContentView(R.layout.activity_chatbot);
        initControls();
    }

    private void initControls() {
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.chatSendButton);
        mic_button=(ImageView) findViewById(R.id.mic);
        loadDummyHistory();

        mic_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setId(122);
                chatMessage.setMessage(messageText);
                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                chatMessage.setMe(false);

                messageET.setText(""); //to make edittext aside send button empty

                displayMessage(chatMessage);
                networkcall(chatMessage.getMessage());

            }
        });
        messagesContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(adapter.getItem(position).getMessage().contains("Let me Google that for you"))
                {
                    String url=adapter.getItem(position).geturl();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }


            }
        });
    }


    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Speech Not Supported",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    messageET.setText(result.get(0));
                }
                break;
            }

        }
    }

    //this mehtod will call api link and return String...this will run on worker thread...whle this is happening show progress dialog.after this is finished call
    private void networkcall(String message) {
        String text_result="";
        //by network call take the sstring



        Call<Question> call= ApiClientChatbot.getInterface().getAnswer(message);
        mView.show(getSupportFragmentManager(), "");
        call.enqueue(new Callback<Question>() {
            @Override
            public void onResponse(Call<Question> call, Response<Question> response) {
                if(response.isSuccessful())
                set_server_message(response.body().result.toString());
                else
                    Toast.makeText(ChatActivity.this,"Sorry Unexpected problem occured",Toast.LENGTH_LONG).show();
                mView.dismiss();
            }

            @Override
            public void onFailure(Call<Question> call, Throwable t) {

                Toast.makeText(ChatActivity.this,"Please connect to Internet",Toast.LENGTH_LONG).show();
                mView.dismiss();


            }
        });

    }

    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }



    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    private void loadDummyHistory(){

        chatHistory = new ArrayList<ChatMessage>();

        ChatMessage msg = new ChatMessage();
        msg.setId(1);
        msg.setMe(true);
        msg.setMessage("Hi");
        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg);
        ChatMessage msg1 = new ChatMessage();
        msg1.setId(2);
        msg1.setMe(true);
        msg1.setMessage("This is your Chatbot.Ask me any question!!");
        msg1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg1);

        adapter = new ChatAdapter(ChatActivity.this, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

        for(int i=0; i<chatHistory.size(); i++) {
            ChatMessage message = chatHistory.get(i);
            displayMessage(message);
        }
    }
    public void set_server_message(String text)
    {
        mView.dismiss();
        if(text.contains("youtube.com"))
        {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(text));
            startActivity(intent);
        }

        else
        {
            ChatMessage msg = new ChatMessage();
            msg.setId(1);
            msg.setMe(true);
            if(text.contains("Let me Google that for you"))
            { String url=text.substring(29);
            msg.setMessage("Let me Google that for you - Click here");
                msg.seturl(url);
            }
            else if(text.length()>9 && "<a href=".equals(text.substring(0,8)))
            {
                int index1=text.indexOf(">");
                int index2=text.lastIndexOf(">");
                int i3=text.lastIndexOf("</a");

                String ans=text.substring(index1+1,i3)+text.substring(index2+1);
                msg.setMessage(""+ans);
            }
            else if(text.equals("101"))
            {
                msg.setMessage("Opening Quiz Game");
                msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));

                startActivity(new Intent(this,GameInitActivity.class));
            }
            else
            msg.setMessage(text);
            msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
            displayMessage(msg); // might have to add in a Arraylist as well
        }
    }
}