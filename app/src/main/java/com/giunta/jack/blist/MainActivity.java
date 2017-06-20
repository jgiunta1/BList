package com.giunta.jack.blist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static EditText keywordView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find EditText view
        keywordView = (EditText) findViewById(R.id.keyword);

    }

    public void searchBooks(View v){
        String keyword = keywordView.getText().toString();

    }
}
