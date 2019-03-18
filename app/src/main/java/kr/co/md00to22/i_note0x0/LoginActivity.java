package kr.co.md00to22.i_note0x0;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
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
    boolean isRun_login=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etId=findViewById(R.id.et_id_login);
        etPw=findViewById(R.id.et_pw_login);

        loginUrl="http://mdmn1.dothome.co.kr/INOTE/loginDB.php";

    }



    public void clickLogin(View view) {

        insertId=etId.getText().toString();
        insertPw=etPw.getText().toString();

        isRun_login=true;

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

    @Override
    protected void onStop() {
        super.onStop();
        finish();

    }


    public void clickSignup(View view) {
        startActivity(new Intent(this, SignupActivity.class));
    }//clickSignup

    public void clickTrial(View view) {
        switch (view.getId()){
            case R.id.btn_trial_director:
                insertId="director@aaa.com";
                break;

            case R.id.btn_trial_teacher:
                insertId="csungA@aaa.com";
                break;

            case R.id.btn_trial_parent:
                insertId="testP@aaa.com";

                break;
        }
        insertPw="test";
        loginRequest();
        isRun_login=true;


    }//clickTrial


    private void loginRequest(){
        if (!isRun_login) return;

        StringRequest stringRequest =new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //에러발생시
                if(response.contains("error:")) {
                    makeErrorMessage( response.substring(6), "php" );
                    return;
                }

                //정상로그인
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONObject memberObj=jsonObject.getJSONObject("member");

                    if (memberObj.getInt("memberGrade")==G.MEMBER_GRADE_PARENT){
                        //부모일 때
                        String idmail=memberObj.getString("idEmail");
                        String pw=memberObj.getString("password");
                        String name=memberObj.getString("name");
                        int memberGrade=memberObj.getInt("memberGrade");
                        int orgCode=memberObj.getInt("organization_code");
                        String kidsCode=memberObj.getString("p_childCode");
                        int mIdentifyCode=memberObj.getInt("mIdentifyCode");
                        MParent p=new MParent(idmail, pw, name, memberGrade, orgCode, mIdentifyCode ,kidsCode);

                        G.setLoginID(p);
                        Toast.makeText(LoginActivity.this, G.getLoginParent().getId(), Toast.LENGTH_SHORT).show();

                    }
                    else{//교사일 때
                        int isWorking=memberObj.getInt("t_isWorking");

                        if (isWorking!=G.IS_WORKING){
                            new AlertDialog.Builder(LoginActivity.this).setMessage("재직 중이 아닙니다.").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                            isRun_login=false;
                            return;
                        }

                        String idmail=memberObj.getString("idEmail");
                        String pw=memberObj.getString("password");
                        String name=memberObj.getString("name");
                        int memberGrade=memberObj.getInt("memberGrade");
                        int orgCode=memberObj.getInt("organization_code");
                        int mIdentifyCode=memberObj.getInt("mIdentifyCode");
                        MTeacher t=new MTeacher(idmail, pw, name, memberGrade, isWorking, orgCode, mIdentifyCode);


                        G.setLoginID(t);
                        dataRequestTeacher();

                    }


                } catch (JSONException e) {
                    makeErrorMessage(e.getMessage(), null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                makeErrorMessage(error.getMessage(), null);
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params=new HashMap<>();
                params.put("password", insertPw);
                params.put("idEmail", insertId);
                return params;
            }
        };

        Volley.newRequestQueue(LoginActivity.this).add(stringRequest);

    }//loginRequest



    private void dataRequestTeacher(){
       String getDatasUrl="http://mdmn1.dothome.co.kr/INOTE/requestTDatas.php";

        //회원등급을 POST로 보내고, 그에 따라 다른 정보 받기

        StringRequest stringRequest=new StringRequest(StringRequest.Method.POST, getDatasUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    //에러발생시
                    if(response.contains("error:")) {
                        makeErrorMessage(response.substring(6), "php");
                        return;
                    }

                    //정상작동
                    JSONObject jsonObject=new JSONObject(response);
                    Log.i("wwwwwwwwwwwhat1", jsonObject.toString());

                    //교사
                    if (jsonObject.getInt("grade")==G.MEMBER_GRADE_TEACHER || jsonObject.getInt("grade")==G.MEMBER_GRADE_DIRECTOR) {
                        //기관
                        try {
                            JSONObject orgObj = jsonObject.getJSONObject("torganization");
                            MOrganization m = new MOrganization(orgObj.getString("organization_name"), orgObj.getString("organization_address"), orgObj.getString("organization_phone"), orgObj.getInt("organization_code"));
                            G.setLoginOrganization(m);
                            Log.i("wwwwwwwwwwwhat2", orgObj.toString());

                            //반구성
                            JSONArray classArray = jsonObject.getJSONArray("tclasses");
                            Log.i("wwwwwwwwwwwhat3", classArray.toString());

                            for (int i = 0; i < classArray.length(); i++) {
                                String name = classArray.getJSONObject(i).getString("class_name");
                                int year = classArray.getJSONObject(i).getInt("class_year");
                                int age = classArray.getJSONObject(i).getInt("class_age");
                                int classcode = classArray.getJSONObject(i).getInt("class_code");
                                String classteachers = classArray.getJSONObject(i).getString("class_teacher");

                                VClass c = new VClass(name, year, age, classcode, classteachers);
                                G.getLoginOrganization().getClassStructure().add(c);
                            }
                            G.calClassArrays();

                            //전체아동
                            JSONArray childrenArray = jsonObject.getJSONArray("tchildren");
                            Toast.makeText(LoginActivity.this, "children size : "+ childrenArray.length()
                                    , Toast.LENGTH_SHORT).show();

                            for (int i = 0; i < childrenArray.length(); i++) {
                                String name = childrenArray.getJSONObject(i).getString("name");
                                String parentsCode = childrenArray.getJSONObject(i).getString("parents_code");
                                int birthY = childrenArray.getJSONObject(i).getInt("birth_year");
                                int isAttending = childrenArray.getJSONObject(i).getInt("isAttending");
                                int organizationCode = childrenArray.getJSONObject(i).getInt("organization_code");
                                int childCode = childrenArray.getJSONObject(i).getInt("child_code");
                                int classCode = childrenArray.getJSONObject(i).getInt("class_code");

                                MChild k = new MChild(name, birthY, isAttending, childCode, parentsCode, organizationCode, classCode);

                                G.getAllChildren().add(k);
                            }

                            Toast.makeText(LoginActivity.this, "children size2 : "+ childrenArray.length()
                                    , Toast.LENGTH_SHORT).show();

                            //G.calClassNKidsArrays();
                            G.setDataLoaded(true);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));


                        } catch (JSONException e) {
                            makeErrorMessage(e.getMessage(), null);
                        }
                    }else makeErrorMessage("계정 오류", null);

                } catch (Exception e) {
                    makeErrorMessage(e.getMessage(), null);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                makeErrorMessage(error.getMessage(), null);
                G.setDataLoaded(false);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params=new HashMap<>();
                params.put("memberGrade", G.getLogin_MEMBER_Grade()+"");

                if (G.getLogin_MEMBER_Grade()==G.MEMBER_GRADE_DIRECTOR){
                    params.put("organizationCode", G.getLoginDirector().getOrganization_code()+"");

                }else if(G.getLogin_MEMBER_Grade()==G.MEMBER_GRADE_TEACHER){
                    params.put("organizationCode", G.getLoginTeacher().getOrganization_code()+"");

                }else if(G.getLogin_MEMBER_Grade()==G.MEMBER_GRADE_PARENT){
                    //todo:학부모정보 얻기 : 아동-기관
                }


                return params;
            }


        };

        Volley.newRequestQueue(LoginActivity.this).add(stringRequest);


    }//downloadDatas()


    private void makeErrorMessage(String e, String type){

        String eMsg="";
        if(type==null)   eMsg = e;
        else if (type.equals("php")) {

            if (e.equals("organization")) eMsg = "기관 정보를 찾을 수 없습니다.";
            else if (e.equals("classes")) eMsg = "반 정보를 찾을 수 없습니다.";
            else if (e.equals("children")) eMsg = "전체 아동 정보를 찾을 수 없습니다.";
            else if (e.equals("memberGrade")) eMsg = "계정 정보를 찾을 수 없습니다.";

        } else eMsg = e;


        new android.app.AlertDialog.Builder(LoginActivity.this).setMessage(eMsg).setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();

        G.setDataLoaded(false);
    }
}
