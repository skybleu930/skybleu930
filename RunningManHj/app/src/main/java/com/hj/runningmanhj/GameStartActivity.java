package com.hj.runningmanhj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class GameStartActivity extends AppCompatActivity implements OnMapReadyCallback {
    private TextView tv; //out
    private Handler process; //스레드간에 메세지 전달
    Socket s;
    InputStream ins;
    OutputStream os;
    BufferedReader i;
    PrintWriter o;
    String inMsg;
    String locationMsg;
    static final String ONE = "100";
    static final String TWO = "200";
    String team;
    String id;
    Double latitude;
    Double longitude;
    GoogleMap googleMap;
    private Marker marker;
    static final String TAG = "GameStartActivity";
    Boolean incheck;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        // 강제모드정책 에러시 추가해주세요
        //onCreate 매소드  setContentView(R.layout.activity_main);위에 넣어야한다
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        Log.i(TAG, "mapFragment");
        mapFragment.getMapAsync(this);
//        mapFragment.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap googleMap) {
//                GameStartActivity.googleMap = googleMap;
//                 Log.i(TAG, "onMapReady");
//            }
//        });
        tv = (TextView) findViewById(R.id.textView);
        tv.setMovementMethod(new ScrollingMovementMethod());


        Button btn01 = (Button) findViewById(R.id.btn01);
        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String outMsg = ((EditText)findViewById(R.id.ed01)).getText().toString();
                o.println(TWO + "#" + latitude + "#" + longitude + "#" + outMsg );
                ((EditText)findViewById(R.id.ed01)).setText("");
            }
        });

        try {
            Log.i(TAG, "소켓");
            s = new Socket("192.168.0.44", 9830); //서버에 접속을 요청한다.
//            s = new Socket("192.168.0.13", 9830); //서버에 접속을 요청한다.
//            s = new Socket("mindroid.codns.com", 9830); //서버에 접속을 요청한다.
            ins = s.getInputStream(); //핸들러가  전달하는 메세지를 읽어드린다.
            os = s.getOutputStream(); //핸들러에게 내메세지를 전달한다.
            i = new BufferedReader(new InputStreamReader(ins, "utf-8"));
            o = new PrintWriter(new OutputStreamWriter(os, "utf-8"), true);
        } catch(IOException e) {}
        id = SaveSharedPreference.getUserName(this);
        Log.i(TAG, id);
        team = SaveSharedPreference.getUserTeam(this);
        Log.i(TAG, team);
        o.println(id + "#" + team);
        process = new Handler();
        startLocationService();
//        SharedPreferences sharedPreferences = getSharedPreferences("MemberInfo",MODE_PRIVATE);
//        id = sharedPreferences.getString("id", "");
//        team = sharedPreferences.getString("team", "");
        incheck = true;


        new Thread(new Thread()){
            public void run (){

                while (true) {
                    try {
                        String in = i.readLine();
//                        if(in != null) {
                            String usersInfo = in;
                            StringTokenizer st4 = new StringTokenizer(in, "$");
                            if (st4.countTokens() > 1) {
                                inMsg = st4.nextToken();
                                process.post(showMSG);
                                continue;
                            }
//

                            StringTokenizer st1 = new StringTokenizer(in, "@");
                            if (st1.countTokens() > 1) { //메세지가 있을대 실행
                                usersInfo = st1.nextToken();
                                inMsg = st1.nextToken();
                                process.post(showMSG);
                            }

                            StringTokenizer st2 = new StringTokenizer(usersInfo, "!");
                            if (st2.hasMoreElements()) {
                                // if(st2.countTokens() > 1) {
                                Log.i(TAG, "메세지");
                                ArrayList<String> list = new ArrayList<String>();
                                while (st2.hasMoreElements()) {
                                    list.add(st2.nextToken());
                                }
                                userInfoList = new ArrayList<String[]>();
                                for (String l : list) {
                                    StringTokenizer st3 = new StringTokenizer(l, "|");
                                    String[] userInfo = {st3.nextToken(), st3.nextToken(),
                                                            st3.nextToken(), st3.nextToken()};
                                    userInfoList.add(userInfo);
                                }
//                                showCurrentLocation(userInfoList);
//                                process.post(showMSG);
                                process.post(showMaker);

                            } else {
                                inMsg = in;
                                process.post(showMSG);
                            }
//                        } else {
//                            incheck = false;
//                        }

                    } catch (IOException e) {}
                }
            }
        }.start();


    } //end onCreate
    ArrayList<String[]> userInfoList;

    private Runnable showMSG = new Runnable() {
        public void run() {
            tv.append(inMsg + "\n");
        }
    };

    private Runnable showMaker = new Runnable() {
        public void run() {
            //tv.append(inMsg + "\n");
            showCurrentLocation(userInfoList);

        }
    };

    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        Log.i(TAG, "onMapReady");
