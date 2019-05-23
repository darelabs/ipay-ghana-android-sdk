package ipay.gh.com.ipaysdkandroid.utils;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyUtilities {

    private String command;
    private Activity activity;

    public KeyUtilities(String command, Activity activity){
        this.command = command;
        this.activity = activity;
    }

    public void hideKeyboard(){
        /**
         * Hides the keyboard when called.
         */
        View v = activity.getCurrentFocus();
        if(v != null) {
            InputMethodManager im = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    public void showKeyboard(){
        /**
         * Shows the keyboard when called.
         * But not needed now in this application
         * because the widgets calls the keyboard on its own
         */
    }
}
