package com.cssoftwaretech.samvad;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Messages extends AppCompatActivity {
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_messages);
        BackgroundMessageWorker backgroundMessageWorker = new BackgroundMessageWorker(context);
        backgroundMessageWorker.execute("sendMessage", "9301107075", "9179670334", "KAHA ho santosh", "2018-08-08");

    }

    public class BackgroundMessageWorker extends AsyncTask<String, Void, String> {
        BackgroundMessageWorker(Context ctx) {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String login_url = "http://192.168.197.1/samvad/sendMessage.php";
            if (type.equals("sendMessage")) {
                try {
                    String m_from = params[1];
                    String m_to = params[2];
                    String m_message = params[3];
                    String m_date = params[4];
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("mess_from", "UTF-8") + "=" + URLEncoder.encode(m_from, "UTF-8")
                            + "&" + URLEncoder.encode("mess_to", "UTF-8") + "=" + URLEncoder.encode(m_to, "UTF-8")
                            + "&" + URLEncoder.encode("mess_message", "UTF-8") + "=" + URLEncoder.encode(m_message, "UTF-8")
                            + "&" + URLEncoder.encode("mess_date", "UTF-8") + "=" + URLEncoder.encode(m_date, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("pws_cng_successful")) {
                Toast.makeText(context, "INSERTED", Toast.LENGTH_LONG).show();
            } else if (result.contains("Welcome")) {

            } else {
                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
