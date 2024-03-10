package homes.hieuiot.pluslistview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setMusicList();
        EditText txtSong = findViewById(R.id.txtAddSong);
        EditText txtSinger = findViewById(R.id.txtAddSinger);
        txtSong.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                ListView listView = findViewById(R.id.list_music);

                if(b) {
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
                else {
                    if(txtSong.getText().toString().trim().equals("")) {
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
            }
        });
        txtSinger.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(txtSinger.getText().toString().trim().equals("")) {
                    Button btnAddSong = findViewById(R.id.btnAddSong);
                    btnAddSong.setEnabled(true);
                }
            }
        });
    }
    public void changeSongName(View v) {
        EditText mEdit = (EditText) findViewById(R.id.name);
        mEdit.setFocusable(true);
    }
    public void addSong(View v) {
        String song = findViewById(R.id.txtAddSong).toString();
        String singer = findViewById(R.id.txtAddSinger).toString();
        Log.d(song, singer);
        musicLists.add(new MusicList(song, singer));
        MusicAdapter musicAdapter = new MusicAdapter(this, musicLists);
        ListView listView = findViewById(R.id.list_music);

        SwipeActionAdapter sAdapter = new SwipeActionAdapter(musicAdapter);
        sAdapter.setListView(listView);
        listView.setAdapter(sAdapter);
        sAdapter
                .addBackground(SwipeDirection.DIRECTION_NORMAL_LEFT,R.layout.row_bg_left);
        sAdapter.notifyDataSetChanged();
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
        getMusicList();
        MusicAdapter musicAdapter = new MusicAdapter(this, musicLists);
        ListView listView = findViewById(R.id.list_music);

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
//                    Toast.makeText(
//                            this,
//                            dir + " swipe Action triggered on " + sAdapter.getItem(position),
//                            Toast.LENGTH_SHORT
//                    ).show();
                    sAdapter.notifyDataSetChanged();
                }
            }
//
//            @Override
//            public void onSwipeStarted(ListView listView, int position, SwipeDirection direction) {
//                // User is swiping
//            }
//
//            @Override
//            public void onSwipeEnded(ListView listView, int position, SwipeDirection direction) {
//                // User stopped swiping (lifted finger from the screen)
//            }
        });
    }

}