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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NoteTAdapter {
//extends RecyclerView.Adapter {
//
//    ArrayList<VOnedayNote> datas;
//    Context context;
//    FragmentManager fragmentManager;
//
//    //아이템이 띄우고 있는 현재의 노트값
//    VOnedayNote noteInItem;
//
//
//    public NoteTAdapter(ArrayList<VOnedayNote> datas, Context context, FragmentManager fragmentManager) {
//        this.datas = datas;
//        this.context = context;
//        this.fragmentManager= fragmentManager;
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//
//        LayoutInflater inflater=LayoutInflater.from(context);
//
//
//        View view=inflater.inflate(R.layout.note_list_t_fromparent, viewGroup,false);
//
//        VH holder=new VH(view);
//
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
//        VH holder=(VH)viewHolder;
//
//        holder.tvName.setText(G.findChildNameT(datas.get(i).getChildCode()));
//        holder.tvClass.setText(G.findClassName(datas.get(i).getClassCode()));
//
//        if (datas.get(i).getNoteTeacher()!=null){
//            holder.tvContents.setVisibility(View.INVISIBLE);
//            holder.tvDate.setVisibility(View.INVISIBLE);
//            holder.ivPhoto.setVisibility(View.INVISIBLE);
//
//            holder.tvTnoteTitle.setVisibility(View.VISIBLE);
//
//            holder.tvTnoteTitle.setText( "20"+ G.calDateData(datas.get(i).getNoteTeacher().getWriteDate(), "date") + " 알림장" );
//
//        }else{
//            holder.tvTnoteTitle.setVisibility(View.INVISIBLE);
//            holder.tvContents.setVisibility(View.VISIBLE);
//            holder.tvDate.setVisibility(View.VISIBLE);
//
//            holder.tvContents.setText(datas.get(i).getNoteParent().getNote());
//            holder.tvDate.setText( G.calDateData(datas.get(i).getWriteDate(), "minute") );
//
//            if (datas.get(i).getNoteParent().getPhotoArray()==null || datas.get(i).getNoteParent().getPhotoArray().size()==0){
//                holder.ivPhoto.setVisibility(View.INVISIBLE);
//            }else holder.ivPhoto.setVisibility(View.VISIBLE);
//        }
//
//
//    }
//
//
//    @Override
//    public int getItemCount() {
//        if( datas==null ) return 0;
//        if( datas.size()==0 ) return 0;
//
//
//        return datas.size();
//    }
//
//
//    class VH extends RecyclerView.ViewHolder{
//        TextView tvName, tvClass, tvContents, tvDate, tvTnoteTitle;
//        ImageView ivPhoto;
//
//        public VH(@NonNull View itemView) {
//            super(itemView);
//
//            tvName=itemView.findViewById(R.id.tnotelist_item_name);
//            tvClass=itemView.findViewById(R.id.tnotelist_item_class);
//            tvContents=itemView.findViewById(R.id.tnotelist_item_content);
//            tvDate=itemView.findViewById(R.id.tnotelist_item_date);
//            ivPhoto=itemView.findViewById(R.id.tnotelist_item_picture);
//            tvTnoteTitle=itemView.findViewById(R.id.tv_tnote_title);
//
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    NoteReadFragment readFragment=new NoteReadFragment();
//                    FragmentTransaction ft=fragmentManager.beginTransaction();
//                    noteInItem =datas.get(getLayoutPosition());
//                    Bundle bundle=new Bundle();
//                    bundle.putSerializable("note", noteInItem);
//                    readFragment.setArguments(bundle);
//
//                    Log.i("이거",noteInItem.getNoteParent().getNote());
//
//                    ft.replace(R.id.note_container, readFragment);
//                    ft.addToBackStack(null);
//                    ft.commit();
//                }
//            });
//
//        }
//    }
}
