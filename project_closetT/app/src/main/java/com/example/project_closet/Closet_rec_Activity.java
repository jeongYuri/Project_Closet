package com.example.project_closet;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Map;

public class Closet_rec_Activity extends Activity {

    private RecyclerView recyclerView;
    private ArrayList<Model> list2;
    ImageAdapter adapter;
    private DatabaseReference mDatabase;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("/ClosetInmyHand/UserAccount/Qph478hjZSMNHu9KKf9BPKm0jd83/images");

    // 범위 설정할 온도
    public float temp_range;

    // 추가설정한 옷 keyid 받아올
    public String keyId;

    TextView tv_notice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 애니메이션 설정
        Animation animation = new AlphaAnimation(0, 1);
        animation.setDuration(1000);

        setContentView(R.layout.activity_closet_rec);

        // 온도 텍스트뷰
        TextView temp_tv = findViewById(R.id.tv_rec_temp);
        // 한 줄로 나오게하기..(?)
//        temp_tv.setSingleLine();

        // 아래 피드백 줄 텍스트뷰
        tv_notice = findViewById(R.id.box_notice_weather);

        recyclerView = findViewById(R.id.recyclerview2_xml); // closet_rec.xml에 있는 것!...
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);


        list2 = new ArrayList<>();
        adapter = new ImageAdapter(Closet_rec_Activity.this, list2);
        recyclerView.setAdapter(adapter);

        // 현재 저장된 온도 데이터 가져오기
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("weather").child("temp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // 실수 초기화
                temp_range = 0;

                String temp_value = snapshot.getValue(String.class);
                String temp_string = temp_value.trim();

                // 텍스트 뷰 전달
                temp_tv.setText(temp_string);

                float temp_result = (float) Double.parseDouble(temp_string); // float 형태로 변환해서 넣기.

                // float 형태로 가져온 temp_result 반올림 해서 temp 에 담기
                temp_range = (float) (Math.round(temp_result));
//                System.out.println("범위 설정할 temp : 확인 -------> " + temp_range);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("온도 데이터 불러오기 에러");
            }
        });

        // 넘어옷 옷 key id 값 받기
        keyId = getIntent().getStringExtra("keyId2");
        System.out.println("추가 정보에서 넘어온 keyID : " + keyId);


        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference("/ClosetInmyHand/UserAccount/Qph478hjZSMNHu9KKf9BPKm0jd83/images");

                DatabaseReference mDatabase3 = FirebaseDatabase.getInstance().getReference("/ClosetInmyHand/UserAccount/Qph478hjZSMNHu9KKf9BPKm0jd83").child("Temp_closet");


                if (temp_range >= 28) {
                    System.out.println("범위 설정할 temp : 확인 -------> " + temp_range);
                    System.out.println("temp_range <= 28 코드 실행");

                    String usertop = "반팔";
                    String userbottom = "반바지";
                    // 쿼리문
                    Query dbquery = mDatabase2.orderByChild("top").equalTo(usertop);
                    dbquery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Model model = dataSnapshot.getValue(Model.class);
                                list2.add(model);
                            }
                            System.out.println("추천된 반팔 확인" + list2.toString());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                    Query dbquery2 = mDatabase2.orderByChild("bottom").equalTo(userbottom);
                    dbquery2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Model model = dataSnapshot.getValue(Model.class);
                                list2.add(model);
                            }
                            System.out.println("추천된 반바지 확인" + list2.toString());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else if (temp_range <= 27 && temp_range >= 23) {
                    System.out.println("범위 설정할 temp : 확인 -------> " + temp_range);
                    System.out.println("27~23 실행");


                    String usertop = "반팔";
                    String userbottom = "반바지";
                    String usertop2 = "셔츠";
                    // 쿼리문
                    Query dbquery = mDatabase2.orderByChild("top").equalTo(usertop);
                    dbquery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Model model = dataSnapshot.getValue(Model.class);
                                list2.add(model);
                            }
                            System.out.println("추천된 반팔 확인" + list2.toString());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                    Query dbquery2 = mDatabase2.orderByChild("bottom").equalTo(userbottom);
                    dbquery2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Model model = dataSnapshot.getValue(Model.class);
                                list2.add(model);
                            }
                            System.out.println("추천된 반바지 확인" + list2.toString());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    Query dbquery3 = mDatabase2.orderByChild("top").equalTo(usertop2);
                    dbquery3.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Model model = dataSnapshot.getValue(Model.class);
                                list2.add(model);
                            }
                            System.out.println("추천된 셔츠 확인" + list2.toString());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else if (temp_range <= 22 && temp_range >= 20) {
                    System.out.println("범위 설정할 temp : 확인 -------> " + temp_range);
                    System.out.println("22~20 실행");

                    String usertop = "긴팔";
                    String userbottom = "긴바지";
                    // 쿼리문
                    Query dbquery = mDatabase2.orderByChild("top").equalTo(usertop);
                    dbquery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Model model = dataSnapshot.getValue(Model.class);
                                list2.add(model);
                            }
                            System.out.println("추천된 긴팔 확인" + list2.toString());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                    Query dbquery2 = mDatabase2.orderByChild("bottom").equalTo(userbottom);
                    dbquery2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Model model = dataSnapshot.getValue(Model.class);
                                list2.add(model);
                            }
                            System.out.println("추천된 긴바지 확인" + list2.toString());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else if (temp_range <= 19 && temp_range >= 17) {
                    System.out.println("범위 설정할 temp : 확인 -------> " + temp_range);
                    System.out.println("19~17 실행");

                    String usertop = "긴팔";
                    String userbottom = "긴바지";
                    String usertop2 = "니트";

                    // 쿼리문
                    Query dbquery = mDatabase2.orderByChild("top").equalTo(usertop);
                    dbquery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Model model = dataSnapshot.getValue(Model.class);
                                list2.add(model);
                            }
                            System.out.println("추천된 긴팔 확인" + list2.toString());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                    Query dbquery2 = mDatabase2.orderByChild("bottom").equalTo(userbottom);
                    dbquery2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Model model = dataSnapshot.getValue(Model.class);
                                list2.add(model);
                            }
                            System.out.println("추천된 긴바지 확인" + list2.toString());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    Query dbquery4 = mDatabase2.orderByChild("top").equalTo(usertop2);
                    dbquery4.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Model model = dataSnapshot.getValue(Model.class);
                                list2.add(model);


                            }
                            System.out.println("추천된 니트 확인" + list2.toString());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

