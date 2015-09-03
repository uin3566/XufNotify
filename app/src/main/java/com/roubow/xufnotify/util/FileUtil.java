package com.roubow.xufnotify.util;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Xuf on 2015/9/3.
 */
public class FileUtil {

    private static final String TAG = "FileUtil";

    public static void saveString(Context context, String fileName, String str) throws IOException{
        BufferedWriter writer = null;
        try{
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            OutputStreamWriter osWriter = new OutputStreamWriter(fos);
            writer = new BufferedWriter(osWriter);
            writer.write(str);
        }finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public static void deleteFile(Context context, String fileName){
        String path = "data/data/" + context.getPackageName() + "/files/" + fileName;
        File file = new File(path);
        file.delete();
    }

    public static String getString(Context context, String fileName) throws IOException{
        String ret = null;
        BufferedReader reader = null;
        try {
            FileInputStream fis= context.openFileInput(fileName);
            InputStreamReader isReader = new InputStreamReader(fis);
            reader = new BufferedReader(isReader);
            String line = null;
            StringBuilder strBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null){
                strBuilder.append(line);
            }
            ret = strBuilder.toString();
        } finally {
            if (reader != null){
                reader.close();
            }
        }

        return ret;
    }

}
