package homes.hieuiot.ex3_simplesumapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void SumHandler(View view) {
        EditText txtA = findViewById(R.id.txtA);
        EditText txtB = findViewById(R.id.txtB);
        EditText txtResult = findViewById(R.id.txtResult);

        double a = Double.parseDouble(txtA.getText().toString());
        double b = Double.parseDouble(txtB.getText().toString());
        double result = a + b;
        if(result % 1 == 0) txtResult.setText(String.valueOf((int)result));
        else txtResult.setText(String.valueOf(result));

    }
}