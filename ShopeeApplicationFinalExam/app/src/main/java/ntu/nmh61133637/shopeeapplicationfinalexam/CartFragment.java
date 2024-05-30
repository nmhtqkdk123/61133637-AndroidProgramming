package ntu.nmh61133637.shopeeapplicationfinalexam;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    CartAdapter cartAdapter;
    ArrayList<Cart> items;
    RecyclerView cartView;
    public CartFragment() {
        // Required empty public constructor
    }
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try (tb_CartHandler tb_cart = new tb_CartHandler(getContext())) {
            items = tb_cart.getAllProduct();
            for (Cart product : items) Log.d(String.valueOf(product.id), String.valueOf(product.getQuantity()));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        cartView = view.findViewById(R.id.cartView);
        cartView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        cartView.setAdapter(new CartAdapter(view.getContext(), items));
        return view;
    }
}