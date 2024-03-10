package homes.hieuiot.pluslistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MusicAdapter extends ArrayAdapter<MusicList> {
    public MusicAdapter(Context context, ArrayList<MusicList> musicLists) {
        super(context, 0, musicLists);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        MusicList song = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.music_list, parent, false);
        }
        // Lookup view for data population
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView singer = (TextView) convertView.findViewById(R.id.singer);
        // Populate the data into the template view using the data object
        name.setText(song.Name);
        singer.setText(song.Singer);
        // Return the completed view to render on screen
        return convertView;
    }
}
