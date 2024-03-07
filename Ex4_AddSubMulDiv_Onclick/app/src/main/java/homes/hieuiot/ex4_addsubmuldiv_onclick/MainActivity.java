package homes.hieuiot.ex4_addsubmuldiv_onclick;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    double a, b;
    EditText txtResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

     void getData() {
        EditText txtA = findViewById(R.id.txtA);
        EditText txtB = findViewById(R.id.txtB);
        a = Double.parseDouble(txtA.getText().toString());
        b = Double.parseDouble(txtB.getText().toString());
        txtResult = findViewById(R.id.txtResult);
    }
    public void plusHandler(View v) {
        getData();
        Calculate cal = new Calculate(a, b);
        txtResult.setText(String.valueOf(cal.Plus()));
    }
    public void minusHandler(View v) {
        getData();
        Calculate cal = new Calculate(a, b);
        txtResult.setText(String.valueOf(cal.Minus()));
    }
    public void multiplyHandler(View v) {
        getData();
        Calculate cal = new Calculate(a, b);
        txtResult.setText(String.valueOf(cal.Multiply()));
    }
    @SuppressLint("SetTextI18n")
    public void divideHandler(View v) {
        getData();
        if(a == 0 || b == 0) {
            txtResult.setText("NaN");
            return;
        }
        DecimalFormat df = new DecimalFormat("#.##");
        Calculate cal = new Calculate(a, b);
        txtResult.setText(df.format(cal.Divide()));
    }
}