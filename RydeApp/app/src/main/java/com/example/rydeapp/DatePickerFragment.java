package com.example.rydeapp;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Calendar rightNow = Calendar.getInstance();
        return new DatePickerDialog(getActivity(), this,
                rightNow.get(Calendar.YEAR),
                rightNow.get(Calendar.MONTH),
                rightNow.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//        ((MainActivity) getActivity()).showDateResult(year, month, day);
    }
}
