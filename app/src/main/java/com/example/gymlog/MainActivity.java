
package com.example.gymlog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText exerciseInput, repsInput;
    private Button logButton, logoutButton;
    private ListView logListView;

    private GymLogRepository repository;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> logList = new ArrayList<>();

    private int currentUserId = 0; // Could map this later based on username
    private String currentUsername = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the username passed from LoginActivity
        Intent intent = getIntent();
        currentUsername = intent.getStringExtra("USERNAME");

        // For demo: assign a fake userId based on username
        if (currentUsername != null) {
            if (currentUsername.toLowerCase().contains("admin")) {
                currentUserId = 1;
            } else {
                currentUserId = 2;
            }
        }

        // Initialize UI
        exerciseInput = findViewById(R.id.exerciseInput);
        repsInput = findViewById(R.id.repsInput);
        logButton = findViewById(R.id.logButton);
        logoutButton = findViewById(R.id.logoutButton);
        logListView = findViewById(R.id.logListView);

        // Repository
        repository = new GymLogRepository(getApplication());

        // Setup list adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, logList);
        logListView.setAdapter(adapter);

        // Load existing logs for this user
        repository.getLogsByUser(currentUserId).observe(this, new Observer<List<GymLog>>() {
            @Override
            public void onChanged(List<GymLog> gymLogs) {
                logList.clear();
                for (GymLog log : gymLogs) {
                    logList.add(log.toString());
                }
                adapter.notifyDataSetChanged();
            }
        });

        // Handle Log button
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String exercise = exerciseInput.getText().toString();
                String repsText = repsInput.getText().toString();

                if (exercise.isEmpty() || repsText.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter exercise and reps", Toast.LENGTH_SHORT).show();
                    return;
                }

                int reps = Integer.parseInt(repsText);
                GymLog newLog = new GymLog(currentUserId, exercise, reps);
                repository.insertGymLog(newLog);

                exerciseInput.setText("");
                repsInput.setText("");
            }
        });

        // Handle Logout button
        logoutButton.setOnClickListener(v -> {
            Intent logoutIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(logoutIntent);
            finish();
        });
    }
}