package com.example.project_closet;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.ContentValues;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity_login extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("weather");

    private TextView rainstate;
    private TextView temp;
    private Timer timerCall;
    private TextView humidity;
    private TextView wind;

    String temp2;
    String windy;
    String humm;
    String rainn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        Intent weatherservice = new Intent(this,WeatherService.class);
        startService(weatherservice);



        Button btn_logout = findViewById(R.id.btn_logout);
        Button btn_weather = findViewById(R.id.btn_weather);
        Button btn_closet = findViewById(R.id.btn_closet);


        // 로그아웃 버튼 클릭시
        btn_logout.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_login.this, MainActivity_notlogin.class);
                startActivity(intent);
            }
        }));


        // 나의 옷장 클릭
        btn_closet.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity_login.this, Closet_all_ImageActivity.class);

                System.out.println("Idtoken 옷장 액티비티로 전달 완료");
                startActivity(intent);
            }
        }));

        btn_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_login.this, WeatherActivity.class);
                startActivity(intent);
            }
        });
        temp = (TextView) findViewById(R.id.tv_temp);
        temp.setSingleLine(true); //한줄로 나오게 하기.
        temp.setEllipsize(TextUtils.TruncateAt.MARQUEE); //Ellipsize의 MARQUEE 속성 주기
        temp.setSelected(true); //해당 텍스트뷰에 포커스가 없어도 문자 흐르게 하기

        humidity = (TextView) findViewById(R.id.tv_humidity);
        humidity.setSingleLine(true);
        humidity.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        humidity.setSelected(true);

        wind = (TextView) findViewById(R.id.tv_wind);
        wind.setSingleLine(true);
        wind.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        wind.setSelected(true);
//
        rainstate = (TextView) findViewById(R.id.tv_rainstate);
        rainstate.setSingleLine(true);
        rainstate.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        rainstate.setSelected(true);

        // 위젯에 대한 참조.
        //날씨 바로 받아오기 x 저장된 날씨 데이터 가져오기 :)
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {

                //tworkTask networkTask = new NetworkTask(url, null);
                //networkTask.execute();
                databaseReference.child("temp").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        String temp22 = datasnapshot.getValue(String.class);
                        // 앞 뒤 공백 제거
                        String temp_reslut = temp22.trim();
                        temp2 = temp_reslut;
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                databaseReference.child("wind").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        String wind2 = datasnapshot.getValue(String.class);
                        windy = wind2;
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                databaseReference.child("Humidity").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        String humm2 = datasnapshot.getValue(String.class);
                        humm = humm2;
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                databaseReference.child("rain").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        String rainn2 = datasnapshot.getValue(String.class);
                        rainn = rainn2;
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                humidity.setText(humm);
                                temp.setText(temp2);
                                wind.setText(windy);
                                rainstate.setText(rainn);
                            }
                        });
                    }
                }).start();

            }

        };
        Timer timer = new Timer();
        timer.schedule(tt,0,1000);
    }


    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpConnection requestHttpURLConnection = new RequestHttpConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.

            humidity.setText(s);
            temp.setText(s);
            wind.setText(s);
            rainstate.setText(s);
            Log.d("onPostEx", "출력 값 : " + s);
        }

    }

}