package com.hj.runningmanhj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String savaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button join = (Button)findViewById(R.id.join);
        Button login = (Button)findViewById(R.id.login);
        join.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    DBHelper h;
    private SQLiteDatabase mDb;
    @Override
    protected void onResume() {
        super.onResume();
        h = new DBHelper(getApplicationContext());
        try {
            mDb = h.getWritableDatabase();
        } catch (SQLiteException e) {
            mDb = h.getReadableDatabase();
        }
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.join) {
            Intent intent = new Intent(MainActivity.this, JoinActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.login) {
            Log.i("MainActivity", "2");
            String id = ((EditText)findViewById(R.id.member_id)).getText().toString();
            String pw = ((EditText)findViewById(R.id.member_pw)).getText().toString();
//            int result = searchMember(id, pw);
//            if(result == 1) {
//                SaveSharedPreference.setUserName(this, savaId);
////                SharedPreferences sharedPreferences = getSharedPreferences("MemberInfo",MODE_PRIVATE);
////
////                //저장을 하기위해 editor를 이용하여 값을 저장시켜준다.
////                SharedPreferences.Editor editor = sharedPreferences.edit();
////                editor.putString("id", savaId); // key, value를 이용하여 저장하는 형태
////
////                //최종 커밋
////                editor.commit();
//
//                Intent intent = new Intent(MainActivity.this, MemberActivity.class);
//                startActivity(intent);
//            } else {
//                Toast.makeText(MainActivity.this, "아이디 또는 비밀번호가 일치하지 않음",
//                        Toast.LENGTH_LONG).show();
//            }
            //db접속 로그인
            try {
                Log.i("MainActivity", "1");
                String result  = new CustomTask().execute(id, pw, "login").get();
                if(result.equals("true")) {
                    Log.i("MainActivity", "true");
                    SaveSharedPreference.setUserName(this, id);
                    Toast.makeText(MainActivity.this,"로그인",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MemberActivity.class);
                    startActivity(intent);
                    finish();
                } else if(result.equals("false")) {
                    Log.i("MainActivity", "false");
                    Toast.makeText(MainActivity.this,"아이디 또는 비밀번호가 틀렸음",Toast.LENGTH_SHORT).show();
                    ((EditText)findViewById(R.id.member_id)).setText("");
                    ((EditText)findViewById(R.id.member_pw)).setText("");
                } else if(result.equals("noId")) {
                    Log.i("MainActivity", "noId");
                    Toast.makeText(MainActivity.this,"존재하지 않는 아이디",Toast.LENGTH_SHORT).show();
                    ((EditText)findViewById(R.id.member_id)).setText("");
                    ((EditText)findViewById(R.id.member_pw)).setText("");
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            Log.i("통과","통과함");

            //그냥로그인
//            SaveSharedPreference.setUserName(this, id);
//            Toast.makeText(MainActivity.this,"로그인",Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(MainActivity.this, MemberActivity.class);
//            startActivity(intent);
//            finish();

        }
    }

    private int searchMember(String id, String pw) {
        //호출할 컬럼의 지정
        final String[] projection = new String[]{"id", "pw"};
        String where = "id=? and pw=?";
        int result = 0;
        //검색

        Cursor c = mDb.query(DBHelper.TABLE, projection, where, new String[]{id, pw},
                null, null, null, null);

        if(c.moveToFirst()) {

            savaId = c.getString(0);
            result = 1;

        }

        c.close();
        return result;
    }
}

