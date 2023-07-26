package com.example.project_closet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class Closet_add_settingActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private final DatabaseReference root = FirebaseDatabase.getInstance().getReference("ClosetInmyHand/UserAccount/Qph478hjZSMNHu9KKf9BPKm0jd83");
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    // Custom_post get, post 할 변수 지정
    private float temp;
    private String imageUrl; // 일단 String 으로 ..
    public String closet;
    public String keyId; // 옷 정보 id
    public String feel;

    private ImageView imgV2;

    //Data 값 저장을 위해 주고받을 변수
    // datakey, top, bottom, season, custom, keyId, closet, feel, temp, hum, wind, rain

    public float hum;
    public float wind;
    public String rain;

    public String top;
    public String bottom;
    public String season;
    public String custom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet_add_setting);

        // 객체 담을 버튼 선언
        Button btn_save_add = findViewById(R.id.btn_save_add);

        // 체크박스 선언
        CheckBox check_cold = findViewById(R.id.btn_cold);
        CheckBox check_very_clod = findViewById(R.id.btn_very_cold);
        CheckBox check_hot = findViewById(R.id.btn_hot);
        CheckBox check_very_hot = findViewById(R.id.btn_very_hot);
        CheckBox check_soso = findViewById(R.id.btn_soso);
        CheckBox check_chilly = findViewById(R.id.btn_chilly);
        CheckBox check_warm = findViewById(R.id.btn_warm);
        CheckBox check_cool = findViewById(R.id.btn_cool);

        Button cancel = findViewById(R.id.btn_cancel_setting);


        TextView temp_tv = findViewById(R.id.tv_rec_temp_add);

        // 가져올 이미지
        imgV2 = findViewById(R.id.image_info_view_add);

        // info 에서 보낸 intent 값 가져와서 담기
        closet = getIntent().getStringExtra("outer_post");
        // 자켓 항목이 없으면..
        if (closet == null) {
            closet = getIntent().getStringExtra("top_post"); //top 에 있는 값 넣기
        }

        imageUrl = getIntent().getStringExtra("imageUrl");
        Glide.with(this).load(imageUrl).into(imgV2);

        keyId = getIntent().getStringExtra("keyId");

        System.out.println("closet, imageUrl, keyID 전달된 intent 값 확인.. ------->    " + closet + " | " +
                imageUrl + " | " + keyId + " | ");

        // info 에서 보낸 intent 값 (data 저장용)
        top = getIntent().getStringExtra("top_post");
        bottom = getIntent().getStringExtra("bottom_post");
        season = getIntent().getStringExtra("season_post");


        // 현재 저장된 온도 데이터 가져오기
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("weather").child("temp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                String result = value.trim();
                temp_tv.setText(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DatabseError _ temp ", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });

        //취소 버튼
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 뒤로가기
                onBackPressed();

            }
        });


        // 추가 저장 버튼 클릭 시
        btn_save_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // 하나만 담아지는건가..?ㅠㅠ
                feel = Checked(check_cold, check_very_clod, check_hot, check_very_hot, check_soso, check_chilly, check_warm, check_cool);

                System.out.println("feel  담아졌나 확인 ------->    " + feel);
                if (feel != null) {
                    // 데이터 저장 코드 실행
                    add_data_post_FirebaseDatabase(true);

                    // 업로드 코드 실행
                    add_post_FirebaseDatabase(true);



                } else {
                    Toast.makeText(Closet_add_settingActivity.this, "선택을 해주세요.", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }


    // 체크되었을 때 동작 코드
    public String Checked(CheckBox check_cold, CheckBox check_very_clod, CheckBox check_hot, CheckBox check_very_hot, CheckBox check_soso,
                          CheckBox check_chilly, CheckBox check_warm, CheckBox check_cool) {

        //체크된것들의 값을 받기 위한 변수
        String checked = "";


        if (check_cold.isChecked()) {
            checked = "추움" + ",";
        }
        if (check_very_clod.isChecked()) {
            checked = "너무 추움" + ",";
        }
        if (check_hot.isChecked()) {
            checked = "더움" + ",";
        }
        if (check_very_hot.isChecked()) {
            checked = "너무 더움" + ",";
        }
        if (check_soso.isChecked()) {
            checked = "적당함" + ",";
        }
        if (check_chilly.isChecked()) {
            checked = "서늘함" + ",";
        }
        if (check_warm.isChecked()) {
            checked = "따뜻함" + ",";
        }
        if (check_cool.isChecked()) {
            checked = "시원함" + ",";
        }
        // 마지막 부분에 , 없애기 위해 , 로 분리한다.
        String[] Arr = checked.split(",");

        // 결과물 담을 변수
        String resultValue = "";

        for (int i = 0; i < Arr.length; i++) {
            if (i == Arr.length - 1) { // i 가 Arr 변수의 마지막 이라면
                resultValue += Arr[i]; // , 를 붙이지 않는다.
            } else { // 마지막이 아니면 , 붙이기
                resultValue += (Arr[i] + ",");
            }


        }
        return resultValue;
    } // Checked end


    // 업로드 코드.
    public void add_post_FirebaseDatabase(boolean add) {

        // DB에 있는 temp(온도) 데이터 가져오기
        mDatabase.child("weather").child("temp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String temp_value = dataSnapshot.getValue(String.class);
                String temp_string = temp_value.trim();
                float temp_result = (float) Double.parseDouble(temp_string); // float 형태로 변환해서 넣기.
                System.out.println("temp_result : type 확인 -------> " + ((Object) temp_result).getClass().getSimpleName()); // type 확인

                // temp_에 담기 (안되는건가?)
//                temp = (float) (Math.ceil(temp_result * 100) / 100.0);

                // float 형태로 가져온 temp_result 반올림 해서 temp 에 담기
                temp = (float) (Math.round(temp_result));

//                System.out.println("temp : type 확인 ------->    "  + temp.getClass().getName());
                System.out.println("temp : 확인 -------> " + temp);

                // 소수점 잘라서 DB 상위 노드로 만들기 위해서.. 가져와서 변환 하고 온점을 버리기..
                // 고민 1 temp 값이 같으면 이거만 들어감..
                // 고민 2 개별 ID를 계속 설정해서 그 온도와 옷만 저장할 수록 계쏙 데이터가 쌓임
                // 둘 중 선택은?
//                String temp_st1 = Float.toString(temp);
//                String temp_st2 = String.format("%.0f", temp);
//                System.out.println("temp_st2 : 확인 -------> " + temp_st2);  //  확인

                DatabaseReference mRootRef = reference.child("ClosetInmyHand").child("UserAccount").child("Qph478hjZSMNHu9KKf9BPKm0jd83").child("Temp_closet");

                Map<String, Object> childUpdates = new HashMap<>();
                Map<String, Object> postValues = null;

                Custom_post post = new Custom_post(temp, imageUrl, closet, keyId, feel);
                postValues = post.toMap();

                // 이렇게 하니 저장은 됐는데..
//                mRootRef.push().setValue(postValues);

                // 옷 keyId 값을 이용해서 목록 만들고 하위에 저장시키기 --> child(keyId)
                mRootRef.child(keyId).setValue(postValues).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Closet_add_settingActivity.this, "추가 저장이 되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Closet_add_settingActivity.this, Closet_all_ImageActivity.class);

                                // 해당하는 옷 key id 값 보내기
                                intent.putExtra("keyId2",keyId);

                                startActivity(intent);
                                finish();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Closet_add_settingActivity.this, "저장을 실패했습니다.", Toast.LENGTH_SHORT).show();

                            }
                        });

                // 업데이트할 값을 담은 후에 temp를 key로 하여 DB에 전달. 기존에 동일한 temp가 존재하는 경우에는 업데이트가 되며, temp가 없는 경우에는 새로운 데이터가 추가.
