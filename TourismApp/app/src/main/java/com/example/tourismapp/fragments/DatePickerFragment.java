package com.example.tourismapp.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.tourismapp.activities.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    private static final String START_DATE = "22/07/2020";
    private static final String END_DATE = "09/08/2020";
    private static final String TAG = "DATE_FRAGMENT";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Calendar rightNow = Calendar.getInstance();
        try{
            Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(START_DATE);
            Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(END_DATE);
            if (rightNow.getTime().compareTo(startDate) < 0){
                rightNow.setTime(startDate);
            }else if (rightNow.getTime().compareTo(endDate) > 0){
                rightNow.setTime(endDate);
            }
        }catch(Exception e){
            Log.d(TAG, e.getMessage());
        }

        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), this,
                rightNow.get(Calendar.YEAR),
                rightNow.get(Calendar.MONTH),
                rightNow.get(Calendar.DAY_OF_MONTH));
//        datePicker.getDatePicker().setMinDate(rightNow.getTimeInMillis());
        return datePicker;
    }

    /**
     * handle date selection for date picker
     * @param datePicker
     * @param year
     * @param month
     * @param day
     */
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        try{
            Log.d(TAG, ""+ year + "-" + month + "-" + day);
            Date currentDate = new GregorianCalendar(year, month, day).getTime();
            Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(START_DATE);
            Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(END_DATE);
            if (currentDate.compareTo(startDate) < 0 || currentDate.compareTo(endDate) > 0){
                throw new Exception("There are no events on the given date.");
            }

            //send year, month, day, to main activity callback (month goes from 0-11)
            ((MainActivity) getActivity()).onDatePickerResultSet(year, month + 1, day);
        }catch(Exception e){
            Log.d(TAG, e.getMessage());
            androidx.appcompat.app.AlertDialog.Builder alertBox = new AlertDialog.Builder(getContext());
            alertBox.setTitle("Error");
            alertBox.setMessage(e.getMessage());
            alertBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alertBox.show();
        }
    }

}
