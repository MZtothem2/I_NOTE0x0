package kr.co.md00to22.i_note0x0;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeachersActivity extends AppCompatActivity {
    FragmentTransaction fragtransaction;
    final int COMPLETE_DOWNLOAD_NOTE=8;
    final int COMPLETE_ARRANGE_NOTE=9;

    final int SELECT_SPINNER_CLS=2;
    final int SELECT_SPINNER_KID=3;

    final int REQUEST_ALL_NOTES=10;
    final int REQUEST_CLASS_NOTES=20;
    final int REQUEST_KID_NOTES=30;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers);

        requestTnote(REQUEST_ALL_NOTES,Global.getLoginOrg().getOrg_code());

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
       // ((TextView)findViewById(R.id.toolbar_title)).setText(Global.getLoginUser().getName()+"교사");

    }//onCreate...

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case COMPLETE_DOWNLOAD_NOTE:
                    int r=Global.seperateNotes();
                    handler.sendEmptyMessage(r);
                    break;

                case COMPLETE_ARRANGE_NOTE:
                    openFragment();
                    break;


                case SELECT_SPINNER_CLS:
                    requestTnote(REQUEST_CLASS_NOTES,Global.getLoginOrg().getOrg_code());
                    break;

                case SELECT_SPINNER_KID:
                    requestTnote(REQUEST_KID_NOTES,Global.getSelectedKid().getKid_code());
                    break;
            }
        }
    };




    void openFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        TNoteListFragment tNoteListFragment=new TNoteListFragment();
        transaction.add(R.id.fragment_container,tNoteListFragment);

        transaction.addToBackStack(null);
        transaction.commit();
    }

    void requestTnote(final int seltype, final int need_code){
        String url="http://mdmn1.dothome.co.kr/iinote/requestTNotes.php";

        StringRequest stringRequest=new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonArray=new JSONArray(response);
                    ArrayList<VNotes> notes=new ArrayList<>();

                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject object=jsonArray.getJSONObject(i);

                        int note_num=Integer.parseInt(object.getString("note_num"));
                        int write_level=Integer.parseInt(object.getString("write_level"));
                        String write_mem=object.getString("write_mem");
                        String write_date=object.getString("write_date");
                        int kid_code=Integer.parseInt(object.getString("kid_code"));
                        int class_code=Integer.parseInt(object.getString("class_code"));
                        int org_code=Integer.parseInt(object.getString("org_code"));
                        String content=object.getString("content");

                        String ch1=object.getString("check1");
                        String ch2=object.getString("check2");
                        String ch3=object.getString("check3");
                        int c1=0;
                        int c2=0;
                        int c3=0;

                        if(ch1!=null && !ch1.equals("null"))  c1=Integer.parseInt(ch1);
                        if(ch2!=null && !ch2.equals("null"))  c2=Integer.parseInt(ch2);
                        if(ch3!=null && !ch3.equals("null"))  c3=Integer.parseInt(ch3);
                        int[] checks={c1, c2, c3};

                        String check_time="";
                        if(object.getString("check_time")!=null && !object.getString("check_time").equals("null")) check_time=object.getString("check_time");

                        ArrayList<String> photoes=new ArrayList<>();
                        if(object.getString("photo1")!=null && !object.getString("photo1").equals("null")) photoes.add(object.getString("photo1"));
                        if(object.getString("photo2")!=null && !object.getString("photo2").equals("null")) photoes.add(object.getString("photo2"));
                        if(object.getString("photo3")!=null && !object.getString("photo3").equals("null")) photoes.add(object.getString("photo3"));
                        if(object.getString("photo4")!=null && !object.getString("photo4").equals("null")) photoes.add(object.getString("photo4"));
                        if(object.getString("photo5")!=null && !object.getString("photo5").equals("null")) photoes.add(object.getString("photo5"));

                        VNotes note=new VNotes(note_num,write_level, write_mem,write_date,kid_code,class_code,org_code,content,checks, check_time, photoes);
                        notes.add(note);
                    }
                    Global.setNotes_got(notes);
                    handler.sendEmptyMessage(COMPLETE_DOWNLOAD_NOTE);
                }catch (Exception e){
                    Toast.makeText(TeachersActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> datas=new HashMap<>();
                datas.put("select_type", seltype+"");
                datas.put("org_code", Global.getLoginOrg().getOrg_code()+"");

                switch (seltype){
                    case REQUEST_ALL_NOTES:
                        datas.put("org_code", Global.getLoginOrg().getOrg_code()+"");
                    case REQUEST_CLASS_NOTES:
                        datas.put("class_code", need_code+"");
                        break;

                    case REQUEST_KID_NOTES:
                        datas.put("kid_code", need_code+"");

                        break;
                }


                return datas;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }//requestTnote


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        handler=null;
        super.onDestroy();
    }



}
