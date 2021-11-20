package com.github.sohn919.charging;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class ChargingDialog extends Dialog {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = mDatabase.getReference();

    Button i_button, c_button, button6, button7, button8, button9, button10; // 충전탭 버튼
    TextView c_pointtext;
    TextView c_amounttext;
    Context context;
    private int point = 0;
    private int c_point = 0; // 충전탭 포인트
    private double dc_point = 0;
    private double c_amount = 0; // 충전탭 전력량


    ChargingDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);   //다이얼로그의 타이틀바를 없애주는 옵션입니다.
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //다이얼로그의 배경을 투명으로 만듭니다.
        getWindow().setGravity(Gravity.BOTTOM);

        setContentView(R.layout.dialog_charging);     //다이얼로그에서 사용할 레이아웃입니다.

        //충전 탭
        i_button = findViewById(R.id.i_button);
        c_button = findViewById(R.id.c_button);
        button6 = findViewById(R.id.Button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        button10 = findViewById(R.id.button10);
        c_pointtext = findViewById(R.id.c_pointtext2);
        c_amounttext = findViewById(R.id.c_amounttext);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        //충전 탭 버튼
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c_point += 1000;
                c_pointtext.setText(Integer.toString(c_point));
                dc_point = (double) c_point;
                c_amount = dc_point / 100000 * 575;
                c_amounttext.setText(Double.toString(c_amount));
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c_point += 5000;
                c_pointtext.setText(Integer.toString(c_point));
                dc_point = (double) c_point;
                c_amount = dc_point / 100000 * 575;
                c_amounttext.setText(Double.toString(c_amount));
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c_point += 10000;
                c_pointtext.setText(Integer.toString(c_point));
                dc_point = (double) c_point;
                c_amount = dc_point / 100000 * 575;
                c_amounttext.setText(Double.toString(c_amount));
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c_point += 50000;
                c_pointtext.setText(Integer.toString(c_point));
                dc_point = (double) c_point;
                c_amount = dc_point / 100000 * 575;
                c_amounttext.setText(Double.toString(c_amount));
            }
        });

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c_point += 100000;
                c_pointtext.setText(Integer.toString(c_point));
                dc_point = (double) c_point;
                c_amount = dc_point / 100000 * 575;
                c_amounttext.setText(Double.toString(c_amount));
            }
        });

        i_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c_point = 0;
                c_pointtext.setText(Integer.toString(c_point));
                dc_point = (double) c_point;
                c_amount = dc_point / 100000 * 575;
                c_amounttext.setText(Double.toString(c_amount));
            }
        });

        c_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("Users").child(user.getUid()).child("point").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int value = (int) snapshot.getValue(Integer.class);
                        if(value > c_point) {
                            Toast.makeText(context, "보유포인트가 부족합니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            value -= c_point;
                            myRef.child("Users").child(user.getUid()).child("point").setValue(value);
                            Toast.makeText(context, "충전을 시작합니다.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

//                //사용내역 데이터베이스 입력
//                myRef.child("UHistory").child(CarNumber).child(getTime()).setValue(c_point);
            }
        });

    }



}

