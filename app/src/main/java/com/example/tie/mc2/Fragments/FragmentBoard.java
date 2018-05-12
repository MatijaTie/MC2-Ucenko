package com.example.tie.mc2.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.DocumentsContract;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tie.mc2.BoardViews.BoardTextView;
import com.example.tie.mc2.BoardViews.BoardTimelineView;
import com.example.tie.mc2.BoardViews.BoardImageView;
import com.example.tie.mc2.BoardViews.BoardTimelineViewButton;
import com.example.tie.mc2.OptionButtons.OptionsAllBorderButton;
import com.example.tie.mc2.OptionButtons.OptionsImageFolderButton;
import com.example.tie.mc2.OptionButtons.OptionsImageTakePhotoButton;
import com.example.tie.mc2.OptionButtons.OptionsTextResizeButton;
import com.example.tie.mc2.OptionButtons.OptionsTimlineAddButton;
import com.example.tie.mc2.BoardViews.RootView;
import com.example.tie.mc2.Listeners.TrashOnDragListener;
import com.example.tie.mc2.R;
import com.example.tie.mc2.BoardViews.ViewBuilder;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

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
    private ProgressBar progressBarLine;
    private LinearLayout loadingScreen;
    private RelativeLayout boardMain;
    private RelativeLayout mainLayout;
    private JSONObject Jroot;
    private FragmentBoard fragment;
    private Context context;
    Point point;

    private ArrayList<RootView> childRootViews;

    public static FragmentBoard newInstance(Uri uri){
        if(uri != null){
            Bundle bundle = new Bundle();
            bundle.putString("uriString" , uri.toString());
            Log.d("newInstance", uri.toString());
            FragmentBoard fragmentBoard = new FragmentBoard();
            fragmentBoard.setArguments(bundle);
            return fragmentBoard;
        }
        return new FragmentBoard();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        boardMain = (RelativeLayout) inflater.inflate(R.layout.fragment_ploca, container, false);

        context = getActivity();
        this.fragment = this;
        childRootViews = new ArrayList<>();
        return boardMain;

    }

    public void addTochildContainer(RootView rootView){
        childRootViews.add(rootView);
    }
    public RelativeLayout getMainLayout(){
        return mainLayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mainLayout = view.findViewById(R.id.board_finished);
        mainLayout.setOnDragListener(this);
        mainLayout.setOnTouchListener(this);

        FrameLayout trash = view.findViewById(R.id.trash);
        trash.bringToFront();
        trash.setOnDragListener(new TrashOnDragListener(trash, this));

        loadingScreen = view.findViewById(R.id.board_loading_layout);
        progressBarLine = view.findViewById(R.id.progressBarLine);

        View save = view.findViewById(R.id.main_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SaveStateTask().execute("");
            }
        });


        Bundle args = getArguments();
        if(args != null && args.getString("uriString") != null){
            Uri uri = Uri.parse(args.getString("uriString"));
            new LoadingTask().execute(uri);
        }else{
            loadingScreen.setVisibility(View.GONE);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    private class LoadingTask extends AsyncTask<Uri, Integer, RelativeLayout> {
        int i = 0;

        @Override
        protected void onPreExecute() {
            mainLayout.setVisibility(View.INVISIBLE);
            progressBarLine.setProgress(0);
        }

        @Override
        protected void onPostExecute(RelativeLayout relativeLayout) {
            loadingScreen.setVisibility(View.GONE);
            mainLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBarLine.setProgress(values[0]);
            i++;
        }

        @Override
        protected RelativeLayout doInBackground(Uri... uris) {
            String jsonString;
            InputStream inputStream;
            try{
                StringWriter writer = new StringWriter();
                inputStream = context.getContentResolver().openInputStream(uris[0]);
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

                ViewBuilder viewBuilder = new ViewBuilder(context, mainLayout);
                double multiplyer = 0;
                while (iterator.hasNext()){
                    String key = iterator.next();
                    multiplyer++;
                    JSONObject jView = (JSONObject) object.get(key);
                    final RootView newView = viewBuilder.buildView(jView);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mainLayout.addView(newView);
                            childRootViews.add(newView);
                        }
                    });
                    publishProgress((int)Math.ceil(100.00/itemCount*multiplyer));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
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
            if (extras != null) {
                lastimageBitmap = (Bitmap) extras.get("data");
                lastImageView.setImage(lastimageBitmap);
            }


        }else if(requestCode == IMAGE_REQUEST && data != null){
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
            //save file intent
        }else if(requestCode == 0202 && data != null){
            Uri uri = data.getData();
            Log.d("0202",""+uri.getPath());
            writeFile(data.getData(), Jroot.toString());
        }
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

    public void removeSoftKeyboard(View v){
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    public void setDraggedViewLocation(View draggedView, Point point) {
        int newPosX, newPosY;
        if(mainLayout != null && draggedView != null){
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

    private class SaveStateTask extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... strings) {
            writeToJson();
            return null;
        }
    }
    public void writeToJson(){
        int counter = 0;
        int posX, posY;
        int width, height;
        String holderClass;
        Jroot = new JSONObject();

        for(RootView r : childRootViews){
            //Log.d("ispis", ""+r.getMainView().getClass());
            Log.d("ispis", "x:"+r.getX());
            Log.d("ispis", "y:"+r.getY());

            try{
                posX = (int) r.getX();
                posY = (int) r.getY();

                width = r.getWidth();
                height = r.getHeight();

                holderClass = r.getMainView().getClass().toString();

                JSONObject JrootView = new JSONObject();

                JrootView.put("class", holderClass);
                JrootView.put("posx", posX);
                JrootView.put("posy", posY);
                JrootView.put("width",width);
                JrootView.put("height",height);
                switch (holderClass){
                    case "class com.example.tie.mc2.BoardViews.BoardTextView":
                        JSONObject childObjectData = new JSONObject();
                        BoardTextView v = (BoardTextView) r.getMainView();
                        childObjectData.put("text", v.getText());
                        childObjectData.put("textSize", v.getCustomTextSize());
                        childObjectData.put("background",v.getEnteredBackgroundColor());
                        childObjectData.put("textColor",v.getCurrentTextColor());
                        JrootView.put("child", childObjectData);
                        break;
                    case "class com.example.tie.mc2.BoardViews.BoardImageView":
                        JSONObject childObjectData2 = new JSONObject();
                        BoardImageView v2 = (BoardImageView) r.getMainView();
                        childObjectData2.put("image", v2.getImageEncoded());
                        JrootView.put("child", childObjectData2);
                        break;

                    case "class com.example.tie.mc2.BoardViews.BoardTimelineView":

                        JSONObject childObjectData3 = new JSONObject();
                        BoardTimelineView v3 = (BoardTimelineView)r.getMainView();
                        int no = 0;
                        ArrayList<BoardTimelineViewButton> timelineComponents = v3.getChildHolder();

                        for(BoardTimelineViewButton b : timelineComponents){
                            JSONObject timelineObjectData = new JSONObject();
                            timelineObjectData.put("textPart",b.getTextPart());
                            timelineObjectData.put("yearPart",b.getYearPart());
                            childObjectData3.put("timelinePart"+no, timelineObjectData);
                            no++;
                        }
                        JrootView.put("child",childObjectData3);
                        Log.d("jsonamo chobj3",JrootView.toString());
                        break;
                }
                Jroot.put("view"+counter, JrootView);
                Log.d("jsonamo jroot",Jroot.toString());
                counter++;
            }catch (JSONException e){
                e.printStackTrace();
            }

        }
        Log.d("ispis", ""+Jroot.toString());
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.setType("application/.tie");
        startActivityForResult(intent, 0202);
    }



    public void writeFile(Uri location,  String data){
        String locationStr = DocumentsContract.getDocumentId(location);
        Log.d("location string uri", locationStr);

        String dataStr = data;
        OutputStream outputStream;
        try{
            outputStream = context.getContentResolver().openOutputStream(location);
            //outputStream.write(data.getBytes(Charset.defaultCharset()));

            outputStream.flush();
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
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
