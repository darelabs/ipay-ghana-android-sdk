package ipay.gh.com.ipayandroidsdk.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import ipay.gh.com.ipayandroidsdk.BuildConfig;
import ipay.gh.com.ipayandroidsdk.utils.IPayUtils;

public final class IPaySDK extends Application {

    @Override
    public void onCreate() {
        super.onCreate( );
        Log.v(getClass().getName(), "Starting SDK Module");
        IPayUtils.validate.hasInternetPermission(getApplicationContext());
        try {
            Intent intent = new Intent(getApplicationContext(),
                    Class.forName("ipay.gh.com.ipayandroidsdk.PaymentActivity"));
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
