package com.technozion.tz_17;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import static com.technozion.tz_17.Profile.bitmap;

/**
 * Created by Anshul Goyal on 13-10-2016.
 */

public class enlargeQR extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlargeqr);
        Toolbar toolbar;

        toolbar = (Toolbar) findViewById(R.id.event_app_toolbar);
        setSupportActionBar(toolbar);

//        int imageId1 = getIntent().getIntExtra(enlargeQR.class.getName(),-1);
//        ImageView myimage = (ImageView) findViewById(R.id.qrcode);
//        if(imageId1==-1)
//        {
//            myimage.setImageResource(R.drawable.notavailable);
//        }
//        else {
//            System.out.println("hello" + imageId1 + "bye" + R.id.qrimage);
//            InputStream is = this.getResources().openRawResource(imageId1);
//            Bitmap originalBitmap = BitmapFactory.decodeStream(is);
//            mage.setImageBitmap(originalBitmap);myi
//            myimage.setScaleType(ImageView.ScaleType.MATRIX);
//        }

        ImageView myimage = (ImageView) findViewById(R.id.qrcode);
        try {
            Bitmap b = bitmap;
            System.out.println("Width : " + b.getWidth() + "Height : " + b.getHeight());
            Bitmap b1 = getResizedBitmap(b, 400, 400);
            System.out.println("Width : " + b1.getWidth() + "Height : " + b1.getHeight());
            myimage.setImageBitmap(b1);
        } catch (Exception e) {

        }

    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

        int width = bm.getWidth();

        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;

        float scaleHeight = ((float) newHeight) / height;

// CREATE A MATRIX FOR THE MANIPULATION

        Matrix matrix = new Matrix();

// RESIZE THE BIT MAP

        matrix.postScale(scaleWidth, scaleHeight);

// RECREATE THE NEW BITMAP

        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

        return resizedBitmap;

    }
}
