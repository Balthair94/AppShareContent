package baltamon.mx.appsharecontent;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 01;
    private static final int LOAD_IMAGE = 02;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();

        setButtons();
    }

    private void setButtons() {
        Button shareButtonText = (Button) findViewById(R.id.btn_share_text);
        shareButtonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareText();
            }
        });

        Button shareButtonImage = (Button) findViewById(R.id.btn_share_image);
        shareButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri != null)
                    shareImge();
                else
                    Toast.makeText(getApplicationContext(), "You have to choose an image", Toast.LENGTH_SHORT).show();
            }
        });

        Button takePhotoButton = (Button) findViewById(R.id.btn_take_photo);
        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
            }
        });

        Button loadImageButton = (Button) findViewById(R.id.btn_load_image);
        loadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImage();
            }
        });
    }

    public void shareText(){
        EditText editText = (EditText) findViewById(R.id.et_content);

        if (!editText.getText().toString().equals("")){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, editText.getText().toString());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } else
            Toast.makeText(this, "You need to include a text", Toast.LENGTH_SHORT).show();
    }

    public void shareImge(){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, "Send image to..."));
    }

    public void takePhoto(){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    public void loadImage(){
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, LOAD_IMAGE);
    }

    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null){
            actionBar.setTitle("Share content");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case CAMERA_REQUEST:
                if (resultCode == Activity.RESULT_OK){
                    imageUri = data.getData();
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    ImageView imageView = (ImageView) findViewById(R.id.iv_image);
                    imageView.setImageBitmap(photo);
                }
                break;
            case LOAD_IMAGE:
                if (resultCode == Activity.RESULT_OK){
                    imageUri = data.getData();
                    Bitmap myBitmap = BitmapFactory.decodeFile(imageUri.getPath());

                    ImageView imageView = (ImageView) findViewById(R.id.iv_image);
                    imageView.setImageBitmap(myBitmap);
                }
                break;
        }
    }
}
