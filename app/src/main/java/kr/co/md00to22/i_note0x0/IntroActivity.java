package kr.co.md00to22.i_note0x0;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class IntroActivity extends AppCompatActivity {

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        iv=findViewById(R.id.iv_logo);

        Animation ani=AnimationUtils.loadAnimation(this,R.anim.intro_appear);
        iv.startAnimation(ani);
        handler.sendEmptyMessageDelayed(10, 5000);
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
            startActivity(intent);

            //MainActivity 전환 후 IntroActivity 없애기 -> 이 경우 전환 시 애니메이션 줄 수 X. finish에 delay하든지
            finish();

        }
    };



    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler=null;
    }
}
