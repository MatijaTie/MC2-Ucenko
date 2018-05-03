package com.example.tie.mc2.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.tie.mc2.BoardViews.BoardTextView;
import com.example.tie.mc2.BoardViews.BoardTimelineView;
import com.example.tie.mc2.BoardViews.BoardImageView;
import com.example.tie.mc2.OptionButtons.OptionsAllBorderButton;
import com.example.tie.mc2.OptionButtons.OptionsImageFolderButton;
import com.example.tie.mc2.OptionButtons.OptionsImageTakePhotoButton;
import com.example.tie.mc2.OptionButtons.OptionsTextResizeButton;
import com.example.tie.mc2.OptionButtons.OptionsTimlineAddButton;
import com.example.tie.mc2.BoardViews.RootView;
import com.example.tie.mc2.Listeners.TrashOnDragListener;
import com.example.tie.mc2.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.example.tie.mc2.Activities.BoardActivity.CAMERA_REQUEST;
import static com.example.tie.mc2.Activities.BoardActivity.IMAGE_REQUEST;


/**
 * Created by Tie on 10-Mar-18.
 */

public class FragmentBoard extends Fragment implements View.OnDragListener, View.OnTouchListener{

    public final String INDENTIFIER_TIMELINE = "class com.example.tie.mc2.ToolbarComponents.TimelineComponentDraggable";
    public final String INDENTIFIER_TEXT = "class com.example.tie.mc2.ToolbarComponents.TextComponentDraggable";
    public final String INDENTIFIER_IMAGE = "class com.example.tie.mc2.ToolbarComponents.ImageComponentDraggable";

    private BoardImageView lastImageView;
    public Bitmap lastimageBitmap;

    private RelativeLayout mainLayout;
    private Fragment fragment;
    private FrameLayout trash;
    private Context context;
    private View save;
    Point point;

