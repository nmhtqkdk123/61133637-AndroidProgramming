package ntu.nmh61133637.cau2_favoritemusiclist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<MusicList> musicLists;
    EditText txtSong, txtSinger;
    Button btnSong;
    ListView listView;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void findView() {
        txtSong = findViewById(R.id.txtAddSong);
        txtSinger = findViewById(R.id.txtAddSinger);
        btnSong = findViewById(R.id.btnSong);
        listView = findViewById(R.id.list_music);
    }

    public void songHandle(View view) {
    }
}