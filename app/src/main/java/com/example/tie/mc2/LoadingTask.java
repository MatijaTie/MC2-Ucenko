package com.example.tie.mc2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.tie.mc2.Activities.BoardActivity;
import com.example.tie.mc2.BoardViews.BoardImageView;

/**
 * Created by Tie on 06-May-18.
 */

public class LoadingTask extends AsyncTask<Void, Void, Void> {
    String str;
    BoardImageView boardImageView;
    Context context;
    Bitmap image;



    public LoadingTask(Context context, String str, BoardImageView boardImageView){
        this.context = context;
        this.str = str;
        this.boardImageView = boardImageView;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        byte[] decodeString = Base64.decode(str, Base64.DEFAULT);
        image = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
        return null;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();


    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        boardImageView.setImage(image);

    }
}
