package kr.co.md00to22.i_note0x0;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.HashMap;
import java.util.Map;


public class PNoteWriteFragment extends Fragment {
    int[] checks=new int[]{0,0,0};

    RadioGroup[] rg=new RadioGroup[3];
    int[][] rbtnIds=new int[3][];
    EditText etNote;
    TextView btnAttatch, btnSubmit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_pnote_write,container,false);


        rg[0]=view.findViewById(R.id.rbgroup1_pwrite);
        rg[1]=view.findViewById(R.id.rbgroup2_pwrite);
        rg[2]=view.findViewById(R.id.rbgroup3_pwrite);


        etNote=view.findViewById(R.id.et_pwrite);

        btnAttatch=view.findViewById(R.id.btn_attatch_p);
        btnSubmit=view.findViewById(R.id.btn_submit_p);

        BtnClickListener btnListener=new BtnClickListener();
        btnSubmit.setOnClickListener(btnListener);
        btnAttatch.setOnClickListener(btnListener);

        rg[0].setOnCheckedChangeListener(btnListener);
        rg[1].setOnCheckedChangeListener(btnListener);
        rg[2].setOnCheckedChangeListener(btnListener);


        rbtnIds[0]=new int[]{R.id.rbtn_pwrite11, R.id.rbtn_pwrite21, R.id.rbtn_pwrite31};
        rbtnIds[1]=new int[]{R.id.rbtn_pwrite12, R.id.rbtn_pwrite22, R.id.rbtn_pwrite32};
        rbtnIds[2]=new int[]{R.id.rbtn_pwrite13, R.id.rbtn_pwrite23, R.id.rbtn_pwrite33};

        return view;
    }//onCreateView

    void submitPNote() {
        String submitNotePUrl="http://mdmn1.dothome.co.kr/iinote/submit_note_p.php";
        StringRequest submitrq=new StringRequest(Request.Method.POST, submitNotePUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("알림장업로드 결과", response);
                if (response.equals("complete")) Toast.makeText(getContext(), "알림장이 작성되었습니다.", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("PNoteWriteFragment", error.getMessage());
                Toast.makeText(getContext(), "알림장 작성에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("write_level",Global.getLoginUser().getLevel()+"");
                params.put("write_mem", Global.getLoginUser().getId());
                params.put("write_date",Global.getTImeNow());
                params.put("kid_code", Global.getSelectedKid().getKid_code()+"");
                params.put("class_code",Global.getSelectedKid().getIn_class()+"");
                params.put("org_code", Global.getSelectedKid().getIn_organization()+"");
                params.put("content",etNote.getText().toString());
                params.put("check1",checks[0]+"");
                params.put("check2",checks[1]+"");
                params.put("check3",checks[2]+"");
                params.put("check_time",Global.getTImeNow());

                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(submitrq);
    }



    class BtnClickListener implements View.OnClickListener,RadioGroup.OnCheckedChangeListener{

        //버튼리스너
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_attatch_p:
                    //attatchPhoto();
                    break;

                case R.id.btn_submit_p:
                    submitPNote();
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
            //Toast.makeText(getContext(), "선택값 : "+checks[0]+"/"+checks[1]+"/"+checks[2], Toast.LENGTH_SHORT).show();
        }
    }//ClickListener
}
