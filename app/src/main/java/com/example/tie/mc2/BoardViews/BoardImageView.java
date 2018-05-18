package com.example.tie.mc2.BoardViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatImageView;
import android.util.Base64;
import android.util.Log;

import com.example.tie.mc2.Fragments.FragmentBoard;

import java.io.ByteArrayOutputStream;

/**
 * Created by Tie on 18-Apr-18.
 */

public class BoardImageView extends AppCompatImageView {
    FragmentBoard parentFragment;
    String imageEncoded;
    Bitmap img;
    RootView rootView;

    public BoardImageView(Context context, FragmentBoard parentFragment, RootView rootView) {
        super(context);
        this.parentFragment = parentFragment;
        this.rootView = rootView;
        setFocusableInTouchMode(true);
        setFocusable(true);
    }
    public void takePicture(){
        parentFragment.takePicture(this);
    }

    public void importPicture(){
        parentFragment.importPicture(this);
    }

    public void setImage(Bitmap image){
        img = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        setImageBitmap(image);
    }

    public Bitmap getImgx(){
        return img;
    }


    public String getImageEncoded() {
        if(imageEncoded == null){
            return "";
        }
        return imageEncoded;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if(focused){
            rootView.showOptions(true);
            Log.d("board image view","has focus");
        }else{
            rootView.showOptions(false);
            Log.d("board image view","lost focus");
        }
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

}
