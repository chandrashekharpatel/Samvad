package com.cssoftwaretech.samvad;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.cssoftwaretech.samvad.style.MessageDialog.exceptionAlert;
import static com.cssoftwaretech.samvad.style.MessageDialog.messAlert;

public class Fees extends AppCompatActivity {
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_STUDENT_FES = "studentFeesList";
    private static final String TAG_NAME = "st_name";
    private static final String TAG_CLASS = "class_name";
    private static final String TAG_TOTAL_AMT = "fee_total_amt";
    private static final String TAG_PAY_DATE = "fee_pay_date";
    private static final String TAG_REMARK = "fee_remark";
    private static final String TAG_DEPOSIT_AMT = "fee_deposit_amt";
    private static final String urlGetStudents = "http://192.168.197.1/samvad/getFeesDetails.php";
    static Context context;
    private static ProgressDialog pDialog;
    private static TextView tvStdName, tvStdClass, tvTotalFees, tvLastPay, tvDueAmt, tvRemark;
    JSONParser jParser = new JSONParser();
    JSONArray std_fees = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_fees);
        tvStdName = (TextView) findViewById(R.id.fd_std_name);
        tvStdClass = (TextView) findViewById(R.id.fd_std_class);
        tvTotalFees = (TextView) findViewById(R.id.fd_std_totalFees);
        tvLastPay = (TextView) findViewById(R.id.fd_std_lastPay);
        tvDueAmt = (TextView) findViewById(R.id.fd_std_dueAmt);
        tvRemark = (TextView) findViewById(R.id.fd_std_remark);
        new LoadFeesDetail().execute();
    }

    class LoadFeesDetail extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Loading Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            JSONObject json = jParser.makeHttpRequest(urlGetStudents, "GET", params);
            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.e("PostExe", "success");
                    std_fees = json.getJSONArray(TAG_STUDENT_FES);

                    JSONObject c = std_fees.getJSONObject(0);
                    tvStdName.setText(c.getString(TAG_NAME));
                    tvStdClass.setText(c.getString(TAG_CLASS));
                    tvTotalFees.setText("Total Fee = " + (c.getString(TAG_TOTAL_AMT)));
                    tvLastPay.setText("Last Pay " + (c.getString(TAG_DEPOSIT_AMT)) + " on " + (c.getString(TAG_PAY_DATE)));
                    int Due = Integer.parseInt(c.getString(TAG_TOTAL_AMT)) - Integer.parseInt(c.getString(TAG_DEPOSIT_AMT));
                    int due = Integer.parseInt(c.getString(TAG_TOTAL_AMT)) - Integer.parseInt(c.getString(TAG_DEPOSIT_AMT));
                    tvDueAmt.setText("Due Amount is = " + due);
                    tvRemark.setText(c.getString(TAG_REMARK));
                } else {
                    messAlert(context, "No data Found", "no fees detail", 1);
                }
            } catch (JSONException e) {
                exceptionAlert(context, "LoadFeesDetail", e);
            } catch (Exception e) {
                exceptionAlert(context, "LoadFeesDetail", e);
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    try {

                    } catch (Exception e) {
                        exceptionAlert(context, "onPostExecute", e);
                    }
                }
            });

        }

    }
}