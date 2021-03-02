package com.example.nativeadsmaster_realfz_;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

public class AdsItem extends RecyclerView.ViewHolder {
    private UnifiedNativeAdView adsView;

    public AdsItem(@NonNull View itemView) {
        super(itemView);

        adsView = (UnifiedNativeAdView) itemView.findViewById(R.id.ad_view);

        adsView.setMediaView((MediaView) adsView.findViewById(R.id.ad_media));
        adsView.setHeadlineView(adsView.findViewById(R.id.ad_headline));
        adsView.setBodyView(adsView.findViewById(R.id.ad_body));
        adsView.setCallToActionView(adsView.findViewById(R.id.ad_call_to_action));
        adsView.setIconView(adsView.findViewById(R.id.ad_icon));
        adsView.setPriceView(adsView.findViewById(R.id.ad_price));
        adsView.setStarRatingView(adsView.findViewById(R.id.ad_stars));
        adsView.setStoreView(adsView.findViewById(R.id.ad_store));
        adsView.setAdvertiserView(adsView.findViewById(R.id.ad_advertiser));
    }

    public UnifiedNativeAdView getAdsView() {
        return adsView;
    }

}
