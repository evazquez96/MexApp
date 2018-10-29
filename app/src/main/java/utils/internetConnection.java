package utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ADGARCIA on 06/03/2018.
 */

public class internetConnection {
    Context mContext;
    public internetConnection(Context mContext) {
        this.mContext = mContext;
    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
}
