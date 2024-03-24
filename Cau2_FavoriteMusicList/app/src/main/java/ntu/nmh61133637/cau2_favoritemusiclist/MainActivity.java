package ntu.nmh61133637.cau2_favoritemusiclist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
        findView();
    }

    public void checkEditTextFocus() {
        txtSong.setOnFocusChangeListener((view, b) -> {
            if(b) moveDownListView();
            else {
                if(txtSong.getText().toString().trim().equals("")) moveUpListView();
            }
        });
        txtSinger.setOnFocusChangeListener((view, b) -> {
            if(txtSinger.getText().toString().trim().equals("")) btnSong.setEnabled(true);
        });
    }
    public void findView() {
        txtSong = findViewById(R.id.txtAddSong);
        txtSinger = findViewById(R.id.txtAddSinger);
        btnSong = findViewById(R.id.btnSong);
        listView = findViewById(R.id.list_music);
    }

    public void songHandle(View view) {
        if(btnSong.getText().toString().equals("Thêm")) addSong(view);
        else {
            editSong(view);
            btnSong.setText("Thêm");
        };
    }

    private void addSong(View view) {
        txtSong = findViewById(R.id.txtAddSong);
        txtSinger = findViewById(R.id.txtAddSinger);
        String song = txtSong.getText().toString();
        String singer = txtSinger.getText().toString();
        musicLists.add(new MusicList(song, singer));
        setMusicList();
        txtSong.getText().clear();
        txtSinger.getText().clear();
        txtSinger.clearFocus();
        moveUpListView();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void editSong(View view) {
        txtSong = findViewById(R.id.txtAddSong);
        txtSinger = findViewById(R.id.txtAddSinger);
        String song = txtSong.getText().toString();
        String singer = txtSinger.getText().toString();
        musicLists.set(position, new MusicList(song, singer));
        setMusicList();
        txtSong.getText().clear();
        txtSinger.getText().clear();
        txtSinger.clearFocus();
        moveUpListView();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    
    public void changeSongName() {
        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            txtSong.setText(String.valueOf(musicLists.get(i).Name));
            txtSinger.setText(String.valueOf(musicLists.get(i).Singer));
            btnSong.setText("Sửa");
            position = i;
            btnSong.setEnabled(true);
            return true;
        });
    }
}