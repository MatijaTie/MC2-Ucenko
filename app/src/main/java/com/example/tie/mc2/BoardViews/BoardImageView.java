package com.example.tie.mc2.BoardViews;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.UnicodeSetSpanner;
import android.provider.MediaStore;
import android.support.v7.widget.AppCompatImageView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.tie.mc2.Fragments.FragmentBoard;
import com.example.tie.mc2.R;

import java.io.ByteArrayOutputStream;

import static com.example.tie.mc2.MainActivity.CAMERA_REQUEST;

/**
 * Created by Tie on 18-Apr-18.
 */

public class BoardImageView extends AppCompatImageView{
    FragmentBoard parentFragment;
    String imageEncoded;

    public BoardImageView(Context context, FragmentBoard parentFragment) {
        super(context);
        this.parentFragment = parentFragment;

    }
    public void takePicture(){
        parentFragment.takePicture(this);
    }

    public void importPicture(){
        parentFragment.importPicture(this);
    }

    public void setImage(Bitmap image){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        setImageBitmap(image);
        Log.d("image encoded", imageEncoded);

    }


}
