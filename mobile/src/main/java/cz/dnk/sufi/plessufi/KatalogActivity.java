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
import java.util.Locale;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class KatalogActivity extends FragmentActivity {

    protected static List<Category> mCategories;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
     * will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_katalog);

        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        mCategories = new ArrayList<Category>();
        try {
            InputStream data = getResources().openRawResource(R.raw.listek);
            mCategories = categoryListFromJSON(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(
                getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.katalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a DummySectionFragment (defined as a static inner class
            // below) with the page number as its lone argument.
            Fragment fragment = new DummySectionFragment();
            Bundle args = new Bundle();
            args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return mCategories.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mCategories.get(position).getCategory();
        }
    }

    /**
     * A dummy fragment representing a section of the app, but that simply
     * displays dummy text.
     */
    public static class DummySectionFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public static final String ARG_SECTION_NUMBER = "section_number";

        public DummySectionFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_zzbozi_dummy,
                    container, false);
            int num = getArguments().getInt(ARG_SECTION_NUMBER, 1);
            GridView mGridView = (GridView) rootView.findViewById(R.id.gridview);

            List<Category> categories = KatalogActivity.mCategories;
            Category category = categories.get(num);
            List<Item> items = category.getItems();
            if (items == null) {
                items = new ArrayList<Item>();
            }
            MyAdapter adapter = new MyAdapter(getActivity(), items);
            mGridView.setAdapter(adapter);
            return rootView;
        }

        private class MyAdapter extends BaseAdapter {
            private Context mContext;
            private List<Item> mItems;

            public MyAdapter (Context c, List<Item> i) {
                mContext = c;
                mItems = i;
            }

            public int getCount() {
                return mItems.size();
            }

            @Override
            public Object getItem(int position) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public long getItemId(int position) {
                // TODO Auto-generated method stub
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                if (convertView == null) {
                    view = View.inflate(mContext, R.layout.item_bar_zbozi, null);
                    //textView.setLayoutParams(new GridView.LayoutParams(316, 116));
                    view.setPadding(8, 8, 8, 8);
                } else {
                    view = convertView;
                }
                Item item = mItems.get(position);
                final TextView titleView = (TextView) view.findViewById(R.id.title);
                final TextView subtitleView = (TextView) view.findViewById(R.id.subtitle);
                final TextView priceView = (TextView) view.findViewById(R.id.price);
                titleView.setText(Html.fromHtml(item.getTitle()));
                subtitleView.setText(Html.fromHtml(item.getSubtitle()));
                priceView.setText(Html.fromHtml(item.getPrice()));
                //textView.setText("ddd");
                ///TextView size = (TextView) textView.findViewById(R.id.size);
                //int w = size.getMeasuredWidth();

                return view;
                //return new View(mContext);
            }

            @Override
            public int getViewTypeCount() {
                // TODO Auto-generated method stub
                return 1;
            }
        }

    }

    public static List<Category> categoryListFromJSON(InputStream in) throws IOException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        List<Category> categories = new ArrayList<Category>();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("result")) {
                reader.beginArray();
                while (reader.hasNext()) {
                    Category category = gson.fromJson(reader, Category.class);
                    categories.add(category);
                }
                reader.endArray();
            }
        }
        reader.endObject();
        reader.close();
        return categories;
    }

    private class Category {
        private String category;
        private List<Item> items;

        public String getCategory() {
            return category;
        }

        public List<Item> getItems() {
            return items;
        }
    }

    private class Item {
        private String title;
        private String subtitle;
        private String price;

        public String getTitle() {
            return title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public String getPrice() {
            return price;
        }

    }
}


//public class KatalogActivity extends ActionBarActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_katalog);
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.katalog, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}
