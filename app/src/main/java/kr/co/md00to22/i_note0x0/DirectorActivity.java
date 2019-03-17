package kr.co.md00to22.i_note0x0;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DirectorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_director);
        if (!G.getIsLogined()) return;
        if (G.getLoginDirector()==null) return;

    }
}
