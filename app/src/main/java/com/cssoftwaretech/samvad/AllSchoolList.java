package com.cssoftwaretech.samvad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AllSchoolList {


/*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_schools);

        // Hashmap for ListView
        //schoolsList = new ArrayList<HashMap<String, String>>();

        // Loading schools in Background Thread
        new LoadAllSchools().execute();

        // Get listview
        //ListView lv = getListView();

        // on seleting single school
        // launching Edit School Screen
    *//*    lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String pid = ((TextView) view.findViewById(R.id.pid)).getText()
                        .toString();

                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        MainActivity.class);
                // sending pid to next activity
                in.putExtra(TAG_PID, pid);

                // starting new activity and expecting some response back
                startActivityForResult(in, 100);
            }
        });*//*

    }
*//*

    // Response from Edit School Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if result code 100
        if (resultCode == 100) {
            // if result code 100 is received
            // means user edited/deleted school
            // reload this screen again
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
*//*

    }

    *//**
     * Background Async Task to Load all school by making HTTP Request
     * *//*
    class LoadAllStudents extends AsyncTask<String, String, String> {
        private ProgressDialog pDialog;

        JSONParser jParser = new JSONParser();
        private static String url_all_schools = "http://192.168.43.8/samvad/getAllSchoolList.php";

        // JSON Node names
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_PRODUCTS = "schools";
        private static final String TAG_NAME = "sc_school_name";
        private static final String TAG_CLASS = "sc_id";
        private static final String TAG_ENROLLNO = "sc_id";
        private static final String TAG_STATUS = "sc_id";

        // schools JSONArray
        JSONArray schools = null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Loading schools. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_schools, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Schools: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    schools = json.getJSONArray(TAG_PRODUCTS);
                    for (int i = 0; i < schools.length(); i++) {
                        JSONObject c = schools.getJSONObject(i);
                        AttendItemInfo listItem = new AttendItemInfo(c.getString(TAG_name),
                                c.getString(TAG_CLASS),c.getString(TAG_ENROLLNO),c.getString(TAG_STATUS));
                        attendItemInfos.add(listItem);
                    }
                } else {
                    // no schools found
                    // Launch Add New school Activity
                    Intent i = new Intent(getApplicationContext(),
                            SignUp.class);
                    // Closing all previous activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    attend_entry_adapter = new Attend_entry_adapter(attendItemInfos, context);
                    rvStdList.setAdapter(attend_entry_adapter);
                }
            });

        }

    }*/
}