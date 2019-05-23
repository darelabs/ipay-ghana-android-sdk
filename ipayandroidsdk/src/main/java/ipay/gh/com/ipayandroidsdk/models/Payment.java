package ipay.gh.com.ipayandroidsdk.models;


import java.io.Serializable;

public class Payment implements Serializable {

    private String merchantKey;
    private String invoiceId;
    private double amount;
    private String mobileNumber;
    private String extraMobileNumber;
    private String networkName;
    private String voucherCode;
    private String paymentDescription;

    public String getMerchantKey() {
        return merchantKey;
    }

    public void setMerchantKey(String merchantKey) {
        this.merchantKey = merchantKey;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getExtraMobileNumber() {
        return extraMobileNumber;
    }

    public void setExtraMobileNumber(String extraMobileNumber) {
        this.extraMobileNumber = extraMobileNumber;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }
}
