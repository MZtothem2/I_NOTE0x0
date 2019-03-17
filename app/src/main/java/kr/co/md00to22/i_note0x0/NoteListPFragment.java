package kr.co.md00to22.i_note0x0;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class NoteListPFragment extends Fragment {

    Spinner spinner;
    TextView btnToWrite;
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_note_list_p, container, false);

        spinner=view.findViewById(R.id.spinner_kids_plist);
        btnToWrite=view.findViewById(R.id.btn_towrite_p);
        listView=view.findViewById(R.id.listview_plist);

        setSpinner();

        btnToWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteWritePFragment writePFragment=new NoteWritePFragment();
                openFragment(writePFragment);
            }
        });

        //ListView
        NotePAdapter adapter=new NotePAdapter(G.getAllNotesP(), getLayoutInflater());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //todo : 읽기로 넘기기
                NoteReadFragment readFragment=new NoteReadFragment();
                FragmentTransaction ft=getFragmentManager().beginTransaction();

                VOnedayNote noteInItem=G.getAllNotesP().get(position);
                Bundle bundle=new Bundle();
                bundle.putSerializable("note", noteInItem);
                readFragment.setArguments(bundle);


                ft.replace(R.id.note_container, readFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return view;
    }


    private void setSpinner(){

        if (G.getLoginParent().getChildrenCodeArray().length==1){
            spinner.setVisibility(View.GONE);
           // (NoteActivity)getActivity().selectedChildCode=G.getLoginParent().getChildrenCods().get(0);

        }else if(G.getLoginParent().getChildrenCodeArray().length>1){
            //스피너셋팅
            ArrayList<String> kidsName=new ArrayList<>();
            for (int i=0 ; i<G.getChildP().size(); i++){
                kidsName.add(G.getChildP().get(i).getName());
            }
            ArrayAdapter classAdapter=new ArrayAdapter(getContext(), R.layout.spinner_item, kidsName);
            spinner.setAdapter(classAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    G.setSelectChildCodeP( G.getLoginParent().getChildrenCodeArray()[position]);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

    }//setSpinner()

    private void openFragment(final Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.note_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
