package ntu.nmh61133637.ex1explicitintents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnAct2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAct2 = findViewById(R.id.btnAct2);
        btnAct2.setOnClickListener(view -> {
            Intent secondAct = new Intent(this, SecondActivity.class);
            startActivity(secondAct);
        });
    }
}