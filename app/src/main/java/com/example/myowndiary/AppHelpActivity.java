package com.example.myowndiary;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AppHelpActivity extends Activity {
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        TextView textview = new TextView(this);
        textview.setTextSize(20);
        textview.setText("나만의 일기장" +
                " "+"만든이: 강서윤");
        setContentView(textview);
    }
}
