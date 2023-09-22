package uqac.dim.alarmesandroidnextgeneration;

import static java.lang.Math.abs;

import static uqac.dim.alarmesandroidnextgeneration.MainActivity.colors;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Locale;

public class TimerClass extends Fragment {
    private  View v = null;
    private final timeContainer timeInit = new timeContainer();
    private final timeContainer timeCourrant = new timeContainer();
    protected  ImageButton playButton = null;
    private boolean stopwatch_started = false;

    @Override
    public void onResume() {
        super.onResume();
        drawTimer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
         v = inflater.inflate(R.layout.timer_layout, container, false);
         init();
        drawTimer();
        return v;
    }

    private void init(){
        TextView txt = v.findViewById(R.id.timer_0);
        txt.setOnClickListener(view -> { timeInit.add(0); displayTime(); });

        txt = v.findViewById(R.id.timer_1);
        txt.setOnClickListener(view -> { timeInit.add(1); displayTime(); });

        txt = v.findViewById(R.id.timer_2);
        txt.setOnClickListener(view -> { timeInit.add(2); displayTime(); });

        txt = v.findViewById(R.id.timer_3);
        txt.setOnClickListener(view -> { timeInit.add(3); displayTime(); });

        txt = v.findViewById(R.id.timer_4);
        txt.setOnClickListener(view -> { timeInit.add(4); displayTime(); });

        txt = v.findViewById(R.id.timer_5);
        txt.setOnClickListener(view -> { timeInit.add(5); displayTime(); });

        txt = v.findViewById(R.id.timer_6);
        txt.setOnClickListener(view -> { timeInit.add(6); displayTime(); });

        txt = v.findViewById(R.id.timer_7);
        txt.setOnClickListener(view -> { timeInit.add(7); displayTime(); });

        txt = v.findViewById(R.id.timer_8);
        txt.setOnClickListener(view -> { timeInit.add(8); displayTime(); });

        txt = v.findViewById(R.id.timer_9);
        txt.setOnClickListener(view -> { timeInit.add(9); displayTime(); });

        ImageButton b = v.findViewById(R.id.timer_erase);
        b.setOnClickListener(view -> { timeInit.erase(); displayTime(); });

        b = v.findViewById(R.id.timer_start);
        b.setOnClickListener(view -> {
            timeCourrant.setTime(timeInit.getTime());
            showTime();
            ViewFlipper flipper = v.findViewById(R.id.viewFlipper);
            flipper.showNext();
            startChron();
        });

        Button bu = v.findViewById(R.id.timer_reset);
        bu.setOnClickListener(view -> { timeCourrant.reset(); displayTime(); });

        bu = v.findViewById(R.id.timer_reset_init);
        bu.setOnClickListener(view -> { timeCourrant.setTime(timeInit.getTime()); showTime(); });

        bu = v.findViewById(R.id.timer_delete);
        bu.setOnClickListener(view -> {
            ViewFlipper flipper = v.findViewById(R.id.viewFlipper);
            flipper.showPrevious();
        });

        MainActivity.runnerTimer = new Runnable() {
            public void run() {
                timeCourrant.decrease();
                showTime();
                MainActivity.handler.postDelayed(this,1000);
            }
        };

        playButton = v.findViewById(R.id.timer_start_init);
        playButton.setOnClickListener(new stopwatchToggle());
    }

    private void drawTimer(){
        DrawableCompat.setTint(
                ((RelativeLayout)((ViewFlipper) v.findViewById(R.id.viewFlipper)).getChildAt(1))
                        .getChildAt(0).getBackground()
                , colors.get("secondary"));
        v.findViewById(R.id.separator).setBackgroundColor(colors.get("primary"));
        drawText();
        drawButton();
    }

