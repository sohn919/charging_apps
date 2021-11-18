package com.github.sohn919.charging;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.Objects;


public class LoadingDialog extends Dialog {

    Button button1, button2;
    Context context;
    private ChargingDialog chargingDialog;

    LoadingDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);   //다이얼로그의 타이틀바를 없애주는 옵션입니다.
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //다이얼로그의 배경을 투명으로 만듭니다.
        getWindow().setGravity(Gravity.TOP);

        setContentView(R.layout.dialog_loading);     //다이얼로그에서 사용할 레이아웃입니다.

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics(); //디바이스 화면크기를 구하기위해
                int width = dm.widthPixels; //디바이스 화면 너비
                int height = dm.heightPixels; //디바이스 화면 높이
                chargingDialog = new ChargingDialog(getContext());
                WindowManager.LayoutParams wm = chargingDialog.getWindow().getAttributes();  //다이얼로그의 높이 너비 설정하기위해
                wm.copyFrom(chargingDialog.getWindow().getAttributes());  //여기서 설정한값을 그대로 다이얼로그에 넣겠다는의미
                wm.width = (int)(width *0.5);  //화면 너비의 절반
                wm.height = (int)(height *0.5);
                chargingDialog.show();
                dismiss();
            }
        });

    }



}

