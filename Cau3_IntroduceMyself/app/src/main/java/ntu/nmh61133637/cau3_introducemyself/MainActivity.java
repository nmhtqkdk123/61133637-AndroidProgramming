package ntu.nmh61133637.cau3_introducemyself;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Info info;
    EditText txtNameVal;
    EditText txtClassVal;
    EditText txtIdVal;
    EditText txtPhoneVal;
    EditText txtEmailVal;
    Button btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findItem();
        getInfo();
    }
    private void findItem() {
        txtNameVal = findViewById(R.id.txtNameValue);
        txtClassVal = findViewById(R.id.txtClassVal);
        txtIdVal = findViewById(R.id.txtIDVal);
        txtPhoneVal = findViewById(R.id.txtPhoneVal);
        txtEmailVal = findViewById(R.id.txtEmailVal);
        btnEdit = findViewById(R.id.btnEdit);
    }
    private void getInfo() {
        String name = txtNameVal.getText().toString();
        String Class = txtClassVal.getText().toString();
        String id = txtIdVal.getText().toString();
        String phone = txtPhoneVal.getText().toString();
        String email = txtEmailVal.getText().toString();
        info = new Info(name, Class, id, phone, email);
    }

    private void editInfo() {
        setEditEnable(true);
        btnEdit.setText("Đồng ý");
    }
    public void editHandle(View view) {
        if(btnEdit.getText().equals("Chỉnh sửa thông tin")) editInfo();
        else {
            btnEdit.setText("Chỉnh sửa thông tin");
            setEditEnable(false);
            getInfo();
        }
    }
    public void handleRemove(View view) {
        txtNameVal.setText("");
        txtClassVal.setText("");
        txtIdVal.setText("");
        txtPhoneVal.setText("");
        txtEmailVal.setText("");
        getInfo();
    }
    public void setEditEnable(boolean check) {
        txtNameVal.setEnabled(check);
        txtClassVal.setEnabled(check);
        txtIdVal.setEnabled(check);
        txtPhoneVal.setEnabled(check);
        txtEmailVal.setEnabled(check);
    }
}