package com.otp.neha.otp;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by neha on 22/5/17.
 */

public class OTPEditTextView extends LinearLayout implements TextView.OnEditorActionListener, View.OnKeyListener {

    Context mContext;
    LinearLayout mLinearLayout;

    EditText mEditText1;
    EditText mEditText2;
    EditText mEditText3;
    EditText mEditText4;

    public OTPEditTextView(Context context) {
        super(context);
        mContext=context;
    }

    public OTPEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        init(context);
    }



    private void setOTPenabled(boolean enabledStatus){
        mEditText1.setEnabled(enabledStatus);
        mEditText2.setEnabled(enabledStatus);
        mEditText3.setEnabled(enabledStatus);
        mEditText4.setEnabled(enabledStatus);
    }

    private void init(Context context) {
        mLinearLayout = (LinearLayout) inflate(context, R.layout.otp_edittext_layout, this);
        mEditText1= (EditText) findViewById(R.id.et_enter_code1);
        mEditText2= (EditText) findViewById(R.id.et_enter_code2);
        mEditText3= (EditText) findViewById(R.id.et_enter_code3);
        mEditText4= (EditText) findViewById(R.id.et_enter_code4);

        mEditText1.setOnEditorActionListener(this);
        mEditText1.addTextChangedListener(new TextChangeListenerOTP(mEditText1));
        mEditText1.setOnKeyListener(this);
        mEditText2.setOnEditorActionListener(this);
        mEditText2.addTextChangedListener(new TextChangeListenerOTP(mEditText2));
        mEditText2.setOnKeyListener(this);
        mEditText3.setOnEditorActionListener(this);
        mEditText3.setOnKeyListener(this);
        mEditText3.addTextChangedListener(new TextChangeListenerOTP(mEditText3));
        mEditText4.setOnEditorActionListener(this);
        mEditText4.addTextChangedListener(new TextChangeListenerOTP(mEditText4));
        mEditText4.setOnKeyListener(this);

        setOTPenabled(false);


    }

    public void clearOTPView(){
        mEditText1.setText("");
        mEditText2.setText("");
        mEditText3.setText("");
        mEditText4.setText("");
    }
    public void getFocusFirst(boolean status){
        setOTPenabled(status);
        if(status) {
            mEditText1.requestFocus();
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            if(imm!=null) {
                imm.showSoftInput(mEditText1, InputMethodManager.SHOW_IMPLICIT);
            }
        }
    }

    public void getFocusLast(boolean status){
        setOTPenabled(status);
        if(status) {
            mEditText4.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        }
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
     if(event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_NEXT)) {
            moveForward(v.getId());
        }else if(event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_DONE)) {
        // CALL YOUR ACTIVITY METHOD
     }

        return false;
    }

    public void setOtp(String otp){
        mEditText1.setText(Character.toString(otp.charAt(0)));
        mEditText2.setText(Character.toString(otp.charAt(1)));
        mEditText3.setText(Character.toString(otp.charAt(2)));
        mEditText4.setText(Character.toString(otp.charAt(3)));
    }
    public String getOTPFromEditText(){
        return  mEditText1.getText().toString().trim()+ mEditText2.getText().toString().trim()+
                mEditText3.getText().toString().trim()+ mEditText4.getText().toString().trim();
    }

    public boolean isValidOTP(){
       if(mEditText1.getText().toString().length() == 0||
               mEditText2.getText().toString().length() == 0||
               mEditText3.getText().toString().length() == 0 ||
               mEditText4.getText().toString().length() == 0){

            return false;
        }else{
           return true;
       }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        //This is the filter
        if (event.getAction()!= KeyEvent.ACTION_DOWN)
            return true;

        switch (keyCode) {
            case KeyEvent.KEYCODE_DEL:
                moveBackward(v.getId());
                return  false;
            case KeyEvent.KEYCODE_0:
            case KeyEvent.KEYCODE_1:
            case KeyEvent.KEYCODE_2:
            case KeyEvent.KEYCODE_3:
            case KeyEvent.KEYCODE_4:
            case KeyEvent.KEYCODE_5:
            case KeyEvent.KEYCODE_6:
            case KeyEvent.KEYCODE_7:
            case KeyEvent.KEYCODE_8:
            case KeyEvent.KEYCODE_9:
                if(v instanceof EditText && ((EditText)v).length()==1){
                    moveForward(v.getId());
                    return false;
                }

                return false;

            default:
                    return true;
        }
    }

    class TextChangeListenerOTP implements TextWatcher {

        int  viewID;
        public TextChangeListenerOTP(View v){
            viewID=v.getId();
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            if(s.length()==1){
                moveForward(viewID);
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.length()==1){
                 if(viewID==R.id.et_enter_code4){
                   //CALL YOUR ACTIVITY METHOD
                }else{
                     moveForward(viewID);
                 }

            }

        }
    }

    private void moveBackward(int viewID){
        if(viewID==R.id.et_enter_code4){
            mEditText4.setText("");
            mEditText3.requestFocus();
        }else if(viewID==R.id.et_enter_code3){
            mEditText3.setText("");
            mEditText2.requestFocus();
        }else if(viewID==R.id.et_enter_code2){
            mEditText2.setText("");
            mEditText1.requestFocus();
        }else{
            mEditText1.setText("");
            mEditText1.requestFocus();
        }
    }

    private void moveForward(int viewID){

        if(viewID==R.id.et_enter_code1){
            mEditText2.requestFocus();
        }else if(viewID==R.id.et_enter_code2){
            mEditText3.requestFocus();
        }else if(viewID==R.id.et_enter_code3){
            mEditText4.requestFocus();
        }
    }


}
