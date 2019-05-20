package kr.co.md00to22.i_note0x0;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText etId, etPw;
    String insertId, insertPw;

    String loginUrl;

    private final int P_COMPLETE_LOGIN=30;
    private final int P_COMPLETE_KID_DATA=31;

    private final int T_COMPLETE_LOGIN=20;
    private final int T_COMPLETE_ORG_DATA=21;
    private final int T_COMPLETE_CLS_DATA=22;
    private final int T_COMPLETE_KIDS_DATA=23;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //초기화
        Global.setLoginUser(null);
        Global.setLoginOrg(null);
        Global.setCls(null);
        Global.setOrgKids(null);
        Global.setNotes_got(null);
        Global.setNotesToShow(null);
        Global.setKids(null);
        Global.setSelectedKid(null);


        setContentView(R.layout.activity_login);

        etId=findViewById(R.id.et_id_login);
        etPw=findViewById(R.id.et_pw_login);

        loginUrl="http://mdmn1.dothome.co.kr/iinote/login.php";
    }

    public void clickLogin(View view) {

        insertId=etId.getText().toString();
        insertPw=etPw.getText().toString();

        //입력조건
        if (!insertId.contains("@") || !insertId.contains(".") || insertId.equals("") || insertId==null || insertPw.equals("") || insertPw==null){
            new AlertDialog.Builder(this).setMessage("회원정보를 올바르게 입력해주세요").setPositiveButton("확인", null).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();

            etId.setText(""); etPw.setText("");

        }else{
         //정상입력시
            loginRequest();
        }
    }

    public void clickSignup(View view) {
        startActivity(new Intent(this, SignupActivity.class));
    }//clickSignup

    public void clickTrial(View view) {
        if(Global.getLoginUser()!=null) Global.setLoginUser(null);
        if(Global.getLoginOrg()!=null) Global.setLoginOrg(null);
        if(Global.getKids()!=null) Global.setKids(null);
        if(Global.getSelectedKid()!=null) Global.setSelectedKid(null);
        if(Global.getCls()!=null) Global.setCls(null);
        if(Global.getOrgKids()!=null) Global.setOrgKids(null);

        switch (view.getId()){
            case R.id.btn_trial_director:
                insertId="director@aaa.com";
                break;

            case R.id.btn_trial_teacher:
                insertId="teacher2@aaa.com";
                break;

            case R.id.btn_trial_parent:
                insertId="parent@aaa.com";

                break;
        }
        insertPw="test";
        loginRequest();


    }//clickTrial

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case P_COMPLETE_LOGIN:
                    requestKIDdata(Global.getLoginUser().getId());
                    break;

                case P_COMPLETE_KID_DATA:
                    startActivity( new Intent(LoginActivity.this, ParentsActivity.class) );
                    break;

                case T_COMPLETE_LOGIN:
                    requestOrg(Global.getLoginUser().getIn_organization());
                    break;

                case T_COMPLETE_ORG_DATA:
                    requestClsDatas(Global.getLoginOrg().getOrg_code());
                    break;

                case T_COMPLETE_CLS_DATA:
                    requestOrgKids(Global.getLoginOrg().getOrg_code());
                    break;

                case T_COMPLETE_KIDS_DATA:
                    startActivity( new Intent(LoginActivity.this, TeachersActivity.class) );
                    break;
            }
        }
    };



    private void requestClsDatas(final int organizationCode){
        String getclsurl="http://mdmn1.dothome.co.kr/iinote/login_getclass.php";
        StringRequest clsRequest=new StringRequest(Request.Method.POST, getclsurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonArray=new JSONArray(response);
                    ArrayList<VClasses> clsss=new ArrayList<>();

                    StringBuffer testBuffer=new StringBuffer();
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        int classcode=Integer.parseInt(jsonObject.getString("class_code"));
                        String classname=jsonObject.getString("class_name");
                        int classage=Integer.parseInt(jsonObject.getString("class_age"));
                        int classorg=Integer.parseInt(jsonObject.getString("in_org"));

                        VClasses cls=new VClasses(classcode,classname,classage,classorg);
                        if(clsss!=null) clsss.add(cls);

                        testBuffer.append("["+classcode+","+classname+"] / ");
                    }

                    Global.setCls(clsss);
                    handler.sendEmptyMessage(T_COMPLETE_CLS_DATA);
                    Log.i("클래스정보", testBuffer.toString());
            }catch (Exception e){

            }
        }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params=new HashMap<>();
                params.put("orgcode", organizationCode+"");
                return params;
            }
        };

        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(clsRequest);
    }

    private void requestOrgKids(final int org_code){
        String url="http://mdmn1.dothome.co.kr/iinote/find_org_kids.php";
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonArray=new JSONArray(response);

                    ArrayList<VKids> arr=new ArrayList<>();
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);

                        String name=jsonObject.getString("kid_name");
                        int code=Integer.parseInt(jsonObject.getString("kid_code"));
                        int cls_code=Integer.parseInt(jsonObject.getString("in_class"));

                        VKids k=new VKids(code,name,cls_code);
                        arr.add(k);
                    }

                    Global.setOrgKids(arr);
                    handler.sendEmptyMessage(T_COMPLETE_KIDS_DATA);
                }catch (Exception e){
                    Log.i("orgkid에러", e.getMessage() +"/"+ response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params=new HashMap<>();
                params.put("in_org", org_code+"");
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }

    private void requestOrg(final int organizationCode){
        String orgRequestUrl="http://mdmn1.dothome.co.kr/iinote/login_getorg.php";

        StringRequest orgRequest=new StringRequest(Request.Method.POST, orgRequestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);

                    String orgcode_s=jsonObject.getString("org_code");


                    int orgcode=Integer.parseInt(orgcode_s);
                    //사용자정보의 기관코드와 비교
                    if(orgcode!=Global.getLoginUser().getIn_organization()) return;

                    String name=jsonObject.getString("name");
                    String address=jsonObject.getString("addr");
                    String phone=jsonObject.getString("phone");


                    VOrganization loginOrg=new VOrganization(orgcode, name, address, phone);
                    Global.setLoginOrg(loginOrg);

                    handler.sendEmptyMessage(T_COMPLETE_ORG_DATA);
                }catch (Exception e){ }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "기관 정보 로드 오류", Toast.LENGTH_SHORT).show();
                Log.i("Login-org", error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params=new HashMap<>();
                params.put("orgcode", organizationCode+"");
                return params;
            }
        };

        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(orgRequest);

    }

    private void requestKIDdata(final String userid){
        String findKidUrl="http://mdmn1.dothome.co.kr/iinote/find_kids.php";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, findKidUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);

                    ArrayList<VKids> kids=new ArrayList<>();
                    for(int i=0; i<jsonArray.length() ; i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);

                        int status=Integer.parseInt(jsonObject.getString("kid_status"));
                        if(status!=Global.KID_STATE_ING) break;

                        int kidCode=Integer.parseInt(jsonObject.getString("kid_code"));
                        String name=jsonObject.getString("kid_name");
                        int age=Integer.parseInt(jsonObject.getString("birth_year"));
                        int in_org=Integer.parseInt(jsonObject.getString("in_org"));
                        String in_orgName=jsonObject.getString("in_orgname");
                        int in_class=Integer.parseInt(jsonObject.getString("in_class"));
                        String photourl=jsonObject.getString("photo");

                        ArrayList<String> adults=new ArrayList<>();
                        if ( (jsonObject.getString("adult1")!=null) || (!jsonObject.getString("adult1").equals(""))) adults.add(jsonObject.getString("adult1"));
                        if ( (jsonObject.getString("adult2")!=null) || (!jsonObject.getString("adult2").equals(""))) adults.add(jsonObject.getString("adult2"));
                        if ( (jsonObject.getString("adult3")!=null) || (!jsonObject.getString("adult3").equals(""))) adults.add(jsonObject.getString("adult3"));
                        if ( (jsonObject.getString("adult4")!=null) || (!jsonObject.getString("adult4").equals(""))) adults.add(jsonObject.getString("adult4"));

                        VKids vKids=new VKids(kidCode,name, status,age,in_org, in_orgName,in_class,photourl,adults);
                        kids.add(vKids);
                    }

                    Global.setKids(kids);
                    handler.sendEmptyMessage(P_COMPLETE_KID_DATA);
                }catch (Exception e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params=new HashMap<>();
                params.put("login_id", userid);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void loginRequest(){
        if (Global.getLoginUser()!=null) return;
        loginUrl="http://mdmn1.dothome.co.kr/iinote/login.php";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String id=jsonObject.getString("member_id");
                    String pw=jsonObject.getString("member_pw");
                    int level=Integer.parseInt(jsonObject.getString("level"));
                    int approved=Integer.parseInt(jsonObject.getString("approved"));
                    String name=jsonObject.getString("name");
                    String phone=jsonObject.getString("phone");
                    String device_code=jsonObject.getString("device_code");
                    int in_org=Integer.parseInt(jsonObject.getString("in_org"));

                    if(device_code==null || device_code.equals("")) device_code="";
                    VMemeber loginMemeber=new VMemeber(id, pw, level, approved, name, phone, device_code, in_org);


                    if (approved!=Global.IS_APPROVED){
                        //미승인 시 로그아웃
                        new AlertDialog.Builder(LoginActivity.this).setMessage("관리자의 승인이 필요합니다.").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        }).show();
                    }else{
                        Global.setLoginUser(loginMemeber);

                        //등급에 따른 정보 받기
                        if(Global.getLoginUser().getLevel()==Global.MEMBER_GRADE_PARENT) handler.sendEmptyMessage(P_COMPLETE_LOGIN);
                        else handler.sendEmptyMessage(T_COMPLETE_LOGIN);
                    }

                } catch (JSONException e) {
                    new AlertDialog.Builder(LoginActivity.this).setMessage(e.getMessage()).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params=new HashMap<>();
                params.put("password", insertPw);
                params.put("idEmail", insertId);
                return params;
            }
        };

        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }//loginRequest


    @Override
    public void onDestroy() {
        super.onDestroy();
        handler=null;
    }
}
