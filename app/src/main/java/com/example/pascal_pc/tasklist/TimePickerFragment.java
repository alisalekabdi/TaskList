package com.example.pascal_pc.tasklist;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment {

    private static final String ARG_TIME = "time";
    public static final String EXTRA_TIME ="com.example.amin.criminalintent.time";
    private Date mDate;
    private TimePicker mTimePicker;

    public static TimePickerFragment newInstance(Date date ) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME,date);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public TimePickerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDate= (Date) getArguments().getSerializable(ARG_TIME);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_time_picker,null);

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);


        int hour=calendar.get(calendar.HOUR_OF_DAY);
        int minuet=calendar.get(calendar.MINUTE);
        final int year = calendar.get(calendar.YEAR);
        final int month = calendar.get(calendar.MONTH);
        final int day = calendar.get(calendar.DAY_OF_MONTH);

        mTimePicker=view.findViewById(R.id.dialog_time_time_picker);
        mTimePicker.setIs24HourView(true);
        mTimePicker.setCurrentHour(hour);
        mTimePicker.setCurrentMinute(minuet);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int hour=mTimePicker.getCurrentHour();
                        int minuet=mTimePicker.getCurrentMinute();


                        Date date = new GregorianCalendar(year,month,day,hour,minuet).getTime();
                        sendResult(date);

                    }
                })
                .create();



    }

    private void sendResult(Date date) {
        Intent intent=new Intent();
        intent.putExtra(EXTRA_TIME,date);
        getTargetFragment().onActivityResult(getTargetRequestCode(),Activity.RESULT_OK,intent);
    }


}
