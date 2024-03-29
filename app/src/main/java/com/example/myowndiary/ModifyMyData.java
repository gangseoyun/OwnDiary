package com.example.myowndiary;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ModifyMyData extends Activity {
    int nowData=0;
    Cursor cursor;
    TextView date;
    EditText t1;
    String diary_date;
    String diary_content;
    Button modifyData;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.modify);
        date=(TextView)findViewById(R.id.date);
        t1=(EditText)findViewById(R.id.t1);
        Intent it = getIntent();
        String str_name=it.getStringExtra("it_name");
        nowData = Integer.parseInt(str_name);
        try{
            DBManager dbmgr = new DBManager(this);
            SQLiteDatabase sdb=dbmgr.getWritableDatabase();
            cursor=sdb.query("diaryTB", null,null,null,null,null,null);
            cursor.moveToPosition(nowData-1);
            diary_date=cursor.getString(0);
            diary_content=cursor.getString(1);
            cursor.close();
            dbmgr.close();
        } catch (SQLiteException e){

        }date.setText(diary_date);
        t1.setText(diary_content);
    }
    public void modifyData(View v){
        try{
            DBManager dbmgr=new DBManager(this);
            SQLiteDatabase sdb=dbmgr.getWritableDatabase();
            cursor=sdb.query("diaryTB", null,null,null,null,null,null);

            cursor.moveToPosition(nowData-1);
            diary_date=cursor.getString(0);
            String str_sex=t1.getText().toString();
            String sql=String.format("UPDATE diaryTB SET data2='%s' WHERE data1='%s'",str_sex, diary_date);
            sdb.execSQL(sql);
            cursor.close();
            dbmgr.close();
        }catch (SQLiteException e){

        }
        Intent it=new Intent();
        it=new Intent(this, MainActivity.class);
        startActivity(it);
        finish();
    }
    public void canceltData(View v){
        Intent it=new Intent();
        it=new Intent(this, MainActivity.class);
        startActivity(it);
        finish();
    }
}
