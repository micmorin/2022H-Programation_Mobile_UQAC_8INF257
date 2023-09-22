package uqac.dim.alarmesandroidnextgeneration;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import java.util.Locale;

public class StopWatchClass extends Fragment {
    private  View v = null;
    protected  ImageButton playButton = null;
    private boolean stopwatch_started = false;
    private  int time = 0;
    private long mStartTime = 0L;

    public StopWatchClass(){super();}

    @Override
    public void onResume() {
        super.onResume();
        drawStopWatch();
        if(stopwatch_started)
            startChron();
    }

    @Override
    public void onPause() {
        boolean temp = stopwatch_started;
        stopChron();
        stopwatch_started = temp;
        super.onPause();
    }

    @Override
    public void onStop() {
        stopChron();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        stopChron();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        stopChron();
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        stopChron();
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putInt("time", time);
        savedInstanceState.putBoolean("stopwatch_started", stopwatch_started);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.stopwatch_layout, container, false);

        MainActivity.runner = new Runnable() {
            public void run() {
                time = time + 7;
                showTime();
                MainActivity.handler.postDelayed(this,112);
            }
        };

        playButton = v.findViewById(R.id.stopwatch_start);
        playButton.setOnClickListener(new stopwatchToggle());

        Button bu = v.findViewById(R.id.stopwatch_reset);
        bu.setOnClickListener(view -> {
            time= 0;
            mStartTime = 0L;
            showTime();
        });

        if (savedInstanceState != null) {
            time = savedInstanceState.getInt("time");
            stopwatch_started = savedInstanceState.getBoolean("stopwatch_started");
        }

        drawStopWatch();

        return v;
    }



    private void showTime(){
        TextView timeView = v.findViewById(R.id.stopwatch_h);

        int hours = time / 3600;
        int minutes = (time % 3600) / 60;
        int secs = time % 60;

        String timeString = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs);
        timeView.setText(timeString);
    }

    private void startChron(){
        playButton.setImageResource(R.drawable.ic_play_pause);
        DrawableCompat.setTint(((ImageButton)v.findViewById(R.id.stopwatch_start)).getDrawable(), colors.get("primary"));
        stopwatch_started = true;
        if (mStartTime == 0L)
            mStartTime = System.currentTimeMillis();

        MainActivity.handler.removeCallbacks(MainActivity.runner);
        MainActivity.handler.postDelayed(MainActivity.runner, 100);
    }

    private void stopChron(){
        MainActivity.handler.removeCallbacks(MainActivity.runner);
        playButton.setImageResource(R.drawable.ic_play_arrow);
        DrawableCompat.setTint(((ImageButton)v.findViewById(R.id.stopwatch_start)).getDrawable(), colors.get("primary"));
        stopwatch_started = false;
    }

    public void drawStopWatch(){
        RelativeLayout r = (RelativeLayout) v;
        DrawableCompat.setTint(r.getChildAt(0).getBackground(), colors.get("secondary"));
        ((TextView) r.findViewById(R.id.stopwatch_h)).setTextColor(colors.get("secondary"));
        ((TextView) r.findViewById(R.id.stopwatch_msec)).setTextColor(colors.get("secondary"));
        ((TextView) r.findViewById(R.id.stopwatch_reset)).setTextColor(colors.get("secondary"));
        DrawableCompat.setTint(r.findViewById(R.id.stopwatch_start).getBackground(), colors.get("secondary"));
        DrawableCompat.setTint(((ImageButton)r.findViewById(R.id.stopwatch_start)).getDrawable(), colors.get("primary"));
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