//        if(userInfoList != null) {
//            for (String[] mapDatas : userInfoList) {
//                Double lat = Double.parseDouble(mapDatas[0]);
//                Double lon = Double.parseDouble(mapDatas[1]);
//                String title = mapDatas[2];
//                LatLng geodata = new LatLng(lat, lon);
//                googleMap.addMarker(new MarkerOptions().position(geodata).title(title)
//                        /*.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))*/);
//            }
//        }
    }


    private void startLocationService(){
        //위치 관리자 객체 참조
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //위치정보를 지속적으로 캐치할 리스너 생성
        GPSListener gpsListener = new GPSListener();
        //최소 시간과 최소거리
        long minTime = 0;
        float minDistance = 0;
        try{
            //GPS를 이용한 위치 요청(GPS위치 정보 제공)
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,//GPS로
                    minTime,
                    minDistance,
                    gpsListener);
            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,//네트워크로
                    minTime,
                    minDistance,
                    gpsListener
            );

            //위치 확인이 안되는 경우에도 최근에 확인된 위치 정보 먼저 확인
            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(lastLocation!=null){
                latitude = lastLocation.getLatitude();
                longitude = lastLocation.getLongitude();
                locationMsg = "Latitude : "+latitude+ " / Longitude : "+longitude;
                o.println(ONE + "#" + /*team + "#" + id + "#" +*/ latitude + "#" + longitude);

            }
        }catch (SecurityException ex){
            ex.printStackTrace();
        }
    }

    private class GPSListener implements LocationListener {
        /*
         * 위치정보가 확인될 때 자동 호출되는 메소드
         * */

        @Override
        public void onLocationChanged(Location location) {//위치가 바뀌게되면 새로운 정보를 가져오도록 호출되는 함수.
            //위경도 정보
            latitude = location.getLatitude();
            longitude = location.getLongitude();
           // Log.i("dfd", ""+longitude);
            o.println(ONE + "#" + /*team + "#" + id + "#" +*/ latitude + "#" + longitude);
        }

        @Override
        public void onProviderDisabled(String s) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }
    }


    //현재 위치의 지도를 보여주기 위해 정의한 메소드
    protected void showCurrentLocation(ArrayList<String[]> userInfoList) {
        //현재 위치를 이용해 LatLon 객체 색성
//        LatLng curPoint = new LatLng(latitude, longitude);
//        if(marker != null) {
//            if(marker.getTitle().equals(title)) { //도착지 도착
//
//            } else {
//                marker.setPosition(curPoint);
//                //  marker.setTitle(curPoint.latitude + ", " + curPoint.longitude); // 위경도로 표현
//                marker.setTitle(geoDataObj.getLocationTitle()); //geocoding 변환 주소로 표현
//            }
//        } else {
        googleMap.clear();
        if(googleMap != null) {
            for (String[] mapDatas : userInfoList) {
                Double lat = Double.parseDouble(mapDatas[0]);
                Double lon = Double.parseDouble(mapDatas[1]);
                String title = mapDatas[2];
                String team = mapDatas[3];
                LatLng geodata = new LatLng(lat, lon);
                if (team.equals("1")) {
                    googleMap.addMarker(new MarkerOptions().position(geodata).title(title).
                            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
//                        /*.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))*/);
                } else if (team.equals("2")) {
                    googleMap.addMarker(new MarkerOptions().position(geodata).title(title));
                }
                if (title.equals(id)) {
                    LatLng curPoint = new LatLng(lat, lon);
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
                }
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        }

//        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
//        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }

}
