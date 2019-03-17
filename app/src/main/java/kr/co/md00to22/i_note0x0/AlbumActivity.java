package kr.co.md00to22.i_note0x0;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class AlbumActivity extends AppCompatActivity {

    //기본 구조
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        if (!G.getIsLogined()) return;

        dfSettings(R.string.activity_name_album);

    }//onCreate

    @Override
    protected void onResume() {
        FragmentManager manager=getSupportFragmentManager();

        //Todo: 조건문 - 로그인한 id의 등급에 따라
        //if(로그인 등급)
        if (G.getLoginDirector()!=null && G.getLogin_MEMBER_Grade()==G.MEMBER_GRADE_DIRECTOR){

        }else if(G.getLoginTeacher()!=null && G.getLogin_MEMBER_Grade()==G.MEMBER_GRADE_TEACHER){

        }else if (G.getLoginParent()!=null && G.getLogin_MEMBER_Grade()==G.MEMBER_GRADE_PARENT){

        }else{
            Toast.makeText(this, "로그인 오류", Toast.LENGTH_SHORT).show();
            finish();
        }

        super.onResume();
    }

    private void makeErrorMessage(String e, String type){

        String eMsg="";
        if(type==null){
            eMsg=e;
        }else if (type!=null && type.equals("php")) {

            if (e.equals("organization")) eMsg = "기관 정보를 찾을 수 없습니다.";
            else if (e.equals("classes")) eMsg = "반 정보를 찾을 수 없습니다.";
            else if (e.equals("children")) eMsg = "전체 아동 정보를 찾을 수 없습니다.";
            else if (e.equals("memberGrade")) eMsg = "계정 정보를 찾을 수 없습니다.";

            else eMsg = "";

        }


        new android.app.AlertDialog.Builder(AlbumActivity.this).setMessage(eMsg).setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();

        G.setAlbumLoaded(false);
    }

    private void getAllPhoto(){
        if (G.getLogin_MEMBER_Grade()==G.MEMBER_GRADE_TEACHER|| G.getLogin_MEMBER_Grade()==G.MEMBER_GRADE_DIRECTOR){

        }else if (G.getLogin_MEMBER_Grade()==G.MEMBER_GRADE_PARENT){

        }else{
            makeErrorMessage("계정 정보 오류",null);
        }
    }


    void dfSettings(int nameOfActivity) {

        //초기화
        toolbar=findViewById(R.id.toolbar);
        drawerLayout=findViewById(R.id.drawerlayout);
        navigationView=findViewById(R.id.navigation);

        TextView toolbarTitle=findViewById(R.id.toolbar_title);

        //Toolbar
        setSupportActionBar(toolbar);
        toolbarTitle.setText(nameOfActivity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //좌측 back버튼
        getSupportActionBar().setDisplayShowTitleEnabled(false); //액티비티별 타이틀, 가운데정렬


////////////////////////////////////////////////////////////////////////////Navigation Setting
        //menu icon tint 삭제
        navigationView.setItemIconTintList(null);
        //navigation drawer 메뉴선택
        //1. 헤더 메뉴
        View headerView=navigationView.getHeaderView(0);

        if(G.getLogin_MEMBER_Grade()!=G.NOT_LOGINED) {
            TextView naviID=headerView.findViewById(R.id.navi_tv_id);
            TextView naviBtnMyInfo=headerView.findViewById(R.id.navi_myinfo);
            TextView naviBtnLogout=headerView.findViewById(R.id.navi_logout);
            CircleImageView naviProfileImg=headerView.findViewById(R.id.navi_img);
            TextView naviName=headerView.findViewById(R.id.navi_tv_name);
            TextView naviGrade=headerView.findViewById(R.id.navi_tv_grade);
            TextView naviDesc=headerView.findViewById(R.id.navi_tv_desc);

            AlbumActivity.RNavigationListener naviDrawerListener=new AlbumActivity.RNavigationListener();
            naviBtnLogout.setOnClickListener(naviDrawerListener);
            naviBtnMyInfo.setOnClickListener(naviDrawerListener);


            if(G.getLoginTeacher()==null)                     naviGrade.setText("로그인 실패");
            switch (G.getLogin_MEMBER_Grade()){
                case G.MEMBER_GRADE_DIRECTOR:
                    naviID.setText(G.getLoginDirector().getId());
                    naviGrade.setText("원장");
                    naviName.setText(G.getLoginDirector().getName());
                    naviDesc.setText("좋은 하루 되세요.");
                    break;

                case G.MEMBER_GRADE_TEACHER:
                    naviID.setText(G.getLoginTeacher().getId());
                    naviGrade.setText("교사");
                    naviName.setText(G.getLoginTeacher().getName());
                    naviDesc.setText(" 담임교사");

                    break;
                case G.MEMBER_GRADE_PARENT:
                    naviID.setText(G.getLoginParent().getId());
                    naviGrade.setText("부모님");
                    naviName.setText(G.getLoginParent().getName());
                    naviDesc.setText("");

                    break;
            }

            //2.메뉴판
            navigationView.setNavigationItemSelectedListener(naviDrawerListener );
        }
    }//dfSettings

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

        if( drawerLayout.isDrawerOpen(Gravity.END) ) drawerLayout.closeDrawer(Gravity.END);
        else super.onBackPressed();
    }

    class RNavigationListener implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

        //NavigationDrawer-Header
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.navi_logout:
                    if (G.getLogin_MEMBER_Grade()!=0){
                        new AlertDialog.Builder(AlbumActivity.this).setMessage("로그아웃 하시겠습니까?").setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                G.setLoginDirector(null);
                                G.setLoginTeacher(null);
                                G.setLoginParent(null);

                                startActivity(new Intent(AlbumActivity.this, LoginActivity.class));
                                finish();
                            }
                        }).setNegativeButton("아니요",null).create().show();
                    }
                    break;

                case R.id.navi_myinfo:
                    Toast.makeText(AlbumActivity.this, "내 정보 수정 페이지", Toast.LENGTH_SHORT).show();
//                    if ( (G.getLoginDirector()!=null || G.getLoginParent()!=null ||G.getLoginTeacher()!=null) && !G.getIsLogined() ) {
//                        startActivity(new Intent(AlbumActivity.this, InfoActivity.class));
//                    }
                    break;
            }
        }//onClick

        //NavigationDrawer-Menu
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){

                case R.id.menu_navi_00main:
                    startActivity( new Intent(AlbumActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) );
                    finish();
                    break;

                case R.id.menu_navi_01note:
                    if (G.isDataLoaded()) startActivity( new Intent(AlbumActivity.this, NoteActivity.class) );
                    else {
                        new AlertDialog.Builder(AlbumActivity.this).setMessage("알림장 정보를 불러올 수 없습니다.").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                    }
                    break;

                case R.id.menu_navi_02album:
                    break;

                case R.id.menu_navi_03notice:
                    startActivity( new Intent(AlbumActivity.this, NoticeActivity.class) );
                    break;

                case R.id.menu_navi_10preference:
                    Toast.makeText(AlbumActivity.this, "설정", Toast.LENGTH_SHORT).show();

                    break;
            }

            drawerLayout.closeDrawers();
            return false;
        }//onNavigationItemSelected
    }//RNavigationListener
}//Activity
