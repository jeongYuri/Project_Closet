package com.example.project_closet;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WeatherActivity extends AppCompatActivity {

    // 날씨 코드 부분
    private TextView tv_outPut;
    private TextView tv_outPut2;

    //Timer timer;
    String weather_data;

    String weather_data2;

    String apiurl = "http://apis.data.go.kr/1360000/MidFcstInfoService/getMidTa?";
    String ServiceKey = "PXL4ObzaxgbrcVcEe5c8QTzAdF5bD9Y714d3X2Tus6DgtWsj10nEfjP6lUL6Z%2FAPd2pM1XQhuvJx%2BWrEo5%2BLfw%3D%3D";

    String ServiceKey2 = "Jc1sDwiKFNfaVAQT%2F8WJBLlkS7lJ8hrvHZS5kSKybHXfW41O3D0mk2WEagYRmLnJTUBM0Qstv68fXM%2BeMSnE3w%3D%3D";

    String numOfRows = "100";
    String page_no = "1";
    String dataType = "XML";
    String regId = "11B10101";

    long mNow = System.currentTimeMillis();
    Date mDate = new Date(mNow);
    SimpleDateFormat formatYDM = new SimpleDateFormat("yyyyMMdd");
    String YDM = formatYDM.format(mDate);

    String tmFc = YDM + "0600";

    Date cal = Calendar.getInstance().getTime();
    Date result_date = new Date(cal.getTime() + (1000 * 60 * 60 * 24 * 3));
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd", Locale.getDefault());
    String current = sdf.format(result_date);
    Date result_date2 = new Date(cal.getTime() + (1000 * 60 * 60 * 24 * 4));
    String current2 = sdf.format(result_date2);
    Date result_date3 = new Date(cal.getTime() + (1000 * 60 * 60 * 24 * 5));
    String current3 = sdf.format(result_date3);
    Date result_date4 = new Date(cal.getTime() + (1000 * 60 * 60 * 24 * 6));
    String current4 = sdf.format(result_date4);
    Date result_date5 = new Date(cal.getTime() + (1000 * 60 * 60 * 24 * 7));
    String current5 = sdf.format(result_date5);
    Date result_date6 = new Date(cal.getTime() + (1000 * 60 * 60 * 24 * 8));
    String current6 = sdf.format(result_date6);
    Date result_date7 = new Date(cal.getTime() + (1000 * 60 * 60 * 24 * 9));
    String current7 = sdf.format(result_date7);
    Date result_date8 = new Date(cal.getTime() + (1000 * 60 * 60 * 24 * 10));
    String current8 = sdf.format(result_date8);


    String url = apiurl +
            "ServiceKey=" + ServiceKey +
            "&numOfRows=" + numOfRows +
            "&pageNo=" + page_no +
            "&dataType=" + dataType +
            "&regId=" + regId +
            "&tmFc=" + tmFc;

    String url2 = apiurl +
            "ServiceKey=" + ServiceKey2 +
            "&numOfRows=" + numOfRows +
            "&pageNo=" + page_no +
            "&dataType=" + dataType +
            "&regId=" + regId +
            "&tmFc=" + tmFc;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        ImageButton btn_back = findViewById(R.id.btn_back_weather);
        Button btn_closet = findViewById(R.id.btn_closet);


        // 뒤로가기 버튼 클릭 시
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 뒤로가기
                onBackPressed();
            }
        });


        // 홈 화면 가기..
        btn_closet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeatherActivity.this, MainActivity_notlogin.class);
                startActivity(intent);
            }
        });

        // 날씨 코드
        tv_outPut = (TextView) findViewById(R.id.tv_outPut_test);
        tv_outPut2 = (TextView) findViewById(R.id.tv_outPut_test2);

        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();

        new Thread(new Runnable() {
            @Override
            public void run() {
                weather_data = getWeatherXmlData();

                weather_data2 = getWeatherXmlData2();


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_outPut.setText(weather_data);
                        tv_outPut2.setText(weather_data2);


                    }
                });
            }
        }).start();

        // 알림창 뜨게 하기
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                AlertDialog.Builder msg = new AlertDialog.Builder(WeatherActivity.this)
//                        .setTitle("오늘 날씨 온도는 어떠셨어요? 추웠을까요 더웠을까요?")
//                        .setTitle("오늘 날씨 온도는 어떠셨어요? 추웠을까요 더웠을까요?")
//                        .setMessage("내 체감온도를 기록해보세요!")
//                        .setPositiveButton("나중에요", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        })
//                        .setNegativeButton("설정하러 가기", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        });
//                AlertDialog msgDlg = msg.create();
//                msgDlg.show();
//            }
//        },10000);



    }

    String getWeatherXmlData() {

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
                        else if (tag.equals("taMin3")) {
                            buffer.append(current + "  " + "최저"  + System.lineSeparator());
                            xpp.next();


                        } else if (tag.equals("taMax3")) {
                            buffer.append(current + "  " + "최고" + System.lineSeparator());
                            xpp.next();

                            buffer.append("\n");
                        } else if (tag.equals("taMin4")) {
                            buffer.append(current2 + "  " + "최저" + System.lineSeparator());
                            xpp.next();

                        } else if (tag.equals("taMax4")) {
                            buffer.append(current2 + "  " + "최고" + System.lineSeparator());
                            xpp.next();

                            buffer.append("\n");
                        } else if (tag.equals("taMin5")) {
                            buffer.append(current3 + "  " + "최저" + System.lineSeparator());
                            xpp.next();

                        } else if (tag.equals("taMax5")) {
                            buffer.append(current3 + "  " + "최고" + System.lineSeparator());
                            xpp.next();

                            buffer.append("\n");
                        } else if (tag.equals("taMin6")) {
                            buffer.append(current4 + "  " + "최저" + System.lineSeparator());
                            xpp.next();

                        } else if (tag.equals("taMax6")) {
                            buffer.append(current4 + "  " + "최고" + System.lineSeparator());
                            xpp.next();

                            buffer.append("\n");
                        } else if (tag.equals("taMin7")) {
                            buffer.append(current5 + "  " + "최저" + System.lineSeparator());
                            xpp.next();

                        } else if (tag.equals("taMax7")) {
                            buffer.append(current5 + "  " + "최고" + System.lineSeparator());
                            xpp.next();

                            buffer.append("\n");
                        } else if (tag.equals("taMin8")) {
                            buffer.append(current6 + "  " + "최저" + System.lineSeparator());
                            xpp.next();

                        } else if (tag.equals("taMax8")) {
                            buffer.append(current6 + "  " + "최고" + System.lineSeparator());
                            xpp.next();

                            buffer.append("\n");
                        } else if (tag.equals("taMin9")) {
                            buffer.append(current7 + "  " + "최저" + System.lineSeparator());
                            xpp.next();

                        } else if (tag.equals("taMax9")) {
                            buffer.append(current7 + "  " + "최고" + System.lineSeparator()) ;
                            xpp.next();

                            buffer.append("\n");
                        } else if (tag.equals("taMin10")) {
                            buffer.append(current8 + "  " + "최저" + System.lineSeparator()) ;
                            xpp.next();

                        } else if (tag.equals("taMax10")) {
                            buffer.append(current8 + "  " + "최고" + System.lineSeparator());
                            xpp.next();

                            buffer.append("\n");
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

    String getWeatherXmlData2() {

        StringBuffer buffer = new StringBuffer();


        try {
            URL urll = new URL(url2);//문자열로 된 요청 url을 URL 객체로 생성.
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
                        else if (tag.equals("taMin3")) {
                            buffer.append(" ");
                            xpp.next();
                            buffer.append(xpp.getText() + "℃" + System.lineSeparator());
                        } else if (tag.equals("taMax3")) {
                            buffer.append(" ");
                            xpp.next();
                            buffer.append(xpp.getText() + "℃" + System.lineSeparator());
                            buffer.append("\n");
                        } else if (tag.equals("taMin4")) {
                            buffer.append(" ");
                            xpp.next();
                            buffer.append(xpp.getText() + "℃" + System.lineSeparator());

                        } else if (tag.equals("taMax4")) {
                            buffer.append(" ");
                            xpp.next();
                            buffer.append(xpp.getText() + "℃" + System.lineSeparator());
                            buffer.append("\n");
                        } else if (tag.equals("taMin5")) {
                            buffer.append(" ");
                            xpp.next();
                            buffer.append(xpp.getText() + "℃" + System.lineSeparator());
                        } else if (tag.equals("taMax5")) {
                            buffer.append(" ");
                            xpp.next();
                            buffer.append(xpp.getText() + "℃" + System.lineSeparator());
                            buffer.append("\n");
                        } else if (tag.equals("taMin6")) {
                            buffer.append(" ");
                            xpp.next();
                            buffer.append(xpp.getText() + "℃" + System.lineSeparator());
                        } else if (tag.equals("taMax6")) {
                            buffer.append(" ");
                            xpp.next();
                            buffer.append(xpp.getText() + "℃" + System.lineSeparator());
                            buffer.append("\n");
                        } else if (tag.equals("taMin7")) {
                            buffer.append(" ");
                            xpp.next();
                            buffer.append(xpp.getText() + "℃" + System.lineSeparator());
                        } else if (tag.equals("taMax7")) {
                            buffer.append(" ");
                            xpp.next();
                            buffer.append(xpp.getText() + "℃" + System.lineSeparator());
                            buffer.append("\n");
                        } else if (tag.equals("taMin8")) {
                            buffer.append(" ");
                            xpp.next();
                            buffer.append(xpp.getText() + "℃" + System.lineSeparator());
                        } else if (tag.equals("taMax8")) {
                            buffer.append(" ");
                            xpp.next();
                            buffer.append(xpp.getText() + "℃" + System.lineSeparator());
                            buffer.append("\n");
                        } else if (tag.equals("taMin9")) {
                            buffer.append(" ");
                            xpp.next();
                            buffer.append(xpp.getText() + "℃" + System.lineSeparator());
                        } else if (tag.equals("taMax9")) {
                            buffer.append(" ");
                            xpp.next();
                            buffer.append(xpp.getText() + "℃" + System.lineSeparator());
                            buffer.append("\n");
                        } else if (tag.equals("taMin10")) {
                            buffer.append(" ");
                            xpp.next();
                            buffer.append(xpp.getText() + "℃" + System.lineSeparator());
                        } else if (tag.equals("taMax10")) {
                            buffer.append(" ");
                            xpp.next();
                            buffer.append(xpp.getText() + "℃" + System.lineSeparator());
                            buffer.append("\n");
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

            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            tv_outPut.setText(s);

            //
            tv_outPut2.setText(s);

            Log.d("onPostEx", "출력 값 : " + s);


        }




    }
}