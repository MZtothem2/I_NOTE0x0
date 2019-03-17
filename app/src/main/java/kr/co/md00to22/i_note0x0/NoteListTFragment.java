package kr.co.md00to22.i_note0x0;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class NoteListTFragment extends Fragment {

    Context context;

    Spinner spinnerClass;
    Spinner spinnerKids;
    Button btnToWrite;
    RecyclerView recyclerViewTList;
    NoteTAdapter recyclerAdapter;
    ArrayList<VOnedayNote> selectedNotes=new ArrayList<>();
    ArrayList<VOnedayNote> notesOfClass;

    //스피너
    ArrayList<String> childrenNamesInClass=new ArrayList<>();
    ArrayList<Integer> childrenCodesInClass=new ArrayList<>();
    ArrayAdapter childrenAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_note_list_t, container, false);
        getNotes();

        context=getContext();
        spinnerClass=view.findViewById(R.id.spinner_class_tlist);
        spinnerKids=view.findViewById(R.id.spinner_kids_tlist);
        btnToWrite=view.findViewById(R.id.btn_towrite_t);
        recyclerViewTList=view.findViewById(R.id.recyclerview_tlist);

        btnToWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteWriteTFragment writeTFragment=new NoteWriteTFragment();
                openFragment(writeTFragment);
            }
        });

        //스피너 설정


        setClassSpinner();

        selectedNotes=G.getAllNotes();
        recyclerAdapter=new NoteTAdapter( selectedNotes, getActivity(), getFragmentManager());
        recyclerViewTList.setAdapter(recyclerAdapter);

        return view;
    }

    private void getNotes(){
        if (G.getLogin_MEMBER_Grade()==G.MEMBER_GRADE_DIRECTOR || G.getLogin_MEMBER_Grade()==G.MEMBER_GRADE_TEACHER){

        }else if (G.getLogin_MEMBER_Grade()==G.MEMBER_GRADE_PARENT){

        }else{

        }
    }

    private void openFragment(final Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.note_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    void setClassSpinner(){


        if(G.getClassNames()==null || G.getClassNames().size()==0){
            Toast.makeText(context, "반 구조 불러오기 실패", Toast.LENGTH_SHORT).show();
            return;
        }

        //반 스피너 - 전체 선택시 모든 알림장 띄우기
        ArrayAdapter classAdapter=new ArrayAdapter(getContext(), R.layout.spinner_item, G.getClassNames());
        spinnerClass.setAdapter(classAdapter);

        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                childrenCodesInClass.clear(); childrenCodesInClass.add(-1);
                childrenNamesInClass.clear(); childrenNamesInClass.add("전체");
//                if (G.getClassCodes().get(position)==G.getClassCodes().get(0)){
                if (parent.getSelectedItemPosition()==0){
                    //'전체'선택 시
                    selectedNotes=G.getAllNotes();
                    recyclerAdapter=new NoteTAdapter( selectedNotes, getActivity(), getFragmentManager());
                    recyclerViewTList.setAdapter(recyclerAdapter);
                    recyclerAdapter.notifyDataSetChanged();
                    childrenNamesInClass.clear();

                }else{
                    //반 선택 시
                    //반별 아동 리스트 만들기
                    for(int i=0; i<G.getAllChildren().size(); i++){
                        if (G.getAllChildren().get(i).getClasscode()==G.getClassCodes().get(position)){
                            childrenCodesInClass.add(G.getAllChildren().get(i).getChildCode());
                            childrenNamesInClass.add(G.getAllChildren().get(i).getName());
                        }
                    }

                }
                setChildrenSpinner(G.getClassCodes().get(position));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    void setChildrenSpinner(final int selectedClassCode){
        childrenAdapter=new ArrayAdapter(getContext(), R.layout.spinner_item, childrenNamesInClass);
        spinnerKids.setAdapter(childrenAdapter);

        spinnerKids.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (G.getAllNotes()==null || G.getAllNotes().size()==0) return;
                //전체 알림장에서 아동 코드와 일치하는 것을 찾아 ArrayList로!
                //RecyclerView: 전체 선택 시 반코드와 일치여부 / 개별 인원 선택시 아동코드와 일치하는 지
                ArrayList<VOnedayNote> notesClass=new ArrayList<>();
                if(position==0){
                    notesClass.clear();
                    for(int i=0; i<G.getAllNotes().size(); i++){
                        if(G.getAllNotes().get(i).getClassCode()==selectedClassCode){
                            notesClass.add( G.getAllNotes().get(i) );
                        }
                    }
                    recyclerAdapter = new NoteTAdapter(notesClass, getActivity(), getFragmentManager());
                    recyclerViewTList.setAdapter(recyclerAdapter);
                    recyclerAdapter.notifyDataSetChanged();
                }

                if (position>0) {
                    int selectedChildCode = childrenCodesInClass.get(position);
                    ArrayList<VOnedayNote> notesKid=new ArrayList<>();
                    Toast.makeText(context, selectedChildCode+"", Toast.LENGTH_SHORT).show();

                    for(int i=0; i<G.getAllNotes().size(); i++){
                        if(G.getAllNotes().get(i).getChildCode()==selectedChildCode){
                            notesKid.add( G.getAllNotes().get(i) );
                        }
                    }
                    Toast.makeText(context, notesKid.size()+"", Toast.LENGTH_SHORT).show();
                    recyclerAdapter = new NoteTAdapter(notesKid, getActivity(), getFragmentManager());
                    recyclerViewTList.setAdapter(recyclerAdapter);
                    recyclerAdapter.notifyDataSetChanged();
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
