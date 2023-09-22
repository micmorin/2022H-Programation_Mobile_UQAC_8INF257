package uqac.dim.alarmesandroidnextgeneration;

import static android.content.Context.ALARM_SERVICE;
import static uqac.dim.alarmesandroidnextgeneration.MainActivity.colors;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.M)
public class AlarmClass extends Fragment  implements AdapterView.OnItemSelectedListener {
    LinearLayout scroll_lin = null;
    final ArrayList<indv_Alarm> alarm_list;
    LinearLayout activeFlipper = null;
    LayoutInflater inflater = null;
    final String[] ringtones = {"Default", "Good", "Bad", "Interesting", "Other"};
    final String[] timeframes = {"Day", "Week", "Month", "Year", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();
        updateAlarms();
    }

    public AlarmClass() {
            alarm_list = new ArrayList<>();

            alarm_list.add(new indv_Alarm.alarmBuilder().build());
            alarm_list.add(
                    new indv_Alarm.alarmBuilder()
                            .hour(1)
                            .min(1)
                            .build()
            );
            alarm_list.add(
                    new indv_Alarm.alarmBuilder()
                            .hour(2)
                            .min(2)
                            .on(true)
                            .ringtone(2)
                            .build()
            );
            alarm_list.add(
                    new indv_Alarm.alarmBuilder()
                            .hour(11)
                            .min(11)
                            .on(true)
                            .ringtone(3)
                            .repeat(true)
                            .vibrate(true)
                            .build()
            );
            alarm_list.add(
                    new indv_Alarm.alarmBuilder()
                            .hour(2)
                            .min(31)
                            .on(true)
                            .ringtone(3)
                            .repeat(true)
                            .vibrate(true)
                            .build()
            );
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inf, ViewGroup container, Bundle savedInstanceState) {
        inflater = inf;
        ScrollView scroll = (ScrollView) inflater.inflate(R.layout.alarms_layout, container, false);
        scroll_lin = (LinearLayout) scroll.getChildAt(0);

        updateAlarms();
        alarmInit();
        return scroll;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setAlarm(LinearLayout viewFlipper, int i) {
        setAlarmTime(viewFlipper, i);
        setAlarmOn(viewFlipper, i);
        setAlarmUpDown(viewFlipper);
        setAlarmIcons(viewFlipper, i);
        setAlarmRepeat(viewFlipper, i);
        setAlarmSpinner(viewFlipper, i);
        setAlarmVibrate(viewFlipper, i);
        setAlarmLabel(viewFlipper, i);
        setAlarmDelete(viewFlipper, i);
        viewFlipper.findViewById(R.id.alarm_separator).setBackgroundColor(colors.get("secondary"));
    }

    private void setAlarmTime(LinearLayout viewFlipper, int i) {
        TextView hourTxt = viewFlipper.findViewById(R.id.time);
        hourTxt.setText(String.format(Locale.getDefault(), "%02d:%02d", alarm_list.get(i).getHour(), alarm_list.get(i).getMin()));
        hourTxt.setOnClickListener(view -> changeAlarmTime(view, i));

        hourTxt = viewFlipper.findViewById(R.id.digit_clock);
        hourTxt.setText(String.format(Locale.getDefault(), "%02d:%02d", alarm_list.get(i).getHour(), alarm_list.get(i).getMin()));
        hourTxt.setOnClickListener(view -> changeAlarmTime(view, i));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setAlarmOn(LinearLayout viewFlipper, int i) {
        SwitchCompat on = viewFlipper.findViewById(R.id.toggle_simple);
        on.setOnCheckedChangeListener((view, isChecked) -> activation(viewFlipper, isChecked));
        on.setChecked(alarm_list.get(i).isOn());

        on = viewFlipper.findViewById(R.id.toggle_adv);
        on.setOnCheckedChangeListener((view, isChecked) -> activation(viewFlipper, isChecked));
        on.setChecked(alarm_list.get(i).isOn());
    }
    private void alarmInit(){
        for(int i = 0; i < alarm_list.size(); i++) {
            if (alarm_list.get(i).isOn()) {
                AlarmManager manager = (AlarmManager) this.getContext().getSystemService(ALARM_SERVICE);
                Intent in = new Intent(this.getContext(), AlarmReceiver.class);

                Calendar cal = Calendar.getInstance();
                long interval = 0;
                if (!alarm_list.get(i).isRepeat()) {
                    cal.set(Calendar.DAY_OF_MONTH, alarm_list.get(i).getStartDay());
                    cal.set(Calendar.MONTH, alarm_list.get(i).getStartMonth());
                    cal.set(Calendar.YEAR, alarm_list.get(i).getStartYear());
                    cal.set(Calendar.HOUR_OF_DAY, alarm_list.get(i).getHour());
                    cal.set(Calendar.MINUTE, alarm_list.get(i).getMin());
                    cal.set(Calendar.SECOND, 0);
                } else {
                    cal.set(Calendar.DAY_OF_MONTH, alarm_list.get(i).getStartDay());
                    cal.set(Calendar.MONTH, alarm_list.get(i).getStartMonth());
                    cal.set(Calendar.YEAR, alarm_list.get(i).getStartYear());
                    cal.set(Calendar.HOUR_OF_DAY, alarm_list.get(i).getHour());
                    cal.set(Calendar.MINUTE, alarm_list.get(i).getMin());
                    cal.set(Calendar.SECOND, 0);

                    switch (alarm_list.get(i).getTimeFrame()) {
                        case 0:
                            interval = (long) alarm_list.get(i).getNrbRepeat() * 24 * 60 * 60 * 1000;
                            break;
                        case 1:
                            interval = (long) alarm_list.get(i).getNrbRepeat() * 30 * 24 * 60 * 60 * 1000;
                            break;
                        case 2:
                            interval = (long) alarm_list.get(i).getNrbRepeat() * 365 * 24 * 60 * 60 * 1000;
                            break;
                        default:
                            interval = (long) alarm_list.get(i).getNrbRepeat() * 7 * 24 * 60 * 60 * 1000;
                            break;
                    }
                }

                in.putExtra("cal", cal);
                in.putExtra("interval", interval);
                if (alarm_list.get(i).getLabel().isEmpty())
                    in.putExtra("label", alarm_list.get(i).getHour() + " " + alarm_list.get(i).getMin());
                else
                    in.putExtra("label", alarm_list.get(i).getLabel());

                PendingIntent intent = PendingIntent.getBroadcast(this.getContext(), i, in, PendingIntent.FLAG_UPDATE_CURRENT);
                if (!alarm_list.get(i).isRepeat())
                    manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), intent);
                else
                    manager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), interval, intent);
            }
        }
    }

    private void setAlarmUpDown(LinearLayout viewFlipper) {
        ImageButton b = viewFlipper.findViewById(R.id.down_arrow);
        b.setOnClickListener(view -> toggleFlipper(viewFlipper));

        b = viewFlipper.findViewById(R.id.up_arrow);
        b.setOnClickListener(view -> toggleFlipper(viewFlipper));
    }

    private void setAlarmIcons(LinearLayout viewFlipper, int i) {
        ImageView bv = viewFlipper.findViewById(R.id.delete);
        if (alarm_list.get(i).isOn())
            DrawableCompat.setTint(bv.getDrawable(), colors.get("primary"));
        else
            DrawableCompat.setTint(bv.getDrawable(), colors.get("secondary"));
    }

    private void setAlarmRepeat(LinearLayout viewFlipper, int i) {
        CheckBox c = viewFlipper.findViewById(R.id.repeat);
        c.setOnCheckedChangeListener((view,isChecked)-> repeatToggle(viewFlipper, isChecked));
        c.setChecked(alarm_list.get(i).isRepeat());

        EditText e = viewFlipper.findViewById(R.id.nbr_repeat);
        e.setText(String.valueOf(alarm_list.get(i).getNrbRepeat()));

        Spinner spin = viewFlipper.findViewById(R.id.timeFrame_spinner);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this.getContext(), R.layout.spinner_item, timeframes);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        spin.setSelection(alarm_list.get(i).getTimeFrame());

        TextView Txt = viewFlipper.findViewById(R.id.start_date);
        Txt.setText(String.format(Locale.getDefault(), "Starts %02d/%02d/%04d", alarm_list.get(i).getStartDay(), alarm_list.get(i).getStartMonth(), alarm_list.get(i).getStartYear()));
        Txt.setOnClickListener(view -> changeStartTime(view, i));
    }

    private void setAlarmSpinner(LinearLayout viewFlipper, int i) {
        Spinner spin = viewFlipper.findViewById(R.id.ring_spinner);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this.getContext(), R.layout.spinner_item, ringtones);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        spin.setSelection(alarm_list.get(i).getRingtone());
    }

    private void setAlarmVibrate(LinearLayout viewFlipper, int i) {
        CheckBox vib = viewFlipper.findViewById(R.id.vibrate);
        vib.setChecked(alarm_list.get(i).isVibrate());

    }

    private void setAlarmLabel(LinearLayout viewFlipper, int i) {
        if (!alarm_list.get(i).getLabel().equals("")) {
            EditText l = viewFlipper.findViewById(R.id.label);
            l.setText(alarm_list.get(i).getLabel());
        }
    }

    private void setAlarmDelete(LinearLayout viewFlipper, int i) {
        TextView txt = viewFlipper.findViewById(R.id.delete_label);
        txt.setOnClickListener((view -> deleteAlarm(i)));

        ImageView img = viewFlipper.findViewById(R.id.delete);
        img.setOnClickListener((view -> deleteAlarm(i)));
    }

    private void toggleFlipper(View view) {
        LinearLayout viewFlipper = (LinearLayout) view;
        if (activeFlipper != null) {
            toggleFlipperPrevious(activeFlipper);
            if (activeFlipper.equals(viewFlipper))
                activeFlipper = null;
            else {
                toggleFlipperNext(viewFlipper);
                activeFlipper = viewFlipper;
            }
        } else {
            toggleFlipperNext(viewFlipper);
            activeFlipper = viewFlipper;
        }
    }

    private void toggleFlipperNext(LinearLayout viewFlipper) {
        viewFlipper.getChildAt(0).setVisibility(View.GONE);
        viewFlipper.getChildAt(1).setVisibility(View.VISIBLE);
    }

    private void toggleFlipperPrevious(LinearLayout viewFlipper) {
        viewFlipper.getChildAt(1).setVisibility(View.GONE);
        viewFlipper.getChildAt(0).setVisibility(View.VISIBLE);
    }

    private void activation(LinearLayout viewFlipper, boolean isChecked) {
        activationTime(viewFlipper, isChecked);
        activationIcons(viewFlipper, isChecked);
        activationSpinner(viewFlipper, isChecked);
        activationRepeat(viewFlipper, isChecked);
        activationVibrate(viewFlipper, isChecked);
        activationLabel(viewFlipper, isChecked);
        activationDelete(viewFlipper, isChecked);
    }

    private void activationTime(LinearLayout viewFlipper, boolean isChecked) {
        TextView txt;

        txt = viewFlipper.findViewById(R.id.time);
        if (isChecked)
            txt.setTextColor(colors.get("primary"));
        else
            txt.setTextColor(colors.get("secondary"));

        txt = viewFlipper.findViewById(R.id.digit_clock);
        if (isChecked)
            txt.setTextColor(colors.get("primary"));
        else
            txt.setTextColor(colors.get("secondary"));

        txt = viewFlipper.findViewById(R.id.day);
        if (isChecked)
            txt.setTextColor(colors.get("primary"));
        else
            txt.setTextColor(colors.get("secondary"));
    }

    private void activationIcons(LinearLayout viewFlipper, boolean isChecked) {
        ImageView img;

        img = viewFlipper.findViewById(R.id.delete);
        if (isChecked)
            DrawableCompat.setTint(img.getDrawable(), colors.get("primary"));
        else
            DrawableCompat.setTint(img.getDrawable(), colors.get("secondary"));

        img = viewFlipper.findViewById(R.id.label_icon);
        if (isChecked)
            DrawableCompat.setTint(img.getDrawable(), colors.get("primary"));
        else
            DrawableCompat.setTint(img.getDrawable(), colors.get("secondary"));

        img = viewFlipper.findViewById(R.id.ring_icon);
        if (isChecked)
            DrawableCompat.setTint(img.getDrawable(), colors.get("primary"));
        else
            DrawableCompat.setTint(img.getDrawable(), colors.get("secondary"));
    }

    private void activationRepeat(LinearLayout viewFlipper, boolean isChecked) {
        CheckBox c = viewFlipper.findViewById(R.id.repeat);
        Spinner spin = viewFlipper.findViewById(R.id.timeFrame_spinner);
        if (spin.getSelectedView() != null)
            if (isChecked)
                ((TextView) spin.getSelectedView()).setTextColor(colors.get("primary"));
            else
                ((TextView) spin.getSelectedView()).setTextColor(colors.get("secondary"));
        if (isChecked){
            c.setTextColor(colors.get("primary"));
            ((TextView)viewFlipper.findViewById(R.id.nbr_repeat)).setTextColor(colors.get("primary"));
            ((TextView)viewFlipper.findViewById(R.id.start_date)).setTextColor(colors.get("primary"));
        }
        else {
            c.setTextColor(colors.get("secondary"));
            ((TextView) viewFlipper.findViewById(R.id.nbr_repeat)).setTextColor(colors.get("secondary"));
            ((TextView) viewFlipper.findViewById(R.id.start_date)).setTextColor(colors.get("secondary"));
        }
        repeatToggle(viewFlipper,isChecked);
    }

    private void activationSpinner(LinearLayout viewFlipper, boolean isChecked) {
        Spinner spin = viewFlipper.findViewById(R.id.ring_spinner);
        if (spin.getSelectedView() != null)
            if (isChecked)
                ((TextView) spin.getSelectedView()).setTextColor(colors.get("primary"));
            else
                ((TextView) spin.getSelectedView()).setTextColor(colors.get("secondary"));


    }

    private void activationVibrate(LinearLayout viewFlipper, boolean isChecked) {
        CheckBox c = viewFlipper.findViewById(R.id.vibrate);
        if (isChecked)
            c.setTextColor(colors.get("primary"));
        else
            c.setTextColor(colors.get("secondary"));
    }

    private void activationLabel(LinearLayout viewFlipper, boolean isChecked) {
        EditText txt = viewFlipper.findViewById(R.id.label);
        if (isChecked){
            txt.setHintTextColor(colors.get("primary"));
            txt.setTextColor(colors.get("primary"));
        } else {
            txt.setHintTextColor(colors.get("secondary"));
            txt.setTextColor(colors.get("secondary"));
        }
    }

    private void activationDelete(LinearLayout viewFlipper, boolean isChecked) {
        TextView txt = viewFlipper.findViewById(R.id.delete_label);
        if (isChecked)
            txt.setTextColor(colors.get("primary"));
        else
            txt.setTextColor(colors.get("secondary"));

    }

    private void repeatToggle(LinearLayout viewFlipper, boolean isChecked) {
        if(isChecked)
        {
            viewFlipper.findViewById(R.id.nbr_repeat).setVisibility(View.VISIBLE);
            viewFlipper.findViewById(R.id.timeFrame_spinner).setVisibility(View.VISIBLE);
            viewFlipper.findViewById(R.id.start_date).setVisibility(View.VISIBLE);
        }
        else{
            viewFlipper.findViewById(R.id.nbr_repeat).setVisibility(View.GONE);
            viewFlipper.findViewById(R.id.timeFrame_spinner).setVisibility(View.GONE);
            viewFlipper.findViewById(R.id.start_date).setVisibility(View.GONE);
        }
    }

    private void changeStartTime(View view, int i) {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void updateAlarms() {
        scroll_lin.removeAllViewsInLayout();

        Collections.sort(alarm_list);

        for (int i = 0; i < alarm_list.size(); i++) {
            LinearLayout viewFlipper = (LinearLayout) inflater.inflate(R.layout.indv_alarm_layout, null);
            setAlarm(viewFlipper, i);
            activation(viewFlipper, alarm_list.get(i).isOn());
            viewFlipper.setOnClickListener(this::toggleFlipper);
            scroll_lin.addView(viewFlipper);
        }

        ImageButton add = (ImageButton) inflater.inflate(R.layout.add_button, null);
        add.setOnClickListener(this::newAlarm);

        scroll_lin.addView(add);
    }

    private void newAlarm(View view) {
        new TimePickerDialog
                (scroll_lin.getContext(),
                        (view1, hour, minute) -> {
                            indv_Alarm a = new indv_Alarm.alarmBuilder()
                                    .hour(hour)
                                    .min(minute)
                                    .on(true)
                                    .build();
                            alarm_list.add(a);

                            updateAlarms();
                        },
                        8, 0,
                        DateFormat.is24HourFormat(view.getContext()))
                .show();
    }

    private void changeAlarmTime(View view, int pos) {
        new TimePickerDialog
                (scroll_lin.getContext(),
                        (vi, hour, minute) -> {
                            indv_Alarm a = alarm_list.get(pos);
                            a.setHour(hour);
                            a.setMin(minute);
                            updateAlarms();
                        },
                        alarm_list.get(pos).getHour(),
                        alarm_list.get(pos).getMin(),
                        DateFormat.is24HourFormat(view.getContext()))
                .show();
    }

    private void deleteAlarm(int pos) {
        alarm_list.remove(pos);
        updateAlarms();
    }

    @Override
    public void onItemSelected (AdapterView < ? > adapterView, View view,int i, long l){
        RelativeLayout r = (RelativeLayout) adapterView.getParent();
        SwitchCompat s = r.findViewById(R.id.toggle_adv);

        if (s.isChecked()) {
            TextView v = (TextView) view;
            v.setTextColor(colors.get("primary"));
        } else {
            TextView v = (TextView) view;
            v.setTextColor(colors.get("secondary"));
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }

}

    class indv_Alarm implements Comparable<indv_Alarm> {
        private int hour, min, ringtone, nrbRepeat, timeFrame, startDay, startMonth, startYear;
        private boolean on, vibrate, repeat;
        private final String label;

        public indv_Alarm(alarmBuilder builder) {
            hour = builder.hour;
            min = builder.min;
            on = builder.on;
            ringtone = builder.ringtone;
            vibrate = builder.vibrate;
            repeat = builder.repeat;
            label = builder.label;
            nrbRepeat = builder.nrbRepeat;
            timeFrame = builder.timeFrame;
            startDay = builder.startDay;
            startMonth = builder.startMonth;
            startYear = builder.startYear;
        }

        public int getHour() {
            return hour;
        }

        public void setHour(int hour) {
            this.hour = hour;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public int getRingtone() {
            return ringtone;
        }

        public void setRingtone(int ringtone) {
            this.ringtone = ringtone;
        }

        public int getNrbRepeat() {
            return nrbRepeat;
        }

        public void setNrbRepeat(int nrbRepeat) {
            this.nrbRepeat = nrbRepeat;
        }

        public int getTimeFrame() {
            return timeFrame;
        }

        public void setTimeFrame(int timeFrame) {
            this.timeFrame = timeFrame;
        }

        public int getStartDay() {
            return startDay;
        }

        public void setStartDay(int startDay) {
            this.startDay = startDay;
        }

        public int getStartMonth() {
            return startMonth;
        }

        public void setStartMonth(int startMonth) {
            this.startMonth = startMonth;
        }

        public int getStartYear() {
            return startYear;
        }

        public void setStartYear(int startYear) {
            this.startYear = startYear;
        }

        public boolean isOn() {
            return on;
        }

        public void setOn(boolean on) {
            this.on = on;
        }

        public boolean isVibrate() {
            return vibrate;
        }

        public void setVibrate(boolean vibrate) {
            this.vibrate = vibrate;
        }

        public boolean isRepeat() {
            return repeat;
        }

        public void setRepeat(boolean repeat) {
            this.repeat = repeat;
        }

        public String getLabel() {
            return label;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public int compareTo(indv_Alarm autre) {
            return Comparator.comparing(indv_Alarm::getHour)
                    .thenComparing(indv_Alarm::getMin)
                    .compare(this, autre);
        }

        public String to_string() {
            String s = "";
            s += hour + "&";
            s += min + "&";
            s += on + "&";
            s += ringtone + "&";
            s += vibrate + "&";
            s += repeat + "&";
            s += label + "&";
            s += nrbRepeat + "&";
            s += timeFrame + "&";
            s += startDay + "&";
            s += startMonth + "&";
            s += startYear + "&";
            return s;
        }

        public static class alarmBuilder {
            private int hour = 8;
            private int min = 0;
            private boolean on = false;
            private int ringtone = 0;
            private boolean vibrate = false;
            private boolean repeat = false;
            private String label = "";
            private int nrbRepeat = 0;
            private int timeFrame = 0;
            private int startDay = Calendar.getInstance().getTime().getDay();
            private int startMonth = Calendar.getInstance().getTime().getMonth();
            private int startYear = Calendar.getInstance().getTime().getYear();


            public alarmBuilder() { }

            public alarmBuilder(String s) {
                String[] sArray = s.split("&");
                hour = Integer.parseInt(sArray[0]);
                min = Integer.parseInt(sArray[1]);
                on = Boolean.parseBoolean(sArray[2]);
                ringtone = Integer.parseInt(sArray[3]);
                vibrate = Boolean.parseBoolean(sArray[4]);
                repeat = Boolean.parseBoolean(sArray[5]);
                label = sArray[6];
                nrbRepeat = Integer.parseInt(sArray[7]);
                timeFrame = Integer.parseInt(sArray[8]);
                startDay = Integer.parseInt(sArray[9]);
                startMonth = Integer.parseInt(sArray[10]);
                startYear = Integer.parseInt(sArray[11]);
            }

            public alarmBuilder hour(int hour) { this.hour = hour; return this; }

            public alarmBuilder min(int min) { this.min = min; return this; }

            public alarmBuilder on(boolean on) { this.on = on; return this; }

            public alarmBuilder ringtone(int ringtone) { this.ringtone = ringtone; return this; }

            public alarmBuilder vibrate(boolean vibrate) { this.vibrate = vibrate; return this; }

            public alarmBuilder repeat(boolean repeat) { this.repeat = repeat; return this; }

            public alarmBuilder label(String label) { this.label = label; return this; }

            public alarmBuilder nrbRepeat(int nrbRepeat) { this.nrbRepeat = nrbRepeat; return this; }

            public alarmBuilder timeFrame(int timeFrame) { this.timeFrame = timeFrame; return this; }

            public alarmBuilder startDay(int startDay) { this.startDay = startDay; return this; }

            public alarmBuilder startMonth(int startMonth) { this.startMonth = startMonth; return this; }

            public alarmBuilder startYear(int startYear) { this.startYear = startYear; return this; }

            public indv_Alarm build() {
                return new indv_Alarm(this);
            }
        }
    }
