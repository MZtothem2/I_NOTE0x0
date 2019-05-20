package kr.co.md00to22.i_note0x0;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class PNoteListFragment extends Fragment {

    TextView btnToWrite;
    RecyclerView recyclerView;

    final int COMPLETE_DOWNLOAD_NOTE=8;
    final int COMPLETE_ARRANGE_NOTE=9;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_pnotelist, container, false);

        btnToWrite=view.findViewById(R.id.pnotelist_btn_write);
        recyclerView=view.findViewById(R.id.pnotelist_recycler);

        requestNoteP();
        btnToWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PNoteWriteFragment writePFragment=new PNoteWriteFragment();
                FragmentTransaction ft=getFragmentManager().beginTransaction();

                ft.replace(R.id.fragment_container, writePFragment);
                ft.addToBackStack(null);
                ft.commit();            }
        });

        return view;
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case COMPLETE_DOWNLOAD_NOTE:
                    int r=Global.seperateNotes();
                    handler.sendEmptyMessage(r);
                    break;

                case COMPLETE_ARRANGE_NOTE:
                    drawRecyclerview();
                    break;
            }
        }
    };

    void drawRecyclerview(){
        PnotelistAdapter adapter=new PnotelistAdapter(getContext(), Global.getNotesToShow(), getFragmentManager());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    //노트 구분하기
    void seperateNotes(){
        ArrayList<VNotes> notes=Global.getNotes_got();
        ArrayList<VNoteOne> rangedArr=new ArrayList<>();

        StringBuffer testBuffer=new StringBuffer();

        for(int i=0; i<notes.size(); i++){
            VNoteOne a=new VNoteOne();

            String date=notes.get(i).getWrite_date().split("_")[0];
            int kid=notes.get(i).getKid_code();
            int level=notes.get(i).getWrite_level();

            a.putNote(notes.get(i));

            for(int k=i+1; k<notes.size(); k++){
                if(notes.get(k).getWrite_date().split("_")[0].equals(date) && notes.get(k).getKid_code()==kid){
                    a.putNote(notes.get(k));
                    i++;
                    break;
                }
            }
            rangedArr.add(a);
            testBuffer.append(a.checkNoteOne() + " // ");
        }//for

        Global.setNotesToShow(rangedArr);
        handler.sendEmptyMessage(COMPLETE_ARRANGE_NOTE);

        Log.i("정리한 알림장",testBuffer.toString());
    }

    //노트 받아오기
    private void requestNoteP(){
        String pRurl="http://mdmn1.dothome.co.kr/iinote/requestPNotes.php";

        final StringRequest request=new StringRequest(Request.Method.POST, pRurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // new AlertDialog.Builder(getContext()).setMessage(response).show();

                try{
                    JSONArray jsonArray=new JSONArray(response);
                    ArrayList<VNotes> notes=new ArrayList<>();

                    StringBuffer testBuffer=new StringBuffer();
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

                        if (ch1.equals("null")) c1=Global.CHECK_NONE; else c1=Integer.parseInt(ch1);
                        if (ch2.equals("null")) c2=Global.CHECK_NONE; else c2=Integer.parseInt(ch2);
                        if (ch3.equals("null")) c3=Global.CHECK_NONE; else c3=Integer.parseInt(ch3);


                        int[] checks={c1, c2, c3};

                        String check_time="";
                        if(object.getString("check_time")!=null && !object.getString("check_time").equals("null")) check_time=object.getString("check_time");

                        ArrayList<String> photoes=new ArrayList<>();
                        if(!object.getString("photo1").equals("null")) photoes.add(object.getString("photo1"));
                        if(!object.getString("photo2").equals("null")) photoes.add(object.getString("photo2"));
                        if(!object.getString("photo3").equals("null")) photoes.add(object.getString("photo3"));
                        if(!object.getString("photo4").equals("null")) photoes.add(object.getString("photo4"));
                        if(!object.getString("photo5").equals("null")) photoes.add(object.getString("photo5"));

                        VNotes note=new VNotes(note_num,write_level, write_mem,write_date,kid_code,class_code,org_code,content,checks, check_time, photoes);
                        notes.add(note);

                        testBuffer.append(note.getCheck()[0]+"."+note.getCheck()[1]+"."+note.getCheck()[2]+"\n");
                    }
                    Global.setNotes_got(notes);
                    //TASK_STATE=COMPLETE_DOWNLOAD_NOTE;

                    Log.i("알림장 체크상태", Global.getNotes_got().size()+" / "+ testBuffer.toString());
                    handler.sendEmptyMessage(COMPLETE_DOWNLOAD_NOTE);
                }catch (Exception e){
                    Log.i("PNoteFrag.Error", e.getMessage());
                    Toast.makeText(getContext(), "알림장 받아오기 실패", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("kid_code", Global.getSelectedKid().getKid_code()+"");
                return params;
            }
        };

        Volley.newRequestQueue(getContext()).add(request);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        handler=null;
    }
}
