package homes.hieuiot.ex_6_listviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> provinceList;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setProvinceList();
        setListView();
        listenItemInListView();
    }
    private void setProvinceList() {
        String[] provinces = new String[]{"Hà Nội", "TP.HCM", "Đồng Nai", "Khánh Hoà", "Bình Thuận", "Ninh Thuận"};
        provinceList = new ArrayList<>();
        provinceList.addAll(Arrays.asList(provinces));
    }
    private void setListView() {
        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, provinceList);
        listView = findViewById(R.id.listProvince);
        listView.setAdapter(provinceAdapter);
    }
    private void listenItemInListView() {

        listView.setOnItemClickListener((adapterView, view, i, l)
                -> Toast.makeText(MainActivity.this, String.valueOf(provinceList.get(i)), Toast.LENGTH_LONG).show());
    }
}