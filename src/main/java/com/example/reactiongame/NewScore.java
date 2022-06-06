/*
This class basically acts like a form, doing basic validations and handling the information for new
entries into the high score
 */

package com.example.reactiongame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewScore extends AppCompatActivity {

    Button submitscorebtn;
    Button returnbtn;
    EditText name;
    EditText score;
    EditText date;


    boolean save_enable=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_score);
        setTitle("New Score");

        name= findViewById(R.id.new_name);
        submitscorebtn=(Button)findViewById(R.id.submitscorebtn);
        submitscorebtn.setEnabled(false);

        submitscorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View nextview){//when the submit button is clicked
                Intent ScreenReturn = new Intent();
                ScreenReturn.putExtra("newName", name.getText().toString());
                ScreenReturn.putExtra("newScore", score.getText().toString());
                ScreenReturn.putExtra("newDate", date.getText().toString());
                setResult(Activity.RESULT_OK, ScreenReturn);
                finish();

            }
        });



        returnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View nextview){//when the submit button is clicked
                Intent ScreenReturn = new Intent();
                setResult(Activity.RESULT_CANCELED, ScreenReturn);
                finish();
            }
        });

        SimpleDateFormat dateformat= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        date.setText(dateformat.format(new Date()));
        name.addTextChangedListener(new TextWatcher() {//checking to see if name empty
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validate_input();//if the text is changed see if the input is valid, and if it is, enable submit button

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        score.addTextChangedListener(new TextWatcher() {//checking if score is a number
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validate_input();//if the text is changed see if the input is valid, and if it is, enable submit button

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        date.addTextChangedListener(new TextWatcher() {//checking if date is correct format
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validate_input();//if the text is changed see if the input is valid, and if it is, enable submit button

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public void validate_input (){
        String inputName=name.getText().toString();
        String inputscore=score.getText().toString();
        String inputdate= date.getText().toString();
        boolean validname=false;
        boolean validscore=false;
        boolean validdate=false;
        if(inputName.length()>0 &&inputName.length()<=30){
            name.setError(null);
            validname=true;
        }
        else{
            name.setError("Name needs to be between 1 and 30 characters");
        }
        if(!inputscore.isEmpty()){//if it is now
            try {
                int parsedscore = Integer.parseInt(inputscore);//check if it is a number
                if (parsedscore<=0){
                    score.setError("Your score must be positive");
                }
                else{
                    validscore=true;
                    score.setError(null);
                }
            }
            catch(Exception e){//if it is not a number
                score.setError("Score must be an integer");
            }
        }
        if(!inputdate.isEmpty()){//if this is not empty, check to see id the input is not a future date, and fits the format given
            SimpleDateFormat dateformat= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            dateformat.setLenient(false);
            Date today=new Date();
            try{
                Date parsedate = dateformat.parse(inputdate);
                int pastorfuturedate= today.compareTo(parsedate);
                if(pastorfuturedate<0){
                    save_enable=false;//valid date format, but not what I want
                    date.setError("that date is in the future");
                }
                else {
                    validdate = true;
                    date.setError(null);
                }

            }catch(ParseException pe){
                date.setError("please format your date in the following way: MM/dd/yyyy HH:mm:ss ");
            }

        }
        save_enable=validname&&validscore&&validdate;
        invalidateOptionsMenu();
        if(save_enable){
            submitscorebtn.setEnabled(true);
        }
        else{
            submitscorebtn.setEnabled(false);
        }

    }
}