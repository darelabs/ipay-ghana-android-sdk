**iPay Android SDK Implementation**

This README file describes in details how to import the Android SDK for receiving payment into your Mobile App Project

---

### STEP 1

To install the iPay SDK into your project, you must first add it as a gradle dependency.

Place this in your **build.gradle** file,

```java
implementation 'com.github.darelabs:ipay-ghana-android-sdk:v0.1'
```

under the dependency object to install the SDK. Kindly build after you add the dependency.

---

### STEP 2

Call the payment object and fill in the necessary details as show below:
1. Make sure this is in the **onCreate** method.
2. Secondly fill in the Payment objects details.
3. When putting the Payment object in the bundle object, name the key as **payment**. E.g **bundle.putSerializable(“payment”, Object);**

**Required fields:**
1. Merchant Key
2. Invoice Id
3. Amount


```java
@Override

protected void onCreate(Bundle savedInstanceState) {

	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	Payment p = new Payment();
	p.setMerchantKey("123456");
	p.setInvoiceId("IPAYTEST001");
	P.setAmount(0.5);
	Bundle bundle = new Bundle();
	bundle.putSerializable("payment", p);
	startActivity(new Intent( packageContext: this, ipay.gh.com.ipayandroidsdk.PaymentActivity.class).putExtras(bundle));
	
}
```

### Note:
***This will start an activity where you are required to select the network type and the phone number needed to process the transaction.   So it is not required to add the phone number to the Payment object.***

---


### I do not have a Merchant key. How do I get one?
Visit our [get started](https://manage.ipaygh.com/xmanage/get-started) page and sign up with the easy steps provided.

### Do I need to pay something before opening an account?
A BIG no.
You don't have to pay anything in order to set up your merchant account; it's absolutely free.
