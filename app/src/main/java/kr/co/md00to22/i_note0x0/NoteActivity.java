package kr.co.md00to22.i_note0x0;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class NoteActivity extends AppCompatActivity {

    TextView tvtestP, tvtestT, tvtest1;


    //기본 구조
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    ProgressDialog dialog;


    //Fragment 붙이기
    FragmentManager fragmanager;
    FragmentTransaction fragtransaction;

    String tNoteTUrl="http://mdmn1.dothome.co.kr/INOTE/requestTNotes.php";
    String tNotePUrl="http://mdmn1.dothome.co.kr/INOTE/requestPNotes.php";

    ArrayList<VNote_Parent> parentNotes=new ArrayList<>();
    ArrayList<VNote_Teacher> teacherNotes=new ArrayList<>();
    ArrayList<VOnedayNote> allNotes=new ArrayList<>();

    private final int tGetTnotes=11;
    private final int tGetPnotes=12;

    private final int pGetTnotes=15;
    private final int pGetPnotes=16;

    private final int tmakeOnedayNote=20;
    private final int pmakeOnedayNote=21;

    private final int noteComplete=30;


    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case tGetTnotes:
                    tdownloadTNotes();
                    break;
                case tGetPnotes:
                    tdownloadPNotes();
                    break;
                case tmakeOnedayNote:
                    tStructNotes();
                    break;


                case pGetTnotes:
                    break;

                case pGetPnotes:
                    break;

                case pmakeOnedayNote:
                    pStructNotes();
                    break;

                case noteComplete:
                    dfSettings(R.string.activity_name_note);
                    drawGradeFragment();
                    //drawGradeFragment();

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_note);

//        //TEST
//        tvtestP=findViewById(R.id.tv_testp);
//        tvtestT=findViewById(R.id.tv_testt);
//        tvtest1=findViewById(R.id.tv_test);



        if(!G.getIsLogined() && G.getLoginDirector()==null && G.getLoginParent()==null && G.getLoginTeacher()==null){
            Toast.makeText(this, "로그인 오류", Toast.LENGTH_SHORT).show();
            finish();
        }

