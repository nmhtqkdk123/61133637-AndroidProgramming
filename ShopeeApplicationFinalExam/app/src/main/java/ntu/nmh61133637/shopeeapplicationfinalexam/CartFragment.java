package ntu.nmh61133637.shopeeapplicationfinalexam;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    CartAdapter cartAdapter;
    ArrayList<Cart> items;
    RecyclerView cartView;
    Button buy;
    public CartFragment() {
        // Required empty public constructor
    }
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @SuppressLint("NotifyDataSetChanged")
    private void buyItem() {
        buy.setOnClickListener(view -> {
            ArrayList<Cart> selectedItems = cartAdapter.getSelectedItems();
            try(tb_CartHandler tb_cart = new tb_CartHandler(getContext())) {
                for(Cart item : selectedItems) {
                    tb_cart.removeProduct(item.getProductID());
                    for(int i = 0; i < items.size(); ++i) if(items.get(i).getProductID() == item.getProductID()) {
                        items.remove(i);
                        break;
                    }
                }
                selectedItems.clear();
                cartAdapter.setSelectedItems(selectedItems);
                cartAdapter.showTotalPrice();
                cartAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Bạn đã đặt hàng thành công.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Very important
        try (tb_CartHandler tb_cart = new tb_CartHandler(getContext())) { items = tb_cart.getAllProduct(); }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        cartView = view.findViewById(R.id.cartView);
        buy = view.findViewById(R.id.buy);
        buyItem();
        cartView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        cartAdapter = new CartAdapter(view.getContext(), items);
        cartView.setAdapter(cartAdapter);

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getLayoutPosition();
                if(direction == ItemTouchHelper.LEFT) {
                    try(tb_CartHandler tb_cart = new tb_CartHandler(getContext())) {
                        tb_cart.removeProduct(items.get(pos).getProductID());
                        TextView itemName = viewHolder.itemView.findViewById(R.id.cartItemName);
                        Toast.makeText(view.getContext(), "Xoá sản phẩm " + itemName.getText() + " khỏi giỏ hàng thành công.", Toast.LENGTH_LONG).show();
                        ArrayList<Cart> selectedItems = cartAdapter.getSelectedItems();
                        for(int i = 0; i < selectedItems.size(); ++i) {
                            if(items.get(pos).getProductID() == selectedItems.get(i).getProductID()) {
                                selectedItems.remove(i);
                                cartAdapter.showTotalPrice();
                                break;
                            }
                        }
                        items.remove(pos);
                        cartAdapter.notifyItemRemoved(pos);
                    }
                }
            }
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(cartView);
        return view;
    }
}