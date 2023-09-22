package uqac.dim.alarmesandroidnextgeneration;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.tabs.TabLayout;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {
    // tab titles
    private final int[] titles = new int[]{R.string.header_alarm, R.string.header_clock, R.string.header_timer, R.string.header_stopwatch};
    private final int[] icons  = new int[]{R.drawable.ic_alarm, R.drawable.ic_clock, R.drawable.ic_timer, R.drawable.ic_stopwatch};
    public static final Handler handler = new Handler();
    public static Runnable runner;
    public static Runnable runnerTimer;
    public static final int[] colorsDefault = {0xFF3E6990, 0xFFAABD8C, 0xFF381D2A, 0xFFE9E3B4};
    public static Hashtable<String, Integer> colors = new Hashtable<String, Integer>();
    static public File fichier = null;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fichier = new File(getApplicationContext().getExternalFilesDir(null),"save.txt");
        try {
            Vector<String> vs = new Vector<>();
            int length = (int) fichier.length();

            byte[] bytes = new byte[length];

            FileInputStream in = new FileInputStream(fichier);
            try {
                in.read(bytes);
            } finally {
                in.close();
            }

            String contents = new String(bytes);

            if(!vs.isEmpty()) {
                colors.put("primary", Integer.parseInt(vs.get(0)));
                colors.put("secondary", Integer.parseInt(vs.get(1)));
                colors.put("background", Integer.parseInt(vs.get(2)));
                colors.put("text", Integer.parseInt(vs.get(3)));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);
        initViewpager2();
        initDrawer();
        drawInterface();
    }

    private void initViewpager2() {
        ViewPager2 viewPager = findViewById(R.id.pager);
        TabLayout tabLayout = findViewById(R.id.header_tabs);

        viewPager.setAdapter(new ViewPagerFragmentAdapter(this));

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                handler.removeCallbacks(runner);
                handler.removeCallbacks(runnerTimer);
                super.onPageSelected(position);

                getSupportFragmentManager().getFragments().get(0).onResume();
            }

        });

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    tab.setText(getResources().getString(titles[position]));
                    tab.setIcon(icons[position]);
                }
                ).attach();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initDrawer(){
        ImageButton menu;
        ImageButton b;

        menu= findViewById(R.id.menu_open);
        menu.setOnClickListener(view -> ((DrawerLayout) findViewById(R.id.drawer)).openDrawer(GravityCompat.END));
        menu= findViewById(R.id.menu_close);
        menu.setOnClickListener(view -> ((DrawerLayout) findViewById(R.id.drawer)).closeDrawer(GravityCompat.END));

        b = findViewById(R.id.primary_color_button);
        b.setOnClickListener(view->colorPicker(view,0));
        b = findViewById(R.id.secondary_color_button);
        b.setOnClickListener(view->colorPicker(view,1));
        b = findViewById(R.id.back_color_button);
        b.setOnClickListener(view->colorPicker(view,2));
        b = findViewById(R.id.secondary_Back_color_button);
        b.setOnClickListener(view->colorPicker(view,3));

        Button bu = findViewById(R.id.save);
        bu.setOnClickListener(view->setColors());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setColors() {
        colors.replace("primary",((ColorDrawable) findViewById(R.id.primary_color_button).getBackground()).getColor());
        colors.replace("secondary",((ColorDrawable) findViewById(R.id.secondary_color_button).getBackground()).getColor());
        colors.replace("background",((ColorDrawable) findViewById(R.id.back_color_button).getBackground()).getColor());
        colors.replace("text",((ColorDrawable) findViewById(R.id.secondary_Back_color_button).getBackground()).getColor());
        drawInterface();
        drawFragments();
    }

    private void drawInterface(){
        if(colors.isEmpty()){
            colors.put("primary", colorsDefault[0]);
            colors.put("secondary", colorsDefault[1]);
            colors.put("background", colorsDefault[2]);
            colors.put("text", colorsDefault[3]);
        }
        drawStatusBar();
        drawHeaderAndPager();
        drawTabs();
        drawDrawer();
    }

    private void drawStatusBar(){
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(colors.get("primary"));
    }

    private void drawHeaderAndPager(){
        ((RelativeLayout) findViewById(R.id.header_title).getParent()).setBackgroundColor(colors.get("primary"));
        ((TextView) findViewById(R.id.header_title)).setTextColor(colors.get("text"));
        ((ImageButton) findViewById(R.id.menu_open)).setColorFilter(colors.get("text"));
        findViewById(R.id.pager).setBackgroundColor(colors.get("background"));
    }

    private void drawTabs(){
        TabLayout tabs = findViewById(R.id.header_tabs);
        tabs.setBackgroundColor(colors.get("primary"));
        tabs.setSelectedTabIndicatorColor(colors.get("text"));
        tabs.setTabTextColors(colors.get("secondary"),colors.get("text"));
        tabs.setTabIconTint(tabs.getTabTextColors());
    }

    private void drawDrawer(){
        ((Button)findViewById(R.id.connect)).setTextColor(colors.get("primary"));
        findViewById(R.id.connect).setBackgroundColor(colors.get("secondary"));
        findViewById(R.id.separator_drawer).setBackgroundColor(colors.get("secondary"));
        findViewById(R.id.primary_color_button).setBackgroundColor(colors.get("primary"));
        findViewById(R.id.secondary_color_button).setBackgroundColor(colors.get("secondary"));
        findViewById(R.id.back_color_button).setBackgroundColor(colors.get("background"));
        findViewById(R.id.secondary_Back_color_button).setBackgroundColor(colors.get("text"));
        ((Button)findViewById(R.id.save)).setTextColor(colors.get("primary"));
        findViewById(R.id.save).setBackgroundColor(colors.get("secondary"));
    }

    private void drawFragments(){
        for ( int i=0; i < getSupportFragmentManager().getFragments().size(); i++) {
            getSupportFragmentManager().getFragments().get(i).onResume();
        }
    }

    private void colorPicker(View view, int i){
        new AmbilWarnaDialog(this, colorsDefault[i],
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) { }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        view.setBackgroundColor(color);
                    }
                }).show();
    }

    private static class ViewPagerFragmentAdapter extends FragmentStateAdapter {

        public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new AlarmClass();
                case 1:
                    return new ClockClass();
                case 2:
                    return new TimerClass();
                case 3:
                    return new StopWatchClass();
            }
            return new AlarmClass();
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }
}