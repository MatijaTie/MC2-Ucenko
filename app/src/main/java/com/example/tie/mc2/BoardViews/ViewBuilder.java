package com.example.tie.mc2.BoardViews;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.DocumentsContract;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.RelativeLayout;

import com.example.tie.mc2.Fragments.FragmentBoard;
import com.example.tie.mc2.OptionButtons.OptionsAllBorderButton;
import com.example.tie.mc2.OptionButtons.OptionsImageFolderButton;
import com.example.tie.mc2.OptionButtons.OptionsImageTakePhotoButton;
import com.example.tie.mc2.OptionButtons.OptionsMapChangeColorButton;
import com.example.tie.mc2.OptionButtons.OptionsMapDrawToggleButton;
import com.example.tie.mc2.OptionButtons.OptionsMapEraseToggleButton;
import com.example.tie.mc2.OptionButtons.OptionsMapNewMap;
import com.example.tie.mc2.OptionButtons.OptionsTextResizeButton;
import com.example.tie.mc2.OptionButtons.OptionsTimlineAddButton;
import com.example.tie.mc2.OptionButtons.OptionsWebviewAddBookmarkButton;
import com.example.tie.mc2.OptionButtons.OptionsWebviewBackButton;
import com.example.tie.mc2.R;
import com.example.tie.mc2.StaticValues.DataKeys;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import static com.example.tie.mc2.StaticValues.DataKeys.FRAGMENT_BOARD_MAIN;
import static com.example.tie.mc2.StaticValues.DataKeys.INDENTIFIER_IMAGE_CLASS;
import static com.example.tie.mc2.StaticValues.DataKeys.INDENTIFIER_MAP_CLASS;
import static com.example.tie.mc2.StaticValues.DataKeys.INDENTIFIER_TEXT_CLASS;
import static com.example.tie.mc2.StaticValues.DataKeys.INDENTIFIER_TIMELINE_CLASS;
import static com.example.tie.mc2.StaticValues.DataKeys.INDENTIFIER_WEB_CLASS;

/**
 * Created by Tie on 06-May-18.
 */

public class ViewBuilder {

    JSONObject jView;
    RelativeLayout mainLayout;
    FragmentBoard fragment;
    Activity context;

    public ViewBuilder(Activity context, RelativeLayout mainLayout){
        this.jView = jView;
        this.context = context;
        this.mainLayout = mainLayout;
        this.fragment = (FragmentBoard) ((FragmentActivity)context).getSupportFragmentManager().findFragmentByTag(FRAGMENT_BOARD_MAIN);
    }

    private RootView createTextClass(RootView rootView, JSONObject jData) throws JSONException {
        String text = jData.getString("text");
        int textSize = jData.getInt("textSize");
        int backgroundColor = jData.getInt("background");
        int textColor = jData.getInt("textColor");

        BoardTextView boardTextView = new BoardTextView(context, rootView);
        boardTextView.setText(text);
        boardTextView.setTextSize(textSize);
        boardTextView.setBackgroundColor(backgroundColor);
        boardTextView.setTextColor(textColor);
        OptionsTextResizeButton optionsTextResizeButton = new OptionsTextResizeButton(context, boardTextView);
        OptionsAllBorderButton optionsAllBorderButton2 = new OptionsAllBorderButton(context, rootView);

        rootView.addViewToViewOptionsHolder(optionsAllBorderButton2);
        rootView.addViewToHolder(boardTextView);
        rootView.addViewToViewOptionsHolder(optionsTextResizeButton);
        return rootView;
    }

    private RootView createImageClass(RootView rootView, JSONObject jData) throws JSONException{
        Bitmap image = null;
        if( jData.getString("image") != null){
            String str = jData.getString("image");
            byte[] decodeString = Base64.decode(str, Base64.DEFAULT);
            image = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
        }


        BoardImageView boardImageView = new BoardImageView(context, fragment, rootView);
        OptionsImageTakePhotoButton optionsImageTakePhotoButton = new OptionsImageTakePhotoButton(context,boardImageView);
        OptionsImageFolderButton optionsImageFolderButton = new OptionsImageFolderButton(context,boardImageView);
        OptionsAllBorderButton optionsAllBorderButton3 = new OptionsAllBorderButton(context, rootView);
        if(image != null){
            boardImageView.setImage(image);
        }

        rootView.addViewToViewOptionsHolder(optionsAllBorderButton3);
        rootView.addViewToHolder(boardImageView);
        rootView.addViewToViewOptionsHolder(optionsImageTakePhotoButton);
        rootView.addViewToViewOptionsHolder(optionsImageFolderButton);

        return rootView;
    }

