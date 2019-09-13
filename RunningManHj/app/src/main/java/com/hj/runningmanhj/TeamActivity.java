package com.hj.runningmanhj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TeamActivity extends AppCompatActivity implements View.OnClickListener {
    Intent intent;

    static final String A_TEAM = "1";
    static final String B_TEAM = "2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        Button a_team = findViewById(R.id.a_team);
        Button b_team = findViewById(R.id.b_team);
        a_team.setOnClickListener(this);
        b_team.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {

        Intent intent = new Intent(TeamActivity.this, GameStartActivity.class);
        SharedPreferences sharedPreferences = getSharedPreferences("MemberInfo", MODE_PRIVATE);

        //저장을 하기위해 editor를 이용하여 값을 저장시켜준다.
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(view.getId() == R.id.a_team) {
            SaveSharedPreference.setUserTeam(this, A_TEAM);
//            editor.putString("team", A_TEAM); // key, value를 이용하여 저장하는 형태
//            //최종 커밋
//            editor.commit();
            startActivity(intent);

        } else if(view.getId() == R.id.b_team) {
            SaveSharedPreference.setUserTeam(this, B_TEAM);
//            editor.putString("team", B_TEAM); // key, value를 이용하여 저장하는 형태
//            //최종 커밋
//            editor.commit();
            startActivity(intent);
        }
    }
}
