package com.example.sqlitelistview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user_lit.db";
    private static final String TABLE_NAME = "user";
    private static final String ID = "id";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final int VERSION_NO = 1;
    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+EMAIL+" TEXT NOT NULL, "+PASSWORD+" TEXT NOT NULL )";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private  Context context;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION_NO);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(CREATE_TABLE);
            Toast.makeText(context,"database created",Toast.LENGTH_LONG).show();

        }catch (Exception e){
            Log.d("name","m"+e);
            Toast.makeText(context,"database create failed",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
       try {
           sqLiteDatabase.execSQL(DROP_TABLE);
           onCreate(sqLiteDatabase);
           Toast.makeText(context,"database Updated",Toast.LENGTH_LONG).show();
       }catch (Exception e){
           Log.d("exp","",e);
       }

    }

    public long RowInsert(UserModel userModel){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMAIL,userModel.getEmail());
        contentValues.put(PASSWORD,userModel.getPassword());

        long rowId = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);

        return  rowId ;
    }

    public Cursor ShowAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_NAME,null);
        return  cursor ;
    }
}