//
//        else if (G.getLoginTeacher()!=null || G.getLoginDirector()!=null)  handler.sendEmptyMessage(tGetTnotes);
//        else if (G.getLoginParent()!=null) handler.sendEmptyMessage(pGetTnotes);


        dfSettings(R.string.activity_name_note);
        drawGradeFragment();

    }//onCreate


    void teacherGetNotesFromFirebase(){
        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference rootRef=firebaseDatabase.getReference();
        DatabaseReference orgNoteRef=rootRef.child("INOTE").child(G.getLoginOrganization().getOrganization_code()+"");

        orgNoteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //dataSnapshot.getValue()
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void pStructNotes(){
        if (teacherNotes.size()==0){
            new AlertDialog.Builder(this).setMessage("알림장 오류").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
            return;
        }

        if (G.getLogin_MEMBER_Grade()==G.MEMBER_GRADE_PARENT) {
            //부모의 알림장 받기 : 한 아이에 대한 알림장 정리하기
            //교사가 작성한 알림장 날짜 기준으로 VOnedayNote 생성 ->tnote 삽입
            HashMap<String, VOnedayNote> mapNotes = new HashMap<>();
            for (int i = 0; i < teacherNotes.size(); i++) {
                String mapKey = G.calDateData(teacherNotes.get(i).getWriteDate(), "_d");
                VOnedayNote mapOnedayNote = new VOnedayNote();

                mapOnedayNote.setNoteTeacher(teacherNotes.get(i));
                mapOnedayNote.setWriteDate(G.calDateData(teacherNotes.get(i).getWriteDate(),"_d"));
                mapOnedayNote.setChildCode(teacherNotes.get(i).getChildCode());
                //mapOnedayNote.setClassCode( );

                mapNotes.put(mapKey, mapOnedayNote);
            }

            //날짜를 기준으로 Pntoe삽입
            for (int k = 0; k < parentNotes.size(); k++) {
                String date = G.calDateData(parentNotes.get(k).getWriteDate(), "_d");

                if (mapNotes.get(date) != null) {
                    //해당날짜의 교사알림장이 있다면 추가
                    mapNotes.get(date).setNoteParent(parentNotes.get(k));
                } else {
                    //해당날짜의 교사 알림장이 없다면 : 만들어서 추가!
                    VOnedayNote onedayNote = new VOnedayNote();
                    onedayNote.setNoteParent(parentNotes.get(k));
                    onedayNote.setWriteDate(G.calDateData(parentNotes.get(k).getWriteDate(), "_d"));

                }
            }
            //HashMap의 키값을 기준으로 오름차순 ArrayList

            TreeMap<String, VOnedayNote> tm=new TreeMap<>(mapNotes);
            Iterator<String> iteratorKey=tm.keySet().iterator();

            while (iteratorKey.hasNext()){
                String key=iteratorKey.next();
                allNotes.add(0, mapNotes.get(key));
            }

        }
    }

    void tStructNotes() {
        if (G.getLogin_MEMBER_Grade() != G.MEMBER_GRADE_TEACHER) return;

        for (int i=0; i<teacherNotes.size(); i++){
            VOnedayNote oned=new VOnedayNote();
            oned.setNoteTeacher(teacherNotes.get(i));
            oned.setWriteDate( G.calDateData(teacherNotes.get(i).getWriteDate(), "_d"));
            oned.setChildCode(teacherNotes.get(i).getChildCode());
            oned.setClassCode(teacherNotes.get(i).getClassCode());

            String noteDate=teacherNotes.get(i).getWriteDate();
            int noteCcode=teacherNotes.get(i).getChildCode();


            for (int k=0; k<parentNotes.size(); k++){
                if (parentNotes.get(k).getWriteDate().equals(noteDate) && parentNotes.get(k).getChildCode()==noteCcode){
                    oned.setNoteParent( parentNotes.get(k) );
                    parentNotes.remove(k);
                }
            }

            allNotes.add(oned);
        }

        Collections.sort(allNotes, new NoteCompare());

        //날짜, 아동코드 일치하는 OnedayNote에 Pnote삽입

        //부모만 작성한 알림장
        if (parentNotes.size()!=0){
            for (int p=0; p<parentNotes.size(); p++){
                String date= G.calDateData(parentNotes.get(p).getWriteDate(), "_d");
                int code=parentNotes.get(p).getChildCode();

                VOnedayNote pwrite=new VOnedayNote();
                pwrite.setNoteParent( parentNotes.get(p) );
                pwrite.setChildCode( parentNotes.get(p).getChildCode() );
                pwrite.setClassCode( parentNotes.get(p). getClassCode() );


                for (int n=0; n<allNotes.size(); n++){
                    String cpdate=G.calDateData(allNotes.get(n).getWriteDate(), "_d");
                    int cpcode=allNotes.get(n).getChildCode();
                    if (date.compareTo( cpdate )==0){
                        if ( code<cpcode ) allNotes.add(n, pwrite);
                    }
                }
            }


        }
        Collections.sort(allNotes, new NoteCompare());
        handler.sendEmptyMessage(noteComplete);

    }
    void drawGradeFragment(){
        //fragment;

        G.setAllNotes(allNotes);

        fragtransaction=getSupportFragmentManager().beginTransaction();
//
//        Bundle bundle=new Bundle();
//        bundle.putParcelableArrayList("allnotes", allNotes);


        if ( (G.getLoginDirector()!=null && G.getLogin_MEMBER_Grade()==G.MEMBER_GRADE_DIRECTOR)
                || (G.getLoginTeacher()!=null && G.getLogin_MEMBER_Grade()==G.MEMBER_GRADE_TEACHER)){
            Toast.makeText(this, teacherNotes.size()+""+parentNotes.size(), Toast.LENGTH_SHORT).show();
            NoteListTFragment noteListTFragment=new NoteListTFragment();
            fragtransaction.add(R.id.note_container,noteListTFragment);


        }else if (G.getLoginParent()!=null && G.getLogin_MEMBER_Grade()==G.MEMBER_GRADE_PARENT){

            NoteListPFragment noteListPfragment= new NoteListPFragment();
            fragtransaction.add(R.id.note_container,noteListPfragment);

        }
        fragtransaction.addToBackStack(null);
        fragtransaction.commit();
    }

    private void tdownloadPNotes(){
        StringRequest stringRequest=new StringRequest(StringRequest.Method.POST, tNotePUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                StringBuffer bufferP=new StringBuffer();

                try{
                    //부모가 쓴 알림장
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray pNoteArr=jsonObject.getJSONArray("pnotes");

                    Log.i("pnote size", pNoteArr.length()+"");

                    for(int i=0; i<pNoteArr.length(); i++){
                        JSONObject pnObj=pNoteArr.getJSONObject(i);

                        String writeDate=pnObj.getString("writeDate");
                        String note=pnObj.getString("note");
                        String photoUrl=pnObj.getString("photoUrls");
                        String gohomeAdult=pnObj.getString("gohome_adult");
                        String gohomeTime=pnObj.getString("gohome_time");

                        int childCode=pnObj.getInt("childCode");
                        int organizationCode=pnObj.getInt("organization_code");

                        String c=pnObj.getString("check_rbs");
                        int[] checks=null;
                        if (c.contains(";")){
                            String[] sarr=c.split(";");
                            checks=new int[sarr.length-1];
                            for (int k=0; k<sarr.length-1; k++){
                                checks[k]=Integer.parseInt( sarr[k] );
                            }
                        }

                        VNote_Parent pn=new VNote_Parent(writeDate, checks, gohomeAdult, gohomeTime, note, photoUrl, childCode);
                        parentNotes.add(0,pn);

                        bufferP.append(pn.getWriteDate() + "/" + note + "\n");
                    }
                    //Log.i("pnote size", parentNotes.size()+"");
//                    bufferP.append("부모 알림장 총 개수 : "+ parentNotes.size());
//                        tvtestP.setText(bufferP.toString());
                    handler.sendEmptyMessage(tmakeOnedayNote);
                }catch (JSONException e) {
                    e.printStackTrace();
                }


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                makeErrorMessage(error.getMessage(), null);
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params=new HashMap<>();
                params.put("organizationCode", G.getLoginOrganization().getOrganization_code()+"");
                return params;
            }
        };
        Volley.newRequestQueue(NoteActivity.this).add(stringRequest);

    }

    private void tdownloadTNotes(){
        StringRequest stringRequest=new StringRequest(StringRequest.Method.POST, tNoteTUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                StringBuffer buffer=new StringBuffer();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    //교사가 쓴 알림장
                    JSONArray tNoteArr=jsonObject.getJSONArray("tnotes");

                    for(int i=0; i<tNoteArr.length(); i++){
                        JSONObject tnObj=tNoteArr.getJSONObject(i);

                        String writeDate=tnObj.getString("writeDate");
                        String napTime=tnObj.getString("napTime");
                        String note=tnObj.getString("note");
                        String photoUrl=tnObj.getString("photoUrls");
                        int childCode=tnObj.getInt("childCode");
                        int organizationCode=tnObj.getInt("organization_code");
                        int classCode=tnObj.getInt("classCode");
                        String c=tnObj.getString("check_rbs");
                        int[] checks=null;
                        if (c.contains(";")){
                            String[] sarr=c.split(";");
                            checks=new int[sarr.length-1];
                            for (int k=0; k<sarr.length-1; k++){
                                checks[k]=Integer.parseInt( sarr[k] );
                            }
                        }

                        VNote_Teacher tn=new VNote_Teacher(writeDate, checks, napTime, note, photoUrl, childCode, organizationCode, classCode);
                        teacherNotes.add(0,tn);

                        buffer.append(tn.getWriteDate()+" / "+ note + "\n");
                    }
//                    buffer.append("교사알림장 총 개수 : "+teacherNotes.size() );
//                    tvtestT.setText(buffer.toString());

                    handler.sendEmptyMessage(tGetPnotes);
                } catch (JSONException e) {
                    makeErrorMessage(e.getMessage(), null);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                makeErrorMessage(error.getMessage(), null);
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params=new HashMap<>();
                params.put("organizationCode", G.getLoginOrganization().getOrganization_code()+"");
                return params;
            }
        };

        Volley.newRequestQueue(NoteActivity.this).add(stringRequest);
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
        new android.app.AlertDialog.Builder(NoteActivity.this).setMessage(eMsg).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
        Log.i("adqwlkqwbfrqkj",eMsg);

        G.setNotesLoaded(false);
    }


    class NoteCompare implements Comparator<VOnedayNote>{
        //날짜(String), 아동코드(int)순 구분
        int ret=0;
        @Override
        public int compare(VOnedayNote o1, VOnedayNote o2) {
            String date1=G.calDateData( o1.getWriteDate(), "_d");
            String date2=G.calDateData( o2.getWriteDate(), "_d");

            int code1=o1.getChildCode();
            int code2=o2.getChildCode();

            //날짜는 오름차순, 이름은 내림차순

            if(date1.compareTo(date2)<0) ret=-1;

            if (date1.equals(date2)){
                //날짜가 같다면 아동코드로 구분(내림차순)
                if ( code1>code2 ) ret=1;
                else if (code1<code2) ret=-1;
            }

            if (date1.compareTo(date2)>0) ret=1;


            return ret;
        }
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

        //헤더메뉴의 view들을 각각 참조
        if(G.getLogin_MEMBER_Grade()!=G.NOT_LOGINED) {
            TextView naviID=headerView.findViewById(R.id.navi_tv_id);
            TextView naviBtnMyInfo=headerView.findViewById(R.id.navi_myinfo);
            TextView naviBtnLogout=headerView.findViewById(R.id.navi_logout);
            CircleImageView naviProfileImg=headerView.findViewById(R.id.navi_img);
            TextView naviName=headerView.findViewById(R.id.navi_tv_name);
            TextView naviGrade=headerView.findViewById(R.id.navi_tv_grade);
            TextView naviDesc=headerView.findViewById(R.id.navi_tv_desc);

            NoteActivity.RNavigationListener naviDrawerListener=new NoteActivity.RNavigationListener();
            naviBtnLogout.setOnClickListener(naviDrawerListener);
            naviBtnMyInfo.setOnClickListener(naviDrawerListener);


            if(G.getLoginTeacher()==null)                     naviGrade.setText("로그인 실패");
            switch (G.getLogin_MEMBER_Grade()){
                case G.MEMBER_GRADE_DIRECTOR:
                    naviID.setText(G.getLoginDirector().getId());
                    naviGrade.setText("원장");
                    naviName.setText(G.getLoginDirector().getName());
                    naviDesc.setText(G.getLoginOrganization().getName());
                    break;

                case G.MEMBER_GRADE_TEACHER:
                    naviID.setText(G.getLoginTeacher().getId());
                    naviGrade.setText("교사");
                    naviName.setText(G.getLoginTeacher().getName());
                    naviDesc.setText(G.getLoginOrganization().getName());

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
        if (drawerLayout.isDrawerOpen(navigationView)) drawerLayout.closeDrawer(navigationView);
        else super.onBackPressed();

    }

    class RNavigationListener implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

        //NavigationDrawer-Header
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.navi_logout:
                    if (G.getLogin_MEMBER_Grade()!=0){
                        new AlertDialog.Builder(NoteActivity.this).setMessage("로그아웃 하시겠습니까?").setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                G.setLoginDirector(null);
                                G.setLoginTeacher(null);
                                G.setLoginParent(null);

                                startActivity(new Intent(NoteActivity.this, LoginActivity.class));
                                finish();
                            }
                        }).setNegativeButton("아니요",null).create().show();
                    }
                    break;

                case R.id.navi_myinfo:
                    Toast.makeText(NoteActivity.this, "내 정보 수정 페이지", Toast.LENGTH_SHORT).show();
