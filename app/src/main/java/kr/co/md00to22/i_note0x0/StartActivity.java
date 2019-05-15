package kr.co.md00to22.i_note0x0;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class StartActivity extends AppCompatActivity {
    int selectCode=0;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        tv=findViewById(R.id.testtv);

        if(Global.getLoginUser().getLevel()==Global.MEMBER_GRADE_PARENT) datatestP();
        else datatestT();

        }

    void testRequest(){
        String url="http://mdmn1.dothome.co.kr/iinote/requestTNotes.php";

        StringRequest stringRequest=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> datas=new HashMap<>();
                datas.put("select_type", selectCode+"");
                datas.put("org_code", selectCode+"");

                switch (selectCode){
                    case 2:
                        datas.put("class_code", selectCode+"");

                        break;

                    case 3:
                        datas.put("class_code", selectCode+"");
                        datas.put("kid_code", selectCode+"");

                        break;
                }


                return datas;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }


    void datatestT(){

            HashMap<String, VClasses> map=Global.getCls();
            StringBuffer buffer=new StringBuffer();
            for(String key : map.keySet()){
                String s=map.get(key).getClass_name() +"/"+ map.get(key).getClass_code()  +"/"+  map.get(key).getIn_organization();
                buffer.append(s+"\n");
            }

            tv.setText(buffer);

    }

    void datatestP(){
        StringBuffer buffer=new StringBuffer();

        for(int i=0; i<Global.getKids().size();i++){
            VKids k=Global.getKids().get(i);
            buffer.append(k.getKid_code()+" / "+k.getAge()+" / "+k.getName()+" / "+k.getAdults().size()+"명 / "+k.getIn_class()+"\n");
        }

        tv.setText(buffer);

    }

}
