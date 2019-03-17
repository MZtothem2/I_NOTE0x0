package kr.co.md00to22.i_note0x0;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TestmodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testmode);


    }

    public void clickTrial(View view) {
        switch (view.getId()){
            case R.id.btn_directormode:
                //testM.testTeacherMode();
                return;

            case R.id.btn_teacherormode:
                //testM.testTeacherMode();
                break;

            case R.id.btn_parentmode:
                //testM.testParentMode();
                break;
        }


        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }//clickTrial
}
