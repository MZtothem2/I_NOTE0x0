package kr.co.md00to22.i_note0x0;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    RadioGroup rb_level;
    EditText et_id, et_pw,et_pw2, et_name, et_org, et_kidname;
    TextView tv_check_id, tv_check_pw;

    boolean pass_id_inuse=false;
    boolean pass_id_form=false;
    boolean correct_pw=false;
    int level=0;
    int org=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        rb_level=findViewById(R.id.rbg_grade);
        et_id=findViewById(R.id.et_mail_signup);
        et_pw=findViewById(R.id.et_pw_signup);
        et_pw2=findViewById(R.id.et_chekcpw_signup);
        et_name=findViewById(R.id.et_name_signup);
        et_org=findViewById(R.id.et_organization_signup);
        et_kidname=findViewById(R.id.et_organization_kidname);
        tv_check_id=findViewById(R.id.tv_idcheck);
        tv_check_pw=findViewById(R.id.tv_pwcheck);

        FocusListener focusListener=new FocusListener();
        et_id.setOnFocusChangeListener( focusListener);
        et_pw2.setOnFocusChangeListener( focusListener);

        rb_level.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_grade1:
                        level=Global.MEMBER_GRADE_DIRECTOR;
                        break;

                    case R.id.rb_grade2:
                        level=Global.MEMBER_GRADE_TEACHER;
                        break;

                    case R.id.rb_grade3:
                        level=Global.MEMBER_GRADE_PARENT;
                        break;
                }
                Toast.makeText(SignupActivity.this, level+"", Toast.LENGTH_SHORT).show();
            }
        });

        tv_check_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkIDinuse();
            }
        });
    }

    void makeAlert(String s){
        new AlertDialog.Builder(this).setMessage(s).setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    };

    public void clickApplyButton(View view) {
       // checkIDForm();
        checkIDForm();
        Toast.makeText(this, "여기까지 버튼", Toast.LENGTH_SHORT).show();

        if (level!=0 && pass_id_form && pass_id_inuse && correct_pw && !et_kidname.getText().toString().equals("") && !et_org.getText().toString().equals("")){
            String url="http://mdmn1.dothome.co.kr/iinote/signup.php";
            StringRequest signupRQ=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("complete")) makeAlert("등록이 완료되었습니다.\n 관리자 승인 후 이용 가능합니다.");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("로그인에러", error.getMessage());
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> params=new HashMap<>();
                    params.put("member_id", et_id.getText().toString());
                    params.put("member_pw",et_pw.getText().toString());
                    params.put("level", level+"");
                    params.put("approved",Global.NOT_APPROVED+"");
                    params.put("phone","");
                    params.put("device_code","");
                    params.put("in_org",org+"");

                    return params;
                }
            };

            Volley.newRequestQueue(this).add(signupRQ);

        }else{

            //makeAlert("올바른 입력이 아닙니다.");
            makeAlert("입력 정보를 다시 확인하세요.");
            return;
        }

    }


    public void checkIDinuse() {
        String url="http://mdmn1.dothome.co.kr/iinote/signup_inuse_check.php";
        StringRequest idcheckRQ=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("complete")){
                    tv_check_id.setTextColor(Color.BLUE);
                    tv_check_id.setText("사용가능");
                    pass_id_inuse=true;
                    Log.i("아이디중복체크",response);
                }
                else{
                    makeAlert("이미 존재하는 메일입니다. \n다시 확인하세요.");
                    tv_check_id.setTextColor(Color.RED);
                    tv_check_id.setText("중복");
                    pass_id_inuse=false;
                    return;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("아이디중복체크 오류",error.getMessage());

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params=new HashMap<>();
                params.put("member_id", et_id.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(this).add(idcheckRQ);
    }

    public void checkIDForm(){
        String input=et_id.getText().toString();
        if(input.contains("@") && input.contains(".")){
            pass_id_form=true;
        }else{
            makeAlert("메일 주소를 올바르게 입력하세요.");
            pass_id_form=false;
            return;
        }
    }

    class FocusListener implements View.OnFocusChangeListener{

        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            //id
            if (v.getId()==R.id.et_mail_signup){
                if(!hasFocus){
                    //메일양식 올바른지 (pass_id_form)
                    checkIDForm();
                    //중복체크 통과했는지 (pass_id_over)
                    checkIDinuse();
                }else pass_id_inuse=false;


            }

            //pw
            if (v.getId()==R.id.et_chekcpw_signup){
                if (hasFocus) correct_pw=false;
                else {
                    if(et_pw.getText().toString().equals(et_pw2.getText().toString())) {
                        tv_check_id.setTextColor(Color.BLUE);
                        tv_check_id.setText("일치");
                        correct_pw=true;
                    }
                    else{
                        correct_pw=false;
                        makeAlert("비밀번호가 일치하지 않습니다.");
                        tv_check_pw.setTextColor(Color.RED);
                        tv_check_pw.setText("일치하지 않음");
                        correct_pw=false;
                    }
                }
            }

        }
    }
}
