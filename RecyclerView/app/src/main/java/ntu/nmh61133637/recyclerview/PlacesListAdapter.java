package ntu.nmh61133637.recyclerview;

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

import java.util.ArrayList;

public class PlacesListAdapter extends RecyclerView.Adapter<PlacesListAdapter.PlaceItemHolder> {
    Context context;
    ArrayList<PlacesList> placesList;

    public PlacesListAdapter(Context context, ArrayList<PlacesList> placesList) {
        this.context = context;
        this.placesList = placesList;
    }

    @NonNull
    @Override
    public PlaceItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(context);
        View viewItem = li.inflate(R.layout.place_layout, parent, false);
        PlaceItemHolder item = new PlaceItemHolder(viewItem);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceItemHolder holder, int position) {
        PlacesList places_list = placesList.get(position);
        holder.placeName.setText(places_list.placeName);
        String packageName = holder.itemView.getContext().getPackageName();
        @SuppressLint("DiscouragedApi") int imageID = holder.itemView.getResources().getIdentifier(places_list.placeImageName, "mipmap", packageName);
        holder.placeImage.setImageResource(imageID);
    }

    @Override
    public int getItemCount() {
        return placesList.size();
    }

    final class PlaceItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView placeName;
        ImageView placeImage;

        public PlaceItemHolder(@NonNull View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.placeName);
            placeImage = itemView.findViewById(R.id.placeImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            PlacesList item = placesList.get(pos);
            Toast.makeText(view.getContext(), "Bạn vừa chọn " + item.getPlaceName(), Toast.LENGTH_SHORT).show();
        }
    }
}
