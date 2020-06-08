package in.ac.iiitd.iiitd_guesthouseapp.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import in.ac.iiitd.iiitd_guesthouseapp.R;

public class BookingActivity extends AppCompatActivity {

    private int day;
    private int month;
    private int year;
    private Button b1;
    private Button b2;
    private Button crb;

    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        b1 = findViewById(R.id.BookingActivity_Button_StartDate);
        b2 = findViewById(R.id.BookingActivity_Button_EndDate);
        fab = findViewById(R.id.floatingActionButton);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BookingActivity.this, UserDetailsActivity.class);
                startActivity(i);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crb = b1;
                showDialog(0);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crb = b2;
                showDialog(0);
            }
        });
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id)
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, datePickerListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        return datePickerDialog;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            day = selectedDay;
            month = selectedMonth;
            year = selectedYear;

            crb.setText( ((day<9)?"0":"") + day + " / " + (((month+1)<9)?"0":"")  + (month + 1) + " / " + year);
        }
    };
}
