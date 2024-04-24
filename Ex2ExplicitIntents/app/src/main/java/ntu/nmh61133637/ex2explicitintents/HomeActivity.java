package ntu.nmh61133637.ex2explicitintents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    TextView txtUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent loginData = getIntent();
        if(loginData != null) {
            txtUsername = findViewById(R.id.txtUsername);
            String username = loginData.getStringExtra("username");
            txtUsername.setText("Xin chào " +username);
        }
    }
}