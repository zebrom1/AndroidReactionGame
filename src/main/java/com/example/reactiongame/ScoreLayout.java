package com.example.reactiongame;
import java.util.Date;
import java.text.SimpleDateFormat;

/*
this class basically allows for me to easier input scores as well as compare them
 */

public class ScoreLayout implements Comparable<ScoreLayout>{

    private String name;
    private String score;
    private String date;

    public ScoreLayout(String inname,String inscore,String indate){//constructor
        this.name=inname;
        this.score=inscore;
        this.date=indate;
    }
    public String getName(){//accessor function
        return name;
    }
    public String getScore(){//accessor function
        return score;
    }
    public String getDate(){//accessor function
        return date;
    }
    public void setName(String inname){
        this.name=inname;
    }
    public void setScore(String inscore){
        this.score=inscore;
    }
    public void setDate(String indate){
        this.date=date;
    }

    /*basically this function wil first compare scores based on the value of said scores
    then the date and time they are achieved
    then worst case they are sorted alphabetically
     */
    @Override
    public int compareTo(ScoreLayout newScore){
        int currscore= Integer.parseInt(this.getScore());
        int newscore= Integer.parseInt(newScore.getScore());
        if(currscore < newscore){
            return 1;//this score should be added to the file
        }
        else if(currscore==newscore){//if the scores are the same move on to the date
            Date currentrydate= null;
            Date newdate=null;
            try{
                currentrydate= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(this.getDate());
                newdate= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(newScore.getDate());
            }catch(Exception e){

            }
            int laterdate = newdate.compareTo(currentrydate);
            if(laterdate != 0){//if the strings are not equivalent
                return laterdate;//return value and we'll work from there
            }
            else{//if they are equivalent
                String currentryname=this.getName();
                String newname=newScore.getName();
                int namecompare= currentryname.compareToIgnoreCase(newname);
                if(namecompare < 0){//if the currententry is higher on alpahbetical scale
                    return -1;//do not add this entry to file
                }
                else if(namecompare>0){//if the currententry is higher on alpahbetical scale
                    return 1;//add this entry to file
                }
                else{
                    return 0;
                }
            }
        }
        else{//if score is less than currentry
            return -1;//don't add it to file
        }

    }
    @Override
    public String toString(){//useful for writing to a file
        return this.getName() + "\t" + this.getScore() + "\t" +this.getDate();
    }

}
