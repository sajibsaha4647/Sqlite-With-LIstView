package com.example.sqlitelistview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseHelper databaseHelper ;
    private EditText EmailEdit,PassEdit;
    private Button SaveButton,ShowLIstBtn;
    private UserModel userModel ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        EmailEdit = findViewById(R.id.Emailid) ;
        PassEdit = findViewById(R.id.PasswordId);

        SaveButton = findViewById(R.id.SaveId);
        ShowLIstBtn = findViewById(R.id.ShowList);
        SaveButton.setOnClickListener(this);
        ShowLIstBtn.setOnClickListener(this);

    }

    boolean isEmailValid(CharSequence email) { //check email address
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.SaveId){
            String email = EmailEdit.getText().toString();
            String passwords = PassEdit.getText().toString();
            userModel = new UserModel(email,passwords);

            if(email.equals("") || passwords.equals("")){
                Toast.makeText(this,"All filed are required",Toast.LENGTH_LONG).show();
            }else if(!isEmailValid(email)){
                Toast.makeText(this,"Invalid email address",Toast.LENGTH_LONG).show();
            }else{
                userModel.setEmail(email);
                userModel.setPassword(passwords);

                long rowId = databaseHelper.RowInsert(userModel);

                if(rowId>0){
                    EmailEdit.setText("");
                    PassEdit.setText("");
                    Toast.makeText(this,"Data Save successFull",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this,"Save UnSuccessFull",Toast.LENGTH_LONG).show();
                }

            }

        }else if(view.getId() == R.id.ShowList){
            Intent intent = new Intent(MainActivity.this,ListViewActivity.class);
            startActivity(intent);
        }

    }
}