package com.example.myowndiary;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowMyData extends Activity {
    int nowData=0;
    Cursor cursor;
    TextView date;
    TextView t1;
    Button previous;
    Button next;
    Button delete;
    Button modify;
    String diary_content;
    String diary_date;
    int numburOfData;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);
        
        date=(TextView) findViewById(R.id.date);
        t1=(TextView) findViewById(R.id.t1);
        previous=(Button)findViewById(R.id.bprevious);

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previous();
            }
        });
        next=(Button)findViewById(R.id.bnext);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
        delete=(Button)findViewById(R.id.bdelete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
        modify=(Button)findViewById(R.id.bmodify);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modify();
            }
        });
        try{
            DBManager dbmgr=new DBManager(this);
            SQLiteDatabase sdb=dbmgr.getReadableDatabase();
            cursor=sdb.query("diaryTb", null,null,null,null,null,null);
            numburOfData=cursor.getCount();
            cursor.moveToFirst();

            if(numburOfData==0)nowData=0;
            else nowData=1;

            if(cursor.getCount()>0){
                diary_date=cursor.getString(0);
                diary_content=cursor.getString(1);
            }

            Log.d("shjang" , "cursor cnt == " + cursor.getCount());

            cursor.close();
            dbmgr.close();
        }catch(SQLiteException e){

        }
        date.setText(diary_date);
        t1.setText(diary_content);
    }
    public void next(){
        try{
            DBManager dbmgr=new DBManager(this);
            SQLiteDatabase sdb=dbmgr.getReadableDatabase();
            cursor=sdb.query("diaryTB", null,null,null,null,null,null);
            if(numburOfData==0)nowData=0;
            if(cursor.getCount()>0 && nowData <= numburOfData){
                nowData+=1;
                if(nowData >= numburOfData) nowData=numburOfData;

                cursor.moveToPosition(nowData-1);
                diary_date=cursor.getString(0);
                diary_content=cursor.getString(1);
            }

            cursor.close();
            dbmgr.close();
        }catch(SQLiteException e){

        }date.setText(diary_date);
        t1.setText(diary_content);
    }
    public void previous(){
        try{
            DBManager dbmgr=new DBManager(this);
            SQLiteDatabase sdb=dbmgr.getReadableDatabase();
            cursor=sdb.query("diaryTB", null,null,null,null,null,null);
            if(numburOfData==0)nowData=0;
            if(cursor.getCount()>0 && nowData>1){
                nowData=-1;

                if(nowData<=1)nowData=1;
                cursor.moveToPosition(nowData-1);
                diary_date=cursor.getString(0);
                diary_content=cursor.getString(1);
            }
            cursor.close();
            dbmgr.close();
        }catch(SQLiteException e){

        }date.setText(diary_date);
        t1.setText(diary_content);
    }
    public void delete(){
        if(numburOfData >= 1)
            try{
                DBManager dbmgr=new DBManager(this);
                SQLiteDatabase sdb;
                sdb=dbmgr.getWritableDatabase();
                cursor=sdb.query("diaryTB",null,null,null,null,null,null);
                cursor.moveToPosition(nowData-1);

                if(cursor.getCount()>0){
                    diary_content=cursor.getString(0);
                    nowData -= 1;
                    String sql = String.format("DELETE FROM diaryTB WHERE data1 = '%s'", diary_date);
                    sdb.execSQL(sql);
                }



                cursor.close();
                dbmgr.close();
            }catch(SQLiteException e){

            }
    }
    public void modify(){
        Intent it=new Intent();
        it=new Intent(this, ModifyMyData.class);
        String msg=nowData + "";
        it.putExtra("it_name", msg);
        startActivity(it);
        finish();
    }
}
