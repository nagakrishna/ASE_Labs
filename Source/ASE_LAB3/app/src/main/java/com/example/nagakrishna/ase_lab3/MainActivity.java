package com.example.nagakrishna.ase_lab3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int TAKE_PHOTO_CODE = 11;
    private ImageView imageView;
    private Bitmap photo;
    private EditText EdittextAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = (ImageView)findViewById(R.id.ImageViewPic);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.cameraicon));

    }

    public void MapsAct(View view){
        //Button button = (Button)findViewById(R.id.pressit)
        //EdittextAddress = (EditText)findViewById(R.id.Address);
        //String Address = EdittextAddress.getText().toString();
        if(photo == null){
            Toast.makeText(this, "Click Picture", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(this , MapsActivity.class);
            intent.putExtra("image", photo);
            //intent.putExtra("Address", Address);
            startActivity(intent);
        }

    }

    public void CameraImage(View view){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            //Intent intent = new Intent(this, MapsActivity.class);
            //intent.putExtra("image_camera", photo);
            imageView.setImageBitmap(photo);
            Log.d("CameraDemo", "Pic saved");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
