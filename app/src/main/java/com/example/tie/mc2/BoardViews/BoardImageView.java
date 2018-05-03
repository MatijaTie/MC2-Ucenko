package com.example.tie.mc2.BoardViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatImageView;
import android.util.Base64;

import com.example.tie.mc2.Fragments.FragmentBoard;

import java.io.ByteArrayOutputStream;

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
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        setImageBitmap(image);
    }

    public String getImageEncoded() {
        return imageEncoded;
    }


}
