package ntu.nmh61133637.shopeeapplicationfinalexam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void createDatabase(View view) {
        try (tb_AccountHandler tb_account = new tb_AccountHandler(MainActivity.this)) {
            List<Account> accounts = tb_account.getAllAccount();
            for(Account account: accounts) {
                Log.d(String.valueOf(account.id), account.name);
            }
        } catch (Exception e) {System.out.println(e.getMessage());}
    }
}