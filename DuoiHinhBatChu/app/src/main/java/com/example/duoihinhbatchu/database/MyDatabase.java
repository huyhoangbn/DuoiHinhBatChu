package com.example.duoihinhbatchu.database;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.duoihinhbatchu.BuildConfig;
import com.example.duoihinhbatchu.Question;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MyDatabase extends SQLiteOpenHelper {

    private Context context;
    private String DB_PATH = "data/data/" + BuildConfig.APPLICATION_ID + "/databases/";
    private static String DB_NAME ="dhbc.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    public MyDatabase(Context context){
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.context = context;

        boolean dbexis = checkdatabase();

        if(!dbexis){
            System.out.println("Database doesn't exist!");
            createDatabase();
        }

    }

    //lấy giá trị trong cái database đó
    public List<Question> getQuestionDB(int id){
        db = this.getReadableDatabase();

        List<Question> questions = new ArrayList<>();

        String select = "SELECT * FROM question where id = " + id;

        Cursor cursor = db.rawQuery(select, null);

        if(cursor.moveToFirst()){
            do {
                Question question = new Question();
                question.setContent(cursor.getString(1));
                question.setGiaiNghia(cursor.getString(2));
                question.setOk(cursor.getInt(3));
                questions.add(question);

            }while (cursor.moveToNext());
        }

        return questions;
    }

    private boolean checkdatabase() {
        boolean checkdb = false;
        try{
            String myPath = DB_PATH + DB_NAME;
            File dbFile = new File(myPath);
            checkdb = dbFile.exists();
        }catch (SQLException e){
            System.out.println("Database doesn's exist");
        }

        return checkdb;
    }

    private void createDatabase() {

        try {
            this.getReadableDatabase();
            copyDatabase();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void copyDatabase() throws IOException {

        db = this.getWritableDatabase();
        AssetManager dirPath = context.getAssets();
        InputStream myInput = context.getAssets().open("databases/" + DB_NAME);
        File file = new File((DB_PATH));
        file.mkdirs();

        OutputStream myOutput = new FileOutputStream(DB_PATH + DB_NAME);

        byte[] buffer = new byte[1024];
        int length;

        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
    //truy vấn không trả kết quả: CREAT, INSERT , UPDATE ,DELETE....
    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();// đọc vs viết đc
        database.execSQL(sql);// thực thi
    }
    //truy vấn trả về kết quả, sử dụng con trỏ Cursor để duyệt
    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();// chỉ đọc đc
        return database.rawQuery(sql,null);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

