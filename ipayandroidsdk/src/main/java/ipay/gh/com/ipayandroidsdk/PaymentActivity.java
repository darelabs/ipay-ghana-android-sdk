package ipay.gh.com.ipayandroidsdk;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.sdsmdg.tastytoast.TastyToast;

import org.angmarch.views.NiceSpinner;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import ipay.gh.com.ipayandroidsdk.models.Payment;


public class PaymentActivity extends AppCompatActivity {

    private ProgressDialog pg;
    private String invoice = "";
    private LinearLayout layout;
    private LinearLayout layout1;
    private String networkName = "";
    TextView confirmationText;
    NiceSpinner niceSpinner;
    private Payment payment;
    EditText mobileNumber;
    EditText voucherCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
        getWindow( ).setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        pg = new ProgressDialog(this);
        /*
         * Receive payment object
         */
        payment = (Payment) getIntent().getSerializableExtra("payment");

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
        SimpleDateFormat timeFormat = new SimpleDateFormat("hhmmss", Locale.ENGLISH);
        AndroidNetworking.initialize(getApplicationContext( ));
        Typeface font = Typeface.createFromAsset(getAssets( ), "fonts/Montserrat-Medium.ttf");
        mobileNumber = findViewById(R.id.mobileNumber);
        voucherCode = findViewById(R.id.voucherCode);
        confirmationText = findViewById(R.id.confirmation_txt);
        final Button confirmPayment = findViewById(R.id.confirm_payout);
        final TextView checkoutText = findViewById(R.id.checkout);
        layout = findViewById(R.id.linearLayout);
        layout1 = findViewById(R.id.linearLayout1);
        layout1.setVisibility(View.INVISIBLE);
        voucherCode.setVisibility(View.INVISIBLE);
        final Button makePayment = findViewById(R.id.proceed);
        mobileNumber.setTypeface(font);
        makePayment.setTypeface(font);
        confirmationText.setTypeface(font);
        confirmPayment.setTypeface(font);
        checkoutText.setTypeface(font);
        niceSpinner = findViewById(R.id.nice_spinner);
        niceSpinner.setTypeface(font);
        voucherCode.setTypeface(font);
        final List<String> dataset = new LinkedList<>(Arrays.asList("MTN Money", "Vodafone Cash", "Tigo Cash", "Airtel Money", "Visa / Mastercard"));
        niceSpinner.attachDataSource(dataset);

        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener( ) {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (dataset.get(position)) {
                    case "Vodafone Cash":
                        voucherCode.setVisibility(View.VISIBLE);
                        networkName = "vodafone";
                        break;
                    case "MTN Money":
                        voucherCode.setVisibility(View.INVISIBLE);
                        networkName = "mtn";
                        break;
                    case "Tigo Cash":
                        voucherCode.setVisibility(View.INVISIBLE);
                        networkName = "tigo";
                        break;
                    case "Airtel Money":
                        voucherCode.setVisibility(View.INVISIBLE);
                        networkName = "airtel";
                        break;
                    case "Visa / Mastercard":
                        voucherCode.setVisibility(View.INVISIBLE);
                        networkName = "visa";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        makePayment.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                /*
                 * Hides the keyboard when the user clicks the make payment button.
                 */
                //KeyUtilities ku = new KeyUtilities("close", PaymentActivity.this);
                //ku.hideKeyboard();

                if (mobileNumber.getText( ).toString( ).isEmpty( )) {
                        TastyToast.makeText(getApplicationContext( ), "Mobile Number cannot be empty.", TastyToast.LENGTH_LONG, TastyToast.ERROR).setGravity(Gravity.TOP, 0, 0);
                } else {
                    if (mobileNumber.getText().toString().startsWith("0") && mobileNumber.getText( ).length( ) < 10) {
                        TastyToast.makeText(getApplicationContext(), "Mobile Number cannot be less than 10 characters long.", TastyToast.LENGTH_LONG, TastyToast.ERROR).setGravity(Gravity.TOP, 0, 0);
                    } else if (mobileNumber.getText().toString().startsWith("2") && mobileNumber.getText( ).length( ) > 12) {
                        TastyToast.makeText(getApplicationContext(), "Mobile Number cannot be more than 12 characters long.", TastyToast.LENGTH_LONG, TastyToast.ERROR).setGravity(Gravity.TOP, 0, 0);
                    } else if (voucherCode.getVisibility( ) == View.VISIBLE) {
                        if (voucherCode.getText().length() < 6) {
                            TastyToast.makeText(getApplicationContext(), "Voucher code cannot be less than 6 characters long.", TastyToast.LENGTH_LONG, TastyToast.ERROR).setGravity(Gravity.TOP, 0, 0);
                        } else if (voucherCode.getText().length() >= 6 && mobileNumber.getText( ).length( ) < 10) {
                            TastyToast.makeText(getApplicationContext(), "Mobile Number cannot be less than 10 characters long.", TastyToast.LENGTH_LONG, TastyToast.ERROR).setGravity(Gravity.TOP, 0, 0);
                        } else {
                            makePayment(payment);
                        }
                    } else {
                        makePayment(payment);
                    }
                }
            }
        });

        confirmPayment.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                pg.show( );
                new Timer( ).schedule(new TimerTask( ) {
                    @Override
                    public void run() {
                        AndroidNetworking.get("https://community.ipaygh.com/v1/gateway/json_status_chk")
                                .setPriority(Priority.MEDIUM)
                                .addQueryParameter("merchant_key", payment.getMerchantKey())
                                .addQueryParameter("invoice_id", payment.getInvoiceId())
                                .build( )
                                .getAsJSONObject(new JSONObjectRequestListener( ) {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        pg.cancel( );
                                        try {
                                            Log.v(getClass( ).getName( ), response.toString( ));
                                            Log.v(getClass( ).getName( ), response.getJSONObject(payment.getInvoiceId()).getString("status"));
                                            switch (response.getJSONObject(payment.getInvoiceId()).getString("status")) {
                                                case "awaiting_payment":
                                                    notifyMe("Awaiting Payment.", "warn");
                                                    break;
                                                case "failed":
                                                    notifyMe("Sorry, Payment Failed.", "danger");
                                                    break;
                                                case "paid":
                                                    notifyMe("Payment successful.", "success");
                                                    break;
                                                default:
                                                    notifyMe("Awaiting successful.", "warn");
                                                    break;
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace( );
                                        }
                                    }

                                    @Override
                                    public void onError(ANError anError) {
                                        pg.cancel( );
                                        Log.v(getClass( ).getName( ), anError.toString( ));
                                    }
                                });
                    }
                }, 2000);
            }
        });
    }

    @SuppressLint("CheckResult")
    public void notifyMe(String message, String type) {
        switch (type) {
            case "success":
                TastyToast.makeText(getApplicationContext(), message, TastyToast.LENGTH_LONG, TastyToast.SUCCESS).setGravity(Gravity.TOP, 0, 0);
                break;
            case "warn":
                TastyToast.makeText(getApplicationContext(), message, TastyToast.LENGTH_LONG, TastyToast.WARNING).setGravity(Gravity.TOP, 0, 0);
                break;
            case "danger":
                TastyToast.makeText(getApplicationContext(), message, TastyToast.LENGTH_LONG, TastyToast.ERROR).setGravity(Gravity.TOP, 0, 0);
                break;
        }
    }

    public void makePayment(final Payment payment) {
        pg.setMessage("Processing...");
        pg.show( );
        Log.v(getClass( ).getName( ), networkName);
        JSONObject obj = new JSONObject( );
        try {
            obj.put("merchant_key", payment.getMerchantKey());
            obj.put("invoice_id", payment.getInvoiceId());
            obj.put("total", payment.getAmount());
            obj.put("pymt_instrument", mobileNumber.getText().toString());
            obj.put("extra_mobile_no", payment.getExtraMobileNumber());
            obj.put("extra_wallet_issuer_hint", networkName);
            obj.put("voucher_code", voucherCode.getText().toString());
            Log.v(getClass().getName(), obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post("https://community.ipaygh.com/v1/mobile_agents_v2")
                .setContentType("application.json")
                .addJSONObjectBody(obj)
                .setPriority(Priority.MEDIUM)
                .build( )
                .getAsJSONObject(new JSONObjectRequestListener( ) {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONObject response) {
                        pg.cancel( );
                        try {
                            Log.v(getClass( ).getName( ), response.toString( ));
                            if (response.getString("message").equals("Merchant not allowed to process payments for this issuer.")) {
                                TastyToast.makeText(getApplicationContext( ), response.getString("message"), TastyToast.LENGTH_LONG, TastyToast.ERROR).setGravity(Gravity.TOP, 0, 0);
                            }
                            if (response.getString("success").equals("false")) {
                                TastyToast.makeText(getApplicationContext( ), response.getString("message"), TastyToast.LENGTH_LONG, TastyToast.ERROR).setGravity(Gravity.TOP, 0, 0);
                            } else {
                                String network = niceSpinner.getText( ).toString( );
                                switch (network) {
                                    case "Airtel Money":
                                        confirmationText.setText("Once you submit this form, we will attempt to pull GHS "+payment.getAmount()+" from your Airtel wallet");
                                        break;
                                    case "Vodafone Cash":
                                        confirmationText.setText("Once you submit this form, we will attempt to consume the voucher worth GHS "+payment.getAmount()+" generated from your Vodafone cash wallet.");
                                        break;
                                    case "Tigo Cash":
                                        confirmationText.setText("1. Payment instructions have been sent via SMS to the tigo number provided from Tigo Cash.\n 2. Dial *501*5# and complete transaction on your Tigo Cash phone by authorizing the payment.\n 3. Click on the Confirm button, once you're done.");
                                        break;
                                    case "MTN Money":
                                        confirmationText.setText("1. Dial *170# and Choose Wallet (Option 7) .\n 2. Choose My Approvals(Option 3).\n 3. Enter your MOMO pin to retrieve your pending approval list.\n 4. Choose the transaction to approve.\n 5. Click on the Confirm button, once you're done.");
                                        break;
                                    case "Visa/Mastercard":
                                        break;
                                    default:
                                        break;
                                }
                                layout.setVisibility(View.INVISIBLE);
                                layout1.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace( );
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        pg.cancel( );
                        Log.v(getClass( ).getName( ), anError.toString( ));
                    }
                });
    }
}
