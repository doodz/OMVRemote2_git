package OMVFragment;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.dev.doods.base.R;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import Client.Call;
import Client.CallbackImpl;
import Client.Response;
import Controllers.SystemController;
import Interfaces.OnFragmentInteractionListener;
import Models.TimeSettings;
import OMV.Classe.FragmentBase;
import utils.ISO8601;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link DateAndTimeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DateAndTimeFragment extends FragmentBase {
    private SystemController controller;
    Handler handler;
    private TextView _mCurrentTimeView;
    private Spinner _mTimeZoneView;
    private Switch _mUseNTPView;
    private EditText _mServersView;
    private Button _mUpdateView;
    private FloatingActionButton _mSaveView;
    private AutoCompleteTextView _mAutoCompleteTextView;
    private OnFragmentInteractionListener mListener;
    private ArrayList<String> _mLstTimeZone;

    private CardView _mManualDateCard;
    private EditText mDateView;
    private EditText mTimeView;
    private Calendar mCalendar =  GregorianCalendar.getInstance();
    private TimeSettings _mTimeSettings;
    public DateAndTimeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DateAndTimeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DateAndTimeFragment newInstance(String param1, String param2) {
        DateAndTimeFragment fragment = new DateAndTimeFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =inflater.inflate(R.layout.fragment_date_and_time, container, false);

        handler= new Handler();
        controller = new SystemController(getActivity());

        bindViews(rootView);

        _mUseNTPView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                _mServersView.setEnabled(isChecked);
                _mManualDateCard.setVisibility(isChecked? View.GONE:View.VISIBLE);
            }
        });

        _mSaveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!IsFinalized(true))return;

                _mTimeSettings.setNtpenable(_mUseNTPView.isChecked());
                _mTimeSettings.setNtptimeservers(_mServersView.getText().toString());

                int i = _mTimeZoneView.getSelectedItemPosition();
                _mTimeSettings.setTimezone(_mLstTimeZone.get(i));

                controller.setTimeSettings(_mTimeSettings, new CallbackImpl(DateAndTimeFragment.this));
            }});

       // _mAutoCompleteTextView =   (AutoCompleteTextView)  rootView.findViewById(R.id.AutoCompleteTimeZone);



        controller.getTimeZoneList(new CallbackImpl(DateAndTimeFragment.this) {
            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {

                super.onResponse(call,response);
                _mLstTimeZone = response.GetResultObject( new TypeToken<ArrayList<String>>(){});

                handler.post(new Runnable(){
                    public void run() {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, _mLstTimeZone);


                        _mTimeZoneView.setAdapter(adapter);


                        //ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,_mLstTimeZone);
                        //_mAutoCompleteTextView.setAdapter(adapter);


                    }
                });
                controller.getTimeSettings(new CallbackImpl(DateAndTimeFragment.this) {

                    @Override
                    public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                        super.onResponse(call,response);
                        _mTimeSettings =response.GetResultObject( new TypeToken<TimeSettings>(){});

                        handler.post(new Runnable(){
                            public void run() {
                                try {
                                    mCalendar = ISO8601.toCalendar( _mTimeSettings.getDate().getISO8601());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                mTimeView.setText( mCalendar.get(Calendar.HOUR) + ":" + mCalendar.get(Calendar.MINUTE));

                                mDateView.setText(new StringBuilder().append(mCalendar.get(Calendar.MONTH)+1)
                                        .append("-").append(mCalendar.get(Calendar.DAY_OF_MONTH)).append("-").append(mCalendar.get(Calendar.YEAR))
                                        .append(" "));

                                _mCurrentTimeView.setText(_mTimeSettings.getDate().getLocal());
                                _mUseNTPView.setChecked(_mTimeSettings.getNtpenable());
                                _mServersView.setText(_mTimeSettings.getNtptimeservers());

                                int i = _mLstTimeZone.indexOf(_mTimeSettings.getTimezone());
                                _mTimeZoneView.setSelection(i);

                                //_mAutoCompleteTextView.setText(res.getTimezone());
                                // _mAutoCompleteTextView.setSelection(i);
                            }
                        });
                    }
                });
            }
        });



        mDateView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    ShowDatePicker();
                }
            }
        });

        mDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDatePicker();
            }
        });

        mTimeView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    ShowTimePicker();
                }
            }
        });

        mTimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowTimePicker();
            }
        });

        _mUpdateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long t = mCalendar.getTimeInMillis();
                t /=1000;

                controller.setDate(t, new CallbackImpl(DateAndTimeFragment.this));

            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }


    private void bindViews(View rootView)
    {
        _mCurrentTimeView = (TextView) rootView.findViewById(R.id.tvCurrentTime);
        _mTimeZoneView  = (Spinner) rootView.findViewById(R.id.spinnerTimeZone);
        _mUseNTPView =(Switch)   rootView.findViewById(R.id.swithNTP);
        _mServersView  = (EditText) rootView.findViewById(R.id.etServers);
        _mUpdateView= (Button) rootView.findViewById(R.id.buttonUpdate);
        _mSaveView= (FloatingActionButton) rootView.findViewById(R.id.fab_Save);
        _mManualDateCard= (CardView) rootView.findViewById(R.id.card_Manual_Date);
        mDateView = (EditText) rootView.findViewById(R.id.etDate);
        mTimeView = (EditText) rootView.findViewById(R.id.etTime);
    }

    private int year;
    private int month;
    private int day;
    //private DatePicker dpResult;
    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            mDateView.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));

            mCalendar.set(Calendar.YEAR,year);
            mCalendar.set(Calendar.MONTH,month);
            mCalendar.set(Calendar.DAY_OF_MONTH,day);
        }
    };

    private void ShowDatePicker()
    {

        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(getContext(),datePickerListener,mCalendar.get(Calendar.YEAR),mCalendar.get(Calendar.MONTH),mCalendar.get(Calendar.DAY_OF_MONTH));
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
    }

    private void ShowTimePicker()
    {
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = mCalendar.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                mTimeView.setText( selectedHour + ":" + selectedMinute);
                mCalendar.set(Calendar.HOUR_OF_DAY,selectedHour);
                mCalendar.set(Calendar.MINUTE,selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }




}
