package cz.dnk.sufi.plessufi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ActionBarActivityWithDrawer extends ActionBarActivity {
    protected DrawerLayout mDrawerLayout;

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    public void initializeDrawer(DrawerLayout drawerLayout) {
        mDrawerLayout = drawerLayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar_with_drawer);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.drawer, new MainActivity.DrawerFragment())
                    .commit();
        }
    }

    public void onDrawerItemClick(int position) {
            Intent intent = new Intent(this, ProgramActivity.class);
            startActivity(intent);

            //https://stackoverflow.com/questions/4486034/get-root-view-from-current-activity
            mDrawerLayout.closeDrawer(findViewById(R.id.drawer));
    }

    public static class DrawerFragment extends Fragment {
        private String[] mItems;
        private ListView mDrawerList;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_drawer, container, false);

            mItems = getResources().getStringArray(R.array.testArray);
            mDrawerList = (ListView) rootView;
            mDrawerList.setAdapter(new ArrayAdapter<String>(getActivity(),
                    R.layout.drawer_list_item, mItems));
            mDrawerList.setOnItemClickListener(new ItemClickListener());

            return rootView;
        }

        private class ItemClickListener implements ListView.OnItemClickListener {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDrawerList.setItemChecked(position, true);
                ((ActionBarActivityWithDrawer) getActivity()).onDrawerItemClick(position);
            }
        }
    }
}
