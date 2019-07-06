package com.wenchao.glidestudy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.iv);
        String url = "https://t11.baidu.com/it/u=295569070,4142143425&fm=76";
        WeGlide.with(this).load(url).into(imageView);
    }
}
