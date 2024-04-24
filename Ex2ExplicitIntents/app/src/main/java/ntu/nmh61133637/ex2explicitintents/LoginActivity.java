package ntu.nmh61133637.ex2explicitintents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Account account = new Account();
    Button btnLogin;
    EditText edtusername;
    EditText edtpassword;
    EditText edtemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getView();
    }
    private void getView() {
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(view -> {
            handleLogin();
        });
        edtusername = findViewById(R.id.edtUsername);
        edtpassword = findViewById(R.id.edtPassword);
        edtemail = findViewById(R.id.edtEmail);
    }
    private void handleLogin() {
        String username = String.valueOf(edtusername.getText());
        String password = String.valueOf(edtpassword.getText());
        String email = String.valueOf(edtemail.getText());
        if (username.equals("") || password.equals("") || email.equals(""))
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        else if (!username.equals(account.getUsername()) || !password.equals(account.getPassword()) || !email.equals(account.getEmail()))
            Toast.makeText(this, "Sai thông tin, vui lòng kiểm tra lại!", Toast.LENGTH_SHORT).show();
        else {
            Intent homeActivity = new Intent(this, HomeActivity.class);
            homeActivity.putExtra("username", username);
            startActivity(homeActivity);
        }
    }
}