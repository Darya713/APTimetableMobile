package com.example.butterfly.aptimetable.restasynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import com.example.butterfly.aptimetable.activities.FacultyRestActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class HttpDeleteAsyncTask extends AsyncTask<String, Void, String> {

    private ProgressDialog progressDialog;
    private Context context;
    private int id;

    public HttpDeleteAsyncTask(Context context, int id) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
        this.id = id;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setTitle("Please wait ...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        deleteJson(id);

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        ((FacultyRestActivity) context).Task();
        Toast.makeText(context, "Received!", Toast.LENGTH_SHORT).show();
    }

    private HttpResponse deleteJson(int id) {
        String URL_faculty = "http://aptimetable.herokuapp.com/security/Faculties/" + id;
        HttpDelete httpDelete = new HttpDelete(URL_faculty);

        try {
            httpDelete.setHeader("Accept", "application/json");
            httpDelete.setHeader("Authorization", "Basic " + Base64.encodeToString("admin:admin".getBytes(), Base64.NO_WRAP));
            httpDelete.setHeader("Content-type", "application/json");

            return new DefaultHttpClient().execute(httpDelete);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
