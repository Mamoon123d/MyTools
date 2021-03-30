package com.moon.mytools;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.moon.utility.blurShadow.BlurShadowImageView;
import com.moon.utility.realTimeBlur.RealtimeBlurView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class MainActivity extends AppCompatActivity {

    private SeekBar blurRadius;
    RealtimeBlurView blurView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        blurRadius = (SeekBar) findViewById(R.id.blur_radius);
        blurView = findViewById(R.id.realtimeBlurView);
        blurRadius.setProgress(10);
        blurRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateRadius();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        imageShadow();
    }

    private void imageShadow() {
        BlurShadowImageView blurshadowimageview = (BlurShadowImageView) findViewById(R.id.blurSImageView);

//Sets Border Round Radius
        //blurshadowimageview.setRound((int) value);

//Sets Image Resource
      //  blurshadowimageview.setImageResource(ImgRes);

//Sets Image Drawable
       // blurshadowimageview.setImageDrawable(drawable);

//Sets Image Bitmap
      //  blurshadowimageview.setImageBitmap(bitmap);

         Picasso.get().load("https://i.imgur.com/DvpvklR.png").into(new Target() {
             @Override
             public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                 blurshadowimageview.setImageBitmap(bitmap);

             }

             @Override
             public void onBitmapFailed(Exception e, Drawable errorDrawable) {

             }

             @Override
             public void onPrepareLoad(Drawable placeHolderDrawable) {

             }
         });

    }

    private void updateRadius() {
        blurView.setBlurRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, blurRadius.getProgress(), getResources().getDisplayMetrics()));
        //  blurRadiusText.setText(blurRadius.getProgress() + "dp");
    }


}