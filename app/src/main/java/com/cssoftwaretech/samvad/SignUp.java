package com.cssoftwaretech.samvad;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.cssoftwaretech.samvad.style.MessageDialog.exceptionAlert;

public class SignUp extends AppCompatActivity {
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "schoolsList";
    private static final String TAG_SCHOOL_ID = "sc_id";
    private static final String TAG_SCHOOL_NAME = "sc_school_name";
    private static final String TAG_ADDRESS = "sc_address";
    private static final String TAG_CONTACT = "sc_contact_no";
    private static final String TAG_EMAIL = "sc_email";
    private static final String TAG_WEBSITE = "sc_website";
    private static final String urlGetSchools = "http://192.168.197.1/samvad/getSchoolsList.php";
    private static int scId = 0;
    EditText et_admNo, et_OTP;
    TextView sp_SchoolList;
    Button btn_submit;
    LinearLayout ll_admNo, ll_OTP;
    Context context;
    JSONParser jParser = new JSONParser();
    JSONArray schools = null;
    RecyclerView rvSchList;
    LinearLayout llNoInterNet;
    private ProgressDialog pDialog;
    private List<SchoolItemInfo> schoolItemInfos;
    private SignUpSchoolListAdapter signUpSchoolListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        initialization();
        action();
        new LoadAllSchools().execute();
    } catch (Exception e) {
        exceptionAlert(context, "SignUp", e);
    }
    }

    private void action() {
        rvSchList.addOnItemTouchListener(new RecyclerItemClickListener(context, rvSchList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int gpPos) {
                TextView tvScIdNo = (TextView) view.findViewById(R.id.tvSSL_idNo);
                TextView tvScName = (TextView) view.findViewById(R.id.tvSSL_name);
                scId = Integer.parseInt(tvScIdNo.getText().toString());
                sp_SchoolList.setText(tvScName.getText().toString());
                rvSchList.setVisibility(View.INVISIBLE);
                ll_admNo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                ll_admNo.setVisibility(View.VISIBLE);
            }

            @Override
            public void onItemLongClick(View view, int gpPos) {

            }
        }));
        sp_SchoolList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvSchList.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initialization() {
        sp_SchoolList = (TextView) findViewById(R.id.signup_sp_school);
        et_admNo = (EditText) findViewById(R.id.signup_et_admNo);
        et_OTP = (EditText) findViewById(R.id.signup_et_OTP);
        btn_submit = (Button) findViewById(R.id.signup_btn_submit);
        ll_admNo = (LinearLayout) findViewById(R.id.signup_ll_admNo);
        ll_OTP = (LinearLayout) findViewById(R.id.signup_ll_OTP);
        ll_admNo.setVisibility(View.INVISIBLE);
        ll_OTP.setVisibility(View.INVISIBLE);
        ll_admNo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0));
        ll_OTP.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0));
        btn_submit.setEnabled(false);
        context = this;
        rvSchList = (RecyclerView) findViewById(R.id.recSignUp_schoolList);
        llNoInterNet = (LinearLayout) findViewById(R.id.tvRecSignUp_NoInterNetConn);
        llNoInterNet.setVisibility(View.INVISIBLE);
        rvSchList.setHasFixedSize(true);
        rvSchList.setLayoutManager(new LinearLayoutManager(context));
        schoolItemInfos = new ArrayList<>();
    }

    class LoadAllSchools extends AsyncTask<String, String, String> {
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
            JSONObject json = jParser.makeHttpRequest(urlGetSchools, "GET", params);
            Log.d("All Schools: ", json.toString());

            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    llNoInterNet.setVisibility(View.INVISIBLE);
                    Log.e("PostExe", "success");
                    schools = json.getJSONArray(TAG_PRODUCTS);
                    String[] scNameList = new String[schools.length()];
                    for (int i = 0; i < schools.length(); i++) {
                        JSONObject c = schools.getJSONObject(i);
                        SchoolItemInfo listItem = new SchoolItemInfo(c.getString(TAG_SCHOOL_ID), c.getString(TAG_SCHOOL_NAME),
                                c.getString(TAG_ADDRESS), c.getString(TAG_CONTACT), c.getString(TAG_EMAIL), c.getString(TAG_WEBSITE));
                        schoolItemInfos.add(listItem);
                    }
                } else {
                    llNoInterNet.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
                exceptionAlert(context, "LoadAllSchools", e);
            } catch (Exception e) {
                exceptionAlert(context, "LoadAllSchools", e);
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        signUpSchoolListAdapter = new SignUpSchoolListAdapter(schoolItemInfos, context);
                        rvSchList.setAdapter(signUpSchoolListAdapter);
                    } catch (Exception e) {
                        exceptionAlert(context, "onPostExecute", e);
                    }
                }
            });

        }

    }
}
