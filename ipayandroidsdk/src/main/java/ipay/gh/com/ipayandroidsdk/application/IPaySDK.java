package ipay.gh.com.ipayandroidsdk.application;

import android.annotation.SuppressLint;
import android.content.Context;

import ipay.gh.com.ipayandroidsdk.BuildConfig;
import ipay.gh.com.ipayandroidsdk.utils.IPayUtils;

public final class IPaySDK {
    /**
     * Value of the version code of this SDK
     */
    public static final int VERSION_CODE = BuildConfig.VERSION_CODE;
    /**
     * The applications context which is important for application initialization
     */
    @SuppressLint("StaticFieldLeak")
    private static Context applicationContext;

    public static synchronized void initialise(Context context) {
        IPayUtils.validate.hasInternetPermission(context);
           IPaySDK.applicationContext = context;
    }

}
