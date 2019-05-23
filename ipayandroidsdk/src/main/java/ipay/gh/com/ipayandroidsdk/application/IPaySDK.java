package ipay.gh.com.ipayandroidsdk.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import ipay.gh.com.ipayandroidsdk.BuildConfig;
import ipay.gh.com.ipayandroidsdk.utils.IPayUtils;

public final class IPaySDK extends Application {
    /**
     * Value of the version code of this SDK
     */
    public static final int VERSION_CODE = BuildConfig.VERSION_CODE;
    /**
     * The applications context which is important for application initialization
     */

    @Override
    public void onCreate() {
        super.onCreate( );
        IPayUtils.validate.hasInternetPermission(getApplicationContext());
        try {
            Intent intent = new Intent(getApplicationContext(),
                    Class.forName("ipay.gh.com.ipaysdkandroid.PaymentActivity"));
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
