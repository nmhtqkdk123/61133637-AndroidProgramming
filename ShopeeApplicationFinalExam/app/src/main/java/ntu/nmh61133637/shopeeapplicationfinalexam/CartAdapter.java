package ntu.nmh61133637.shopeeapplicationfinalexam;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartItemHolder> {
    Context context;
    ArrayList<Cart> items;
    ArrayList<Cart> selectedItems = new ArrayList<>();
    public CartAdapter(Context context, ArrayList<Cart> item) {
        this.context = context;
        this.items = item;
    }
    @NonNull
    @Override
    public CartItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(context);
        View viewItem = li.inflate(R.layout.cart_item, parent, false);
        return new CartItemHolder(viewItem);
    }
    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartItemHolder holder, int position) {
        Cart item = items.get(position);
        setCartItem(holder, item);
        setPlusQuantity(holder, item);
        setMinusQuantity(holder, item);
        selectCartItem(holder, item);
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
    @SuppressLint("SetTextI18n")
    public void setCartItem(@NonNull CartAdapter.CartItemHolder holder, Cart item) {
        try (tb_ProductHandler tb_product = new tb_ProductHandler(context.getApplicationContext())) {
            ArrayList<Product> products = tb_product.getAllProduct();
            for(Product product : products) {
                if(item.getProductID() == product.getId()) {
                    holder.cartItemName.setText(product.getName());
                    String packageName = holder.itemView.getContext().getPackageName();
                    @SuppressLint("DiscouragedApi") int imageID = holder.itemView.getResources().getIdentifier(product.getImage(), "mipmap", packageName);
                    holder.cartItemImage.setImageResource(imageID);
                    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY);
                    holder.cartItemPrice.setText("₫" + numberFormat.format(product.getPrice()));
                    holder.cartItemQuantity.setText(String.valueOf(item.getQuantity()));
                    break;
                }
            }
        }
    }
    public void setPlusQuantity(@NonNull CartAdapter.CartItemHolder holder, Cart item) {
        holder.quantityPlus.setOnClickListener(view -> {
            item.setQuantity(item.getQuantity() + 1);
            holder.cartItemQuantity.setText(String.valueOf(item.getQuantity()));
            try (tb_CartHandler tb_cart = new tb_CartHandler(view.getContext())) {
                tb_cart.editProduct(item.getProductID(), item.getQuantity());
            }
        });
    }
    public void setMinusQuantity(@NonNull CartAdapter.CartItemHolder holder, Cart item) {
        holder.quantityMinus.setOnClickListener(view -> {
            item.setQuantity(item.getQuantity() - 1);
            holder.cartItemQuantity.setText(String.valueOf(item.getQuantity()));
            try (tb_CartHandler tb_cart = new tb_CartHandler(view.getContext())) {
                tb_cart.editProduct(item.getProductID(), item.getQuantity());
            }
        });
    }
    public void selectCartItem(@NonNull CartAdapter.CartItemHolder holder, Cart item) {
        holder.cartItemSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    selectedItems.add(item);
                    for (Cart product : selectedItems) Log.d(String.valueOf(product.getProductID()), String.valueOf(product.getQuantity()));
                }
                else {
                    for(int i = 0; i < selectedItems.size(); ++i) {
                        if(selectedItems.get(i).getProductID() == item.getProductID()) {
                            selectedItems.remove(i);
                            break;
                        }
                    }
                }
            }
        });
    }
    final class CartItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView cartItemName, cartItemPrice;
        EditText cartItemQuantity;
        Button quantityMinus, quantityPlus;
        CheckBox cartItemSelect;
        ImageView cartItemImage;

        public CartItemHolder(@NonNull View itemView) {
            super(itemView);
            cartItemName = itemView.findViewById(R.id.cartItemName);
            cartItemImage = itemView.findViewById(R.id.cartItemImage);
            cartItemPrice = itemView.findViewById(R.id.cartItemPrice);
            cartItemQuantity = itemView.findViewById(R.id.cartItemQuantity);
            quantityMinus = itemView.findViewById(R.id.quantityMinus);
            quantityPlus = itemView.findViewById(R.id.quantityPlus);
            cartItemSelect = itemView.findViewById(R.id.cartItemSelect);
//            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            Cart item = items.get(pos);
//            try (tb_CartHandler cart = new tb_CartHandler(view.getContext())) {
//                ArrayList<Cart> cartItems = cart.getAllProduct();
//                boolean isExist = false;
//                for(Cart product: cartItems) {
//                    if (product.getProductID() == item.getId()) {
//                        isExist = true;
//                        cart.editProduct(product.getProductID(), product.getQuantity()+1);
//                        break;
//                    }
//                }
//                if(!isExist) cart.addProduct(item.getId());
//            }
            Toast.makeText(view.getContext(), "Thêm sản phẩm " + item.getProductID() + " vào giỏ hàng thành công.", Toast.LENGTH_SHORT).show();
        }
    }
}
