package ntu.nmh61133637.ex2explicitintents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnBeginLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBeginLogin = findViewById(R.id.btnBeginLogin);
        btnBeginLogin.setOnClickListener(view -> {
            Intent beginLogin = new Intent(this, LoginActivity.class);
            startActivity(beginLogin);
        });
    }
}