    private ArrayList<RootView> childRootViews;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final RelativeLayout mainLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_ploca, container, false);
        mainLayout.setOnDragListener(this);
        mainLayout.setOnTouchListener(this);
        context = getActivity();
        this.mainLayout = mainLayout;
        this.fragment = this;
        childRootViews = new ArrayList<>();
        return mainLayout;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        trash = view.findViewById(R.id.trash);
        trash.bringToFront();
        trash.setOnDragListener(new TrashOnDragListener(trash, this));
        save = view.findViewById(R.id.main_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ispisi();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    public static Point getTouchPositionFromDragEvent(View item, DragEvent event) {
        Rect rItem = new Rect();
        item.getGlobalVisibleRect(rItem);

        return new Point(rItem.left + Math.round(event.getX()), rItem.top + Math.round(event.getY()));
    }

    //metoda koja ce primati listenere za drag evente
    @Override
    public boolean onDrag(View boardView, DragEvent event) {
        //pozicija gdje se dropa
        String sizeString, typeString;
        View draggedView;

        switch(event.getAction()){
            case DragEvent.ACTION_DRAG_LOCATION:
                point = getTouchPositionFromDragEvent(boardView, event);
                return true;

            case DragEvent.ACTION_DROP:
                if (event.getClipData().getItemAt(0).getText().length() > 0){
                    sizeString = event.getClipData().getItemAt(0).getText().toString();
                    typeString = event.getClipData().getDescription().getLabel().toString();
                    createDraggedView(event, sizeString, typeString);
                }
                else{                                                                               //Mozda posaviti na else if sa if this == view.getparent()
                    draggedView = (View) event.getLocalState();
                    setDraggedViewLocation(draggedView, point);
                }

                return true;
        }
        return true;
    }

    public void takePicture(BoardImageView boardImageView){
        lastImageView = boardImageView;
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    public void importPicture(BoardImageView boardImageView){
        lastImageView = boardImageView;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 22);

    }

    private void createDraggedView(DragEvent event, String sizeString, String typeString){
        float posX, posY, x, y;
        float offsetX,offsetY;
        RootView rootView;


        posX = event.getX();
        posY = event.getY();

        offsetX = Float.valueOf(sizeString.substring(0, sizeString.indexOf(":")))/2;
        offsetY = Float.valueOf(sizeString.substring(sizeString.indexOf(":")+1))/2;

        x = posX-offsetX;
        y = posY-offsetY;

        switch (typeString){
            case INDENTIFIER_TIMELINE://class com.example.tie.mc2.ToolbarComponents.TimelineComponentDraggable
                //ovdje idu konstruktori i dodavanje custom viewa na plocu
                //primjer:
                //inicijalizacija RootViewa
                rootView = getRootViewWithParams(x, y);

                //kreiranje elemenata za glavni i options holder
                BoardTimelineView boardTimelineView = new BoardTimelineView(context, rootView);
                OptionsTimlineAddButton OptionsTimlineAddButton = new OptionsTimlineAddButton(context, boardTimelineView);
                OptionsAllBorderButton optionsAllBorderButton = new OptionsAllBorderButton(context, rootView);
                //dodavanje elementa u glavni i options holder
                rootView.addViewToViewOptionsHolder(optionsAllBorderButton);
                rootView.addViewToViewOptionsHolder(OptionsTimlineAddButton);

                rootView.addViewToHolder(boardTimelineView);
                childRootViews.add(rootView);
                break;

            case INDENTIFIER_TEXT://class com.example.tie.mc2.ToolbarComponents.TextComponentDraggable
                rootView = getRootViewWithParams(x, y);
                BoardTextView boardTextView = new BoardTextView(context);

                OptionsTextResizeButton optionsTextResizeButton = new OptionsTextResizeButton(context, boardTextView);
                OptionsAllBorderButton optionsAllBorderButton2 = new OptionsAllBorderButton(context, rootView);

                rootView.addViewToViewOptionsHolder(optionsAllBorderButton2);
                rootView.addViewToHolder(boardTextView);
                rootView.addViewToViewOptionsHolder(optionsTextResizeButton);
                childRootViews.add(rootView);
                break;

            case INDENTIFIER_IMAGE://class com.example.tie.mc2.ToolbarComponents.ImageComponentDraggable
                rootView = getRootViewWithParams(x, y);

                BoardImageView boardImageView = new BoardImageView(context, this);
                OptionsImageTakePhotoButton optionsImageTakePhotoButton = new OptionsImageTakePhotoButton(getContext(),boardImageView);
                OptionsImageFolderButton optionsImageFolderButton = new OptionsImageFolderButton(getContext(),boardImageView);
                OptionsAllBorderButton optionsAllBorderButton3 = new OptionsAllBorderButton(context, rootView);

                rootView.addViewToViewOptionsHolder(optionsAllBorderButton3);
                rootView.addViewToHolder(boardImageView);
                rootView.addViewToViewOptionsHolder(optionsImageTakePhotoButton);
                rootView.addViewToViewOptionsHolder(optionsImageFolderButton);
                childRootViews.add(rootView);

                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                //Toast.makeText(getContext(), "action up", Toast.LENGTH_SHORT).show();
                return true;

            case MotionEvent.ACTION_DOWN:
                removeSoftKeyboard(v);
                return true;

        }
        return false;
    }

    //hvatanje slike
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if(requestCode == CAMERA_REQUEST && data != null){
            Bundle extras = data.getExtras();
            if(lastimageBitmap != null){
                lastimageBitmap.recycle();
            }

            lastimageBitmap = (Bitmap) extras.get("data");
            lastImageView.setImage(lastimageBitmap);

        }else if(requestCode == IMAGE_REQUEST && data != null){
            if(lastimageBitmap != null){
                lastimageBitmap.recycle();
            }
            InputStream stream;
            try {
                stream = context.getContentResolver().openInputStream(
                        data.getData());

                 lastimageBitmap = BitmapFactory.decodeStream(stream);
                 stream.close();
                 lastImageView.setImage(lastimageBitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeSoftKeyboard(View v){
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    public void setDraggedViewLocation(View draggedView, Point point) {
        int newPosX, newPosY;
        newPosX = point.x - mainLayout.getWidth() / 15 - draggedView.getWidth() / 2;
        newPosY = point.y - draggedView.getHeight() / 2 - 45;

        if(newPosX < 0) {
            draggedView.setX(0);
        }else {
            draggedView.setX(newPosX);
        }

        if(newPosY < 0){
            draggedView.setY(0);
        }else{
            draggedView.setY(newPosY);
        }
        draggedView.setVisibility(View.VISIBLE);
    }

    private RootView getRootViewWithParams(float x, float y){
        ViewGroup.LayoutParams params;
        RootView rootView = new RootView(context);
        mainLayout.addView(rootView);
        params = rootView.getLayoutParams();
        params.width = 350;
        params.height = 350;
        rootView.setLayoutParams(params);
        rootView.setX(x);
        rootView.setY(y);

        return rootView;
    }

    public void ispisi(){
        int counter = 0;
        float posX, posY;
        String holderClass;
        JSONObject Jroot = new JSONObject();

        for(RootView r : childRootViews){
            Log.d("ispis", ""+r.getMainView().getClass());
            Log.d("ispis", "x:"+r.getX());
            Log.d("ispis", "y:"+r.getY());

            try{

                posX = r.getX();
                posY = r.getY();

                JSONObject JrootView = new JSONObject();

                holderClass = r.getMainView().getClass().toString();

                JrootView.put("class", holderClass);
                JrootView.put("posx", posX);
                JrootView.put("posy", posY);


                Jroot.put("view"+counter, JrootView);

                counter++;

                switch (holderClass){
                    case "class com.example.tie.mc2.BoardViews.BoardTextView":

                        JSONObject childObjectData = new JSONObject();
                        BoardTextView v = (BoardTextView) r.getMainView();
                        Log.d("INDENTIFIER_TEXT", ""+v.getText());
                        childObjectData.put("text", v.getText());
                        childObjectData.put("textSize", v.getCustomTextSize());
                        childObjectData.put("background",v.getEnteredBackgroundColor());
                        JrootView.put("child", childObjectData);

                        break;
                    case "class com.example.tie.mc2.BoardViews.BoardImageView":
                        JSONObject childObjectData2 = new JSONObject();
                        BoardImageView v2 = (BoardImageView) r.getMainView();
                        childObjectData2.put("image", v2.getImageEncoded());
                        JrootView.put("child", childObjectData2);
                        break;

                    case INDENTIFIER_TIMELINE:

                        break;
                }
                Log.d("ispis", ""+Jroot.toString());
            }catch (JSONException e){
                e.printStackTrace();
            }

        }
    }

    public void removeViewFromList(View v){
        for(View view : childRootViews){
            if(view.equals(v)){
                childRootViews.remove(v);
                break;
            }
        }
    }


}
