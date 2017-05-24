package com.example.butterfly.aptimetable.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.butterfly.aptimetable.R;
import com.example.butterfly.aptimetable.models.Faculty;
import com.example.butterfly.aptimetable.restasynctask.HttpPostAsyncTask;

public class FacultySaveActivity extends AppCompatActivity {

    EditText editTextShortName, editTextFullName;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_save);

        editTextShortName = (EditText) findViewById(R.id.editText_short_name);
        editTextFullName = (EditText) findViewById(R.id.editText_full_name);
        save = (Button) findViewById(R.id.button_save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editTextShortName.getText().toString().equals("") && !editTextFullName.getText().toString().equals("")) {
                    Faculty faculty = new Faculty(editTextShortName.getText().toString(), editTextFullName.getText().toString());
                    new HttpPostAsyncTask(FacultySaveActivity.this, faculty).execute();
                } else Toast.makeText(FacultySaveActivity.this, "Все поля должны быть заполнены!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
