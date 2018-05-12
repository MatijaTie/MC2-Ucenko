package com.example.tie.mc2.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tie.mc2.BoardViews.RootView;
import com.example.tie.mc2.BoardViews.ViewBuilder;
import com.example.tie.mc2.LoadingTask;
import com.example.tie.mc2.R;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Tie on 06-May-18.
 */

public class LoadingActivity extends AppCompatActivity {
    private ArrayList<RootView> childRootViews;
    TextView progressBar;
    ProgressBar progressBarLine;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        Bundle bundledData = getIntent().getExtras();
        Intent data = (Intent) bundledData.get("data");
        childRootViews = new ArrayList<>();
        progressBarLine = findViewById(R.id.progressBarLine);
        progressBar = findViewById(R.id.loading_acitivity_percentage);
        new Task().execute(data.getData());

    }

    private class Task extends AsyncTask<Uri, Integer, RelativeLayout>{
        int i = 0;

        @Override
        protected void onPreExecute() {
           // progressBar.setText("0");
            progressBarLine.setProgress(0);
        }

        @Override
        protected void onPostExecute(RelativeLayout relativeLayout) {
            Toast.makeText(getApplicationContext(), "gotovo",Toast.LENGTH_SHORT);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
               // progressBar.setText(String.valueOf(values[0]));
                 progressBarLine.setProgress(values[0]);
                Log.d("OnProgressUpdate",""+5);
                i++;
        }

        @Override
        protected RelativeLayout doInBackground(Uri... uris) {
            RelativeLayout mainLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.fragment_ploca, null, false);
            String jsonString;
            InputStream inputStream;
            try{
                StringWriter writer = new StringWriter();
                inputStream = getBaseContext().getContentResolver().openInputStream(uris[0]);
                IOUtils.copy(inputStream, writer, Charset.defaultCharset());
                jsonString = writer.toString();
                inputStream.close();

                JSONObject object = new JSONObject(jsonString);

                Iterator<String> iterator = object.keys();
                Iterator<String> counter = object.keys();
                double itemCount = 0;
                while (counter.hasNext()){
                    counter.next();
                    itemCount++;
                }

                ViewBuilder viewBuilder = new ViewBuilder(getApplicationContext(), mainLayout);
                double multiplyer = 0;
                while (iterator.hasNext()){
                    String key = iterator.next();
                    multiplyer++;
                    JSONObject jView = (JSONObject) object.get(key);
                    childRootViews.add(viewBuilder.buildView(jView));
                    publishProgress((int)Math.ceil(100.00/itemCount*multiplyer));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
