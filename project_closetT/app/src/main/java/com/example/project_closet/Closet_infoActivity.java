package com.example.project_closet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Closet_infoActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("/ClosetInmyHand/UserAccount/Qph478hjZSMNHu9KKf9BPKm0jd83/images");
    ImageAdapter adapter;

    // 가져올 텍스트뷰.. (?)
    private TextView topV, bottomV, outerV, seasonV, customV;
    private ImageView imgV;

    private TextView all_info;

    // 정보 담을 리스트..
    ArrayList<Model> closetinfolist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet_info);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        // 이미지 뷰
        imgV = findViewById(R.id.image_info_view);

        topV = findViewById(R.id.toptv_view);
        bottomV = findViewById(R.id.bottomtv_view);
        outerV = findViewById(R.id.outertv_view);
        seasonV = findViewById(R.id.seasontv_view);
        customV = findViewById(R.id.customtv_view);

//        all_info = findViewById(R.id.closet_all_info);


        // 데이터 수신?
        Intent intent = getIntent();

        // 옷은 어떻게..
        String imageUrl = getIntent().getStringExtra("imgView");
        Glide.with(this).load(imageUrl).into(imgV);

        String top = intent.getExtras().getString("topView");
        topV.setText(top);

        String bottom = intent.getExtras().getString("bottomView");
        bottomV.setText(bottom);

        String outer = intent.getExtras().getString("outerView");
        outerV.setText(outer);

        String season = intent.getExtras().getString("seasonView");
        seasonV.setText(season);

        String custom = intent.getExtras().getString("customView");
        customV.setText(custom);

        String keyid = intent.getExtras().getString("keyID");


        Log.d("확인/", "이미지 확인" + imageUrl);

        Log.d("확인/", "옷 정보 확인/" + top + "/" + bottom + "/" + outer + "/" + season + "/" + custom + "/");

//        if (top == null) {
//            top = "";
//        } else if (bottom == null) {
//            bottom = "";
//        } else if (outer == null) {
//            outer = "";
//        } else if (season == null) {
//            season = "";
//        } else if (custom == null) {
//            custom = "";
//        }
//        all_info.setText(top + (" | ") + bottom + (" | ") + outer + (" | ") + season + (" | ") + custom);


        // PopupActivity 띄우게 하기....
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                AlertDialog.Builder msg = new AlertDialog.Builder(Closet_infoActivity.this)
//                        .setTitle("저희가 추천해준 옷이 잘 맞나요?")
//                        .setMessage("맞지 않았다면 추가 설정을 해보세요!")
//                        .setPositiveButton("괜찮아요", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        })
//                        .setNegativeButton("설정 바꾸러 가기", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Intent intent = new Intent(Closet_infoActivity.this, Closet_add_settingActivity.class);
//
//                                startActivity(intent);
//                                finish();
//
//
//                            }
//                        });
//                AlertDialog msgDlg = msg.create();
//                msgDlg.show();
//            }
//        }, 5000);


        // 설정하러 가기 버튼
        Button btn_setting = findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Closet_infoActivity.this, Closet_add_settingActivity.class);

                // 옷 정보와, keyId, 등 보내기
                intent.putExtra("outer_post", outer) ;
                intent.putExtra("imageUrl", imageUrl);
                intent.putExtra("keyId", keyid);

                // 추가로 데이터 저장을 위한 다른 옷도 보내기
                intent.putExtra("top_post", top);
                intent.putExtra("bottom_post", bottom);
                intent.putExtra("season_post", season);





                startActivity(intent);
                overridePendingTransition(R.anim.vertical_enter, R.anim.vertical_exit);



            }
        });


        // 뒤로가기 버튼
        ImageButton btn_back = findViewById(R.id.btn_back_info);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 뒤로가기
                onBackPressed();
            }
        });

        // 삭제 버튼
        ImageButton btn_delete = findViewById(R.id.btn_delete);

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener confirm = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

//                        int urlkey = Integer.parseInt(mDatabase.getKey());

                        String key = keyid;
                        System.out.println("keyid 확인  " + keyid);

                        delete(key);
                    }
                };
                DialogInterface.OnClickListener cancle = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 취소 되었을 때
                    }
                };
                new AlertDialog.Builder(Closet_infoActivity.this)
                        .setTitle("삭제하시겠습니까?")
                        .setPositiveButton("삭제", confirm)
                        .setNegativeButton("취소", cancle)
                        .show();
            }

            // 삭제기능
            private void delete(String a) {

                mDatabase.child("ClosetInmyHand").child("UserAccount").child("Qph478hjZSMNHu9KKf9BPKm0jd83").child("images").child(a).setValue(null);


                Intent intent = new Intent(Closet_infoActivity.this, Closet_all_ImageActivity.class);
                startActivity(intent);
                System.out.println("삭제 완료  " + a);
            }

//            public void remove(DataSnapshot dataSnapshot) {
//
//                mDatabase.child("ClosetInmyHand").child("UserAccount").child("c259Vtar0RNjE5B508IPWdvcA7B2").child("images").child(dataSnapshot.getKey()).removeValue();
//                System.out.println("삭제 테스트 완료?????????" + dataSnapshot.getKey());

//            }


        });

    }
}