package kr.co.md00to22.i_note0x0;

import android.os.Bundle;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class TNoteListFragment extends Fragment {

    RecyclerView recyclerView;
    Spinner spinnerClass, spinnerKids;
    ArrayList<VNoteOne> notes_toshow;

    TnotelistAdapter recyclerAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (Global.getNotesToShow()==null){
            Toast.makeText(getContext(), "알림장 받아오기 오류", Toast.LENGTH_SHORT).show();
            return null;
        }
        Global.arrangeClsDatas();
        notes_toshow=Global.getNotesToShow();

        View view= inflater.inflate(R.layout.fragment_tnote_list,container,false);
        recyclerView=view.findViewById(R.id.recyclerview_tlist);
        spinnerClass=view.findViewById(R.id.spinner_class_tlist);
        spinnerKids=view.findViewById(R.id.spinner_kids_tlist);

        view.findViewById(R.id.btn_towrite_t).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TNoteWriteFragment tNoteWriteFragment=new TNoteWriteFragment();
                FragmentTransaction transaction=getFragmentManager().beginTransaction();

                transaction.replace(R.id.fragment_container, tNoteWriteFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        setClassSpinner();
        drawRecyclerview();
        return view;
    }


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

                    arrangeClsNotes(Global.getArrClsCode().get(position));
                    //recyclerAdapter.notifyDataSetChanged();
                    drawRecyclerview();
                }else if(position==0){
                    notes_toshow=Global.getNotesToShow();
                    drawRecyclerview();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }//setClassSpinner

    void setChildrenSpinner( ){
        ArrayAdapter kidSpinnerAdapter=new ArrayAdapter(getContext(),R.layout.spinner_item, Global.getArrKIdsName());
        spinnerKids.setAdapter(kidSpinnerAdapter);
        kidSpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinnerKids.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    arrangeKidsNotes(Global.getArrKIdsCode().get(position));
                    notes_toshow.clear();
                    arrangeKidsNotes(Global.getArrKIdsCode().get(position));
                    //recyclerAdapter.notifyDataSetChanged();
                    drawRecyclerview();

                }else if (position==0){

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }//

    void arrangeClsNotes(int classCode){
        StringBuffer testbuffer=new StringBuffer();

        ArrayList<VNoteOne> globalArr = Global.getNotesToShow();
        ArrayList<VNoteOne> arr = new ArrayList<>();

        for (int i = globalArr.size()-1; i >= 0; i--) {
            if (globalArr.get(i).getPnote() != null) {
                if (globalArr.get(i).getPnote().getClass_code() == classCode) {
                    arr.add(globalArr.get(i));
                    testbuffer.append(globalArr.get(i).getPnote().getNote_num() + " : " + globalArr.get(i).getPnote().getContnet() + "\n");
                }
            }else{
                if (globalArr.get(i).getTnote().getClass_code() == classCode) {
                    arr.add(globalArr.get(i));
                    testbuffer.append(globalArr.get(i).getTnote().getNote_num() + " : " + globalArr.get(i).getTnote().getContnet() + "\n");
                }
            }
        }
        notes_toshow=arr;

        Log.i("알림장","["+notes_toshow.size()+"]\n"+testbuffer.toString());
    }

    void arrangeKidsNotes(int KidCode){
        StringBuffer testbuffer=new StringBuffer();

        ArrayList<VNoteOne> globalArr = Global.getNotesToShow();
        ArrayList<VNoteOne> arr = new ArrayList<>();

        for (int i = globalArr.size()-1; i >= 0; i--) {
            if (globalArr.get(i).getTnote() != null) {
                if (globalArr.get(i).getTnote().getKid_code() == KidCode) {
                    arr.add(globalArr.get(i));
                    testbuffer.append(globalArr.get(i).getTnote().getNote_num() + " : " + globalArr.get(i).getTnote().getContnet() + "\n");
                }
            } else if (globalArr.get(i).getPnote() != null) {
                if (globalArr.get(i).getPnote().getKid_code() == KidCode) {
                    arr.add(globalArr.get(i));
                    testbuffer.append(globalArr.get(i).getPnote().getNote_num() + " : " + globalArr.get(i).getPnote().getContnet() + "\n");
                }
            }
        }
        notes_toshow=arr;

        Log.i("알림장","["+notes_toshow.size()+"]\n"+testbuffer.toString());
    }

    void drawRecyclerview(){
        recyclerAdapter=new TnotelistAdapter(getContext(), notes_toshow, getFragmentManager());
        recyclerView.setAdapter(recyclerAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }


}