    private RootView createTimelineClass(RootView rootView, JSONObject jData)throws JSONException{
        BoardTimelineView boardTimelineView = new BoardTimelineView(context, rootView);
        Iterator<String> iterator = jData.keys();
        while (iterator.hasNext()){
            String key = iterator.next();
            JSONObject jTimelineData = (JSONObject) jData.get(key);
            String textPart = jTimelineData.getString("textPart");
            String yearPart = jTimelineData.getString("yearPart");
            int icon = jTimelineData.getInt("icon");
            int marginLeft = jTimelineData.getInt("marginLeft");
            boardTimelineView.addTimelineView(yearPart, textPart, marginLeft, icon);

        }
        OptionsTimlineAddButton OptionsTimlineAddButton = new OptionsTimlineAddButton(context, boardTimelineView);
        OptionsAllBorderButton optionsAllBorderButton = new OptionsAllBorderButton(context, rootView);
        //dodavanje elementa u glavni i options holder
        rootView.addViewToViewOptionsHolder(optionsAllBorderButton);
        rootView.addViewToViewOptionsHolder(OptionsTimlineAddButton);
        rootView.addViewToHolder(boardTimelineView);

        return rootView;
    }

    private RootView createWebClass(final RootView rootView, final JSONObject jData)throws JSONException{
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BoardWebView boardWebView = new BoardWebView(context, rootView);
                OptionsWebviewBackButton optionsWebviewBackButton = new OptionsWebviewBackButton(context,boardWebView);
                OptionsWebviewAddBookmarkButton optionsWebviewAddBookmarkButton = new OptionsWebviewAddBookmarkButton(context,boardWebView);
                rootView.addViewToViewOptionsHolder(optionsWebviewBackButton);
                rootView.addViewToViewOptionsHolder(optionsWebviewAddBookmarkButton);
                Iterator<String> iterator2 = jData.keys();

                while(iterator2.hasNext()){
                    String key = iterator2.next();
                    try {
                        boardWebView.addToBookmark(jData.getString(key));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                rootView.addViewToHolder(boardWebView);
            }
        });

        return rootView;
    }

    private RootView createMapClass(final RootView rootView, final JSONObject jData)throws JSONException{
        Bitmap map = null;
        Drawable drawable = null;
        if( jData.getString("map") != null){
            String str = jData.getString("map");
            byte[] decodeString = Base64.decode(str, Base64.DEFAULT);
            map = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
        }
        if(map != null){
            drawable = new BitmapDrawable(context.getResources(),map);
        }

        BoardMapDrawingLayout boardMapDrawingLayout = new BoardMapDrawingLayout(context);
        boardMapDrawingLayout.setBackground(drawable);

        OptionsAllBorderButton optionsAllBorderButton4 = new OptionsAllBorderButton(context, rootView);
        OptionsMapDrawToggleButton optionsMapDrawToggleButton = new OptionsMapDrawToggleButton(context, boardMapDrawingLayout);
        OptionsMapEraseToggleButton optionsMapEraseToggleButton = new OptionsMapEraseToggleButton(context, boardMapDrawingLayout);
        OptionsMapNewMap optionsMapNewMap = new OptionsMapNewMap(context, boardMapDrawingLayout);
        OptionsMapChangeColorButton optionsMapChangeColorButton = new OptionsMapChangeColorButton(context, boardMapDrawingLayout);

        optionsMapEraseToggleButton.setSibilingButton(optionsMapDrawToggleButton);
        optionsMapDrawToggleButton.setSibilingButton(optionsMapEraseToggleButton);

        rootView.addViewToViewOptionsHolder(optionsAllBorderButton4);
        rootView.addViewToViewOptionsHolder(optionsMapNewMap);
        rootView.addViewToViewOptionsHolder(optionsMapChangeColorButton);
        rootView.addViewToViewOptionsHolder(optionsMapDrawToggleButton);
        rootView.addViewToViewOptionsHolder(optionsMapEraseToggleButton);



        rootView.addViewToHolder(boardMapDrawingLayout);

        return rootView;
    }
    public RootView buildView(JSONObject jView){
        try {
            String viewClass = jView.getString("class");
            int viewPosX  = (int) jView.get("posx");
            int viewPosY = (int) jView.get("posy");
            int width = (int) jView.get("width");
            int height = (int) jView.get("height");
            final JSONObject jData = jView.getJSONObject("child");

            final RootView rootView = new RootView(context);
            rootView.setX((float) viewPosX);
            rootView.setY((float) viewPosY);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
            rootView.setLayoutParams(params);
            Log.d("json builder",jView.toString());
            rootView.hideOptionHolder();
            switch (viewClass){
                case INDENTIFIER_TEXT_CLASS:
                    return createTextClass(rootView, jData);

                case INDENTIFIER_IMAGE_CLASS:
                    return createImageClass(rootView, jData);

                case INDENTIFIER_TIMELINE_CLASS:
                    return createTimelineClass(rootView, jData);

                case INDENTIFIER_WEB_CLASS:
                    return createWebClass(rootView, jData);

                case INDENTIFIER_MAP_CLASS:
                    return createMapClass(rootView, jData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }



}
