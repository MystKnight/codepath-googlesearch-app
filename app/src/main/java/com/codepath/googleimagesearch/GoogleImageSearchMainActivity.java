package com.codepath.googleimagesearch;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.codepath.googleimagesearch.models.GoogleImage;
import com.codepath.googleimagesearch.models.GoogleImageResponse;
import com.etsy.android.grid.StaggeredGridView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class GoogleImageSearchMainActivity extends AppCompatActivity implements GoogleImageFiltersDialog.GoogleImageFiltersDialogListener {

    private GoogleImageAdapter googleImageAdapter;
    private String searchQuery = "painting";
    private GoogleFilter googleFilter = new GoogleFilter();
    private static int MAX_PAGE = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_image_search_main);

        // Set app icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        // Initialize all the things
        List<GoogleImage> googleImages = new ArrayList<GoogleImage>();
        this.googleImageAdapter = new GoogleImageAdapter(this, googleImages);

        final StaggeredGridView gvPhotos = (StaggeredGridView)findViewById(R.id.gv_photos);
        gvPhotos.setAdapter(this.googleImageAdapter);

        gvPhotos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GoogleImage googleImage = googleImageAdapter.getItem(position);

                Intent intent = new Intent(GoogleImageSearchMainActivity.this, GoogleImageDetailActivity.class);
                intent.putExtra("url", googleImage.unescapedUrl);
                intent.putExtra("title", googleImage.titleNoFormatting);
                startActivity(intent);
            }
        });

        gvPhotos.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Apparently Google doesn't want to paginate anymore than 8 pages
                if (page < MAX_PAGE) {
                    loadData(page);
                }
            }
        });

        // Load initial set of data
        this.loadData(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_google_image_search_main, menu);

        // Add the action search bar to the top menu
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView)MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Set the new search query and reset the list
                searchQuery = query;

                // Reset search filters and adapter
                googleFilter.setDefaults();
                googleImageAdapter.clear();
                googleImageAdapter.heightRatios.clear();

                // Hide keyboard
                searchView.clearFocus();

                // Load in the new data
                loadData(0);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            // Show dialog fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            GoogleImageFiltersDialog googleImageFiltersDialog = GoogleImageFiltersDialog.newInstance(this.googleFilter);
            googleImageFiltersDialog.show(fragmentManager, "image_filter");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFinishDialog(GoogleFilter googleFilter) {
        this.googleFilter = googleFilter;

        // When we get new filters back, we want to clear our our existing adapter and start fresh
        googleImageAdapter.clear();
        googleImageAdapter.heightRatios.clear();

        this.loadData(0);
    }

    public void loadData(int currentPage) {
        // No network is available, don't even bother with a network call
        if (!isNetworkAvailable()) {
            Toast.makeText(this, "No Internet Available", Toast.LENGTH_LONG).show();
            return;
        }

        int start = currentPage * 8;
        Map filters = this.googleFilter.buildFilters();

        // Build retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ajax.googleapis.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Call the google image service
        GoogleImageService googleImageService = retrofit.create(GoogleImageService.class);
        Call<GoogleImageResponse> googleImageResponse = googleImageService.getImages(
                "1.0",
                this.searchQuery,
                8,
                start,
                filters);

        // Callback
        googleImageResponse.enqueue(new Callback<GoogleImageResponse>() {
            @Override
            public void onResponse(Response<GoogleImageResponse> response) {
                googleImageAdapter.addAll(response.body().responseData.results);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("Fail", t.getLocalizedMessage());
            }
        });
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
