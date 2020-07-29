package com.example.application1.saveinfile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    EditText etName,etSurname;
    TextView tvResult;
    ArrayList<Person> persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        tvResult = findViewById(R.id.tvResult);
        
        persons = new ArrayList<Person>();
        LoadData();
    }
    public void btnAddData(View v)
    {
        String name = etName.getText().toString();
        String surname = etSurname.getText().toString();
        Person person = new Person(name,surname);
        persons.add(person);
        
        setTextToTextView();
    }

    private void setTextToTextView() {
        String text="";
        for(int i =0;i<persons.size();i++)
        {
            text = text+persons.get(i).getName()+ " "+ persons.get(i).getSurname()+"\n";
        }
        tvResult.setText(text);
    }
    public void LoadData()
    {
        persons.clear();
        File file = getApplicationContext().getFileStreamPath("Data.txt");
        String lineFromFile;
        if(file.exists())
        {
            try
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("Data.txt")));
                while ((lineFromFile = reader.readLine()) !=null)
                {
                    StringTokenizer tokens = new StringTokenizer(lineFromFile,",");
                    Person person = new Person(tokens.nextToken(),tokens.nextToken());
                    persons.add(person);
                }
                reader.close();
                setTextToTextView();
            }
            catch (IOException e)
            {
                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void btnSaveData(View v)
    {
        try{
            FileOutputStream file= openFileOutput("Data.txt",MODE_PRIVATE);
            OutputStreamWriter outputFile = new OutputStreamWriter(file);

            for(int i=0;i<persons.size();i++)
            {
                outputFile.write(persons.get(i).getName()+","+ persons.get(i).getSurname()+"\n");
            }
            outputFile.flush();
            outputFile.close();
            Toast.makeText(MainActivity.this,"Successfully Saved",Toast.LENGTH_SHORT).show();
        }
        catch (IOException e)
        {
            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }
}
