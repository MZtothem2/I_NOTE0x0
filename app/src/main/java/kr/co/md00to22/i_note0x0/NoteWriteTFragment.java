package kr.co.md00to22.i_note0x0;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NoteWriteTFragment extends Fragment {


    VNote_Teacher note;

    Spinner spinnerClass, spinnerKids;
    RadioGroup[] rg=new RadioGroup[3];
    EditText etCotnent;
    TextView btnAttatchPhoto, btnSubmit, btnDelete, btnNapStart, btnNapEnd;

    LinearLayout layoutAttatchment;

    int[][] rbtnIds=new int[3][3];

    //보낼값
    String dateOftody="";
    int selecctChildCode=-1;
    int[] writeChecks=new int[3];
    String noteContents="";
    String photoUrls="";
    String napTIme="";


    ArrayList<String> childrenNamesInClass=new ArrayList<>();
    ArrayList<Integer> childrenCodesInClass=new ArrayList<>();
    ArrayAdapter childrenAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_note_write_t,container,false);

        spinnerClass=view.findViewById(R.id.spinner_class_twrite);
        spinnerKids=view.findViewById(R.id.spinner_kids_twrite);
        rg[0]=view.findViewById(R.id.rbgroup1_twrite);
        rg[1]=view.findViewById(R.id.rbgroup2_twrite);
        rg[2]=view.findViewById(R.id.rbgroup3_twrite);
        etCotnent=view.findViewById(R.id.et_twrite);
        btnAttatchPhoto=view.findViewById(R.id.btn_attatch_t);
        btnSubmit=view.findViewById(R.id.btn_submit_t);
        layoutAttatchment=view.findViewById(R.id.layout_attatchlist);
        btnDelete=view.findViewById(R.id.btn_delete_t);
        btnNapStart=view.findViewById(R.id.btn_nap_start);
        btnNapEnd=view.findViewById(R.id.btn_nap_end);

        rbtnIds[0]=new int[]{R.id.rbtn_twrite11, R.id.rbtn_twrite12, R.id.rbtn_twrite13};
        rbtnIds[1]=new int[]{R.id.rbtn_twrite21, R.id.rbtn_twrite22, R.id.rbtn_twrite23};
        rbtnIds[2]=new int[]{ R.id.rbtn_twrite31, R.id.rbtn_twrite32, R.id.rbtn_twrite33};


        setTNoteView();

        return view;
    }


    void setTNoteView(){
        //Spinner
        setClassSpinner(); //이 메소드 안에서 childrenSpinner 호출

        //RadioButtons
        RbtnClickListener rbtnClickListener= new RbtnClickListener();
        for(int i=0; i<rg.length; i++) {
            rg[i].setOnCheckedChangeListener(rbtnClickListener);
        }

        //Buttons
        ButtonsClickListener buttonsClickListener =new ButtonsClickListener();
        btnSubmit.setOnClickListener(buttonsClickListener);
        btnDelete.setOnClickListener(buttonsClickListener);
        btnAttatchPhoto.setOnClickListener(buttonsClickListener);
    }//writeTeacherNote

    void setClassSpinner(){

        if(G.getClassNames()==null || G.getClassNames().size()==0){
            Toast.makeText(getContext(), "반 구조 불러오기 실패", Toast.LENGTH_SHORT).show();
            return;
        }


        ArrayAdapter classAdapter=new ArrayAdapter(getContext(), R.layout.spinner_dropdown, G.getClassNames());
        spinnerClass.setAdapter(classAdapter);

        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                childrenCodesInClass.clear(); childrenCodesInClass.add(-1);
                childrenNamesInClass.clear(); childrenNamesInClass.add("전체");
                if (parent.getSelectedItemPosition()==0){
                    //'전체'선택 시
                    childrenNamesInClass.clear();
                    selecctChildCode=-1;
                }else{
                    //반 선택 시
                    //반별 아동 리스트 만들기
                    for(int i=0; i<G.getAllChildren().size(); i++){
                        if (G.getAllChildren().get(i).getClasscode()==G.getClassCodes().get(position)){
                            childrenCodesInClass.add(G.getAllChildren().get(i).getChildCode());
                            childrenNamesInClass.add(G.getAllChildren().get(i).getName());
                        }
                    }
                    setChildrenSpinner();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }//setClassSpinner()

    void setChildrenSpinner(){
        childrenAdapter=new ArrayAdapter(getContext(), R.layout.spinner_dropdown, childrenNamesInClass);
        spinnerKids.setAdapter(childrenAdapter);

        spinnerKids.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0) {
                    selecctChildCode= childrenCodesInClass.get(position);
                    Toast.makeText(getContext(), selecctChildCode+"", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }//setChildrenSpinner()


    void submitNote(){
        String dateOftody=G.getTodayDate().get("_s");

        if (selecctChildCode==-1){
            new AlertDialog.Builder(getContext()).setMessage("어린이를 선택하세요").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
            return;
        }

        noteContents=etCotnent.getText().toString();
        //todo : 사진파일첨부, 낮잠시간
        VNote_Teacher tnote=new VNote_Teacher("dateOftody", writeChecks, napTIme, noteContents, photoUrls, selecctChildCode, G.getLoginOrganization().getOrganization_code());


        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference rootReference=firebaseDatabase.getReference();//최상위노드
        DatabaseReference todayChildRef=rootReference.child("INOTE").child(""+G.getLoginOrganization().getOrganization_code()).child("19_03_14").child(selecctChildCode+"");

        DatabaseReference tnoteRef= todayChildRef.child("tnote");
        tnoteRef.setValue(tnote);


        tnoteRef.addValueEventListener(new ValueEventListener() {
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


        etCotnent.setText("");
    }//submitNote





    class RbtnClickListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

           for (int i=0; i<rbtnIds.length; i++){
               if (checkedId==rbtnIds[i][0]) writeChecks[i]=G.CHECK_O;
               else if (checkedId==rbtnIds[i][1]) writeChecks[i]=G.CHECK_M;
               else if (checkedId==rbtnIds[i][2]) writeChecks[i]=G.CHECK_X;
           }

            Toast.makeText(getContext(), "선택값 : "+writeChecks[0]+"/"+writeChecks[1]+"/"+writeChecks[2], Toast.LENGTH_SHORT).show();
           }

    }//listener

    class ButtonsClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_delete_t:
                    break;

                case R.id.btn_submit_t:
                    submitNote();
                    break;

                case R.id.btn_attatch_t:
                    break;

                case R.id.btn_nap_start:
                   // new TimePickerDialog(getContext(), )
                    break;

                case R.id.btn_nap_end:
                    break;
            }
        }
    }


}
