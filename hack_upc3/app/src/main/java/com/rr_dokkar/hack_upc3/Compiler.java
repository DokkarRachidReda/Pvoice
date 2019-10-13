package com.rr_dokkar.hack_upc3;


import android.content.Context;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Compiler {


    private static ArrayList<String> shapes =new ArrayList<String> (Arrays.asList(
            "sphere","cube","gear"));


    public ArrayList<String> getAllAvailableShapes(){
        return shapes;
    }


    /// methode one :
    public static String containsWords(String inputString) {
        boolean found = true;
        for (String shape_ : shapes) {
            if (inputString.toLowerCase().contains(shape_)) {
                return shape_;
            }
        }
        return null;
    }


    /// methode two :

    public static String[] getShapeScript(String shape_){

        if (shape_ == null )return  null;
        File scriptFile = null;
        switch (shape_){
            case "sphere":
                Log.e("tyyR","good");
                String [] res = {"s","sphere.txt"};
                return res;


            case "cube" :
                Log.e("tyyR","good");
                String [] res1 = {"c","cube.txt"};
                return res1;

            case "gear" :
                Log.e("tyyR","good");
                String [] res2 = {"g","gear.txt"};
                return res2;

                default: Log.e("tyyR","def");
                return null;
        }

    }


    /// this method gives you the script of the file that the user mentioned in the input string

    public static String[] getShapeScriptSTLfromImputString(String imputString){
        return  getShapeScript(containsWords(imputString));
    }



}
