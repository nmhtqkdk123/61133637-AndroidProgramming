package ntu.nmh61133637.ex1explicitintents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {
    Button btnActMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        btnActMain = findViewById(R.id.btnActMain);
        btnActMain.setOnClickListener(view -> {
            Intent mainAct = new Intent(this, MainActivity.class);
            startActivity(mainAct);
        });
    }
}