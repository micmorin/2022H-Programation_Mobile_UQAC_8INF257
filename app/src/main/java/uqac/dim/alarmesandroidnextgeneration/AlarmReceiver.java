package uqac.dim.alarmesandroidnextgeneration;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ((Intent.ACTION_BOOT_COMPLETED).equals(intent.getAction())) {
            // populate alarms from FILE

        } else {
            String s = intent.getExtras().getString("label");
            Toast.makeText(context, s, Toast.LENGTH_LONG).show();
        }

    }
}
