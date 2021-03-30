package com.moondroid.pharmacyproject01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements MenuItem.OnActionExpandListener{

    private Toolbar toolbar;
    private ArrayList<ItemVO> itemVOData;
    private RecyclerView recyclerViewSearchActivity;
    private SearchView searchView;
    private String searchTarget;
    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //xml 참조영역
        toolbar = (Toolbar) findViewById(R.id.toolbar_search_activity);
        recyclerViewSearchActivity = (RecyclerView) findViewById(R.id.recycler_search_activity);

        //리사이클러뷰 제어
        recyclerViewSearchActivity.setLayoutManager(new LinearLayoutManagerWrapper(this, LinearLayoutManager.VERTICAL, false));
        itemVOData = new ArrayList<>();
        searchAdapter = new SearchAdapter(this, itemVOData);
        recyclerViewSearchActivity.setAdapter(searchAdapter);

        //toolbar 제어
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //검색기능 구현
        getMenuInflater().inflate(R.menu.menu_search_view, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.menu_search_activity_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconified(false);
        searchView.setQueryHint("약국 검색");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchTarget = query.replace(" ", "");
                itemVOData.clear();
                for (int i = 0 ; i< G.items.size();  i++){
                    ItemVO itemVO = G.items.get(i);
                    if (itemVO.getName().contains(searchTarget) || itemVO.getAddress().contains(searchTarget)) {
                        itemVOData.add(0, itemVO);
                        searchAdapter.notifyItemInserted(itemVOData.size() - 1);
                    }
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS |
                MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        //Call menu to be redrawn
        invalidateOptionsMenu();
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}