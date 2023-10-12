package com.example.semana8;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Button botonDescargar;
    ImageView imagenC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonDescargar = findViewById(R.id.botonDes);
        imagenC = findViewById(R.id.imageViewasd);

        botonDescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    public void run() {
                        final Bitmap bitmap =
                                loadImageFromNetwork("https://lh3.googleusercontent.com/5herE96nsqogvHKMCXzDO-aqLFQ5WYgVDC6JsWgsD17ZzVGhZS0IArMkcH8vsh81-s-2r2jaPLMV-yUPmJdTBQ8plWUQqHZGucxcye-aS6_3lcHSgQ");
                        imagenC.post(new Runnable() {
                            public void run() {
                                imagenC.setImageBitmap(bitmap);
                            }
                        });
                    }
                }).start();
            }
        });

    }

    private Bitmap loadImageFromNetwork (String imageURL){
        try {
            URL url = new URL(imageURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            return BitmapFactory.decodeStream(inputStream);

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}