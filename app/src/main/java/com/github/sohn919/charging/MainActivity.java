package com.github.sohn919.charging;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import kr.co.bootpay.Bootpay;
import kr.co.bootpay.BootpayAnalytics;
import kr.co.bootpay.enums.Method;
import kr.co.bootpay.enums.PG;
import kr.co.bootpay.enums.UX;
import kr.co.bootpay.listener.CancelListener;
import kr.co.bootpay.listener.CloseListener;
import kr.co.bootpay.listener.ConfirmListener;
import kr.co.bootpay.listener.DoneListener;
import kr.co.bootpay.listener.ReadyListener;
import kr.co.bootpay.model.BootExtra;
import kr.co.bootpay.model.BootUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = mDatabase.getReference();
    private FirebaseAuth firebaseAuth;

    Button button, button1, button2, button3, button4, button5;
    Button button6, button7, button8, button9, button10; // 충전탭 버튼
    TextView pointtext;
    TextView chargetext;
    TextView c_pointtext;
    TextView c_amounttext;
    private TextView textViewUserEmail;
    private TextView textViewUPoint;
    private int stuck = 10;
    private int point = 0;
    private int c_point = 0; // 충전탭 포인트
    private int u_point = 0; // 현재 사용자 보유 포인트
    private double dc_point = 0;
    private double c_amount = 0; // 충전탭 전력량
    String uid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 초기설정 - 해당 프로젝트(안드로이드)의 application id 값을 설정합니다. 결제와 통계를 위해 꼭 필요합니다.
        // 앱에서 확인하지 말고 꼭 웹 사이트에서 확인하자. 앱의 application id 갖다 쓰면 안됨!!!
        BootpayAnalytics.init(this, "61910e247b5ba4b3a352b0d0");

        //포인트 충전 결제
        button = findViewById(R.id.test);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        pointtext = findViewById(R.id.pointtext2);

        // 충전 탭
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        button10 = findViewById(R.id.button10);
        c_pointtext = findViewById(R.id.c_pointtext2);
        c_amounttext = findViewById(R.id.c_amounttext);




        //로그인 표시
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        textViewUPoint = (TextView) findViewById(R.id.textViewUPoint);
        chargetext = (TextView) findViewById(R.id.chargetext);
        firebaseAuth = FirebaseAuth.getInstance();

        //유저가 로그인 하지 않은 상태라면 null 상태이고 이 액티비티를 종료하고 로그인 액티비티를 연다.
        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        //유저가 있다면, null이 아니면 계속 진행
        FirebaseUser user = firebaseAuth.getCurrentUser();
        //textViewUserEmail의 내용을 변경해 준다.
        textViewUserEmail.setText("반갑습니다.\n"+ user.getEmail()+"으로 로그인 하였습니다.");

        //보유포인트 표시
        textViewUPoint.setText("보유포인트 "+ u_point +" P");


        //충전량 표시
        myRef.child("Users").child(user.getUid()).child("electric").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object value = snapshot.getValue(Object.class);
                chargetext.setText(value.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        /*
        myRef.child("Users").child(user.getUid()).child("electric").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                chargetext.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });
         */

        //포인트 탭 버튼

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                point = 1000;
                pointtext.setText(Integer.toString(point));
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                point = 5000;
                pointtext.setText(Integer.toString(point));
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                point = 10000;
                pointtext.setText(Integer.toString(point));
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                point = 50000;
                pointtext.setText(Integer.toString(point));
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                point = 100000;
                pointtext.setText(Integer.toString(point));
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                BootUser bootUser = new BootUser().setPhone("010-8371-1690"); // !! 자신의 핸드폰 번호로 바꾸기
                BootExtra bootExtra = new BootExtra().setQuotas(new int[] {0, 2, 3});

                Bootpay.init(getFragmentManager())
                        .setApplicationId("61910e247b5ba4b3a352b0d0") // 해당 프로젝트(안드로이드)의 application id 값(위의 값 복붙)
                        .setPG(PG.INICIS) // 결제할 PG 사
                        .setMethod(Method.CARD) // 결제수단
                        .setContext(MainActivity.this)
                        .setBootUser(bootUser)
                        .setBootExtra(bootExtra)
                        .setUX(UX.PG_DIALOG)
//                .setUserPhone("010-1234-5678") // 구매자 전화번호
                        .setName("포인트 결제") // 결제할 상품명
                        .setOrderId("1234") // 결제 고유번호 (expire_month)
                        .setPrice(point) // 결제할 금액
//                        .addItem("마우스", 1, "ITEM_CODE_MOUSE", 100) // 주문정보에 담길 상품정보, 통계를 위해 사용
//                        .addItem("키보드", 1, "ITEM_CODE_KEYBOARD", 200, "패션", "여성상의", "블라우스") // 주문정보에 담길 상품정보, 통계를 위해 사용
                        .onConfirm(new ConfirmListener() { // 결제가 진행되기 바로 직전 호출되는 함수로, 주로 재고처리 등의 로직이 수행
                            @Override
                            public void onConfirm(@Nullable String message) {

                                if (0 < stuck) Bootpay.confirm(message); // 재고가 있을 경우.
                                else Bootpay.removePaymentWindow(); // 재고가 없어 중간에 결제창을 닫고 싶을 경우
                                Log.d("confirm", message);
                            }
                        })
                        .onDone(new DoneListener() { // 결제완료시 호출, 아이템 지급 등 데이터 동기화 로직을 수행합니다
                            @Override
                            public void onDone(@Nullable String message) {
                                Log.d("done", message);
                            }
                        })
                        .onReady(new ReadyListener() { // 가상계좌 입금 계좌번호가 발급되면 호출되는 함수입니다.
                            @Override
                            public void onReady(@Nullable String message) {
                                Log.d("ready", message);
                            }
                        })
                        .onCancel(new CancelListener() { // 결제 취소시 호출
                            @Override
                            public void onCancel(@Nullable String message) {

                                Log.d("cancel", message);
                            }
                        })
                        .onClose(
                                new CloseListener() { //결제창이 닫힐때 실행되는 부분
                                    @Override
                                    public void onClose(String message) {
                                        Log.d("close", "close");
                                    }
                                })
                        .request();
            }
        });

        //충전 탭 버튼

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c_point += 1000;
                c_pointtext.setText(Integer.toString(c_point));
                dc_point = (double)c_point;
                c_amount = dc_point / 100000 * 575 ;
                c_amounttext.setText(Double.toString(c_amount));
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c_point += 5000;
                c_pointtext.setText(Integer.toString(c_point));
                dc_point = (double)c_point;
                c_amount = dc_point / 100000 * 575 ;
                c_amounttext.setText(Double.toString(c_amount));
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c_point += 10000;
                c_pointtext.setText(Integer.toString(c_point));
                dc_point = (double)c_point;
                c_amount = dc_point / 100000 * 575 ;
                c_amounttext.setText(Double.toString(c_amount));
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c_point += 50000;
                c_pointtext.setText(Integer.toString(c_point));
                dc_point = (double)c_point;
                c_amount = dc_point / 100000 * 575 ;
                c_amounttext.setText(Double.toString(c_amount));
            }
        });

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c_point += 100000;
                c_pointtext.setText(Integer.toString(c_point));
                dc_point = (double)c_point;
                c_amount = dc_point / 100000 * 575 ;
                c_amounttext.setText(Double.toString(c_amount));
            }
        });

    }
}