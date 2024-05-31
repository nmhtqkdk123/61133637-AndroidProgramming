package ntu.nmh61133637.shopeeapplicationfinalexam;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
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
    public ArrayList<Cart> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(ArrayList<Cart> selectedItems) {
        this.selectedItems = selectedItems;
    }
    private boolean isSelectAll = false;

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
        setQuantity(holder, item);
        setPlusQuantity(holder, item);
        setMinusQuantity(holder, position);
        selectCartItem(holder, item);
        selectAllItem();
    }
    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
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
    public void setQuantity(@NonNull CartAdapter.CartItemHolder holder, Cart item) {
        holder.cartItemQuantity.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_NEXT) {
                String s_quantity = holder.cartItemQuantity.getText().toString();
                if(s_quantity.equals("")) s_quantity += 1;
                int quantity = Integer.parseInt(s_quantity);
                if(quantity < 1) quantity = 1;
                else if(quantity > 99) quantity = 99;
                item.setQuantity(quantity);
                holder.cartItemQuantity.setText(String.valueOf(quantity));
                try (tb_CartHandler tb_cart = new tb_CartHandler(textView.getContext())) {
                    tb_cart.editProduct(item.getProductID(), quantity);
                }
                InputMethodManager imm = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                return true;
            }
            return false;
        });
    }
    public void setPlusQuantity(@NonNull CartAdapter.CartItemHolder holder, Cart item) {
        holder.quantityPlus.setOnClickListener(view -> {
            if(item.getQuantity() + 1 > 99) Toast.makeText(view.getContext(), "Quá số lượng sản phẩm có thể mua.", Toast.LENGTH_SHORT).show();
            else {
                item.setQuantity(item.getQuantity() + 1);
                holder.cartItemQuantity.setText(String.valueOf(item.getQuantity()));
                showTotalPrice();
                try (tb_CartHandler tb_cart = new tb_CartHandler(view.getContext())) {
                    tb_cart.editProduct(item.getProductID(), item.getQuantity());
                }
            }
        });
    }
    public void setMinusQuantity(@NonNull CartAdapter.CartItemHolder holder, int position) {
        Cart item = items.get(position);
        holder.quantityMinus.setOnClickListener(view -> {
            if(item.getQuantity() - 1 > 0) {
                item.setQuantity(item.getQuantity() - 1);
                holder.cartItemQuantity.setText(String.valueOf(item.getQuantity()));
                showTotalPrice();
                try (tb_CartHandler tb_cart = new tb_CartHandler(view.getContext())) {
                    tb_cart.editProduct(item.getProductID(), item.getQuantity());
                }
            } else {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
                alertDialog.setCancelable(true);
                alertDialog.setTitle("Xoá sản phẩm");
                alertDialog.setMessage("Bạn có chắc chắn muốn xoá sản phẩm này khỏi giỏ hàng không?");
                alertDialog.setPositiveButton("Không",
                        (dialog, which) -> {
                        });
                alertDialog.setNegativeButton("Có", (dialog, which) -> {
                    try(tb_CartHandler tb_cart = new tb_CartHandler(view.getContext())) {
                        tb_cart.removeProduct(items.get(position).getProductID());
                        TextView itemName = holder.itemView.findViewById(R.id.cartItemName);
                        Toast.makeText(view.getContext(), "Xoá sản phẩm " + itemName.getText() + " khỏi giỏ hàng thành công.", Toast.LENGTH_LONG).show();
                        for(int i = 0; i < selectedItems.size(); ++i) {
                            if(item.getProductID() == selectedItems.get(i).getProductID()) {
                                selectedItems.remove(i);
                                showTotalPrice();
                                break;
                            }
                        }
                        items.remove(position);
                        notifyItemRemoved(position);
                    }
                });
                AlertDialog dialog = alertDialog.create();
                dialog.show();
            }
        });
    }
    @SuppressLint("SetTextI18n")
    public void selectCartItem(@NonNull CartAdapter.CartItemHolder holder, Cart item) {
        holder.cartItemSelect.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if(isChecked) {
                selectedItems.add(item);
                showTotalPrice();
                for (Cart product : selectedItems) Log.d(String.valueOf(product.getProductID()), String.valueOf(product.getQuantity()));
            }
            else {
                for(int i = 0; i < selectedItems.size(); ++i) {
                    if(selectedItems.get(i).getProductID() == item.getProductID()) {
                        selectedItems.remove(i);
                        break;
                    }
                }
                showTotalPrice();
            }
        });
    }
    @SuppressLint("NotifyDataSetChanged")
    public void selectAllItem() {
        Activity activity = (Activity) context;
        CheckBox cb_selectAll = activity.findViewById(R.id.selectAll);
        cb_selectAll.setOnCheckedChangeListener(((compoundButton, isChecked) -> {
            if(isChecked) {
                isSelectAll = true;
                Log.d("OK", "OK");
                notifyDataSetChanged();
            }
        }));
    }
    @SuppressLint("SetTextI18n")
    protected void showTotalPrice() {
        int totalPrice = 0;
        for(Cart item : selectedItems) {
            try (tb_ProductHandler tb_product = new tb_ProductHandler(context.getApplicationContext())) {
                Product product = tb_product.getProduct(item.getProductID());
                totalPrice += item.getQuantity() * product.getPrice();
            }
        }
        Activity activity = (Activity) context;
        TextView tv_totalPrice = activity.findViewById(R.id.totalPrice);
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY);
        tv_totalPrice.setText("₫" + numberFormat.format(totalPrice));
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
            int pos = getBindingAdapterPosition();
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
            Toast.makeText(view.getContext(), "Thêm sản phẩm " + item.getProductID() + " vào giỏ hàng thành công.", Toast.LENGTH_LONG).show();
        }
    }
}
