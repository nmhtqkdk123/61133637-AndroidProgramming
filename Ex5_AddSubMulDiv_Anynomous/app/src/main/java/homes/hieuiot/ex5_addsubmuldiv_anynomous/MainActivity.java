package homes.hieuiot.ex5_addsubmuldiv_anynomous;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    double a, b;
    EditText txtResult;
    Button plus, minus, multiply, divide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findButton();
    }
    void findButton() {
        plus = findViewById(R.id.btnPlus);
        plus.setOnClickListener(view -> plusHandler());
        minus = findViewById(R.id.btnMinus);
        minus.setOnClickListener(view -> minusHandler());
        multiply = findViewById(R.id.btnMultiply);
        multiply.setOnClickListener(view -> multiplyHandler());
        divide = findViewById(R.id.btnDivide);
        divide.setOnClickListener(view -> divideHandler());
    }
    void getData() {
        EditText txtA = findViewById(R.id.txtA);
        EditText txtB = findViewById(R.id.txtB);
        a = Double.parseDouble(txtA.getText().toString());
        b = Double.parseDouble(txtB.getText().toString());
        txtResult = findViewById(R.id.txtResult);
    }
     void plusHandler() {
        getData();
        Calculate cal = new Calculate(a, b);
        txtResult.setText(String.valueOf(cal.Plus()));
    }
     void minusHandler() {
        getData();
        Calculate cal = new Calculate(a, b);
        txtResult.setText(String.valueOf(cal.Minus()));
    }
     void multiplyHandler() {
        getData();
        Calculate cal = new Calculate(a, b);
        txtResult.setText(String.valueOf(cal.Multiply()));
    }
    @SuppressLint("SetTextI18n")
     void divideHandler() {
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