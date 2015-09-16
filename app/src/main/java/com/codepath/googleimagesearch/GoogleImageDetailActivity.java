package com.codepath.googleimagesearch;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GoogleImageDetailActivity extends AppCompatActivity {

    TouchImageView touchImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_image_detail);

        // Show the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the args
        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        setTitle(title);

        this.touchImageView = (TouchImageView)findViewById(R.id.iv_image);
        Picasso.with(this).load(url).into(this.touchImageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_google_image_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Uri bmpUri = getLocalBitmapUri(this.touchImageView);
            if (bmpUri != null) {
                // Construct a ShareIntent with link to image
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                shareIntent.setType("image/*");

                // Launch sharing dialog for image
                startActivity(Intent.createChooser(shareIntent, "Share Image"));
            } else {
                Toast.makeText(this, "Sharing failed", Toast.LENGTH_SHORT).show();
            }

            return true;
        } else {
            // Close the dialog on back
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    // Returns the URI path to the Bitmap displayed in specified ImageView
    public Uri getLocalBitmapUri(TouchImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }

        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file =  new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bmpUri;
    }
}
