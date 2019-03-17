package kr.co.md00to22.i_note0x0;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    RadioGroup rbg;
    int memberGrade=-1;
    EditText etName, etPw, etPw2, etMail, etOrg;

    DatabaseReference dataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        rbg=findViewById(R.id.rbg_grade);

        etName=findViewById(R.id.et_name_signup);
        etPw=findViewById(R.id.et_pw_signup);
        etPw2=findViewById(R.id.et_chekcpw_signup);
        etMail=findViewById(R.id.et_mail_signup);
        etOrg=findViewById(R.id.et_organization_signup);

        rbg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_grade1:
                        memberGrade=G.MEMBER_GRADE_DIRECTOR;
                        break;

                    case R.id.rb_grade2:
                        memberGrade=G.MEMBER_GRADE_TEACHER;
                        break;

                    case R.id.rb_grade3:
                        memberGrade=G.MEMBER_GRADE_PARENT;
                        break;
                }

                Toast.makeText(SignupActivity.this, memberGrade+"", Toast.LENGTH_SHORT).show();
            }

        });


        dataRef=FirebaseDatabase.getInstance().getReference("testOrganization/waiting");

    }

    public void clickApplyButton(View view) {

        String name=etName.getText().toString();
        String pw=etPw.getText().toString();
        String email=etMail.getText().toString();
        String or=etOrg.getText().toString();
        int orgNum;
        //입력확인
        if(memberGrade==-1 || name.equals("") || pw.equals("") || email.equals("") || etMail.getText().toString().equals("") || or.equals("")){
            Toast.makeText(this, "모든 항목을 올바르게 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            //orgNum=Integer.parseInt(or);
        }

        //TOdo : 중복확인

        orgNum=2017022700;

        //가입
        if (memberGrade==G.MEMBER_GRADE_PARENT){
            Sign_Parent memberM=new Sign_Parent(email, pw, name, memberGrade);
            dataRef=dataRef.child(memberM.getmIdentifyCode()+"");
            dataRef.setValue(memberM);
        }else{
            Sign_Teacher memberT=new Sign_Teacher(email,pw,name,memberGrade);
            dataRef=dataRef.child(memberT.getmIdentifyCode()+"");
            dataRef.setValue(memberT);
        }


        //초대장, 인증 필요한 다이얼로그
        new AlertDialog.Builder(this).setMessage("가입 신청이 완료되었습니다. \n관리자의 승인 후 이용 가능합니다.").show();
    }
}
