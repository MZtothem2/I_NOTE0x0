package kr.co.md00to22.i_note0x0;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NoteWritePFragment extends Fragment {

    int[] checks=new int[3];
    String gohomeAdult="";
    String gohomeTime="";
    String noteContents;
    String photoUrls="";
    int selectChildCode=-1;

    RadioGroup[] rg=new RadioGroup[3];
    int[][] rbtnIds=new int[3][];
    TextView tvGohomeTime;
    EditText etGohomeAdult, etNote;
    TextView btnTimePicker, btnAttatch, btnSubmit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_note_write_p,container,false);

        rg[0]=view.findViewById(R.id.rbgroup1_pwrite);
        rg[1]=view.findViewById(R.id.rbgroup2_pwrite);
        rg[2]=view.findViewById(R.id.rbgroup3_pwrite);
        etGohomeAdult=view.findViewById(R.id.et_gohome_adult);
        tvGohomeTime=view.findViewById(R.id.tv_time_gohome);
        etNote=view.findViewById(R.id.et_pwrite);
        btnTimePicker=view.findViewById(R.id.btn_timepicker_gohome);
        btnAttatch=view.findViewById(R.id.btn_attatch_p);
        btnSubmit=view.findViewById(R.id.btn_submit_p);

        BtnClickListener btnListener= new BtnClickListener();
        btnTimePicker.setOnClickListener(btnListener);
        btnSubmit.setOnClickListener(btnListener);
        btnAttatch.setOnClickListener(btnListener);

        RbtnClickListener rbtnClickListener=new RbtnClickListener();
        rg[0].setOnCheckedChangeListener(rbtnClickListener);
        rg[1].setOnCheckedChangeListener(rbtnClickListener);
        rg[2].setOnCheckedChangeListener(rbtnClickListener);


        rbtnIds[0]=new int[]{R.id.rbtn_pwrite11, R.id.rbtn_pwrite21, R.id.rbtn_pwrite31};
        rbtnIds[1]=new int[]{R.id.rbtn_pwrite12, R.id.rbtn_pwrite22, R.id.rbtn_pwrite32};
        rbtnIds[2]=new int[]{R.id.rbtn_pwrite13, R.id.rbtn_pwrite23, R.id.rbtn_pwrite33};



        return view;
    }

    void submitPNote(){

        String dateOftody=G.getTodayDate().get("_s");

        if (selectChildCode==-1){
            new AlertDialog.Builder(getContext()).setMessage("아동코드 오류").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }

        gohomeAdult=etGohomeAdult.getText().toString();
        noteContents=etNote.getText().toString();
        //todo : 사진파일첨부, 낮잠시간
        VNote_Parent pnote=new VNote_Parent(dateOftody, checks, gohomeAdult, gohomeTime, noteContents, photoUrls, selectChildCode);


        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference rootReference=firebaseDatabase.getReference();//최상위노드
        DatabaseReference todayChildRef=rootReference.child("INOTE").child(""+G.getLoginOrganization().getOrganization_code()).child("19_03_14").child(selectChildCode+"");

        DatabaseReference pnoteRef= todayChildRef.child("pnote");
        pnoteRef.setValue(pnote);

        pnoteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                new AlertDialog.Builder(getContext()).setMessage("알림장이 작성되었습니다.").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    void attatchPhoto(){

    }

    class RbtnClickListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            for (int i=0; i<rbtnIds.length; i++){
                if (checkedId==rbtnIds[i][0]) checks[i]=G.CHECK_O;
                else if (checkedId==rbtnIds[i][1]) checks[i]=G.CHECK_M;
                else if (checkedId==rbtnIds[i][2]) checks[i]=G.CHECK_X;
            }

            Toast.makeText(getContext(), "선택값 : "+checks[0]+"/"+checks[1]+"/"+checks[2], Toast.LENGTH_SHORT).show();
        }

    }//listener


    class BtnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_timepicker_gohome:
                    TimePickerDialog dialog=new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            gohomeTime=hourOfDay+":"+minute;
                            tvGohomeTime.setText(gohomeTime);
                        }
                    }, Integer.parseInt(G.getTodayDate().get("hour")),  Integer.parseInt(G.getTodayDate().get("minute")), false);
                    dialog.show();
                    break;


                case R.id.btn_attatch_p:
                    attatchPhoto();
                    break;

                case R.id.btn_submit_p:
                    submitPNote();
                    break;
            }

        }
    }

}
