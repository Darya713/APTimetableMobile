package com.example.butterfly.aptimetable.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.butterfly.aptimetable.R;
import com.example.butterfly.aptimetable.models.Faculty;
import com.example.butterfly.aptimetable.restasynctask.HttpPostAsyncTask;

public class FacultyEditActivity extends AppCompatActivity {

    TextView textViewShortName, textViewFullName;
    EditText editTextShortName, editTextFullName;
    Button update;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_edit);

        textViewShortName = (TextView) findViewById(R.id.textView_short_name);
        textViewFullName = (TextView) findViewById(R.id.textView_full_name);
        editTextShortName = (EditText) findViewById(R.id.editText_short_name);
        editTextFullName = (EditText) findViewById(R.id.editText_full_name);
        update = (Button) findViewById(R.id.button_update);

        id = getIntent().getIntExtra("id", 0);
        textViewShortName.setText(getIntent().getStringExtra("shortName"));
        textViewFullName.setText(getIntent().getStringExtra("fullName"));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editTextShortName.getText().toString().equals("") && !editTextFullName.getText().toString().equals("")) {
                    Faculty faculty = new Faculty(id, editTextShortName.getText().toString(), editTextFullName.getText().toString());
                    new HttpPostAsyncTask(FacultyEditActivity.this, faculty).execute();
                } else Toast.makeText(FacultyEditActivity.this, "Все поля должны быть заполнены!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
