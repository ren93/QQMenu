package com.renny.qqmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final QQMenu relativeLayout = (QQMenu) findViewById(R.id.avater_container);
        relativeLayout.setImgages(R.drawable.skin_tab_icon_conversation_normal, R.drawable.skin_tab_icon_conversation_selected
                , R.drawable.rvq, R.drawable.rvr);
        relativeLayout.setOnMenuClickListener(new QQMenu.OnMenuClickListener() {
            @Override
            public void onItemClick(View view) {
                Toast.makeText(MainActivity.this, "Click "+relativeLayout.isHasClick(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
