package kr.co.md00to22.i_note0x0;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class KidsListRAdapter extends RecyclerView.Adapter {


    ArrayList<VKids> kids;
    Context context;
    int nowItem;

    public KidsListRAdapter(ArrayList<VKids> kids, Context context) {
        this.kids = kids;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.listview_kidslist, viewGroup, false);

        //ViewHolder객체 생성
        VH holder = new VH(itemView);



        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        nowItem=i;
        VKids kid=kids.get(i);
        VH holder=(VH)viewHolder;

        holder.cimg.setImageResource(R.drawable.leaf128);
        if (kid.getPhotoUrl()!=null && !kid.getPhotoUrl().equals("")) Glide.with(context).load(kid.getPhotoUrl()).into(holder.cimg);


        holder.kidName.setText(kid.getName());
        holder.kidOrg.setText(kid.getIn_orgName());
    }

    @Override
    public int getItemCount() {
        return kids.size();
    }


    class VH extends RecyclerView.ViewHolder {
        CircleImageView cimg;
        TextView kidName,kidOrg;

        public VH(@NonNull View itemView) {
            super(itemView);

            cimg=itemView.findViewById(R.id.kids_photo);
            kidName=itemView.findViewById(R.id.kids_name);
            kidOrg=itemView.findViewById(R.id.kids_org);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "item", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
    }
