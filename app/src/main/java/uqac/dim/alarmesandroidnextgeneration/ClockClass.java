package uqac.dim.alarmesandroidnextgeneration;

import static uqac.dim.alarmesandroidnextgeneration.MainActivity.colors;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextClock;

import androidx.fragment.app.Fragment;

public class ClockClass extends Fragment {
    RelativeLayout r = null;

    @Override
    public void onResume() {
        super.onResume();
        drawClock();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        r = (RelativeLayout) inflater.inflate(R.layout.clock_layout, container, false);
        drawClock();
        return r;
    }

    private void drawClock(){
        ((TextClock)r.findViewById(R.id.hourClock)).setTextColor(colors.get("primary"));
        ((TextClock)r.findViewById(R.id.textClock)).setTextColor(colors.get("secondary"));
    }
}
