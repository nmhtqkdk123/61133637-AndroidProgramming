package ntu.nmh61133637.shopeeapplicationfinalexam;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductItemHolder> {
    Context context;
    ArrayList<Product> products;
    public ProductAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }
    @NonNull
    @Override
    public ProductItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(context);
        View viewItem = li.inflate(R.layout.product_item, parent, false);
        return new ProductItemHolder(viewItem);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductItemHolder holder, int position) {
        Product product = products.get(position);
        holder.productName.setText(product.getName());
        String packageName = holder.itemView.getContext().getPackageName();
        @SuppressLint("DiscouragedApi") int imageID = holder.itemView.getResources().getIdentifier(product.getImage(), "mipmap", packageName);
        holder.productImage.setImageResource(imageID);
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY);
        holder.productPrice.setText("₫" + numberFormat.format(product.getPrice()));
        holder.productSaleCount.setText("Đã bán " + formatNumber(product.getSaleCount()));
    }
    @SuppressLint("DefaultLocale")
    public String formatNumber(int number) {
        if (number < 1000) return String.valueOf(number);
        else if (number < 1000000) return String.format("%.1fk", number / 1000.0);
        else return String.format("%.1fM", number / 1000000.0);
    }
    @Override
    public int getItemCount() {
        return products.size();
    }
    final class ProductItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView productName , productPrice, productSaleCount;
        ImageView productImage;

        public ProductItemHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productImage = itemView.findViewById(R.id.productImage);
            productPrice = itemView.findViewById(R.id.productPrice);
            productSaleCount = itemView.findViewById(R.id.productSaleCount);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            Product item = products.get(pos);
            Toast.makeText(view.getContext(), "Bạn vừa chọn " + item.getName(), Toast.LENGTH_SHORT).show();
        }
    }
}
