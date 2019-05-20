package kr.co.md00to22.i_note0x0;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;

public class TnotelistAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<VNoteOne> notes;
    FragmentManager fragmentManager;
    VNoteOne nowItem;

    public TnotelistAdapter(Context context, ArrayList<VNoteOne> notes, FragmentManager fragmentManager) {
        this.context = context;
        this.notes = notes;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);

        View itemView=inflater.inflate(R.layout.layout_noteitem_t,viewGroup,false);

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
            holder.tnote_title.setVisibility(View.VISIBLE);

            holder.kid_name.setText(Global.getNameOfKid(nowItem.getTnote().getKid_code()));
            holder.cls_name.setText(Global.getNameOfCls(nowItem.getTnote().getClass_code()));

        }else{
            //부모알림장만 있을 때
            holder.pnote_layout.setVisibility(View.VISIBLE);
            holder.pnote_content.setVisibility(View.VISIBLE);

            holder.pnote_content.setText(nowItem.getPnote().getContnet());
            holder.kid_name.setText(Global.getNameOfKid(nowItem.getPnote().getKid_code()));
            holder.cls_name.setText(Global.getNameOfCls(nowItem.getPnote().getClass_code()));
            holder.pnote_wirtetime.setText( pnote_time );

            holder.tnote_title.setVisibility(View.GONE);

        }

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        RelativeLayout pnote_layout;
        TextView pnote_content, pnote_wirtetime, kid_name, cls_name;
        TextView tnote_title;



        public Holder(@NonNull View itemView) {
            super(itemView);

            kid_name=itemView.findViewById(R.id.tnotelist_item_name);
            cls_name=itemView.findViewById(R.id.tnotelist_item_class);
            pnote_layout=itemView.findViewById(R.id.tnotelist_p_layout);

            pnote_content=itemView.findViewById(R.id.tnotelist_item_content);
            pnote_wirtetime=itemView.findViewById(R.id.tnotelist_item_date);

            tnote_title=itemView.findViewById(R.id.tnotelist_if_tnote);


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

                }
            });


        }
    }//Holder
}
