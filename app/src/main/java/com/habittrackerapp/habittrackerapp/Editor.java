package com.habittrackerapp.habittrackerapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.habittrackerapp.habittrackerapp.HabitContract.HabitEntry;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SaherOs on 2/18/2018.
 */

public class Editor extends AppCompatActivity {

    @BindView(R.id.editTextName)
    EditText mNameEditText;
    @BindView(R.id.editTextNumberOfTimes)
    EditText times_number;
    @BindView(R.id.textViewDate)
    TextView mDate;
    private String present_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        ButterKnife.bind(this);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        present_date = sdf.format(c.getTime());
        Log.d("Edit", present_date);
        mDate.setText(present_date);
    }

    private void insertHabit() {

        MyDatabase mDbHelper = new MyDatabase(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String nameString = mNameEditText.getText().toString().trim();
        String numberOfTimesString = times_number.getText().toString().trim();
        int numberOfTimes = 0;
        if (!numberOfTimesString.equals(""))
            numberOfTimes = Integer.parseInt(numberOfTimesString);


        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_NAME, nameString);
        values.put(HabitEntry.COLUMN_START_DATE, present_date);
        values.put(HabitEntry.COLUMN_NUMBER_OF_TIMES, numberOfTimes);

        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Toast.makeText(this, "Error with saving Habit", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Habit saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }



    public void saveMyHabit(View view) {
        if (mNameEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "you must enter your habit name", Toast.LENGTH_SHORT).show();
            return;
        }
        insertHabit();
        finish();
    }

    public void cancelMYHabit(View view) {
        finish();
    }
}

