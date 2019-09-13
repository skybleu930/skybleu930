package com.hj.runningmanhj;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {

    static final String USER_ID = "id";
    static final String USER_TEAM = "team";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    // 계정 정보 저장
    public static void setUserName(Context ctx, String userId) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(USER_ID, userId);
        editor.commit();
    }

    // 저장된 정보 가져오기
    public static String getUserName(Context ctx) {
        return getSharedPreferences(ctx).getString(USER_ID, "");
    }

    // 계정 정보 저장
    public static void setUserTeam(Context ctx, String team) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(USER_TEAM, team);
        editor.commit();
    }

    // 저장된 정보 가져오기
    public static String getUserTeam(Context ctx) {
        return getSharedPreferences(ctx).getString(USER_TEAM, "");
    }



    // 로그아웃
    public static void clearUserInfo(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.commit();
    }
}
