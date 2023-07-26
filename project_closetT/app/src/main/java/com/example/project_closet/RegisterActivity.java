package com.example.project_closet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
    private EditText mEtEmail, mEtPwd, mEtname; // 회원가입 입력필드
    private Button mBtnRegister; // 회원가입 버튼

    private Button mBtnLogin; // 로그인 버튼

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ClosetInmyHand");

        mEtname = findViewById((R.id.box_name)); // 이름
        mEtEmail = findViewById(R.id.box_email); // 이메일
        mEtPwd = findViewById((R.id.box_pw)); // 패스워드

        mBtnRegister = findViewById(R.id.btn_register); // 회원가입

        mBtnLogin = findViewById(R.id.btn_text); // 로그인창으로 가기 버튼

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입 처리 시작

                String strName = mEtname.getText().toString();
                String strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();

                // 입력 안할 시 처리
                if (strName.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("별명을 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;

                } else if (strEmail.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("이메일을 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                } else if (strPwd.equals("") ||strPwd.length() < 6 ) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("비밀번호 입력 또는 6자리 이상 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;



                    } else {

                        //파이버에이스 진행

                        mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser(); // 현재 유저 가지고오는 행위?

                                    com.example.project_closet.UserAccount account = new com.example.project_closet.UserAccount();
                                    account.setIdToken(firebaseUser.getUid());
                                    account.setUserName(strName);
                                    account.setEmailId(firebaseUser.getEmail());
                                    account.setPassword(strPwd);
                                    System.out.println("확인____________________________Log" + strName + strEmail + strPwd);

                                    // satValue : database에 insert(삽입) 행위
                                    mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                                    Toast.makeText(RegisterActivity.this, "회원가입이 되었습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(RegisterActivity.this, "중복된 이름이 있거나, 회원가입이 되지 않았습니다.", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                    }


                }
            });


        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
    }