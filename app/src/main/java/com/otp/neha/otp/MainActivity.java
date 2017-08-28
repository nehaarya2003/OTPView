package com.otp.neha.otp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private OTPEditTextView motpView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.verify).setOnClickListener(this);
        motpView= (OTPEditTextView) findViewById(R.id.otpview);
        motpView.getFocusFirst(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.verify:
                if(motpView.getOTPFromEditText().length()==4){
                    Toast.makeText(this, "Valid OTP", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show();

                }
        }
    }
}
