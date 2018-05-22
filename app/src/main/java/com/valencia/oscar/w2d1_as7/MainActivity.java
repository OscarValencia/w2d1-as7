package com.valencia.oscar.w2d1_as7;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    final static private String MY_LOG_TAG = "MY_TAG";
    private String fileName = "Name.txt";
    private String filePath = "MyFileStorage";
    EditText name;
    Button saveButton;
    TextView storagePath;
    File file;
    FileOutputStream fileOutputStream;
    FileInputStream fileInputStream;
    DataInputStream dataInputStream;
    BufferedReader bufferedReader;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initElements();
    }

    public void saveName(View view) {
        String nameValue = name.getText().toString()+"\n";

        try{
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(nameValue.getBytes());
            fileOutputStream.close();
        }catch(Exception e){
            Toast.makeText(this,"Exception" + e.toString(),Toast.LENGTH_SHORT).show();
            Log.d(MY_LOG_TAG,e.toString());
        }
        name.setText("");
        Toast.makeText(this,"Name saved on external storage!",Toast.LENGTH_SHORT).show();
        storagePath.setText(file.getAbsolutePath());
    }

    public void showName(View view) {
        String string = "";
        data = "";
        try {
            fileInputStream = new FileInputStream(file);
            dataInputStream = new DataInputStream(fileInputStream);
            bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
            while((string=bufferedReader.readLine())!=null){
                data = data + string;
            }
            dataInputStream.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
            Log.d(MY_LOG_TAG,e.toString());
        } catch (IOException e) {
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
            Log.d(MY_LOG_TAG,e.toString());
        }
        Toast.makeText(this,"Most recent: "+data,Toast.LENGTH_SHORT).show();
        Log.d(MY_LOG_TAG,"Most recent: "+data);
        storagePath.setText(file.getAbsolutePath());
    }

    public void initElements(){
        name = findViewById(R.id.name);
        saveButton = findViewById(R.id.saveButton);
        storagePath = findViewById(R.id.storagePath);
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            saveButton.setEnabled(false);
            Toast.makeText(this,"Unable to use external storage!",Toast.LENGTH_SHORT).show();
            Log.d(MY_LOG_TAG,"Unable to use external storage!");
        }
        else {
            file = new File(getExternalFilesDir(filePath), fileName);
        }
    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }
}
