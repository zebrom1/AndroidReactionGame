package com.example.reactiongame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Highscorelist extends AppCompatActivity {
    static final int ADD_SCORE_REQUEST= 1;
    List<ScoreLayout> highscorelist;//the list for holding the score entries
    ListView lstview;
    Button Backbtn;





    Toolbar mainactionbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println(highscorelist);
        setContentView(R.layout.activity_main);
        Backbtn= findViewById(R.id.backbtn);

        setSupportActionBar(mainactionbar);

        highscorelist=new ArrayList<>(20);
        lstview = findViewById(R.id.ScoreList);//linking variable to object
        Populatelist();
        Backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gamestart= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(gamestart);
            }
        });
    }

    public void Populatelist(){
        FileIO fileinteract= new FileIO(this);
        highscorelist=fileinteract.readFile();
        Collections.sort(highscorelist,Collections.reverseOrder());
        if(highscorelist.size()<=20){
            StableArrayAdapter listadapter= new StableArrayAdapter(this,R.layout.fragment_scores,highscorelist);
            lstview.setAdapter(listadapter);

        }
        else{//if the highscorelist size if more than 20
            List<ScoreLayout> highscorelisttemp= new ArrayList<>();
            for(int listcount=0; listcount<20; listcount++) {//seeing how the list is sorted already, just read through and pick out the first 20, then populate list
                highscorelisttemp.add(highscorelist.get(listcount));
            }
            highscorelist=highscorelisttemp;
            StableArrayAdapter listadapter= new StableArrayAdapter(this,R.layout.fragment_scores,highscorelist);
            lstview.setAdapter(listadapter);
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent passentry){
        super.onActivityResult(requestCode,resultCode,passentry);
        if(requestCode==ADD_SCORE_REQUEST){
            if(resultCode==RESULT_OK){
                String newentryname=passentry.getStringExtra("newName");
                String newentryscore=passentry.getStringExtra("newScore");
                String newentrydate=passentry.getStringExtra("newDate");
                ScoreLayout newScoreentry= new ScoreLayout(newentryname,newentryscore,newentrydate);

                highscorelist.add(newScoreentry);

                FileIO filewrite= new FileIO(this);
                filewrite.writetofile(highscorelist);
                Populatelist();
            }
        }

    }
}