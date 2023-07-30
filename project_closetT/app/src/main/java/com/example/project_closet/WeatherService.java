package com.example.project_closet;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class WeatherService extends Service {
    public WeatherService() {
    }
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("weather");

    private TextView rainstate;
    private TextView tempp;
    private Timer timerCall;
    private TextView humidityy;
    private TextView windd;

    Timer timer;
    String weather_data;
    String temp_data;
    String wind_data;
    //String temp2;
    //String windy;
    //String humm;
    //String rainn;
    String rain_data;


    String Service_key = " serviceKey";
    String num_of_rows = "1000";
    String page_no = "1";
    String date_type = "XML";
    long now = System.currentTimeMillis();
    Date mDate = new Date(now);
    String nx = "51";
    String ny = "69";
    SimpleDateFormat formatYDM = new SimpleDateFormat("yyyyMMdd");
    String YDM = formatYDM.format(mDate);

//    //
//    SimpleDateFormat formatTime = new SimpleDateFormat("hh00");
//    //
//    String getTime = String.valueOf(Integer.parseInt(formatTime.format(mDate))+800);
    String base_date = YDM;
    //
//    String base_time = getTime;
    String base_time = "1100"; // 11시 1100 12시 1200

    String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst?" +
            "ServiceKey=" + Service_key +
            "&numOfRows=" + num_of_rows +
            "&pageNo=" + page_no +
            "&dataType=" + date_type +
            "&base_date=" + base_date +
            "&base_time=" + base_time +
            "&nx=" + nx +
            "&ny=" + ny;


    @Override
    public void onCreate() { //서비스 객체가 새롭게 생성될때 호출됩니다.
        super.onCreate();
        TimerTask tt = new TimerTask() {

            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        weather_data = getHummXMLData();
                        temp_data = getTempXMLData();
                        wind_data = getwindXMLData();
                        rain_data = getrainXmlData();

                        if (!temp_data.equals("")&& !wind_data.equals("")&&!rain_data.equals("")&&!weather_data.equals("")) {
                            databaseReference.child("temp").setValue(temp_data); //db에 저장
                            databaseReference.child("Humidity").setValue(weather_data); //db 저장
                            databaseReference.child("wind").setValue(wind_data);
                            databaseReference.child("rain").setValue(rain_data);
                            //                            System.out.println("temp_ type 확인" + temp_data.getClass().getName());

                        }


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //humidityy.setText(weather_data);
                                //tempp.setText(temp_data);
                                //windd.setText(wind_data);
                                //rainstate.setText(rain_data);
                            }
                        });

                    }
                }).start();
            }
        };
        Timer timer = new Timer();
        timer.schedule(tt, 0, 1000);

    }




    String getTempXMLData() {
        StringBuffer buffer = new StringBuffer();

        try {
            URL urll = new URL(url);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = urll.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

            String tag;
            int i = 0;
            xpp.next();
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//태그 이름 얻어오기

                        if (tag.equals("item")) ; // 첫번째 검색결과
                        else if (tag.equals("obsrValue")) {
                            i++;
                            if (i == 4) {
                                //buffer.append("기온 ");
                                xpp.next();
                                buffer.append(xpp.getText());
                                buffer.append("\n");
                            }


                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기
                        if (tag.equals("item")) buffer.append("\n");
                        break;
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }
        return buffer.toString();

    }
    String getHummXMLData(){
        StringBuffer buffer = new StringBuffer();

        try {
            URL urll = new URL(url);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = urll.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

            String tag;
            int i = 0;
            xpp.next();
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        //list = new ArrayList<weather>();
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//태그 이름 얻어오기

                        if (tag.equals("item")) ; // 첫번째 검색결과
                        else if (tag.equals("obsrValue")) {
                            i++;
                            if (i == 2) {

                                xpp.next();
                                buffer.append(xpp.getText());
                                buffer.append("\n");

                            }
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기
                        if (tag.equals("item")) buffer.append("\n");
                        break;
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }
        return buffer.toString();

    }
    String getwindXMLData(){
        StringBuffer buffer = new StringBuffer();

        try {
            URL urll = new URL(url);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = urll.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

            String tag;
            int i = 0;
            xpp.next();
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        //list = new ArrayList<weather>();
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//태그 이름 얻어오기

                        if (tag.equals("item")) ; // 첫번째 검색결과
                        else if (tag.equals("obsrValue")) {
                            i++;
                            if (i == 8) {

                                xpp.next();
                                buffer.append(xpp.getText());
                                buffer.append("\n");

                            }
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기
                        if (tag.equals("item")) buffer.append("\n");
                        break;
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }
        return buffer.toString();

    }
    String getrainXmlData(){

        StringBuffer buffer = new StringBuffer();

        try {
            URL urll = new URL(url);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = urll.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

            String tag;
            int i = 0;
            xpp.next();
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//태그 이름 얻어오기

                        if (tag.equals("item")) ; // 첫번째 검색결과
                        else if (tag.equals("obsrValue")) {
                            i++;

                            if (i == 1) {
                                //buffer.append("강수형태 : ");
                                xpp.next();
                                if (xpp.getText().equals("0")) {
                                    buffer.append("없음");
                                    buffer.append("\n");
                                } else if (xpp.getText().equals("1")) {
                                    buffer.append("비");
                                    buffer.append("\n");
                                } else if (xpp.getText().equals("2")) {
                                    buffer.append("비+눈(진눈개비)");
                                    buffer.append("\n");
                                } else if (xpp.getText().equals("3")) {
                                    buffer.append("눈");
                                    buffer.append("\n");
                                } else if (xpp.getText().equals("4")) {
                                    buffer.append("소나기");
                                    buffer.append("\n");
                                } else if (xpp.getText().equals("5")) {
                                    buffer.append("빗방울");
                                    buffer.append("\n");
                                } else if (xpp.getText().equals("6")) {
                                    buffer.append("빗방울+눈날림");
                                    buffer.append("\n");
                                } else if (xpp.getText().equals("7")) {
                                    buffer.append("눈날림");
                                    buffer.append("\n");
                                }
                            }


                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기
                        if (tag.equals("item")) buffer.append("\n");
                        break;
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }
        return buffer.toString();
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
            Log.d("onPostEx", "출력 값 : " + s);


        }

    }
    private void runOnUiThread(Runnable runnable) {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {//시스템이 startService 함수를 이용해 서비스를 시작시 호출
        // return super.onStartCommand(intent, flags, startId);
        if(intent == null){
            return START_STICKY;
        }
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public IBinder onBind(Intent intent) {//새로운 binding이 연결될때 호출
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
