package kr.co.md00to22.i_note0x0;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private final static int TO_SETTING =100;
    private final static int TEACHER_GET_DATAS=10;
    private final static int PARENT_GET_DATAS=20;
    private final static int COMPLTE_DOWNLOAD=500;

    //기본 구조
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    TextView tv;

    String getDatasUrl;
    int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv=findViewById(R.id.orgname);

        dfSettings("I.NOTE");


        //퍼미션받기
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        }
    }//onCreate

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 100:
                if (grantResults[0]==PackageManager.PERMISSION_DENIED){
                    Toast.makeText(this, "외부저장소 접근 권한 거부\n이미지 업로드 불가", Toast.LENGTH_SHORT).show();
                }
        }
    }


    void dfSettings(String OrganizationName) {

        //초기화
        toolbar=findViewById(R.id.toolbar);
        drawerLayout=findViewById(R.id.drawerlayout);
        navigationView=findViewById(R.id.navigation);

        TextView toolbarTitle=findViewById(R.id.toolbar_title);

        //Toolbar
        setSupportActionBar(toolbar);
        toolbarTitle.setText(OrganizationName);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //좌측 back버튼
        getSupportActionBar().setDisplayShowTitleEnabled(false); //액티비티별 타이틀, 가운데정렬


////////////////////////////////////////////////////////////////////////////Navigation Setting
        //menu icon tint 삭제
        navigationView.setItemIconTintList(null);
        //navigation drawer 메뉴선택
        //1. 헤더 메뉴
        View headerView=navigationView.getHeaderView(0);

        //헤더메뉴의 view들을 각각 참조


        RNavigationListener naviDrawerListener=new RNavigationListener();

        //2.메뉴판
        navigationView.setNavigationItemSelectedListener(naviDrawerListener );

    }//dfSettings


    //상단부 ToolBar설정
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.toolbar_navi_btn, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.navi_btn:
                if(drawerLayout.isDrawerOpen(Gravity.END)) {
                    drawerLayout.closeDrawer(Gravity.END);
                }
                else {
                    drawerLayout.openDrawer(Gravity.END);
                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if( drawerLayout.isDrawerOpen(Gravity.END) ){
            drawerLayout.closeDrawer(Gravity.END);
        }else {
            new AlertDialog.Builder(this).setMessage("I.NOTE를 종료하시겠습니까?").setNegativeButton("아니요", null).setPositiveButton("네", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.this.finish();
                }
            }).create().show();
        }
    }

    public void clickBtns(View view) {
    }

    //NavigationDrawer Setting
    class RNavigationListener implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener  {

        //NavigationDrawer-Header
        @Override
        public void onClick(View v) {

        }//onClick

        //NavigationDrawer-Menu
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            drawerLayout.closeDrawers();
            return false;
        }//onNavigationItemSelected()
    }//RNavigationListener

}//MainActivity
