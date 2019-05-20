package kr.co.md00to22.i_note0x0;

import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class ParentsActivity extends AppCompatActivity {
    FragmentTransaction fragtransaction;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parents);

        fragtransaction=getSupportFragmentManager().beginTransaction();

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //좌측 back버튼
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        TextView textView=getSupportActionBar().findViewById(R.id.toolbar_title);
//        textView.setText(Global.getLoginUser().getName()+" 님");

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if(Global.getKids().size()>1){
            PKidslistFragment kidslistFragment=new PKidslistFragment();
            transaction.add(R.id.fragment_container,kidslistFragment);
        }else{
            PNoteListFragment pnoteListFragment=new PNoteListFragment();
            transaction.add(R.id.fragment_container,pnoteListFragment);

        }
        transaction.addToBackStack(null);
        transaction.commit();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount()==1){
            new AlertDialog.Builder(this).setMessage("I.NOTE를 종료하시겠습니까?").setPositiveButton("종료하기", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
        }else super.onBackPressed();
    }

}
