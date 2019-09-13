package com.hj.runningmanhj;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//데이터베이스 오픈용의 Helper 클래스
public class DBHelper extends SQLiteOpenHelper {

    private static final String PATH = "/data/data/com.hj.runningmanhj/databases/";

    //데이터베이스의 파일명
    static final String DB = PATH + "RunMember.db";

    //테이블 이름
    static final String TABLE = "RunMember";

    //데이터베이스의 버전
    static final int DB_VERSION = 3;

    //테이블 작성의 SQL문
    static final String CREATE_TABLE =
            "create table RunMember (" +
                    "id text primary key, " +
                    "pw text not null, " +
                    "image text default 'default.png');";
    static final String DROP_TABLE = "DROP TABLE IF EXISTS RunMember";

    public DBHelper(Context ctx) {
        super(ctx, DB, null, DB_VERSION);
    }

    //데이터베이스를 신규로 작성한 후 호출된다.
    public void onCreate(SQLiteDatabase db) {
        //내부 테이블을 작성한다.
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
}
