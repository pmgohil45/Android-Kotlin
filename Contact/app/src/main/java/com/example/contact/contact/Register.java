package com.example.contact.contact;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.contact.R;
import com.example.contact.contact.database.Contact;
import com.example.contact.contact.database.DbHelper;

import java.io.IOException;

public class Register extends AppCompatActivity {
Button save,cancle;
ImageView imageView;
private Uri imagefilepath;
private Bitmap imagetostore;
private static final int PICK_IMAGE_REQUEST=100;
EditText add_name,add_number,add_email,add_organization,add_address,add_webAddress,add_notes,add_nickname;    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findid();

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleardata();
            }
        });


            }

    private void cleardata() {
    add_name.setText("");
    add_number.setText("");
    add_email.setText("");
    add_organization.setText("");
    add_nickname.setText("");
    add_notes.setText("");
    add_webAddress.setText("");
    add_address.setText("");
        }

    private void findid() {
        add_name=(EditText) findViewById(R.id.add_name);
        add_number=(EditText)findViewById(R.id.add_phone);
        add_email=(EditText) findViewById(R.id.add_Email);
        add_organization=(EditText) findViewById(R.id.add_Organization);
        add_address=(EditText) findViewById(R.id.add_Address);
        add_webAddress=(EditText) findViewById(R.id.add_webAdderess);
        add_notes=(EditText) findViewById(R.id.add_Notes);
        add_nickname=(EditText) findViewById(R.id.add_Nickname);
        save=(Button)findViewById(R.id.save1);
        cancle=(Button)findViewById(R.id.CANCLE);
        imageView=(ImageView) findViewById(R.id.pick_image);

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Mobilepattern="[0-9]{10}";

                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    String name=add_name.getText().toString();
                    if(TextUtils.isEmpty(name))
                    {
                        add_name.setError("Require");
                        return;
                    }
                    String phone=add_number.getText().toString();
                    if(TextUtils.isEmpty(phone))
                    {
                        add_number.setError("Require");
                        return;
                    }
                    if(!add_number.getText().toString().matches(Mobilepattern))
                    {
                            Toast.makeText(getApplicationContext(),"please Enter valid 10 digit phone number",Toast.LENGTH_SHORT).show();
                            return;
                    }
                    if(!add_email.getText().toString().matches(emailPattern))
                    {
                        Toast.makeText(getApplicationContext(),"Please Enter Valid Email !!",Toast.LENGTH_SHORT).show();
                    }


                    String email=add_email.getText().toString();
                    String organization=add_organization.getText().toString();
                    String address=add_address.getText().toString();
                    String webaddress=add_webAddress.getText().toString();
                    String notes=add_notes.getText().toString();
                    String nickname=add_nickname.getText().toString();

                    //DbHelper d=new DbHelper(this);
                  //  Context c=getBaseContext();
                // DbHelper dbHelper = new DbHelper(c);
                    DbHelper db=new DbHelper(Register.this);
                    Contact contact=new Contact(name,phone,email,organization,address,webaddress,notes,nickname);
                    long result =  db.addConatct(contact);
                   // Toast.makeText(getApplicationContext(),String.valueOf(result),Toast.LENGTH_SHORT).show();
                        if(result != 1)
                        {
                            Intent i=new Intent(Register.this,MainActivity.class);
                            startActivity(i);
                           // Toast.makeText(getApplicationContext(),"save data ...",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"failed ...",Toast.LENGTH_SHORT).show();

                        }
                }
            });

    }


public  void chooseimg(View view)
{
    Intent obj=new Intent();
    obj.setType("image/*");
    obj.setAction(Intent.ACTION_GET_CONTENT);
    startActivityForResult(obj,PICK_IMAGE_REQUEST);
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            imagefilepath=data.getData();
            try {
                imagetostore= MediaStore.Images.Media.getBitmap(getContentResolver(),imagefilepath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            imageView.setImageBitmap(imagetostore);
        }
    }
//    private void ins(View view)
//    {
//            String name=add_name.getText().toString();
//            if(TextUtils.isEmpty(name))
//            {
//                add_name.setError(name);
//            }
//            String phone=add_number.getText().toString();
//            if(TextUtils.isEmpty(phone))
//            {
//                add_number.setError(phone);
//            }
//            String email=add_email.getText().toString();
//            String organization=add_organization.getText().toString();
//            String address=add_address.getText().toString();
//            String webaddress=add_webAddress.getText().toString();
//            String notes=add_notes.getText().toString();
//            String nickname=add_nickname.getText().toString();
//        Contact  contact=new Contact(name,phone,email,organization,address,webaddress,notes,nickname);
//        DbHelper dbHelper=new DbHelper(this);
//        long result=dbHelper.addConatct(contact);
//        if(result!=1)
//        {
//            Toast.makeText(this,"data are saved ", Toast.LENGTH_SHORT).show();
//
//        }
//        else
//        {
//            Toast.makeText(this,"Failed!!! ",Toast.LENGTH_SHORT).show();
//
//        }
    }
