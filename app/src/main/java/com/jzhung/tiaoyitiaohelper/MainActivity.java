package com.jzhung.tiaoyitiaohelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jzhung.tiaoyitiaohelper.service.FloatViewService;
import com.jzhung.tiaoyitiaohelper.util.ShellUtil;

public class MainActivity extends AppCompatActivity {
    private Button startBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startBtn = findViewById(R.id.startBtn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FloatViewService.class);
                startService(intent);
            }
        });

        ShellUtil.execShellCmd("echo 1");
    }
}
