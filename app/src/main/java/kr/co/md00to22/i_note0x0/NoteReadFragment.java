package kr.co.md00to22.i_note0x0;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;


public class NoteReadFragment extends Fragment {

    VNoteOne note;

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

        Bundle bundle=getArguments();
        if (bundle!=null) note=(VNoteOne)bundle.getSerializable("select_note");
        else{
            Toast.makeText(getContext(), "알림장 읽어오기 오류", Toast.LENGTH_SHORT).show();
            return new View(getContext());
        }

        View view = inflater.inflate(R.layout.fragment_note_read, container, false);

        layoutP=view.findViewById(R.id.layout_pnote);
        tvPcheck[0]=view.findViewById(R.id.tv_pnote_chekc1);
        tvPcheck[1]=view.findViewById(R.id.tv_pnote_chekc2);
        tvPcheck[2]=view.findViewById(R.id.tv_pnote_chekc3);
        //tvPcheck[3]=view.findViewById(R.id.tv_pnote_return_adult);
        //tvPcheck[4]=view.findViewById(R.id.tv_pnote_return_time);
        layoutPContents=view.findViewById(R.id.layout_pnote_contents);
        tvPcontents=view.findViewById(R.id.tv_pnote);
        btnPEdit=view.findViewById(R.id.btn_p_edit);
        tvPwirteTime=view.findViewById(R.id.tv_writetime_p);

        layoutT=view.findViewById(R.id.layout_tnote);
        tvTcheck[0]=view.findViewById(R.id.tv_tnote_check1);
        tvTcheck[1]=view.findViewById(R.id.tv_tnote_check2);
        tvTcheck[2]=view.findViewById(R.id.tv_tnote_check3);
        layoutTContents=view.findViewById(R.id.layout_tnote_contents);
        tvTcontents=view.findViewById(R.id.tv_tnote);
        btnTEdit=view.findViewById(R.id.btn_t_edit);

        try {
            fillParentNote();
        } catch (ParseException e) {
            Log.i("NoteRead", e.getMessage());
        }
        fillTeacherNote();

        return view;
    }

    void fillParentNote() throws ParseException {

//        //수정/삭제 버튼
//        if(G.getLogin_MEMBER_Grade()==G.MEMBER_GRADE_PARENT){
//            btnPEdit.setVisibility(View.VISIBLE);
//            btnPEdit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
//        }
        if ((note.getPnote()==null) || (!note.getPnote().getVisibility())){
            layoutP.setVisibility(View.GONE);
            return;
        }

         for (int i = 0; i < 3; i++) {
            String checkState = "";
            switch (note.getPnote().getCheck()[i] ) {
                case Global.CHECK_O:
                    checkState = "O";
                    break;

                case Global.CHECK_M:
                    checkState = "△";
                    break;

                case Global.CHECK_X:
                    checkState = "X";
                    break;

                case Global.CHECK_NONE:
                    checkState = " - ";
                    break;
            }
                tvPcheck[i].setText(checkState);
        }
        tvPcontents.setText(note.getPnote().getContnet());
        String time="";
        time="["+Global.transDateToDay( note.getPnote().getWrite_date(), Global.GETTIME_SECOND)+ " 작성]" ;
        tvPwirteTime.setText(time);

    }//fillParentNote()

    void fillTeacherNote(){

        if((note.getTnote()==null)||(!note.getTnote().getVisibility())) {
            layoutT.setVisibility(View.GONE);
            return;
        }

        for(int i=0; i<3; i++){
            String checkState = "";
            switch (note.getTnote().getCheck()[i]) {
                case Global.CHECK_O:
                    checkState = "O";
                    break;

                case Global.CHECK_M:
                    checkState = "△";
                    break;

                case Global.CHECK_X:
                    checkState = "X";

                case Global.CHECK_NONE:
                    checkState = " - ";
                    break;
            }
            tvTcheck[i].setText(checkState);
        }

        tvTcontents.setText(note.getTnote().getContnet());

    }//fillParentNote()

}
