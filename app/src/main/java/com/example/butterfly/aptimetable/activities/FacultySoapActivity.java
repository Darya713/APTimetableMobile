package com.example.butterfly.aptimetable.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.butterfly.aptimetable.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class FacultySoapActivity extends AppCompatActivity {

    EditText option;
    Button find;
    TextView result1, result2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculties_soap);

        option = (EditText) findViewById(R.id.editText_find_faculty);
        find = (Button) findViewById(R.id.find);
        result1 = (TextView) findViewById(R.id.textView_result1);
        result2 = (TextView) findViewById(R.id.textView_result2);

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (option.getText() != null) {
                    new SoapAsyncTask().execute(option.getText().toString());
                } else Toast.makeText(FacultySoapActivity.this, "Необходимо ввести ID факультета", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class SoapAsyncTask extends AsyncTask<String, Void, String> {

        String request, requestJson;
        ProgressDialog progressDialog = new ProgressDialog(FacultySoapActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Пожалуйста подождите ...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            request = SOAPRequest(strings[0]);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            result1.setText(request);
            result2.setText(requestJson);
        }

        private String SOAPRequest(String option) {
            String SOAP_ACTION = "http://example.com/soap/getFacultyRequest";
            String METHOD_NAME = "getFacultyRequest";
            String NAMESPACE = "http://example.com/soap";
            String URL = "http://aptimetable.herokuapp.com/soap/faculty.wsdl";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            PropertyInfo info = new PropertyInfo();
            info.setName("id");
            info.setValue(option);
            info.setType(int.class);
            request.addProperty(info);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransportSE = new HttpTransportSE(URL);
            try {
                httpTransportSE.debug = true;
                httpTransportSE.call(SOAP_ACTION, envelope);
                SoapObject soapObject = (SoapObject) envelope.getResponse();
                requestJson = soapObject.toString();

                return httpTransportSE.responseDump;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
