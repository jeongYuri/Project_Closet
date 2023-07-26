package com.example.project_closet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
    private EditText mEtEmail, mEtPwd;  // 로그인 입력필드

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ClosetInmyHand");

        mEtEmail = findViewById(R.id.box_email); // 아이디 (res 에서 login.xml에서 id 박스 아이디 찾아라)
        mEtPwd = findViewById((R.id.box_pw)); // 패스워드


        Button btn_login = findViewById(R.id.btn_login2);
        Button btn_back = findViewById(R.id.btn_text_back);

        btn_login.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 로그인 요청
                        String strEmail = mEtEmail.getText().toString();
                        String strPwd = mEtPwd.getText().toString();


                        mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    // 로그인 성공!!
                                    Toast.makeText(LoginActivity.this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity_login.class); // 메인으로 이동
                                    startActivity(intent);
                                    finish(); // 현재 액티비티 파괴



                                } else {
                                    Toast.makeText(LoginActivity.this, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }));


        Button btn_register = findViewById(R.id.btn_register);


        btn_register.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                Intent intent = new Intent(LoginActivity.this, com.example.project_closet.RegisterActivity.class);
                startActivity(intent);

            }
        }));


        btn_back.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity_notlogin.class);
                startActivity(intent);
            }
        }));
    }
}