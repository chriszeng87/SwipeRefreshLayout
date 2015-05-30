package com.demievil.swiperefreshlayout;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    private final static String TAG = "MainActivity";
    private RefreshLayout mRefreshLayout;
    private SwipeMenuListView mListView;
    private ArrayAdapter<String> mArrayAdapter;
    private ArrayList<String> values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRefreshLayout = (RefreshLayout) findViewById(R.id.swipe_container);
        mListView = (SwipeMenuListView) findViewById(R.id.list);
        mRefreshLayout.setFooterView(this, mListView, R.layout.listview_footer);

        values = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            values.add("Item " + i);
        }
        mArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
        mListView.setAdapter(mArrayAdapter);

        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        mListView.setMenuCreator(creator);

        // step 2. listener item click event
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
//                ApplicationInfo item = mAppList.get(position);
                switch (index) {
                    case 0:
                        // open
//                        open(item);
                        break;
                    case 1:
                        // delete
//					delete(item);
//                        mAppList.remove(position);
//                        mAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });

        mRefreshLayout.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);

        mRefreshLayout.setRefreshing(true);
        mRefreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        values.add(0, "Swipe Down to Refresh " + values.size());
                        mArrayAdapter.notifyDataSetChanged();
                        mRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        mRefreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        values.add("Swipe Up to Load More " + values.size());
                        mArrayAdapter.notifyDataSetChanged();
                        mRefreshLayout.setLoading(false);
                    }
                }, 2000);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                if (mRefreshLayout !=null) {
                    mRefreshLayout.setRefreshing(true);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            values.add(0, "Swipe Down to Refresh " + values.size());
                            mArrayAdapter.notifyDataSetChanged();
                            mRefreshLayout.setRefreshing(false);
                        }
                    }, 2000);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
