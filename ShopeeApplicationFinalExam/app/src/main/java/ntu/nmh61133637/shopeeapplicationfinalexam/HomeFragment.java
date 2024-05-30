package ntu.nmh61133637.shopeeapplicationfinalexam;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ProductAdapter productAdapter;
    ArrayList<Product> products;
    RecyclerView productView;
    public HomeFragment() {
        // Required empty public constructor
    }
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            try (tb_ProductHandler tb_product = new tb_ProductHandler(getContext())) {
                products = tb_product.getAllProduct();
                for (Product product : products) Log.d(String.valueOf(product.id), product.name);
            }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        productView = view.findViewById(R.id.productView);
        productView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        productView.setAdapter(new ProductAdapter(view.getContext(), products));
        return view;
    }
}
