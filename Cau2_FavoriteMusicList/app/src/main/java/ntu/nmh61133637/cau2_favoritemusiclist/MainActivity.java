package ntu.nmh61133637.cau2_favoritemusiclist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.wdullaer.swipeactionadapter.SwipeActionAdapter;
import com.wdullaer.swipeactionadapter.SwipeDirection;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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
        getMusicList();
        setMusicList();
        checkEditTextFocus();
        changeSongName();
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

    @SuppressLint("SetTextI18n")
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
    private void getMusicList() {
        try {
            InputStream file = getAssets().open("music_list.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("Song");
            musicLists = new ArrayList<>();
            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) node;
                    String name = e.getElementsByTagName("Name").item(0).getTextContent();
                    String singer = e.getElementsByTagName("Singer").item(0).getTextContent();
                    musicLists.add(new MusicList(name, singer));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setMusicList() {
        MusicAdapter musicAdapter = new MusicAdapter(this, musicLists);

        SwipeActionAdapter sAdapter = new SwipeActionAdapter(musicAdapter);
        sAdapter.setListView(listView);
        listView.setAdapter(sAdapter);
        sAdapter
                .addBackground(SwipeDirection.DIRECTION_NORMAL_LEFT,R.layout.row_bg_left);
        sAdapter.setSwipeActionListener(new SwipeActionAdapter.SwipeActionListener(){
            @Override
            public boolean hasActions(int position, SwipeDirection direction){
                if(direction.isLeft()) return true; // Change this to false to disable left swipes
                if(direction.isRight()) return true;
                return false;
            }

            @Override
            public boolean shouldDismiss(int position, SwipeDirection direction){
                // Only dismiss an item when swiping normal left
                return direction == SwipeDirection.DIRECTION_NORMAL_LEFT;
            }

            @Override
            public void onSwipe(int[] positionList, SwipeDirection[] directionList){
                for(int i=0;i<positionList.length;i++) {
                    SwipeDirection direction = directionList[i];
                    int position = positionList[i];
                    String dir = "";

                    switch (direction) {
                        case DIRECTION_FAR_LEFT:
                            musicLists.remove(position);
                            dir = "Far left";
                            break;
                        case DIRECTION_NORMAL_LEFT:
                            dir = "Left";
                            break;
                        case DIRECTION_FAR_RIGHT:
                            dir = "Far right";
                            break;
                        case DIRECTION_NORMAL_RIGHT:
                            dir = "Right";
                            break;
                    }
                    sAdapter.notifyDataSetChanged();
                }
            }
        });
    }
    private void moveDownListView() {
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                ViewGroup.MarginLayoutParams margin = (ViewGroup.MarginLayoutParams) listView.getLayoutParams();
                margin.setMargins(0, 100, 2, 0);
                listView.setLayoutParams(margin);
            }
        };
        a.setDuration(3000);
        listView.startAnimation(a);
        txtSinger.setVisibility(View.VISIBLE);
    }
    private void moveUpListView() {
        txtSinger.setVisibility(View.INVISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                ViewGroup.MarginLayoutParams margin = (ViewGroup.MarginLayoutParams) listView.getLayoutParams();
                margin.setMargins(0, 0, 2, 0);
                listView.setLayoutParams(margin);
            }
        };
        a.setDuration(3000);
        listView.startAnimation(a);
    }
}