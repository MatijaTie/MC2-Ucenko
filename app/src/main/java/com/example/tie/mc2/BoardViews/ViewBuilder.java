package com.example.tie.mc2.BoardViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.RelativeLayout;

import com.example.tie.mc2.Fragments.FragmentBoard;
import com.example.tie.mc2.LoadingTask;
import com.example.tie.mc2.OptionButtons.OptionsAllBorderButton;
import com.example.tie.mc2.OptionButtons.OptionsImageFolderButton;
import com.example.tie.mc2.OptionButtons.OptionsImageTakePhotoButton;
import com.example.tie.mc2.OptionButtons.OptionsTextResizeButton;
import com.example.tie.mc2.OptionButtons.OptionsTimlineAddButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import static com.example.tie.mc2.StaticValues.DataKeys.INDENTIFIER_IMAGE_CLASS;
import static com.example.tie.mc2.StaticValues.DataKeys.INDENTIFIER_TEXT_CLASS;
import static com.example.tie.mc2.StaticValues.DataKeys.INDENTIFIER_TIMELINE_CLASS;

/**
 * Created by Tie on 06-May-18.
 */

public class ViewBuilder {

    JSONObject jView;
    RelativeLayout mainLayout;
    FragmentBoard fragment;
    Context context;

    public ViewBuilder(Context context, RelativeLayout mainLayout){
        this.jView = jView;
        this.fragment = fragment;
        this.context = context;
        this.mainLayout = mainLayout;

    }

    public RootView buildView(JSONObject jView){
        Log.d("building view",jView.toString());
        try {
            String viewClass = jView.getString("class");
            int viewPosX  = (int) jView.get("posx");
            int viewPosY = (int) jView.get("posy");
            int width = (int) jView.get("width");
            int height = (int) jView.get("height");
            JSONObject jData = jView.getJSONObject("child");

            Log.d("json class", viewClass +" x "+viewPosX+" y "+ viewPosY);

            //ROOT VIEW
            RootView rootView = new RootView(context);

            rootView.setX((float) viewPosX);
            rootView.setY((float) viewPosY);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
            rootView.setLayoutParams(params);
            Log.d("json builder",jView.toString());

            //CHILD
            switch (viewClass){
                case INDENTIFIER_TEXT_CLASS:

                    String text = jData.getString("text");
                    int textSize = jData.getInt("textSize");
                    int backgroundColor = jData.getInt("background");
                    int textColor = jData.getInt("textColor");

                    BoardTextView boardTextView = new BoardTextView(context);
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

                case INDENTIFIER_IMAGE_CLASS:
                    Log.d("builder image","unutra");
                    String str = jData.getString("image");

                    byte[] decodeString = Base64.decode(str, Base64.DEFAULT);
                    Bitmap image = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);

                    BoardImageView boardImageView = new BoardImageView(context, fragment);
                    OptionsImageTakePhotoButton optionsImageTakePhotoButton = new OptionsImageTakePhotoButton(context,boardImageView);
                    OptionsImageFolderButton optionsImageFolderButton = new OptionsImageFolderButton(context,boardImageView);
                    OptionsAllBorderButton optionsAllBorderButton3 = new OptionsAllBorderButton(context, rootView);
                    boardImageView.setImage(image);

                    rootView.addViewToViewOptionsHolder(optionsAllBorderButton3);
                    rootView.addViewToHolder(boardImageView);
                    rootView.addViewToViewOptionsHolder(optionsImageTakePhotoButton);
                    rootView.addViewToViewOptionsHolder(optionsImageFolderButton);
                    return rootView;

                case INDENTIFIER_TIMELINE_CLASS:
                    BoardTimelineView boardTimelineView = new BoardTimelineView(context, rootView);
                    Iterator<String> iterator = jData.keys();
                    while (iterator.hasNext()){
                        String key = iterator.next();
                        JSONObject jTimelineData = (JSONObject) jData.get(key);
                        String textPart = jTimelineData.getString("textPart");
                        String yearPart = jTimelineData.getString("yearPart");
                        Log.d("timeline text",textPart);
                        boardTimelineView.addTimelineView(yearPart, textPart);

                    }
                    OptionsTimlineAddButton OptionsTimlineAddButton = new OptionsTimlineAddButton(context, boardTimelineView);
                    OptionsAllBorderButton optionsAllBorderButton = new OptionsAllBorderButton(context, rootView);
                    //dodavanje elementa u glavni i options holder
                    rootView.addViewToViewOptionsHolder(optionsAllBorderButton);
                    rootView.addViewToViewOptionsHolder(OptionsTimlineAddButton);
                    rootView.addViewToHolder(boardTimelineView);

                    return rootView;

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }



}
