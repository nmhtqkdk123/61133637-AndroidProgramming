package ntu.nmh61133637.cau3_introducemyself;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Info info;
    EditText txtNameVal;
    EditText txtClassVal;
    EditText txtIdVal;
    EditText txtPhoneVal;
    EditText txtEmailVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findItem();
    }
    private void findItem() {
        txtNameVal = findViewById(R.id.txtNameValue);
        txtClassVal = findViewById(R.id.txtClassVal);
        txtIdVal = findViewById(R.id.txtIDVal);
        txtPhoneVal = findViewById(R.id.txtPhoneVal);
        txtEmailVal = findViewById(R.id.txtEmailVal);
    }
    private void getInfo() {
        String name = txtNameVal.getText().toString();
        String Class = txtClassVal.getText().toString();
        String id = txtIdVal.getText().toString();
        String phone = txtPhoneVal.getText().toString();
        String email = txtEmailVal.getText().toString();
        info = new Info(name, Class, id, phone, email);
    }
}