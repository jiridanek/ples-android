package cz.dnk.sufi.plessufi;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import cz.dnk.sufi.plessufi.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

public class ProgramActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);
        // Show the Up button in the action bar.
        setupActionBar();

        ListView listView = (ListView) findViewById(R.id.listview);
        InputStream data;
        List<Event> events;
        try {
            data = getResources().openRawResource(R.raw.program);
            events = eventListFromJSON(data);
            listView.setAdapter(new MyListAdapter(this, events));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Set up the {@link android.app.ActionBar}.
     */
    private void setupActionBar() {

        getActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.program, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static List<Event> eventListFromJSON(InputStream in) throws IOException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        List<Event> events = new ArrayList<Event>();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("result")) {
                reader.beginArray();
                while (reader.hasNext()) {
                    Event event = gson.fromJson(reader, Event.class);
                    events.add(event);
                }
                reader.endArray();
            }
        }
        reader.endObject();
        reader.close();
        return events;
    }

    private class Event {
        private String start_time;
        private String title;
        private String subtitle;

        public String getStartTime() {
            return start_time;
        }

        public String getTitle() {
            return title;
        }

        public String getSubtitle() {
            return subtitle;
        }

    }

    private class MyListAdapter extends BaseAdapter {

        private Context mContext;
        private List<Event> mArray;

        public MyListAdapter(Context c, List<Event> e) {
            mContext = c;
            mArray = e;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mArray.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            View view;
            if (arg1 == null) {
                view = View.inflate(mContext, R.layout.item_program, null);
            } else {
                view = arg1;
            }
            final TextView timeView = (TextView) view.findViewById(R.id.block_time);
            final TextView titleView = (TextView) view.findViewById(R.id.block_title);
            final TextView subtitleView = (TextView) view.findViewById(R.id.block_subtitle);
            //final ImageButton extraButton = (ImageButton) view.findViewById(R.id.extra_button);
            //final View primaryTouchTargetView = view.findViewById(R.id.list_item_middle_container);
            Event data = mArray.get(arg0);
            timeView.setText(data.getStartTime());
            titleView.setText(data.getTitle());
            subtitleView.setText(data.getSubtitle());

            return view;
        }
    }
}


