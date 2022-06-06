package com.example.reactiongame;
import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileIO {
    Context context;
    public String file_name="raacthighscores.txt";

    public FileIO(Context incontext){
        this.context=incontext;
    }

    public List<ScoreLayout> readFile(){//this will read the file, line by line and return the list of the score entries
        FileInputStream fileread;
        BufferedReader filebufread;
        String file_path= context.getFilesDir()+"/"+file_name;
        System.out.println(file_name);
        File entryfile= new File(file_path);
        List<ScoreLayout>  entrylist= new ArrayList<>(20);
        try{//check if file exists
            if(entryfile.exists()){
                fileread= new FileInputStream(entryfile);
                filebufread= new BufferedReader(new InputStreamReader(fileread));//using this so that I can split the entry
                String currline=filebufread.readLine();
                while(currline != null){//read to enmd of file and add entries to the entrylist as we go
                    String[] currentry= currline.split("\t");
                    ScoreLayout newlistentry= new ScoreLayout(currentry[0],currentry[1],currentry[2]);
                    entrylist.add(newlistentry);
                    currline=filebufread.readLine();
                }

            }
        }catch(Exception e){

        }
        return entrylist;

    }

    public boolean writetofile(List<ScoreLayout> entrylist){
        String file_path= context.getFilesDir()+"/"+file_name;
        System.out.println(file_path);
        File entryfile= new File(file_path);
        if(!entryfile.exists()){
            try {
                entryfile.createNewFile();
            }
            catch(IOException e){

            }
        }
        //begin writing the entire file
        try {
            FileWriter writer = new FileWriter(entryfile);
            for (ScoreLayout currentry : entrylist) {
                writer.append(currentry.toString() + "\n");
            }
            writer.close();
        }catch(IOException e){

        }
        return true;//only really here to avoid errors

    }


}