//                    if ( (G.getLoginDirector()!=null || G.getLoginParent()!=null ||G.getLoginTeacher()!=null) && !G.getIsLogined() ) {
//                        startActivity(new Intent(NoteActivity.this, InfoActivity.class));
//                    }
//                    break;
            }
        }//onClick

        //NavigationDrawer-Menu
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){

                case R.id.menu_navi_00main:
                    startActivity( new Intent(NoteActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) );
                    finish();
                    break;

                case R.id.menu_navi_01note:
                    if (G.isDataLoaded()) startActivity( new Intent(NoteActivity.this, NoteActivity.class) );
                    else {
                        new AlertDialog.Builder(NoteActivity.this).setMessage("알림장 정보를 불러올 수 없습니다.").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                    }
                    //TODO : 알림장 작성 중에 나갈 때 작성내용에 대해 확인 Dialog띄우기


                    break;

                case R.id.menu_navi_02album:
                    startActivity( new Intent(NoteActivity.this, AlbumActivity.class) );
                    break;

                case R.id.menu_navi_03notice:
                    startActivity( new Intent(NoteActivity.this, NoticeActivity.class) );
                    break;

                case R.id.menu_navi_10preference:
                    Toast.makeText(NoteActivity.this, "설정", Toast.LENGTH_SHORT).show();

                    break;
            }

            drawerLayout.closeDrawers();
            return false;
        }//onNavigationItemSelected
    }//RNavigationListener
}//Activity
