package com.example.butterfly.aptimetable.restasynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import com.example.butterfly.aptimetable.models.Faculty;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.IOException;

public class HttpPostAsyncTask extends AsyncTask<String, Void, String> {

    private ProgressDialog progressDialog;
    private Context context;
    private Faculty faculty;

    public HttpPostAsyncTask(Context context, Faculty faculty) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
        this.faculty = faculty;
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
        postJson(faculty);

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        ((Activity) context).finish();
        Toast.makeText(context, "Received!", Toast.LENGTH_SHORT).show();
    }

    private HttpResponse postJson(Faculty faculty) {
        String URL_faculty = "http://aptimetable.herokuapp.com/security/Faculties";
        HttpPost httpPut = new HttpPost(URL_faculty);
        Gson gson = new Gson();
        String facultyJson = gson.toJson(faculty);

        try {
            httpPut.setEntity(new StringEntity(facultyJson, HTTP.UTF_8));
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Authorization", "Basic " + Base64.encodeToString("admin:admin".getBytes(), Base64.NO_WRAP));
            httpPut.setHeader("Content-type", "application/json; charset=utf-8");

            return new DefaultHttpClient().execute(httpPut);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
