package com.example.nativeadsmaster_realfz_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdLoader adLoader;
    private List<Object> list = new ArrayList<>();
    private List<UnifiedNativeAd> adsNative = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, "ca-app-pub-3940256099942544/2247696110");

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadData();
        loadNative();
    }

    private void loadNative() {
        AdLoader.Builder builder = new AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110");
        adLoader = builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                adsNative.add(unifiedNativeAd);
                if (!adLoader.isLoading()) {
                    insertAds();
                }
            }
        }).withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int error) {
                if (!adLoader.isLoading()) {
                    insertAds();
                }
            }
        }).build();

        adLoader.loadAds(new AdRequest.Builder().build(), 3);
    }

    private void insertAds() {
        if (adsNative.size() <= 0){
            return;
        }

        int offset = (list.size() / adsNative.size() + 1 );
        int index = 0;
        for (UnifiedNativeAd ads : adsNative) {
            list.add(index, ads);
            index = index + offset;
        }
    }

    private void loadData() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://api.npoint.io/63c29a4452fcc3f9a643", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String respon = new String(responseBody);
                try {
                    JSONObject object = new JSONObject(respon);
                    JSONArray array = object.getJSONArray("Maps");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject data = array.getJSONObject(i);

                        String title = data.getString("judul_maps");
                        String img = data.getString("img_maps");
                        String desk = data.getString("desk_maps");
                        Model model = new Model(title, img, desk);
                        list.add(model);
                    }
                    Adapter adapter = new Adapter(list, MainActivity.this);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}