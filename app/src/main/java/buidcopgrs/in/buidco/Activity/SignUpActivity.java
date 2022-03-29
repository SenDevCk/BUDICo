package buidcopgrs.in.buidco.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Random;

import buidcopgrs.in.buidco.R;
import buidcopgrs.in.buidco.Utilitites.Utiilties;
import buidcopgrs.in.buidco.asyncTasks.GetOTPService;
import buidcopgrs.in.buidco.asyncTasks.SignUpService;
import buidcopgrs.in.buidco.interfaces.OTPBinder;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText etben_name,etben_fname,et_mob_no,et_otp,et_pass;
    Button btn_cancel;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    TextInputLayout til_name,til_email,til_mb,til_otp,til_pass;
    String sts="0";
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etben_name=(TextInputEditText)findViewById(R.id.etben_name);
        etben_fname=(TextInputEditText)findViewById(R.id.etben_fname);
        et_mob_no=(TextInputEditText)findViewById(R.id.et_mob_no);
        et_pass=(TextInputEditText)findViewById(R.id.et_pass);
        et_otp=(TextInputEditText)findViewById(R.id.et_otp);

        til_name=(TextInputLayout) findViewById(R.id.til_name);
        til_email=(TextInputLayout) findViewById(R.id.til_email);
        til_mb=(TextInputLayout) findViewById(R.id.til_mb);
        til_pass=(TextInputLayout) findViewById(R.id.til_pass);
        til_otp=(TextInputLayout) findViewById(R.id.til_otp);



        btn_cancel=(Button) findViewById(R.id.btn_cancel);
        btn_cancel.setText("GET OTP FOR Regiter");
        btn_cancel.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        til_pass.setVisibility(View.GONE);
        til_email.setVisibility(View.GONE);
        til_name.setVisibility(View.GONE);
        til_otp.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_cancel){
            if (sts.equals("1")) {
                if (validate()) {
                    if (Utiilties.isOnline(SignUpActivity.this)) {
                        new SignUpService(SignUpActivity.this).execute(etben_name.getText().toString().trim(), et_mob_no.getText().toString().trim(), etben_fname.getText().toString().trim(), et_otp.getText().toString().trim(), et_pass.getText().toString().trim());
                    } else {
                        Toast.makeText(this, "Go Online !", Toast.LENGTH_SHORT).show();
                    }
                }
            }else{
                Random random = new Random();
                id = String.format("%04d", random.nextInt(10000));
                if (validate2()) {
                    if (Utiilties.isOnline(SignUpActivity.this)) {
                        GetOTPService.otpBIND(new OTPBinder() {
                            @Override
                            public void otpSuccess(String success) {
                                til_email.setVisibility(View.VISIBLE);
                                til_name.setVisibility(View.VISIBLE);
                                til_otp.setVisibility(View.VISIBLE);
                                btn_cancel.setText("Register");
                                Toast.makeText(SignUpActivity.this, "Please Enter All Fields !", Toast.LENGTH_SHORT).show();
                                sts="1";
                            }

                            @Override
                            public void otpFails() {
                                til_email.setVisibility(View.GONE);
                                til_name.setVisibility(View.GONE);
                                til_otp.setVisibility(View.GONE);
                                btn_cancel.setText("GET OTP FOR REGISTER");
                                Toast.makeText(SignUpActivity.this, "User Already Exits !", Toast.LENGTH_SHORT).show();
                                sts="0";
                            }
                        });
                        new GetOTPService(SignUpActivity.this).execute(et_mob_no.getText().toString().trim(), id.trim(), "1");
                    } else {
                        Toast.makeText(this, "Go Online !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private boolean validate2() {
        boolean validated2=false;
         if (et_mob_no.getText().toString().trim().length()<=0){
            et_mob_no.setError("Enter Mobile Number !");
            validated2=false;
        }

        else if (et_mob_no.getText().toString().trim().length()!=10){
            et_mob_no.setError("Invalid Mobile Number !");
            validated2=false;
        }
       /* else if (et_pass.getText().toString().trim().length()<=0){
            et_pass.setError("Enter Password !");
            validated=false;
        }
        else if (et_otp.getText().toString().trim().length()!=4){
            et_otp.setError("Invalid OTP !");
            validated2=false;
        }*/
        else{
            validated2=true;
        }
        return validated2;
    }

    private boolean validate(){
        boolean validated=false;
        if (etben_name.getText().toString().trim().length()<=0){
            etben_name.setError("Enter Name !");
            validated=false;
        }

       /* else if (etben_fname.getText().toString().trim().length()<=0){
            etben_fname.setError("Enter Email !");
            validated=false;
        }*/

      else if (!etben_fname.getText().toString().trim().equals(""))
       {
           if (!etben_fname.getText().toString().trim().matches(emailPattern)){
               etben_fname.setError("Invalid Email !");
               validated=false;
           }
       }

        else if (et_mob_no.getText().toString().trim().length()<=0){
            et_mob_no.setError("Enter Mobile Number !");
            validated=false;
        }

        else if (et_mob_no.getText().toString().trim().length()!=10){
            et_mob_no.setError("Invalid Mobile Number !");
            validated=false;
        }
       /* else if (et_pass.getText().toString().trim().length()<=0){
            et_pass.setError("Enter Password !");
            validated=false;
        }*/
        else if (et_otp.getText().toString().trim().length()!=4){
            et_otp.setError("Invalid OTP !");
            validated=false;
        }
        else if (!et_otp.getText().toString().trim().equals(id)){
            et_otp.setError("Invalid OTP ! Please Enter Valid OTP");
            validated=false;
        }
        else{
            validated=true;
        }
        return validated;
    }

}
