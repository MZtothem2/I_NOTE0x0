package kr.co.md00to22.i_note0x0;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;

public class PnotelistAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<NoteOne> notes;
    FragmentManager fragmentManager;
    NoteOne nowItem;

    public PnotelistAdapter(Context context, ArrayList<NoteOne> notes, FragmentManager fragmentManager) {
        this.context = context;
        this.notes = notes;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);

        View itemView=inflater.inflate(R.layout.layout_noteitem_p,viewGroup,false);

        Holder holder=new Holder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        nowItem= notes.get(i);
        Holder holder=(Holder)viewHolder;

        String tnote_title="";
        String pnote_time="";

        try {
            if(nowItem.getTnote()!=null)tnote_title = Global.transDateToDay(nowItem.getTnote().getWrite_date(),Global.GETTIME_DAY) + " 알림장";
            if(nowItem.getPnote()!=null)pnote_time = Global.transDateToDay(nowItem.getPnote().getWrite_date(),Global.GETTIME_SECOND);
        }catch (ParseException e) {
            e.printStackTrace();
        }

        if (nowItem.getTnote()!=null){
            holder.pnote_layout.setVisibility(View.GONE);
            holder.tnote_title.setText(tnote_title);

        }else{
            //부모알림장만 있을 때
            holder.pnote_content.setText(nowItem.getPnote().getContnet());
            holder.tnote_title.setVisibility(View.GONE);
            holder.pnote_wirtetime.setText( pnote_time );

        }

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        RelativeLayout pnote_layout;
        TextView pnote_content, pnote_wirtetime;
        TextView tnote_title;



        public Holder(@NonNull View itemView) {
            super(itemView);

            pnote_layout=itemView.findViewById(R.id.pnotelist_p_layout);
            pnote_content=itemView.findViewById(R.id.plist_pcontents);
            pnote_wirtetime=itemView.findViewById(R.id.plist_ptime);
            tnote_title=itemView.findViewById(R.id.pnotelist_if_tnote);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    NoteReadFragment noteReadFragment=new NoteReadFragment();
                    FragmentTransaction transaction=fragmentManager.beginTransaction();
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("select_note", notes.get(getLayoutPosition()));
                    noteReadFragment.setArguments(bundle);
                    transaction.add(R.id.fragment_container, noteReadFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

//                    String a="", b="";
//                    if(notes.get(getLayoutPosition()).getPnote()!=null) a = notes.get(getLayoutPosition()).getPnote().getContnet();
//                    if(notes.get(getLayoutPosition()).getTnote()!=null) a = notes.get(getLayoutPosition()).getTnote().getContnet();
//
//                    Log.i("알림장 클릭한거", a +" / "+ b);


                }
            });


        }
    }//Holder
}
