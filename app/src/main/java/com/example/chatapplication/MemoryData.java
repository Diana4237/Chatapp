package com.example.chatapplication;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MemoryData {
    public static void saveData(String data, Context context){
      try{
          FileOutputStream fileOutputStream=context.openFileOutput("datata.txt",Context.MODE_PRIVATE);
     fileOutputStream.write(data.getBytes());
     fileOutputStream.close();
      }
      catch (IOException e)
      {
          e.printStackTrace();
      }
    }

    public static void saveVideo(String data, Context context){
        try{
            FileOutputStream fileOutputStream=context.openFileOutput("videoo.txt",Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void saveLastMsgTS(String data,String chatId, Context context){
        try{
            FileOutputStream fileOutputStream=context.openFileOutput(chatId+".txt",Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void saveName(String data, Context context){
        try{
            FileOutputStream fileOutputStream=context.openFileOutput("namee.txt",Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static String getData(Context context) {
    String data="";
    try
    {
        FileInputStream fis = context.openFileInput("datata.txt");
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader bufferedReader = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        data = sb.toString();
        fis.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
        return data;
    }


    public static String getVideo(Context context) {
        String data="";
        try
        {
            FileInputStream fis = context.openFileInput("videoo.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static String getName(Context context) {
        String data="";
        try
        {
            FileInputStream fis = context.openFileInput("namee.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


    public static String getLastMsgTS(Context context,String chatId) {
        String data="0";
        try
        {
            FileInputStream fis = context.openFileInput(chatId+".txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
