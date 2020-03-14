package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class registeration extends AppCompatActivity {
    DatabaseHelper db;
EditText username , email, phone,password,confirmpas ;
Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_registeration);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        db =new DatabaseHelper(this);

        email =(EditText) findViewById(R.id.email);
        username=(EditText)findViewById(R.id.username);
        phone= (EditText) findViewById(R.id.phone);
        password =(EditText) findViewById(R.id.password);
        confirmpas=(EditText) findViewById(R.id.confirmpas);
        button = (Button)findViewById(R.id.signup);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emaill = email.getText().toString();
                String user= username.getText().toString();
                String phonee = phone.getText().toString();
                String pass= password.getText().toString();
                String cpass=confirmpas.getText().toString();
                if(emaill.equals("")|| user.equals("")|| phonee.equals("")
                || pass.equals("")||cpass.equals("")){
                    Toast.makeText(getApplicationContext(),"Fiels are empty",Toast.LENGTH_SHORT).show();
                } else{
                    if (pass.equals(cpass)){
                        Boolean checkemail=db.checkemail(emaill);
                        if(checkemail==true){
                            Boolean insert =db.insert(emaill,pass);
                            if (insert ==true){
                                Toast.makeText(getApplicationContext(),"Registerd successfully",Toast.LENGTH_SHORT).show();

                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Email Alredy exist", Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Password do not match", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }
}
