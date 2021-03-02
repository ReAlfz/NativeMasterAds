package com.example.nativeadsmaster_realfz_;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int menu_ads = 0;
    private static final int menu_default = 1;
    private List<Object> listed;
    private Context context;

    public Adapter(List<Object> list, Context context) {
        this.context = context;
        this.listed = list;
    }

    @Override
    public int getItemViewType(int position) {
        Object defaults = listed.get(position);
        if (defaults instanceof UnifiedNativeAd) {
            return menu_ads;
        }
        return menu_default;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case menu_ads:
                View viewAds = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_ads, parent, false);
                return new AdsItem(viewAds);
            case menu_default:

            default:
                View views = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
                return new ListHolder(views);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int ves = getItemViewType(position);
        switch (ves) {
            case menu_ads:
                UnifiedNativeAd nativeAd = (UnifiedNativeAd) listed.get(position);
                reloadAds(nativeAd, ((AdsItem) holder).getAdsView());
                break;

            case menu_default:

            default:
                ListHolder nList = (ListHolder) holder;
                Model data = (Model) listed.get(position);

                nList.title.setText(data.getTitle());
                nList.desk.setText(data.getDesk());
                Picasso.get()
                        .load(data.getUrl())
                        .into(nList.imageView);

                nList.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), Detail.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("title", data.getTitle());
                        bundle.putString("content", data.getDesk());
                        bundle.putString("img", data.getUrl());

                        intent.putExtras(bundle);
                        view.getContext().startActivity(intent);
                    }
                });
        }
    }

    @Override
    public int getItemCount() {
        return listed.size();
    }

    class ListHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, desk;

        public ListHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_item);
            imageView = itemView.findViewById(R.id.image_item);
            desk = itemView.findViewById(R.id.desk_item);
        }
    }

    public void reloadAds(UnifiedNativeAd uNative, UnifiedNativeAdView uView) {
        ((TextView) uView.getHeadlineView()).setText(uNative.getHeadline());
        ((TextView) uView.getBodyView()).setText(uNative.getBody());

        NativeAd.Image icons = uNative.getIcon();

        if (icons == null) {
            uView.getIconView().setVisibility(View.INVISIBLE);
        } else {
            ((ImageView) uView.getIconView()).setImageDrawable(icons.getDrawable());
            uView.getIconView().setVisibility(View.VISIBLE);
        }

        if (uNative.getPrice() == null) {
            uView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            uView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) uView.getPriceView()).setText(uNative.getPrice());
        }

        if (uNative.getStore() == null) {
            uView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            uView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) uView.getStoreView()).setText(uNative.getStore());
        }

        if (uNative.getStarRating() == null) {
            uView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) uView.getStarRatingView())
                    .setRating(uNative.getStarRating().floatValue());
            uView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (uNative.getAdvertiser() == null) {
            uView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) uView.getAdvertiserView()).setText(uNative.getAdvertiser());
            uView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        uView.setNativeAd(uNative);
    }
}
