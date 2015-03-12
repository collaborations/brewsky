package edu.uw.informatics.brewsky;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Robby on 3/11/2015.
 */
public class AlarmReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        /*
        * Launch alarmReceiverActivity with alert in it from here
        * http://stackoverflow.com/questions/16332049/how-to-setup-alertbox-from-broadcastreceiver
        * */
        Intent mIntent = new Intent(context,AlarmReceiverActivity.class); //Same as above two lines
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mIntent);
    }
}
