package dream.logsys.com.logsysdream;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import logsys.dream.com.mx.activities.LoginActivity;
import logsys.dream.com.mx.activities.MainActivity;

public class Monitor extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

    }
}