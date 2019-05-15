package kr.co.md00to22.i_note0x0;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class TeachersActivity extends AppCompatActivity {
    FragmentTransaction fragtransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        NoteListTFragment noteListTFragment=new NoteListTFragment();
        transaction.add(R.id.fragment_container,noteListTFragment);


        transaction.commit();


    }
}