//                childUpdates.put("ClosetInmyHand/UserAccount/Qph478hjZSMNHu9KKf9BPKm0jd83" +
//                        "/temp/" + temp, postValues); // temp 경로 만들어서 넣기..
//                mRootRef.updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                Toast.makeText(Closet_add_settingActivity.this, "추가 저장이 되었습니다.", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(Closet_add_settingActivity.this, "저장을 실패했습니다.", Toast.LENGTH_SHORT).show();
//                            }
//                        });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DatabseError _ temp ", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });


    }

    public void add_data_post_FirebaseDatabase(boolean add) {
        // DB에 있는 Hum, rain, wind  데이터 가져오기
        mDatabase.child("weather").child("Humidity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String hum_value = snapshot.getValue(String.class);
                String hum_string = hum_value.trim();
                float hum_result = (float) Double.parseDouble(hum_string); // float 형태로 변환해서 넣기.
                // float 형태로 가져온 temp_result 반올림 해서 Hum 에 담기
                hum = (float) (Math.round(hum_result));

                System.out.println("Hum : 확인 -------> " + hum);

                mDatabase.child("weather").child("wind").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String wind_value = snapshot.getValue(String.class);
                        String wind_string = wind_value.trim();

                        float wind_result = (float) Double.parseDouble(wind_string); // float 형태로 변환해서 넣기.

                        // 둘 째 자리 반 올림
                        wind = (float) (Math.round(wind_result * 10) / 10.0);

                        System.out.println("wind : 확인 -------> " + wind);

                        mDatabase.child("weather").child("rain").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                rain = snapshot.getValue(String.class);
                                System.out.println("rain : 확인 -------> " + rain);

                                DatabaseReference mRootRef = reference.child("ClosetInmyHand").child("UserAccount").child("Qph478hjZSMNHu9KKf9BPKm0jd83").child("Data_save");

                                Map<String, Object> postValues = null;

                                Data_post post = new Data_post(keyId, top, bottom, custom, closet, season, feel, temp, hum, wind, rain);
                                postValues = post.toMap();

                                // 저장할 때마다 랜덤 key 값 생성.
                                mRootRef.push().setValue(postValues).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                System.out.println("Data 저장 성공 !!!!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                System.out.println("Data 저장 실패 ...");
                                            }
                                        });

                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}