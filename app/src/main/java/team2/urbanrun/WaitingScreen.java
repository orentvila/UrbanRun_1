package team2.urbanrun;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class WaitingScreen extends ListActivity {

    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_screen);

        map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        int Radius=0;
        double centerLat=0,centerLng=0;

        int[] scores = {};
        int myIndex = 0;
        String[] names = {}, images = {},checkImages={};
        try {
            String res = (new ServletGetGameProperties().execute(getIntent().getExtras().getString("GameID"),
                    getIntent().getExtras().getString("myName")).get());
            Log.d("Aviv", "Response from servlet: " + res);
            JSONObject response =  new JSONObject(res);
            String time = response.getString("time");
            int radius = response.getInt("Radius");
            int CenterLat = response.getInt("CenterLat");
            int CenterLng = response.getInt("CenterLng");
            JSONArray array = response.getJSONArray("players");

            //////////set time ////////
            EditText timeText = (EditText) findViewById(R.id.TimeText);
            timeText.setText(time);

            int numPlayers = array.length();
            names = new String[numPlayers];
            images = new String[numPlayers];
            checkImages = new String[numPlayers];
            ///////// set players //////////
            for (int i = 0; i < numPlayers; i++) {
                JSONObject player = array.getJSONObject(i);
                names[i] = player.getString("FullName");
                images[i] = player.getString("ImageURL");
                checkImages[i] = player.getString("ImageURL");
                if (player.getString("Username").equals(getIntent().getExtras().getString("myName")))
                    myIndex = i;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        setListAdapter(new FriendsListAdapter(this, android.R.layout.simple_list_item_1, R.id.textView ,
                names, images, checkImages ,myIndex));



    }



    private class FriendsListAdapter extends ArrayAdapter<String> {
        private String[] names, images, checkImages;
        int[] scores;
        int myIndex;

        public FriendsListAdapter(Context context, int resource, int textViewResourceId
                , String[] _names, String[] _img, String[] _cImg, int _myIndex) {
            super(context, resource, textViewResourceId, _names);
            names = _names;
            images = _img;
            checkImages = _cImg;
            myIndex = _myIndex;

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_waiting_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}