    private void drawText(){
        TextView txt = v.findViewById(R.id.timer_0);
        txt.setTextColor(colors.get("secondary"));

        txt = v.findViewById(R.id.timer_1);
        txt.setTextColor(colors.get("secondary"));

        txt = v.findViewById(R.id.timer_2);
        txt.setTextColor(colors.get("secondary"));

        txt = v.findViewById(R.id.timer_3);
        txt.setTextColor(colors.get("secondary"));

        txt = v.findViewById(R.id.timer_4);
        txt.setTextColor(colors.get("secondary"));

        txt = v.findViewById(R.id.timer_5);
        txt.setTextColor(colors.get("secondary"));

        txt = v.findViewById(R.id.timer_6);
        txt.setTextColor(colors.get("secondary"));

        txt = v.findViewById(R.id.timer_7);
        txt.setTextColor(colors.get("secondary"));

        txt = v.findViewById(R.id.timer_8);
        txt.setTextColor(colors.get("secondary"));

        txt = v.findViewById(R.id.timer_9);
        txt.setTextColor(colors.get("secondary"));

        txt = v.findViewById(R.id.timer_hour);
        txt.setTextColor(colors.get("secondary"));

        txt = v.findViewById(R.id.timer_hour_label);
        txt.setTextColor(colors.get("secondary"));

        txt = v.findViewById(R.id.timer_min);
        txt.setTextColor(colors.get("secondary"));

        txt = v.findViewById(R.id.timer_min_label);
        txt.setTextColor(colors.get("secondary"));

        txt = v.findViewById(R.id.timer_sec);
        txt.setTextColor(colors.get("secondary"));

        txt = v.findViewById(R.id.timer_sec_label);
        txt.setTextColor(colors.get("secondary"));

        txt = v.findViewById(R.id.timer_h);
        txt.setTextColor(colors.get("primary"));
    }

    private void drawButton(){
        Button b;
        ImageButton ib;

        b = v.findViewById(R.id.timer_reset);
        b.setTextColor(colors.get("secondary"));

        b = v.findViewById(R.id.timer_reset_init);
        b.setTextColor(colors.get("secondary"));

        b = v.findViewById(R.id.timer_delete);
        b.setTextColor(colors.get("secondary"));

        ib = v.findViewById(R.id.timer_start_init);
        DrawableCompat.setTint(ib.getBackground(), colors.get("secondary"));
        DrawableCompat.setTint(ib.getDrawable(), colors.get("primary"));

        ib = v.findViewById(R.id.timer_start);
        DrawableCompat.setTint(ib.getBackground(), colors.get("secondary"));
        DrawableCompat.setTint(ib.getDrawable(), colors.get("primary"));

        ib = v.findViewById(R.id.timer_erase);
        DrawableCompat.setTint(ib.getDrawable(), colors.get("secondary"));
    }

    private void displayTime(){
        TextView txt = v.findViewById(R.id.timer_hour);
        txt.setText(String.format(Locale.getDefault(), "%02d", -0));
        txt.setText(String.format(Locale.getDefault(), "%02d", timeInit.getHour()));

        txt = v.findViewById(R.id.timer_min);
        txt.setText(String.format(Locale.getDefault(), "%02d", timeInit.getMin()));

        txt = v.findViewById(R.id.timer_sec);
        txt.setText(String.format(Locale.getDefault(), "%02d", abs(timeInit.getSec())));

    }

    private void showTime(){
        TextView timeView = v.findViewById(R.id.timer_h);

        String timeString;
        if(timeCourrant.getSec() < 0) {
            timeString = String.format(Locale.getDefault(), "-%02d:%02d:%02d", timeCourrant.getHour(),timeCourrant.getMin(), abs(timeCourrant.getSec()));
        }
        else{
            timeString = String.format(Locale.getDefault(), "%02d:%02d:%02d", timeCourrant.getHour(), timeCourrant.getMin(), timeCourrant.getSec());
        }
        timeView.setText(timeString);
    }

    private void startChron(){
        playButton.setImageResource(R.drawable.ic_play_pause);
        DrawableCompat.setTint(playButton.getDrawable(), colors.get("primary"));
        stopwatch_started = true;

        MainActivity.handler.removeCallbacks(MainActivity.runnerTimer);
        MainActivity.handler.postDelayed(MainActivity.runnerTimer, 100);
    }

    public void stopChron(){
        MainActivity.handler.removeCallbacks(MainActivity.runnerTimer);
        playButton.setImageResource(R.drawable.ic_play_arrow);
        DrawableCompat.setTint(playButton.getDrawable(), colors.get("primary"));

        stopwatch_started = false;
    }

    static class timeContainer{
        long time = 0;

        public void add(int i){
            time = time * 10 + i;
            time = time % 1000000;
        }

        public int getHour(){
            return (int) time / 10000;
        }

        public int getMin(){
            return (int) time % 10000 / 100;
        }

        public int getSec(){
            return (int) time % 100;
        }

        public void erase() {
            time = time /10;
        }

        public void reset() {
            time = 0;
        }

        public void decrease() {
            time--;
        }

        public void setTime(long i){
            time = i;
        }

        public long getTime(){
            return time;
        }
    }

    private class stopwatchToggle implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if(stopwatch_started){
                stopChron();
            }
            else {
                startChron();
            }
        }
    }

}
