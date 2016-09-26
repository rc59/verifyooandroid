package com.software.verifyoo.verifyooofflinesdk.Utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by roy on 12/28/2015.
 */
public class Files {
    public static String GetFileName(String userName) {
        String fileName = String.format("%s-%s", userName, Consts.STORAGE_NAME);
        return fileName;
    }

    public static void writeToFile(String data, OutputStreamWriter outputStreamWriter) {
        try {
            //File file = new File(Environment.getExternalStorageDirectory(), "/shapeRecognition/user.txt");
            //OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("shapesrec.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
        catch (Exception e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static String readFromFile(InputStream inputStream) {

        String ret = "";

        try {
            //InputStream inputStream = openFileInput("shapesrec.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        catch (Exception e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret;
    }
}
