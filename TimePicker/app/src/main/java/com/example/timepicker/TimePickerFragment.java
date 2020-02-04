package com.example.timepicker;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Calendar rightNow = Calendar.getInstance();
        return new TimePickerDialog(getActivity(), this, rightNow.get(Calendar.HOUR_OF_DAY), rightNow.get(Calendar.MINUTE), true);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        Log.d("onTimeSet", hour + ":" + minute);
        ((MainActivity) getActivity()).showTimeResult(hour, minute);
    }

}
