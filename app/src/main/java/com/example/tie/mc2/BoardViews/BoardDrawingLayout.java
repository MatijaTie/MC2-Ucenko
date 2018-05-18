package com.example.tie.mc2.BoardViews;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.media.ThumbnailUtils;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import java.io.ByteArrayOutputStream;

/**
 * Created by Tie on 12-May-18.
 */

public class BoardDrawingLayout extends RelativeLayout implements View.OnTouchListener {
        private Boolean isPainting, isErasing;
        public int width;
        public int height;
        private Bitmap mBitmap;
        private Canvas mCanvas;
        private Path mPath;
        private Paint mBitmapPaint;
        Context context;
        private Paint circlePaint, paint;
        private Path circlePath;


    public BoardDrawingLayout(Context context, AttributeSet attrs, int defStyleAttr, int width) {
        super(context, attrs, defStyleAttr);
        this.width = width;
        this.context = context;
        init();
    }

    public BoardDrawingLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, int width) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.width = width;
        this.context = context;
        init();
    }

    public BoardDrawingLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        init();
    }
    public BoardDrawingLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init(){
        setDrawingCacheEnabled(true);
        //setBackgroundColor(Color.TRANSPARENT);
        isPainting = false;
        isErasing = false;

        setFocusable(true);
        setFocusableInTouchMode(true);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(15);

        setOnTouchListener(this);

        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        circlePaint = new Paint();
        circlePath = new Path();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.BLUE);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeJoin(Paint.Join.MITER);
        circlePaint.setStrokeWidth(4f);
    }

    private void setDravingStyle(){
        paint.setXfermode(null);
        paint.setStrokeWidth(15);
    }

    public int getDrawingColor(){
        return paint.getColor();
    }

    public void setDrawingColor(int color){

        paint.setColor(color);
    }

    private void setErasingStyle(){
        paint.setStrokeWidth(45);
    }

    public void setPainting(Boolean b){
        if(b){
            setDravingStyle();
            isPainting = true;
        }else{
            isPainting = false;
        }
    }

    public void setErasing(Boolean b){
        if(b){
            isErasing = true;
            setErasingStyle();
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        }else{
            isErasing = false;
            paint.setXfermode(null);
        }
    }

    public void setCanvas(Canvas canvas){
        draw(canvas);

    }
    public void removeSoftKeyboard(View v){
        InputMethodManager inputMethodManager =(InputMethodManager)getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }


    public void clearDrawing()
    {

        setDrawingCacheEnabled(false);

        onSizeChanged(width, height, width, height);
        invalidate();

        setDrawingCacheEnabled(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap( mBitmap, 0, 0, mBitmapPaint);
        canvas.drawPath( mPath,  paint);
        canvas.drawPath( circlePath,  circlePaint);
    }

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 5;

    private void touch_start(float x, float y) {
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
            mX = x;
            mY = y;
            circlePath.reset();
            circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
        }
    }

    private void touch_up() {
        mPath.lineTo(mX, mY);
        circlePath.reset();
        mCanvas.drawPath(mPath,  paint);
        mPath.reset();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                requestFocus();
                removeSoftKeyboard(v);
                if(isPainting || isErasing) {
                    touch_start(x, y);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(isPainting || isErasing) {
                    touch_move(x, y);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if(isPainting || isErasing) {
                    touch_up();
                    invalidate();
                }
                break;
        }
        return true;
    }

    public String saveDrawing()
    {
        Bitmap bitmap = getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }


}
