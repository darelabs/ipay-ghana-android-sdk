package ipay.gh.com.ipayandroidsdk.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Defines a blueprint for the applications validation methods
 */
public class IPayUtils {
    /**
     * Blueprint for the applications validation methods
     */
    public static class validate{
        /**
         *
         * @param context
         * Checks if the application has internet connection permission
         */
        public static void hasInternetPermission(Context context) {
            PackageManager pm = context.getPackageManager();
            int hasPermission = pm.checkPermission(Manifest.permission.INTERNET, context.getPackageName());
            if (hasPermission == PackageManager.PERMISSION_DENIED) {
                throw new IllegalStateException("IPay SDK requires internet permission, Please add internet permission to your manifest.xml file.");
            }
        }

      }
}