//                    // 니트가 있는지 확인.
                    Query query_set = mDatabase3.orderByChild("closet").equalTo(usertop2);
                    Query query_set2 = mDatabase3.orderByChild("feel").equalTo("더움");

                    query_set.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // 해당 되는 snapshot 있으면 map에 저장
                            Map<String, Object> map = (Map<String, Object>) snapshot.getValue();

                            query_set2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    // 해당 되는 snapshot 있으면 map에 저장
                                    Map<String, Object> map2 = (Map<String, Object>) snapshot.getValue();

                                    // 둘다 해당 되는 값이 있으면 실행
                                    if (map != null && map2 != null) {
                                        System.out.println("사용자에게 추천된 니트 '더움' 확인 완료 ");
                                        String tv_text = "현재 추천된  [니트]  는 조금 더우실 수도 있어요.";
                                        tv_notice.setText(tv_text);

                                        tv_notice.setVisibility(View.VISIBLE);
                                        tv_notice.setAnimation(animation);


                                    }
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

                    // 12도에서 16도
                } else if (temp_range <= 16 && temp_range >= 12) {
                    System.out.println("범위 설정할 temp : 확인 -------> " + temp_range);
                    System.out.println("16~12 실행");

                    String userouter = "자켓";
                    String userbottom = "긴바지";
                    String usertop2 = "니트";
                    // 쿼리문
                    Query dbquery = mDatabase2.orderByChild("outer").equalTo(userouter);
                    dbquery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Model model = dataSnapshot.getValue(Model.class);
                                list2.add(model);
                            }
                            System.out.println("추천된 자켓 확인" + list2.toString());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    Query dbquery2 = mDatabase2.orderByChild("bottom").equalTo(userbottom);
                    dbquery2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Model model = dataSnapshot.getValue(Model.class);
                                list2.add(model);
                            }
                            System.out.println("추천된 긴바지 확인" + list2.toString());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    Query dbquery4 = mDatabase2.orderByChild("top").equalTo(usertop2);
                    dbquery4.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Model model = dataSnapshot.getValue(Model.class);
                                list2.add(model);
                            }
                            System.out.println("추천된 니트 확인" + list2.toString());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    // 9도에서 11도
                } else if (temp_range <= 11 && temp_range >= 9) {
                    System.out.println("범위 설정할 temp : 확인 -------> " + temp_range);
                    System.out.println("11~9 실행");

                    String userouter = "패딩";

                    String userbottom = "긴바지";
                    String usertop = "니트";

                    Query dbquery1 = mDatabase2.orderByChild("outer").equalTo(userouter);
                    dbquery1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Model model = dataSnapshot.getValue(Model.class);
                                list2.add(model);
                            }
                            System.out.println("추천된 패딩 확인" + list2.toString());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    Query dbquery2 = mDatabase2.orderByChild("bottom").equalTo(userbottom);
                    dbquery2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Model model = dataSnapshot.getValue(Model.class);
                                list2.add(model);
                            }
                            System.out.println("추천된 긴바지 확인" + list2.toString());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    Query dbquery4 = mDatabase2.orderByChild("top").equalTo(usertop);
                    dbquery4.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Model model = dataSnapshot.getValue(Model.class);
                                list2.add(model);
                            }
                            System.out.println("추천된 니트 확인" + list2.toString());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else if (temp_range <= 8 && temp_range >= 5) {
                    System.out.println("범위 설정할 temp : 확인 -------> " + temp_range);
                    System.out.println("8~5 실행");

                    String userouter2 = "패딩";
                    String userouter3 = "코트";

                    String usertop = "니트";

                    Query dbquery2 = mDatabase2.orderByChild("outer").equalTo(userouter2);
                    dbquery2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Model model = dataSnapshot.getValue(Model.class);
                                list2.add(model);
                            }
                            System.out.println("추천된 패딩 확인" + list2.toString());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    Query dbquery3 = mDatabase2.orderByChild("outer").equalTo(userouter3);
                    dbquery3.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Model model = dataSnapshot.getValue(Model.class);
                                list2.add(model);
                            }
                            System.out.println("추천된 코트 확인" + list2.toString());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    Query dbquery4 = mDatabase2.orderByChild("top").equalTo(usertop);
                    dbquery4.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Model model = dataSnapshot.getValue(Model.class);
                                list2.add(model);
                            }
                            System.out.println("추천된 니트 확인" + list2.toString());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    // 니트가 있는지 확인.
                    Query query_set = mDatabase3.orderByChild("closet").equalTo(usertop);
                    Query query_set2 = mDatabase3.orderByChild("feel").equalTo("추움");

                    // 너무 추움 찾기 쿼리문
                    Query query_set3 = mDatabase3.orderByChild("feel").equalTo("너무 추움");

                    query_set.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // 해당 되는 snapshot 있으면 map에 저장
                            Map<String, Object> map = (Map<String, Object>) snapshot.getValue();

                            query_set2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    // 해당 되는 snapshot 있으면 map에 저장
                                    Map<String, Object> map2 = (Map<String, Object>) snapshot.getValue();

                                    // 둘다 해당 되는 값이 있으면 실행
                                    if (map != null && map2 != null) {
                                        System.out.println("사용자에게 추천된 니트 '추움' 확인 완료 ");
                                        String tv_text = "현재 추천된  [니트]  는 좀 추우실 수도 있어요.";
                                        tv_notice.setText(tv_text);

                                        tv_notice.setVisibility(View.VISIBLE);
                                        tv_notice.setAnimation(animation);

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            // 너무 추움 쿼리
                            query_set3.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    // 해당 되는 snapshot 있으면 map에 저장
                                    Map<String, Object> map3 = (Map<String, Object>) snapshot.getValue();

                                    // 둘다 해당 되는 값이 있으면 실행
                                    if (map != null && map3 != null) {
                                        System.out.println("사용자에게 추천된 니트 '많이 !! 추움' 확인 완료 ");
                                        String tv_text = "현재 추천된  [니트]  는 많이 추우실 수도 있어요.";
                                        tv_notice.setText(tv_text);
                                        tv_notice.setVisibility(View.VISIBLE);
                                        tv_notice.setAnimation(animation);

                                    }
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

                } else if (temp_range <= 4) {
                    System.out.println("범위 설정할 temp : 확인 -------> " + temp_range);
                    System.out.println("4도 아래 실행");

                    String userouter2 = "패딩";
                    String usertop = "니트";

                    Query dbquery2 = mDatabase2.orderByChild("outer").equalTo(userouter2);
                    dbquery2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Model model = dataSnapshot.getValue(Model.class);
                                list2.add(model);
                            }
                            System.out.println("추천된 패딩 확인" + list2.toString());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    Query dbquery3 = mDatabase2.orderByChild("top").equalTo(usertop);
                    dbquery3.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Model model = dataSnapshot.getValue(Model.class);
                                list2.add(model);
                            }
                            System.out.println("추천된 코트 확인" + list2.toString());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    // 니트가 있는지 확인.
                    Query query_set = mDatabase3.orderByChild("closet").equalTo(usertop);
                    Query query_set2 = mDatabase3.orderByChild("feel").equalTo("추움");

                    // 너무 추움 찾기 쿼리문
                    Query query_set3 = mDatabase3.orderByChild("feel").equalTo("너무 추움");

                    query_set.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // 해당 되는 snapshot 있으면 map에 저장
                            Map<String, Object> map = (Map<String, Object>) snapshot.getValue();

                            query_set2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    // 해당 되는 snapshot 있으면 map에 저장
                                    Map<String, Object> map2 = (Map<String, Object>) snapshot.getValue();

                                    // 둘다 해당 되는 값이 있으면 실행
                                    if (map != null && map2 != null) {
                                        System.out.println("사용자에게 추천된 니트 '추움' 확인 완료 ");
                                        String tv_text = "현재 추천된  [니트]  는 좀 추우실 수도 있어요.";
                                        tv_notice.setText(tv_text);

                                        tv_notice.setVisibility(View.VISIBLE);
                                        tv_notice.setAnimation(animation);

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            // 너무 추움 쿼리
                            query_set3.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    // 해당 되는 snapshot 있으면 map에 저장
                                    Map<String, Object> map3 = (Map<String, Object>) snapshot.getValue();

                                    // 둘다 해당 되는 값이 있으면 실행
                                    if (map != null && map3 != null) {
                                        System.out.println("사용자에게 추천된 니트 '많이 !! 추움' 확인 완료 ");
                                        String tv_text = "현재 추천된  [니트]  는 많이 추우실 수도 있어요.";
                                        tv_notice.setText(tv_text);
                                        tv_notice.setVisibility(View.VISIBLE);
                                        tv_notice.setAnimation(animation);

                                    }
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

                } else {
                    Toast.makeText(Closet_rec_Activity.this, "추천할 옷이 없습니다.", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

//        // 현재 저장된 온도 데이터 가져오기
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        mDatabase.child("weather").child("temp").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String value = dataSnapshot.getValue(String.class);
//                String result = value.trim();
//                temp_tv.setText(result);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.e("DatabseError _ temp ", String.valueOf(databaseError.toException())); // 에러문 출력
//            }
//        });


        // 닫기 버튼
        ImageButton btn_back = findViewById(R.id.btn_x);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 뒤로가기
                onBackPressed();
            }
        });

    }
}