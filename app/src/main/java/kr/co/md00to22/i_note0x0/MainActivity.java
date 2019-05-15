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

import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
//
//    private final static int TO_SETTING =100;
//    private final static int TEACHER_GET_DATAS=10;
//    private final static int PARENT_GET_DATAS=20;
//    private final static int COMPLTE_DOWNLOAD=500;
//
//    //기본 구조
//    Toolbar toolbar;
//    DrawerLayout drawerLayout;
//    NavigationView navigationView;
//
//    TextView tv;
//
//    String getDatasUrl;
//    int a;
//
//    Handler handler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what){
//                case TO_SETTING:
//                    break;
//
//                case TEACHER_GET_DATAS:
//                    dfSettings("I.NOTE");
//                    break;
//
//                case PARENT_GET_DATAS:
//                    //dataRequestTeacher();
//                    break;
//            }
//        }
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        //퍼미션받기
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
//            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
//                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
//            }
//        }
//
//        //회원등급에 따라 자료받기
//        if (G.getLoginDirector()!=null && G.getLogin_MEMBER_Grade()==G.MEMBER_GRADE_DIRECTOR){
//            handler.sendEmptyMessage(TEACHER_GET_DATAS);
//
//        }else if(G.getLoginTeacher()!=null && G.getLogin_MEMBER_Grade()==G.MEMBER_GRADE_TEACHER){
//            handler.sendEmptyMessage(TEACHER_GET_DATAS);
//
//
//        }else if (G.getLoginParent()!=null && G.getLogin_MEMBER_Grade()==G.MEMBER_GRADE_PARENT){
//            handler.sendEmptyMessage(PARENT_GET_DATAS);
//
//        }else{
//            Toast.makeText(this, "로그인 오류", Toast.LENGTH_SHORT).show();
//            finish();
//        }
//
//        setContentView(R.layout.activity_main);
//        tv=findViewById(R.id.orgname);
//
//    }//onCreate
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode){
//            case 1001:
//                if (grantResults[0]==PackageManager.PERMISSION_DENIED){
//                    Toast.makeText(this, "외부저장소 접근 권한 거부\n이미지 업로드 불가", Toast.LENGTH_SHORT).show();
//                }
//        }
//    }
//
//    //메인화면 버튼
//    public void clickBtns(View view) {
//        switch (view.getId()){
//            case R.id.btn_note:
//                if (G.isDataLoaded()) startActivity(new Intent(MainActivity.this, NoteActivity.class));
//                else new AlertDialog.Builder(MainActivity.this).setMessage("알림장 정보를 불러올 수 없습니다.").setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                }).show();
//                break;
//
//            case R.id.btn_album:
//                startActivity(new Intent(MainActivity.this, AlbumActivity.class));
//                break;
//
//            case R.id.btn_notice:
//                startActivity(new Intent(MainActivity.this, NoticeActivity.class));
//                break;
//        }
//    }
//
//
//    void dfSettings(String OrganizationName) {
//
//        //초기화
//        toolbar=findViewById(R.id.toolbar);
//        drawerLayout=findViewById(R.id.drawerlayout);
//        navigationView=findViewById(R.id.navigation);
//
//        TextView toolbarTitle=findViewById(R.id.toolbar_title);
//
//        //Toolbar
//        setSupportActionBar(toolbar);
//        toolbarTitle.setText(OrganizationName);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //좌측 back버튼
//        getSupportActionBar().setDisplayShowTitleEnabled(false); //액티비티별 타이틀, 가운데정렬
//
//
//////////////////////////////////////////////////////////////////////////////Navigation Setting
//        //menu icon tint 삭제
//        navigationView.setItemIconTintList(null);
//        //navigation drawer 메뉴선택
//        //1. 헤더 메뉴
//        View headerView=navigationView.getHeaderView(0);
//
//        //헤더메뉴의 view들을 각각 참조
//        if(G.getLogin_MEMBER_Grade()!=G.NOT_LOGINED) {
//            TextView naviID=headerView.findViewById(R.id.navi_tv_id);
//            TextView naviBtnMyInfo=headerView.findViewById(R.id.navi_myinfo);
//            TextView naviBtnLogout=headerView.findViewById(R.id.navi_logout);
//            CircleImageView naviProfileImg=headerView.findViewById(R.id.navi_img);
//            TextView naviName=headerView.findViewById(R.id.navi_tv_name);
//            TextView naviGrade=headerView.findViewById(R.id.navi_tv_grade);
//            TextView naviDesc=headerView.findViewById(R.id.navi_tv_desc);
//
//            RNavigationListener naviDrawerListener=new RNavigationListener();
//            naviBtnLogout.setOnClickListener(naviDrawerListener);
//            naviBtnMyInfo.setOnClickListener(naviDrawerListener);
//
//            if(G.getLoginTeacher()==null)                     naviGrade.setText("로그인 실패");
//            switch (G.getLogin_MEMBER_Grade()){
//                case G.MEMBER_GRADE_DIRECTOR:
//                    naviID.setText(G.getLoginDirector().getId());
//                    naviGrade.setText("원장");
//                    naviName.setText(G.getLoginDirector().getName());
//                    naviDesc.setText(G.getLoginOrganization().getName());
//                    if (G.getLoginDirector().getProfilePhotoAddr()!=null && !G.getLoginDirector().getProfilePhotoAddr().equals(""))
//                        Picasso.get().load(G.getLoginDirector().getProfilePhotoAddr()).into(naviProfileImg);
//                    break;
//
//                case G.MEMBER_GRADE_TEACHER:
//                    if (G.getLoginTeacher()!=null){
//                        naviID.setText(G.getLoginTeacher().getId());
//
//                        naviGrade.setText("교사");
//                        naviName.setText(G.getLoginTeacher().getName());
//                        naviDesc.setText(G.getLoginOrganization().getName());
//                        if (G.getLoginTeacher().getProfilePhotoAddr()!=null && !G.getLoginTeacher().getProfilePhotoAddr().equals(""))
//                            Picasso.get().load(G.getLoginTeacher().getProfilePhotoAddr()).into(naviProfileImg);
//                    }
//                    break;
//
//                case G.MEMBER_GRADE_PARENT:
//                    naviID.setText(G.getLoginParent().getId());
//                    naviGrade.setText("부모님");
//                    naviName.setText(G.getLoginParent().getName());
//                    naviDesc.setText("");
//                    if (G.getLoginParent().getProfilePhotoAddr()!=null && !G.getLoginParent().getProfilePhotoAddr().equals(""))
//                        Picasso.get().load(G.getLoginParent().getProfilePhotoAddr()).into(naviProfileImg);
//                    break;
//            }
//
//            //2.메뉴판
//            navigationView.setNavigationItemSelectedListener(naviDrawerListener );
//        }
//    }//dfSettings
//
//
//    //상단부 ToolBar설정
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        new MenuInflater(this).inflate(R.menu.toolbar_navi_btn, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case android.R.id.home:
//                onBackPressed();
//                break;
//
//            case R.id.navi_btn:
//                if(drawerLayout.isDrawerOpen(Gravity.END)) {
//                    drawerLayout.closeDrawer(Gravity.END);
//                }
//                else {
//                    drawerLayout.openDrawer(Gravity.END);
//                }
//                return true;
//
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onBackPressed() {
//
//        if( drawerLayout.isDrawerOpen(Gravity.END) ){
//            drawerLayout.closeDrawer(Gravity.END);
//        }else {
//            new AlertDialog.Builder(this).setMessage("I.NOTE를 종료하시겠습니까?").setNegativeButton("아니요", null).setPositiveButton("네", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    MainActivity.this.finish();
//                }
//            }).create().show();
//        }
//    }
//
//    //NavigationDrawer Setting
//    class RNavigationListener implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener  {
//
//        //NavigationDrawer-Header
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()){
//                case R.id.navi_logout:
//                    if ( (G.getLoginDirector()!=null || G.getLoginParent()!=null ||G.getLoginTeacher()!=null) && !G.getIsLogined() ){
//                        new AlertDialog.Builder(MainActivity.this).setMessage("로그아웃 하시겠습니까?").setPositiveButton("네", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                G.setLoginDirector(null);
//                                G.setLoginTeacher(null);
//                                G.setLoginParent(null);
//                                G.setIsLogined(false);
//                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                                finish();
//                            }
//                        }).setNegativeButton("아니요",null).create().show();
//                    }else{
//                        Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
//                    }
//                    break;
//
//                case R.id.navi_myinfo:
//                    Toast.makeText(MainActivity.this, "내 정보 수정 페이지", Toast.LENGTH_SHORT).show();
//                    if ( (G.getLoginDirector()!=null || G.getLoginParent()!=null ||G.getLoginTeacher()!=null) && !G.getIsLogined() ) {
//                        startActivity(new Intent(MainActivity.this, InfoActivity.class));
//                    }
//                    break;
//            }
//        }//onClick
//
//        //NavigationDrawer-Menu
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//            switch (menuItem.getItemId()) {
//
//                case R.id.menu_navi_00main:
//                    break;
//
//                case R.id.menu_navi_01note:
//                    if (G.isDataLoaded()) startActivity(new Intent(MainActivity.this, NoteActivity.class));
//                    else {
//                        new AlertDialog.Builder(MainActivity.this).setMessage("알림장 정보를 불러올 수 없습니다.").setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        }).show();
//                    }
//                    break;
//
//                case R.id.menu_navi_02album:
//                    startActivity(new Intent(MainActivity.this, AlbumActivity.class));
//                    break;
//
//                case R.id.menu_navi_03notice:
//                    startActivity(new Intent(MainActivity.this, NoticeActivity.class));
//                    break;
//
//                case R.id.menu_navi_10preference:
//                    //startActivity(new Intent(MainActivity.this, NoteActivity.class));
//                    break;
//            }
//
//            drawerLayout.closeDrawers();
//            return false;
//        }//onNavigationItemSelected()
//    }//RNavigationListener

}//MainActivity
