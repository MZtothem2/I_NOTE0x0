package kr.co.md00to22.i_note0x0;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class ParentsActivity extends AppCompatActivity {
    FragmentTransaction fragtransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parents);

        fragtransaction=getSupportFragmentManager().beginTransaction();

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //좌측 back버튼
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if(Global.getKids().size()>1){
            PKidslistFragment kidslistFragment=new PKidslistFragment();
            transaction.add(R.id.fragment_container,kidslistFragment);
        }else{
            PnoteListFragment pnoteListFragment=new PnoteListFragment();
            transaction.add(R.id.fragment_container,pnoteListFragment);

        }

        transaction.commit();
    }
}
