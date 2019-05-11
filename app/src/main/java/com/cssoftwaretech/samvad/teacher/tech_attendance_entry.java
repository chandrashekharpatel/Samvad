package com.cssoftwaretech.samvad.teacher;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cssoftwaretech.samvad.JSONParser;
import com.cssoftwaretech.samvad.R;
import com.cssoftwaretech.samvad.SignUp;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.cssoftwaretech.samvad.style.MessageDialog.exceptionAlert;

public class tech_attendance_entry extends AppCompatActivity {
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "attendanceList";
    private static final String TAG_NAME = "st_name";
    private static final String TAG_CLASS = "class_name";
    private static final String TAG_ENROLLNO = "adm_no";
    private static final String TAG_STATUS = "att_status";
    private static final String urlGetStudents = "http://192.168.197.1/samvad/getStudentList.php";
    Context context;
    JSONParser jParser = new JSONParser();
    JSONArray schools = null;
    private List<AttendItemInfo> attendItemInfos;
    private RecyclerView rvStdList;
    private Attend_entry_adapter attend_entry_adapter;
    private ProgressDialog pDialog;
    private LinearLayout llNoStudentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.tech_actv_attendance_entry);
            initialization();
            new LoadAllStudents().execute();
        } catch (Exception e) {
            exceptionAlert(context, "attendEntry", e);
        }
    }

    private void initialization() {
        context = this;
        rvStdList = (RecyclerView) findViewById(R.id.recAttend_stdList);
        llNoStudentList = (LinearLayout) findViewById(R.id.tvRecAttend_NoStudentList);
        llNoStudentList.setVisibility(View.INVISIBLE);
        rvStdList.setHasFixedSize(true);
        rvStdList.setLayoutManager(new LinearLayoutManager(context));
        attendItemInfos = new ArrayList<>();

    }

    class LoadAllStudents extends AsyncTask<String, String, String> {
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
            Log.d("All Schools: ", json.toString());

            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    llNoStudentList.setVisibility(View.INVISIBLE);
                    Log.e("PostExe", "success");
                    schools = json.getJSONArray(TAG_PRODUCTS);
                    for (int i = 0; i < schools.length(); i++) {
                        JSONObject c = schools.getJSONObject(i);
                        AttendItemInfo listItem = new AttendItemInfo(c.getString(TAG_NAME),
                                c.getString(TAG_CLASS), c.getString(TAG_ENROLLNO), c.getString(TAG_STATUS));
                        attendItemInfos.add(listItem);
                    }
                } else {
                    llNoStudentList.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
                exceptionAlert(context, "LoadAllStudents", e);
            } catch (Exception e) {
                exceptionAlert(context, "LoadAllStudents", e);
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        attend_entry_adapter = new Attend_entry_adapter(attendItemInfos, context);
                        rvStdList.setAdapter(attend_entry_adapter);
                    } catch (Exception e) {
                        exceptionAlert(context, "onPostExecute", e);
                    }
                }
            });

        }

    }
}