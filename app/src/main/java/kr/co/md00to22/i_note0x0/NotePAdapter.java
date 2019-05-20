package kr.co.md00to22.i_note0x0;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NotePAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
//
//    ArrayList<VOnedayNote> datas;
//    LayoutInflater layoutInflater;
//
//    public NotePAdapter(ArrayList<VOnedayNote> datas, LayoutInflater layoutInflater) {
//        this.datas = datas;
//        this.layoutInflater = layoutInflater;
//    }
//
//    @Override
//    public int getCount() {
//        return datas.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        VOnedayNote noteItem=datas.get(position);
//        View itemView=null;
//
//        if (noteItem.getNoteTeacher()==null){
//            itemView=layoutInflater.inflate(R.layout.note_list_p_fromparent, parent, false);
//        }else{
//            itemView=layoutInflater.inflate(R.layout.note_list_tohome, parent, false);
//        }
//
//        //bind
//        TextView tvPcontets, tvPdate;
//        TextView tvTtitle;
//
//        if (noteItem.getNoteTeacher()==null){//교사알림장 X : 부모알림장표현
//            tvPcontets=itemView.findViewById(R.id.note_item_toorgani_content);
//            tvPdate=itemView.findViewById(R.id.note_item_toorgani_date);
//
//            tvPcontets.setText(noteItem.getNoteParent().getNote());
//            tvPdate.setText( "20"+G.calDateData(noteItem.getNoteParent().getWriteDate(), "date") );
//        }else{
//            tvTtitle=itemView.findViewById(R.id.note_item_basic_title);
//            tvTtitle.setText("20"+ G.calDateData(noteItem.getWriteDate(),"date") + " 알림장" );
//        }
//
//
//        return itemView;
//    }
}
