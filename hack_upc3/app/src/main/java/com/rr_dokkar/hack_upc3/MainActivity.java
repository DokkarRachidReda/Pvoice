package com.rr_dokkar.hack_upc3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.speech.RecognizerIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class MainActivity extends AppCompatActivity {
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getSpeechInput(view);


            }
        });
    }


    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 100);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String res = result.get(0);
                    String ress[]  = Compiler.getShapeScriptSTLfromImputString(res);
                    if (ress != null ){
                        //goforIt(ress);
                        final Doit doit = new Doit(ress,this);



                        final AlertDialog.Builder alertadd = new AlertDialog.Builder(this);
                        LayoutInflater factory = LayoutInflater.from(this);
                        int x = 0;
                        if(ress[0].equals("s")){x = R.layout.sphere;}
                        else if(ress[0].equals("c")){x = R.layout.cube;}
                        else if(ress[0].equals("g")){x = R.layout.gear;}
                        final View view = factory.inflate(x, null);
                        alertadd.setView(view);
                        alertadd.setPositiveButton("Print it", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                doit.execute();

                            }
                        });

                        alertadd.setNegativeButton("cancel", null);

                        alertadd.show();
                    }else {Toast.makeText(this,"we don't have this model yet :/",Toast.LENGTH_SHORT).show();}

                }
                break;
        }
    }









}


 class Doit extends AsyncTask<String, Void, String> {
    String [] res;
    Context c;
    public Doit(String[] res, Context c){
        this.res = res;
        this.c = c;
    }

    @Override
    protected String doInBackground(String... Voids) {

        FTPClient con = null;

        try
        {
            con = new FTPClient();
            con.connect("192.168.137.251");

            if (con.login("upload", "chocolatefries"))
            {
                Log.e("ress", "gggg");
                con.enterLocalPassiveMode(); // important!
                con.setFileType(FTP.BINARY_FILE_TYPE);


                //FileInputStream in = new FileInputStream(res[0]);

                File f = new File(c.getCacheDir()+"/"+res[1]);
                if (!f.exists())
                    try {

                        InputStream is = c.getAssets().open(res[1]);
                        byte[] buffer = new byte[1024];
                        is.read(buffer);



                        boolean result = con.storeFile(res[1], is);
                        is.close();
                        if (result) Log.v("upload_result", "succeeded");
                        else Log.v("upload_result", "ffff");
                        con.logout();
                        con.disconnect();

                } catch (Exception e) { throw new RuntimeException(e); }

                //

            }else {Log.e("ress", "fuuucked");}
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.e("ress", e.toString());
        }

        return "some message";
    }

    @Override
    protected void onPostExecute(String message) {
        //process message
    }
}
