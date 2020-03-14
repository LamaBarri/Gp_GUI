package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.github.chrisbanes.photoview.PhotoView;

public class ShowMap extends AppCompatActivity {

    ImageView map;
Button loadanchor;
    private static final int[] FROM_COLOR = new int[]{255, 255, 255};
    private static final int THRESHOLD = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_show_map);

        map = (ImageView) findViewById(R.id.map);
        loadanchor = (Button)findViewById(R.id.loadanchor);
        loadanchor.setVisibility(View.INVISIBLE);

        map.setOnTouchListener(new View.OnTouchListener()

        {

            @Override
            public boolean onTouch (View view, MotionEvent event){
                loadanchor.setVisibility(View.VISIBLE);

                Drawable d = getResources().getDrawable(R.drawable.map1);

                float eventX = event.getRawX();
                float eventY = event.getRawY();
                float[] eventXY = new float[]{eventX, eventY};
                Matrix invertMatrix = new Matrix();
                ((ImageView) view).getImageMatrix().invert(invertMatrix);

                invertMatrix.mapPoints(eventXY);
                int x = Integer.valueOf((int) eventXY[0]);
                int y = Integer.valueOf((int) eventXY[1]);

                map.setImageDrawable(adjust(d,x,y));

                Toast.makeText(ShowMap.this, "touched position: "
                        + String.valueOf(x) + " , "
                        + String.valueOf(y) , Toast.LENGTH_LONG).show();


                return true;
            }
        });
    }

    private Drawable adjust(Drawable d , int x1 ,int y1) {
        int to = Color.RED;

        //Need to copy to ensure that the bitmap is mutable.
        Bitmap src = ((BitmapDrawable) d).getBitmap();

        Bitmap bitmap = src.copy(Bitmap.Config.ARGB_8888, true);
        bitmap = Bitmap.createScaledBitmap(bitmap,1080, 1857, true);

        for(int x = x1;x < x1+10;x++)
            for(int y = y1;y < y1+10;y++)
                if(match(bitmap.getPixel(x, y)))
                    bitmap.setPixel(x, y, to);

        return new BitmapDrawable(bitmap);
    }

    private boolean match(int pixel) {
        return Math.abs(Color.red(pixel) - FROM_COLOR[0]) < THRESHOLD &&
                Math.abs(Color.green(pixel) - FROM_COLOR[1]) < THRESHOLD &&
                Math.abs(Color.blue(pixel) - FROM_COLOR[2]) < THRESHOLD;
    }



    }
