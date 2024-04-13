package ntu.nmh61133637.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    PlacesListAdapter adapter;
    ArrayList<PlacesList> placesList;
    RecyclerView placeListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        placesList = getPlacesList();
        placeListView = findViewById(R.id.placeView);
        RecyclerView.LayoutManager linearLayout = new LinearLayoutManager(this);
        placeListView.setLayoutManager(linearLayout);
        adapter = new PlacesListAdapter(this, placesList);
        placeListView.setAdapter(adapter);
    }
    ArrayList<PlacesList> getPlacesList() {
        ArrayList<PlacesList> list = new ArrayList<PlacesList>();
        list.add(new PlacesList("Thành phố Nha Trang, tỉnh Khánh Hoà", "nha_trang"));
        list.add(new PlacesList("Thành phố Đà Lạt, tỉnh Lâm Đồng", "da_lat"));
        list.add(new PlacesList("Vịnh Hạ Long, thành phố Hải Phòng", "vinh_ha_long"));
        list.add(new PlacesList("Thành phố Sa Pa, tỉnh Lào Cai", "sapa"));
        return list;
    }
}