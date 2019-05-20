package kr.co.md00to22.i_note0x0;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TNoteWriteFragment extends Fragment {
    int[] checks=new int[]{0,0,0};

    Spinner spinnerClass, spinnerKids;
    RadioGroup[] rg=new RadioGroup[3];
    int[][] rbtnIds=new int[3][];
    EditText etNote;
    TextView btnSubmit;
    ArrayAdapter kidSpinnerAdapter;
    int selectedKidCode=0; int selectedClsCode=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_tnote_write, container, false);

        spinnerClass=view.findViewById(R.id.spinner_class_twrite);
        spinnerKids=view.findViewById(R.id.spinner_kids_twrite);
        etNote=view.findViewById(R.id.et_twrite);
        btnSubmit=view.findViewById(R.id.btn_submit_t);

        rg[0]=view.findViewById(R.id.rbgroup1_twrite);
        rg[1]=view.findViewById(R.id.rbgroup2_twrite);
        rg[2]=view.findViewById(R.id.rbgroup3_twrite);
        rbtnIds[0]=new int[]{R.id.rbtn_twrite11, R.id.rbtn_twrite21, R.id.rbtn_twrite31};
        rbtnIds[1]=new int[]{R.id.rbtn_twrite12, R.id.rbtn_twrite22, R.id.rbtn_twrite32};
        rbtnIds[2]=new int[]{R.id.rbtn_twrite13, R.id.rbtn_twrite23, R.id.rbtn_twrite33};

        TNoteWriteFragment.BtnClickListener btnListener=new TNoteWriteFragment.BtnClickListener();
        btnSubmit.setOnClickListener(btnListener);
        rg[0].setOnCheckedChangeListener(btnListener);
        rg[1].setOnCheckedChangeListener(btnListener);
        rg[2].setOnCheckedChangeListener(btnListener);

        setClassSpinner();
        return view;
    }//onCreateView

    void setClassSpinner(){

        if(Global.getCls()==null){
            Toast.makeText(getContext(), "반 구조 불러오기 실패", Toast.LENGTH_SHORT).show();
            return;
        }

        //반 스피너 - 전체 선택시 모든 알림장 띄우기
        ArrayAdapter classAdapter=new ArrayAdapter(getContext(), R.layout.spinner_item, Global.getArrClsName());
        spinnerClass.setAdapter(classAdapter);
        classAdapter.setDropDownViewResource(R.layout.spinner_dropdown);

        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    Global.arrangeCls_KidsDatas(Global.getArrClsCode().get(position));
                    setChildrenSpinner();
                    selectedClsCode=Global.getArrClsCode().get(position);
                    Toast.makeText(getContext(), selectedClsCode+"", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }//setClassSpinner
    void setChildrenSpinner( ){
        kidSpinnerAdapter=new ArrayAdapter(getContext(),R.layout.spinner_item, Global.getArrKIdsName());
        spinnerKids.setAdapter(kidSpinnerAdapter);
        kidSpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinnerKids.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    Toast.makeText(getContext(), Global.getArrKIdsCode().get(position)+"", Toast.LENGTH_SHORT).show();

                    selectedKidCode=Global.getArrKIdsCode().get(position);
                    Toast.makeText(getContext(), selectedKidCode+"", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }//



    void submitTNote(){
        String url="http://mdmn1.dothome.co.kr/iinote/submit_note_t.php";
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("submit Tnote", response);

                if(response.equals("complete")) Toast.makeText(getContext(), "업로드 완료", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("submit Tnote error", error.getMessage());

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params=new HashMap<>();
                params.put("write_level",Global.getLoginUser().getLevel()+"");
                params.put("write_mem",Global.getLoginUser().getName());
                params.put("write_date",Global.getTImeNow());
                params.put("kid_code",selectedKidCode+"");
                params.put("class_code",selectedClsCode+"");
                params.put("org_code",Global.getLoginUser().getIn_organization()+"");
                params.put("content",etNote.getText().toString());
                params.put("check1",checks[0]+"");
                params.put("check2",checks[1]+"");
                params.put("check",checks[1]+"");


                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(request);
    }


    class BtnClickListener implements View.OnClickListener,RadioGroup.OnCheckedChangeListener{

        //버튼리스너
        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(), "등록버튼", Toast.LENGTH_SHORT).show();
            switch (v.getId()){
                case R.id.btn_submit_t:
                    if (selectedClsCode==0 || selectedKidCode==0){
                        new AlertDialog.Builder(getContext()).setMessage("반 혹은 아동을 선택하세요").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                        return;
                    }
                    submitTNote();
                    break;
            }
        }

        // 라디오버튼 리스너
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            for (int i=0; i<rbtnIds.length; i++){
                if (checkedId==rbtnIds[0][i]) checks[i]=Global.CHECK_O;
                else if (checkedId==rbtnIds[1][i]) checks[i]=Global.CHECK_M;
                else if (checkedId==rbtnIds[2][i]) checks[i]=Global.CHECK_X;
            }
            Toast.makeText(getContext(), "선택값 : "+checks[0]+"/"+checks[1]+"/"+checks[2], Toast.LENGTH_SHORT).show();
        }
    }//ClickListener
}
