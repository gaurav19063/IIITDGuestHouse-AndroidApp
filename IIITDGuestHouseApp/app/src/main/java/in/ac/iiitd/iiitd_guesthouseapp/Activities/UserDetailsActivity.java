package in.ac.iiitd.iiitd_guesthouseapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.ac.iiitd.iiitd_guesthouseapp.R;

public class UserDetailsActivity extends AppCompatActivity {
    private FloatingActionButton fab1;

    // TODO define the editext and radio button
    private EditText editTextName, editTextEmail, editTextEmpNo, editTextVName, editTextVPurpose;
    private RadioButton radioButtonSelf, radioButtonVisitor;
    private RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);


        editTextName = findViewById(R.id.userDetails_name);
        editTextEmail = findViewById(R.id.userDetails_email);
        editTextEmpNo = findViewById(R.id.userDetails_emp_no);
        editTextVName = findViewById(R.id.userDetails_visitor_name);
        editTextVPurpose = findViewById(R.id.userDetails_visitor_purpose);

        radioGroup = findViewById(R.id.BookingActivity_RadioGroup);
        radioButtonSelf = findViewById(R.id.userDetails_radio_self);
        radioButtonVisitor = findViewById(R.id.userDetails_radio_visitor);


        fab1 = findViewById(R.id.userDetails_fab_next);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkInputData()) {

                } else {


                    Intent i = new Intent(UserDetailsActivity.this, PaymentActivity.class);
                    startActivity(i);
                }
            }
        });
    }


    private boolean checkInputData() {

        String name = editTextName.getText().toString().trim();
        String email = editTextName.getText().toString().trim();
        String empno = editTextName.getText().toString().trim();
        String vname = editTextName.getText().toString().trim();
        String vpurpose = editTextName.getText().toString().trim();
        final int[] radio = {-1};

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.userDetails_radio_self) {
                    radio[0] = 0;
                }
                if (checkedId == R.id.userDetails_radio_visitor) {
                    radio[0] = 1;
                }
            }
        });

        if (name.equals("")) {
            editTextVName.setError("Enter Input Correctly");
            return false;
        }
        if (TextUtils.isEmpty(email) || isValidMail(email)) {
            return false;
        }
        if (radio[0] == -1) {
            Toast.makeText(this, "Please Select the type of Guest", Toast.LENGTH_SHORT).show();
            radioGroup.setFocusable(true);
            return false;
        } else if (radio[0] == 1) {
            if (TextUtils.isEmpty(vname)) {
                editTextVName.setError("Enter Input Correctly");
                return false;
            }
            if (TextUtils.isEmpty(vpurpose)) {
                editTextVPurpose.setError("Enter Input Correctly");
                return false;
            }
        }

        return true;
    }

    private boolean isValidMail(String email) {
        boolean check;
        Pattern p;
        Matcher m;

        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        p = Pattern.compile(EMAIL_STRING);
        m = p.matcher(email);
        check = m.matches();

        if (!check) {
            editTextEmail.setError("Not Valid Email");
        }
        return check;
    }
}
