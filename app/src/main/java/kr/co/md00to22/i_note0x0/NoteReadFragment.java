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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


public class NoteReadFragment extends Fragment {

    VOnedayNote note;

    LinearLayout layoutP;
    TextView[] tvPcheck=new TextView[5];
    LinearLayout layoutPContents;
    TextView tvPcontents;
    TextView tvPwirteTime;
    TextView btnPEdit;

    LinearLayout layoutT;
    TextView[] tvTcheck=new TextView[5];
    LinearLayout layoutTContents;
    TextView tvTcontents;
    TextView btnTEdit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_note_read, container, false);

        if(getArguments()!=null){
            note=(VOnedayNote)getArguments().getSerializable("note");
        }else {
            Toast.makeText(getContext(), "알림장이 존재하지 않습니다", Toast.LENGTH_SHORT).show();

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().remove(NoteReadFragment.this).commit();
            fragmentManager.popBackStack();

            //return view;
        }
        layoutP=view.findViewById(R.id.layout_pnote);
        tvPcheck[0]=view.findViewById(R.id.tv_pnote_chekc1);
        tvPcheck[1]=view.findViewById(R.id.tv_pnote_chekc2);
        tvPcheck[2]=view.findViewById(R.id.tv_pnote_chekc3);
        tvPcheck[3]=view.findViewById(R.id.tv_pnote_return_adult);
        tvPcheck[4]=view.findViewById(R.id.tv_pnote_return_time);
        layoutPContents=view.findViewById(R.id.layout_pnote_contents);
        tvPcontents=view.findViewById(R.id.tv_pnote);
        btnPEdit=view.findViewById(R.id.btn_p_edit);
        tvPwirteTime=view.findViewById(R.id.tv_writetime_p);

        layoutT=view.findViewById(R.id.layout_tnote);
        tvTcheck[0]=view.findViewById(R.id.tv_tnote_check1);
        tvTcheck[1]=view.findViewById(R.id.tv_tnote_check2);
        tvTcheck[2]=view.findViewById(R.id.tv_tnote_check3);
        tvTcheck[3]=view.findViewById(R.id.tv_tnote_check4);
        layoutTContents=view.findViewById(R.id.layout_tnote_contents);
        tvTcontents=view.findViewById(R.id.tv_tnote);
        btnTEdit=view.findViewById(R.id.btn_t_edit);

        fillParentNote();
        fillTeacherNote();

        return view;
    }

    void fillParentNote(){

        //수정/삭제 버튼
        if(G.getLogin_MEMBER_Grade()==G.MEMBER_GRADE_PARENT){
            btnPEdit.setVisibility(View.VISIBLE);
            btnPEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        if (note.getNoteParent()==null){
            layoutP.setVisibility(View.GONE);
        }else if( note.getNoteParent()!=null) {
            for (int i = 0; i < 3; i++) {
                String checkState = "";
                switch (note.getNoteParent().getChecks().get(i) ) {
                    case 1:
                        checkState = "O";
                        break;

                    case 2:
                        checkState = "△";
                        break;

                    case 3:
                        checkState = "X";
                        break;
                }
                tvPcheck[i].setText(checkState);
            }
            tvPcheck[3].setText(note.getNoteParent().getGohome_adlut());
            tvPcheck[4].setText(note.getNoteParent().getGohome_time());
            tvPcontents.setText(note.getNoteParent().getNote());
            if( G.calDateData(note.getNoteParent().getWriteDate(), "minute")!=null )   tvPwirteTime.setText( "[20"+G.calDateData(note.getNoteParent().getWriteDate(), "minute")+" 작성]" );
            else tvPwirteTime.setVisibility(View.GONE);

        }
//        }else if (note.getNoteParent()==null){
//            tvPcheck[3].setText("");
//            tvPcheck[4].setText("");
//            tvPcontents.setText("");
//        }

        //ImageView
        if(note.getNoteParent().getPhotoArray()!=null && note.getNoteParent().getPhotoArray().size()!=0){
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,500);
            ImageView[] imagesP=new ImageView[note.getNoteParent().getPhotoArray().size()];
            for(int i=0; i<imagesP.length; i++){
                imagesP[i]=new ImageView(getContext());
                Picasso.get().load(note.getNoteParent().getPhotoArray().get(i)).into(imagesP[i]);
                imagesP[i].setLayoutParams(layoutParams);

                layoutPContents.addView(imagesP[i]);
            }
        }
    }//fillParentNote()

    void fillTeacherNote(){

        if(G.getLogin_MEMBER_Grade()==G.MEMBER_GRADE_TEACHER || G.getLogin_MEMBER_Grade()==G.MEMBER_GRADE_DIRECTOR ){
            btnTEdit.setVisibility(View.VISIBLE);
            btnTEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NoteWriteTFragment writeTFragment=new NoteWriteTFragment();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();

                    Bundle bundle=new Bundle();
                    bundle.putSerializable("note", note.getNoteTeacher());

                    ft.replace(R.id.note_container, writeTFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });
        }


        //TextView
        if (note.getNoteTeacher()==null){
            layoutT.setVisibility(View.GONE);
        }else if( note.getNoteTeacher()!=null) {
            for (int i = 0; i < 3; i++) {
                String checkState = "";
                switch (note.getNoteTeacher().getChecks().get(i)) {
                    case 1:
                        checkState = "O";
                        break;

                    case 2:
                        checkState = "△";
                        break;

                    case 3:
                        checkState = "X";
                        break;
                }
                tvTcheck[i].setText(checkState);
            }
            tvTcheck[3].setText(note.getNoteTeacher().getNapTime());
            tvTcontents.setText(note.getNoteTeacher().getNote());
        }
//        }else if (note.getNoteTeacher()==null){
//            tvPcheck[3].setText("");
//            tvPcheck[4].setText("");
//            tvPcontents.setText("");
//        }

        //ImageView
        if(note.getNoteTeacher().getPhotoArray()!=null && note.getNoteTeacher().getPhotoArray().size()!=0){
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,500);
            ImageView[] imagesT=new ImageView[note.getNoteTeacher().getPhotoArray().size()];
            for(int i=0; i<imagesT.length; i++){
                imagesT[i]=new ImageView(getContext());
                Picasso.get().load(note.getNoteTeacher().getPhotoArray().get(i)).into(imagesT[i]);
                imagesT[i].setLayoutParams(layoutParams);

                layoutPContents.addView(imagesT[i]);
            }
        }
    }//fillParentNote()

}
