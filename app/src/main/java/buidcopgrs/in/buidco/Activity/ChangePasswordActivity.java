package buidcopgrs.in.buidco.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import buidcopgrs.in.buidco.R;
import buidcopgrs.in.buidco.Utilitites.CommonPref;
import buidcopgrs.in.buidco.asyncTasks.ChangePassService;

public class ChangePasswordActivity extends AppCompatActivity {
Toolbar toolbar_cp;
TextInputEditText etben_old_pass,etben_new_pass,et_con_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        toolbar_cp = (Toolbar) findViewById(R.id.toolbar_cp);
        toolbar_cp.setTitle("Change Password");
        setSupportActionBar(toolbar_cp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

            init();
    }

    private void init() {
        etben_old_pass=(TextInputEditText)findViewById(R.id.etben_old_pass);
        etben_new_pass=(TextInputEditText)findViewById(R.id.etben_new_pass);
        et_con_pass=(TextInputEditText)findViewById(R.id.et_con_pass);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void ChangePassword(View view) {
        if (etben_old_pass.getText().toString().trim().equals("")){
            etben_old_pass.setError("Enter Old Pass");
        }
        else if (etben_new_pass.getText().toString().trim().equals("")){
            etben_new_pass.setError("Enter New Pass");
        }
        else if (et_con_pass.getText().toString().trim().equals("")){
            et_con_pass.setError("Confirm new Pass");
        }
        else if (!et_con_pass.getText().toString().trim().equals(etben_new_pass.getText().toString().trim())){
            et_con_pass.setError("Invalid");
            etben_new_pass.setError("Invalid");
        }
        else{
            new ChangePassService(ChangePasswordActivity.this).execute(CommonPref.getUserDetails(ChangePasswordActivity.this).getUserID(),
                    etben_old_pass.getText().toString().trim(),etben_new_pass.getText().toString().trim());
        }
    }
}
