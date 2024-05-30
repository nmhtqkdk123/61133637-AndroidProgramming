package ntu.nmh61133637.shopeeapplicationfinalexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener((NavigationBarView.OnItemSelectedListener) item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();
            if (itemId == R.id.product) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.cart) {
            selectedFragment = new CartFragment();
            } else if (itemId == R.id.profile) {
            selectedFragment = new ProfileFragment();
            }
            // It will help to replace the
            // one fragment to other.
            if (selectedFragment != null) getSupportFragmentManager().beginTransaction().replace(R.id.fragment_wrapper, selectedFragment).commit();
            return true;
        });
    }
    public void createDatabase(View view) {
//        try (tb_AccountHandler tb_account = new tb_AccountHandler(MainActivity.this)) {
//            List<Account> accounts = tb_account.getAllAccount();
//            for(Account account: accounts) {
//                Log.d(String.valueOf(account.id), account.name);
//            }
//        } catch (Exception e) {System.out.println(e.getMessage());}
            try (tb_CartHandler tb_cart = new tb_CartHandler(MainActivity.this)) {
                List<Cart> products = tb_cart.getAllProduct();
                for(Cart product: products) {
                    Log.d(String.valueOf(product.productID), String.valueOf(product.quantity));
                }
//                tb_cart.onCreate(tb_cart.getWritableDatabase());
//                tb_product.addProduct(new Product(0, "Dây cáp sạc nhanh USB to type-C hãng XIAOMI", "cap_sac_type_c_xiaomi", 23000, 3109));
//                tb_product.addProduct(new Product(0, "Cà phê đặc sản Arabica Indonesia nhập khẩu", "ca_phe_arabica_indonesia", 168000, 62));
//                tb_product.addProduct(new Product(0, "Cáp sạc nhanh Rocoren 240W 5A USB to TypeC", "cap_sac_rocoren_240w_type_c", 34000, 157));
//                tb_product.addProduct(new Product(0, "Keycap profile Cherry bằng gỗ nguyên khối", "keycap_profile_cherry_bang_go", 45000, 4));
//                tb_product.addProduct(new Product(0, "Dụng cụ thước đo chìa khoá cầm tay 2 trong 1", "dung_cu_thuoc_do_chia_khoa", 286000, 42));
//                tb_product.addProduct(new Product(0, "Móc gỗ treo tường Medogo, treo quần áo", "moc_go_treo_tuong_medogo", 170000, 724));
//                tb_product.addProduct(new Product(0, "Van áp suất lọc nước bằng thép không gỉ", "van_ap_suat_bang_thep_khong_gi", 117000, 530));
//                tb_product.addProduct(new Product(0, "Tham lau chân siêu mềm siêu dày", "tham_lau_chan_sieu_mem", 49000, 20824));
//                tb_product.addProduct(new Product(0, "Chuột máy tính không dây Lofree Touch", "chuot_may_tinh_khong_day_lofree_touch", 1590000, 145));
//                tb_product.addProduct(new Product(0, "Vòi hoa sen nhà bếp bằng thép SUS304", "voi_hoa_sen_nha_bep_bang_thep", 151000, 2158));
        } catch (Exception e) {System.out.println(e.getMessage());}
    }
}