package com.hj.runningmanhj;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener {

    private SQLiteDatabase mDb;
    private EditText editId;
    private EditText editPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        editId = (EditText) findViewById(R.id.member_id);
        editPw = (EditText) findViewById(R.id.member_pw);
        int buttons[] = {R.id.join_button, R.id.join_cancel};
        for (int id : buttons) {
            Button button = (Button) findViewById(id);
            button.setOnClickListener(this);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        DBHelper h = new DBHelper(getApplicationContext());
        try {
            mDb = h.getWritableDatabase();
        } catch (SQLiteException e) {
            mDb = h.getReadableDatabase();
        }
    }


    //버튼이 눌러졌을 때의 처리
    @Override
    public void onClick(View view) {
        String id = editId.getText().toString();
        String pw = editPw.getText().toString();
        if(view.getId() == R.id.join_button) {
            if(id.length() != 0 && pw.length() != 0) {
                //addMember(id, pw);
                try {
                    String result  = new CustomTask().execute(id,pw,"join").get();
                    if(result.equals("id")) {
                        Toast.makeText(JoinActivity.this,"이미 존재하는 아이디입니다.",Toast.LENGTH_SHORT).show();
                        editId.setText("");
                        editPw.setText("");
                    } else if(result.equals("ok")) {
                        editId.setText("");
                        editPw.setText("");
                        Toast.makeText(JoinActivity.this,"회원가입을 축하합니다.",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e) {}

                Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                startActivity(intent);
            }
        } else if(view.getId() == R.id.join_cancel) {
            Intent intent = new Intent(JoinActivity.this, MainActivity.class);
            startActivity(intent);
        }
        editId.setText("");
        editPw.setText("");
    }

    private String addMember(String id, String pw) {
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("pw", pw);

        long checkId =  mDb.insert(DBHelper.TABLE, null, values);
        if(checkId<0) {
            return "";
        }
        return Long.toString(checkId);
    }



//    private String searchMemo(String str) {
//        //호출할 컬럼의 지정
//        final String[] projection = new String[]{"id", "pw"};
//        //projection내의 컬럼의 인덱스
//        final int ID_INDEX = 0;
//        final int PW_INDEX = 1;
//        String where = "memo like ?";
//        String param = str.substring(0, 1) +"%";
//
//        //검색
//        Cursor c = mDb.query(TABLE, projection, where, new String[]{param},
//                null, null, null, "10");
//        StringBuffer buff = new StringBuffer();
//        if(c.moveToFirst()) {
//            do {
//                String id = c.getString(ID_INDEX);
//                buff.append("id:").append(id);
//                String pw = c.getString(PW_INDEX);
//                buff.append(" pw:").append(pw).append("\n");
//            } while (c.moveToNext());
//        }
//        c.close();
//        return buff.toString();
//    }

}
