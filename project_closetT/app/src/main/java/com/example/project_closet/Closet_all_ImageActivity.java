package com.example.project_closet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Closet_all_ImageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Model> list;
    ImageAdapter adapter;
    private DatabaseReference mDatabase;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("/ClosetInmyHand/UserAccount/Qph478hjZSMNHu9KKf9BPKm0jd83/images");


//    private Context mContext; (?)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet_list);

        TextView name = findViewById(R.id.tv_login3);
        TextView login = findViewById(R.id.btn_logout);
        ImageButton btn_plus = findViewById(R.id.btn_plus);
        final String[] type = {null};

        // 테스트 버튼 (확인용)
//        Button btn_test = findViewById(R.id.btn_test);

        // 추천 버튼 (추천 팝업 띄우기)
        ImageButton btn_rec = findViewById(R.id.btn_rec_click);


        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("ClosetInmyHand").child("UserAccount").child("Qph478hjZSMNHu9KKf9BPKm0jd83").child("userName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                name.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Closet_allActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });

        // 아이템 추가해보기


        recyclerView = findViewById(R.id.recyclerview_xml);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//        Button top = findViewById(R.id.btn_text4);
        ImageView rec = findViewById(R.id.rec);

        list = new ArrayList<>();
        adapter = new ImageAdapter(Closet_all_ImageActivity.this, list);
        recyclerView.setAdapter(adapter);
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Model model = dataSnapshot.getValue(Model.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();
//                adapter.getItemId(0) 어떻게 한 항목만 불러와서 처리할 수 있을까?


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // SignUpPhotoActivity 에서 keyId 전달한거 받아보기.
//        Intent intent = getIntent();
//        String kyeid = intent.getExtras().getString("KeyId");

        // 커스텀 리스너 객체 생성 및 전달
        adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                Log.d("확인", "클릭 위치 확인" + pos);
//

                Intent intent = new Intent(Closet_all_ImageActivity.this, Closet_infoActivity.class);
                intent.putExtra("imgView", list.get(pos).getImageUrl());
                intent.putExtra("topView", list.get(pos).getTop());
                intent.putExtra("bottomView", list.get(pos).getBottom());
                intent.putExtra("outerView", list.get(pos).getOuter());
                intent.putExtra("seasonView", list.get(pos).getSeason());
                intent.putExtra("customView", list.get(pos).getCustom());
                intent.putExtra("keyID", list.get(pos).getKeyId());

                System.out.println("key test  " + list.get(pos).getKeyId()); // 됐다 들어갔다.


                startActivity(intent);
//                finish();

//                final TextView topView = (TextView) view.findViewById(R.id.toptv_view);
//                final TextView bottomView = (TextView) view.findViewById(R.id.bottomtv_view);
//                final TextView outerView = (TextView) view.findViewById(R.id.outertv_view);
//                final TextView seasonView = (TextView) view.findViewById(R.id.seasontv_view);
//                final TextView customView = (TextView) view.findViewById(R.id.customtv_view);


//                topView.setText(list.get(pos).getTop());
//                bottomView.setText(list.get(pos).getBottom());
//                outerView.setText(list.get(pos).getOuter());
//                seasonView.setText(list.get(pos).getSeason());
//                customView.setText(list.get(pos).getCustom());

            }
        });


        ImageButton btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 뒤로가기
                onBackPressed();

            }
        });


        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Closet_all_ImageActivity.this, SignUpPhotoActivity.class);
                startActivity(intent);
            }
        });

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                AlertDialog.Builder msg = new AlertDialog.Builder(ImageActivity.this)
//                        .setTitle("저희가 추천해준 옷이 잘 맞나요?")
//                        .setMessage("맞지 않았다면 다시 설정해보세요!")
//                        .setPositiveButton("괜찮아요", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        })
//                        .setNegativeButton("설정 바꾸러 가기", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        });
//                AlertDialog msgDlg = msg.create();
//                msgDlg.show();
//            }
//        },5000);


        // 추천 버튼 클릭 시
        btn_rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Closet_all_ImageActivity.this, Closet_rec_Activity.class);
                // 추가 저장해서 온 keyid 값 받기
                String keyId = getIntent().getStringExtra("keyId2");

                if (keyId != null) {
                    // 해당하는 옷 key id 값 보내기
                    intent.putExtra("keyId2",keyId);
                }

                startActivity(intent);
            }
        });

    }

}


