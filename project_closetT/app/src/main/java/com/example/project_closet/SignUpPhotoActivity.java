package com.example.project_closet;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class SignUpPhotoActivity extends AppCompatActivity {

    private ImageView imageView;
    private ProgressBar progressBar;
    private final DatabaseReference root = FirebaseDatabase.getInstance().getReference("ClosetInmyHand/UserAccount/Qph478hjZSMNHu9KKf9BPKm0jd83/images");
    private final StorageReference reference = FirebaseStorage.getInstance().getReference();

    private Uri imageUri;
    private String top;
    private String bottom;
    private String outer;
    private String season;
    private String custom;

    private String keyId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet_photosave);

        //컴포넌트 객체에 담기
        Button uploadBtn = findViewById(R.id.upload_btn);
        progressBar = findViewById(R.id.progress_View);
        imageView = findViewById(R.id.image_view);

        // 상의 부분 버튼
        CheckBox LongTopBtn = findViewById(R.id.LongTop);
        CheckBox ShortTopBtn = findViewById(R.id.ShortTop);
        CheckBox NeatTopBtn = findViewById(R.id.NeatTop);
        CheckBox ShirtTopBtn = findViewById(R.id.ShirtTop);

        // 하의 부분 버튼
        CheckBox LongBottomBtn = findViewById(R.id.LongBottom);
        CheckBox ShortBottomBtn = findViewById(R.id.ShortBottom);

        // 아우터 부분 버튼
        CheckBox OuterJacketBtn = findViewById(R.id.jacket);
        CheckBox OuterPaddingBtn = findViewById(R.id.padding);
        CheckBox OuterCoatBtn = findViewById(R.id.coat);

        // 계절 부분 버튼
        CheckBox SeasonSpringBtn = findViewById(R.id.spring);
        Button list = findViewById(R.id.img_list); // ?

        CheckBox SeasonSummerBtn = findViewById(R.id.summer);
        CheckBox SeasonFallBtn = findViewById(R.id.fall);
        CheckBox SeasonWinterBtn = findViewById(R.id.winter);

        // 맞춤 설정 부분 버튼
        CheckBox CustomThinBtn = findViewById(R.id.thin); // 얇음
        CheckBox CustomThickBtn = findViewById(R.id.thick); // 두꺼움
        CheckBox CustomFourBtn = findViewById(R.id.fourSeason); // 사계절

        //프로그래스바 숨기기
        progressBar.setVisibility(View.INVISIBLE);

        //이미지 클릭 이벤트
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/");
                activityResult.launch(galleryIntent);
            }
        });

        //업로드버튼 클릭 이벤트
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //선택한 이미지가 있다면
                if (imageUri != null) {

                    uploadToFirebase(imageUri);

                } else {
                    Toast.makeText(SignUpPhotoActivity.this, "사진을 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //상의버튼 클릭 이벤트
        LongTopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                LongTopBtn.setVisibility(View.VISIBLE);
//                ShortTopBtn.setVisibility(View.VISIBLE);
                top = "긴팔";
            }
        });


        ShortTopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                top = "반팔";
            }
        });

        NeatTopBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                top = "니트";
            }
        }));

        ShirtTopBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                top = "셔츠";
            }
        }));

        // 하의 버튼 클릭 이벤트
        LongBottomBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottom = "긴바지";
            }
        }));
        ShortBottomBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottom = "반바지";
            }
        }));

        // 아우터 클릭 이벤트
        OuterJacketBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outer = "자켓";
            }
        }));
        OuterPaddingBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outer = "패딩";
            }
        }));
        OuterCoatBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outer = "코트";
            }
        }));

        // 계절 클릭 이벤트
        SeasonSpringBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                season = "봄";
            }
        });

        SeasonSummerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                season = "여름";
            }
        });

        SeasonFallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                season = "가을";
            }
        });

        SeasonWinterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                season = "겨울";
            }
        });

        // 맞춤설정 클릭 이벤트
        CustomThinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                custom = "얇음";
            }
        });

        CustomThickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                custom = "두꺼움";
            }
        });

        CustomFourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                custom = "사계절";
            }
        });


        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpPhotoActivity.this, Closet_all_ImageActivity.class);
                startActivity(intent);
            }
        });
    } // onCreate

    //사진 가져오기
    ActivityResultLauncher<Intent> activityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                        imageUri = result.getData().getData();
                        imageView.setImageURI(imageUri);
                    }
                }
            });

    //파이어베이스 이미지 업로드
    private void uploadToFirebase(Uri uri) {

        StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));

        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                //키로 아이디 생성 (순서 변경해봄)
                                keyId = root.push().getKey();


                                //이미지 모델에 담기
                                Model model = new Model(uri.toString(), top, bottom, outer, season, custom, keyId);


                                //데이터 넣기
                                root.child(keyId).setValue(model);

                                //프로그래스바 숨김
                                progressBar.setVisibility(View.INVISIBLE);

                                Toast.makeText(SignUpPhotoActivity.this, "업로드 성공", Toast.LENGTH_SHORT).show();
                                System.out.println("업로드 확인" + keyId);

                                imageView.setImageResource(R.drawable.img_clothes_tee_ex);




                                // 성공 시 옷장으로 이동하기
                                Intent intent = new Intent(SignUpPhotoActivity.this, Closet_all_ImageActivity.class);
                                intent.putExtra("KeyId", keyId); // 키 값 보내기
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // 저장 실패시 처리
                                Toast.makeText(SignUpPhotoActivity.this, "업로드가 되지 않았습니다.", Toast.LENGTH_SHORT).show();

                            }
                        });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                //프로그래스바 보여주기
                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                //프로그래스바 숨김
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(SignUpPhotoActivity.this, "업로드 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //파일타입 가져오기
    private String getFileExtension(Uri uri) {

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
}