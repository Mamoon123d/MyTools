package com.moon.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;

public class ImageM {
    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public Bitmap ImageGrayscale(Bitmap bmpOriginal)
    {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

//=============================================================
    public static class BlurOption{
        private static final float FULL_SCALE = 1f;
        private static BlurOption instance;
        private static RenderScript rs;

        public static void init(Context context){
            if (instance!=null){
                return;
            }
            instance=new BlurOption();
            rs = RenderScript.create(context.getApplicationContext());
        }

        public Bitmap blur(Bitmap src, int radius) {
            final Allocation input = Allocation.createFromBitmap(rs, src);
            final Allocation output = Allocation.createTyped(rs, input.getType());
            final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            script.setRadius(radius);
            script.setInput(input);
            script.forEach(output);
            output.copyTo(src);
            return src;
        }
        public Bitmap blur(View src, int radius) {
            Bitmap bitmap = getBitmapForView(src);
            return blur(bitmap, radius);
        }

        private Bitmap getBitmapForView(View src) {
            Bitmap bitmap = Bitmap.createBitmap(
                    src.getWidth(),
                    src.getHeight(),
                    Bitmap.Config.ARGB_8888
            );

            Canvas canvas = new Canvas(bitmap);
            src.draw(canvas);

            return bitmap;
        }
        private Bitmap getBitmapForView(View src, float downscaleFactor) {
            Bitmap bitmap = Bitmap.createBitmap(
                    (int) (src.getWidth() * downscaleFactor),
                    (int) (src.getHeight() * downscaleFactor),
                    Bitmap.Config.ARGB_8888
            );

            Canvas canvas = new Canvas(bitmap);
            Matrix matrix = new Matrix();
            matrix.preScale(downscaleFactor, downscaleFactor);
            canvas.setMatrix(matrix);
            src.draw(canvas);

            return bitmap;
        }


        public static BlurOption getInstance(){
            if (instance==null){
            throw new RuntimeException("not Initialize");
            }
            return instance;
        }

        public Bitmap fastBlur(View src, int radius, float downscaleFactor) {
            Bitmap bitmap = getBitmapForView(src, downscaleFactor);
            return blur(bitmap, radius);
        }

    }


}
