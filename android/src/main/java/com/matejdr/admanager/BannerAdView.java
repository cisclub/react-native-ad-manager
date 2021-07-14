package com.matejdr.admanager;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.views.view.ReactViewGroup;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.ResponseInfo;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.admanager.AdManagerAdView;
import com.google.android.gms.ads.AdSize;

import java.util.ArrayList;

import com.google.android.gms.ads.admanager.AppEventListener;
import com.matejdr.admanager.customClasses.CustomTargeting;
import com.matejdr.admanager.utils.Targeting;

import com.criteo.publisher.Bid;
import com.criteo.publisher.BidResponseListener;
import com.criteo.publisher.Criteo;
import com.criteo.publisher.model.BannerAdUnit;
import com.criteo.publisher.model.NativeAdUnit;

public class BannerAdView extends ReactViewGroup implements AppEventListener, LifecycleEventListener {

    protected AdManagerAdView adView;

    String[] testDevices;
    AdSize[] validAdSizes;
    String adUnitID;
    AdSize adSize;

    // Targeting
    Boolean hasTargeting = true;
    CustomTargeting[] customTargeting;
    String[] categoryExclusions;
    String[] keywords;
    String contentURL;
    String publisherProvidedID;
    Location location;
    String correlator;

    public static final String HOME_LEADER = "/1019334/GULFNEWS/Gulfnews_App/gulfnews_newapp_home/gulfnews_newapp_home_leaderboard";
    public static final String HOME_BANNER = "/1019334/GULFNEWS/Gulfnews_App/gulfnews_newapp_home/gulfnews_newapp_home_mpu";

    public static final String HOME_NATIVE_1 = "/1019334/GULFNEWS/Gulfnews_App/gulfnews_newapp_native/gulfnews_newapp_home_native1";
    public static final String HOME_NATIVE_2 = "/1019334/GULFNEWS/Gulfnews_App/gulfnews_newapp_native/gulfnews_newapp_home_native2";
    public static final String HOME_NATIVE_3 = "/1019334/GULFNEWS/Gulfnews_App/gulfnews_newapp_native/gulfnews_newapp_home_native3";
    public static final String HOME_NATIVE_4 = "/1019334/GULFNEWS/Gulfnews_App/gulfnews_newapp_native/gulfnews_newapp_home_native4";
    public static final String HOME_NATIVE_5 = "/1019334/GULFNEWS/Gulfnews_App/gulfnews_newapp_native/gulfnews_newapp_home_native5";
    public static final String HOME_NATIVE_6 = "/1019334/GULFNEWS/Gulfnews_App/gulfnews_newapp_native/gulfnews_newapp_home_native6";

    public static final String SECTION_LEADER = "/1019334/GULFNEWS/Gulfnews_App/gulfnews_newapp_sections/gulfnews_newapp_sections_leaderboard";
    public static final String SECTION_BANNER_1 = "/1019334/GULFNEWS/Gulfnews_App/gulfnews_newapp_sections/gulfnews_newapp_sections_mpu1";
    public static final String SECTION_BANNER_2 = "/1019334/GULFNEWS/Gulfnews_App/gulfnews_newapp_sections/gulfnews_newapp_sections_mpu2";

    public static final String SECTION_NATIVE_1 = "/1019334/GULFNEWS/Gulfnews_App/gulfnews_newapp_native/gulfnews_newapp_sections_native1";
    public static final String SECTION_NATIVE_2 = "/1019334/GULFNEWS/Gulfnews_App/gulfnews_newapp_native/gulfnews_newapp_sections_native2";
    public static final String SECTION_NATIVE_3 = "/1019334/GULFNEWS/Gulfnews_App/gulfnews_newapp_native/gulfnews_newapp_sections_native3";
    public static final String SECTION_NATIVE_4 = "/1019334/GULFNEWS/Gulfnews_App/gulfnews_newapp_native/gulfnews_newapp_sections_native4";
    public static final String SECTION_NATIVE_5 = "/1019334/GULFNEWS/Gulfnews_App/gulfnews_newapp_native/gulfnews_newapp_sections_native5";
    public static final String SECTION_NATIVE_6 = "/1019334/GULFNEWS/Gulfnews_App/gulfnews_newapp_native/gulfnews_newapp_sections_native6";

    public static final BannerAdUnit CRITEO_HOME_LEADER = new BannerAdUnit(HOME_LEADER, new com.criteo.publisher.model.AdSize (320, 50));
    public static final BannerAdUnit CRITEO_HOME_BANNER = new BannerAdUnit(HOME_BANNER, new com.criteo.publisher.model.AdSize(300, 250));
    public static final BannerAdUnit CRITEO_HOME_BANNER_TABLET = new BannerAdUnit(HOME_BANNER, new com.criteo.publisher.model.AdSize (320, 50));

    public static final NativeAdUnit CRITEO_HOME_NATIVE_1 = new NativeAdUnit(HOME_NATIVE_1);
    public static final NativeAdUnit CRITEO_HOME_NATIVE_2 = new NativeAdUnit(HOME_NATIVE_2);
    public static final NativeAdUnit CRITEO_HOME_NATIVE_3 = new NativeAdUnit(HOME_NATIVE_3);
    public static final NativeAdUnit CRITEO_HOME_NATIVE_4 = new NativeAdUnit(HOME_NATIVE_4);
    public static final NativeAdUnit CRITEO_HOME_NATIVE_5 = new NativeAdUnit(HOME_NATIVE_5);
    public static final NativeAdUnit CRITEO_HOME_NATIVE_6 = new NativeAdUnit(HOME_NATIVE_6);

    public static final BannerAdUnit CRITEO_SECTION_LEADER = new BannerAdUnit(SECTION_LEADER, new com.criteo.publisher.model.AdSize(320, 50));
    public static final BannerAdUnit CRITEO_SECTION_BANNER_1 = new BannerAdUnit(SECTION_BANNER_1, new com.criteo.publisher.model.AdSize(300, 250));
    public static final BannerAdUnit CRITEO_SECTION_BANNER_2 = new BannerAdUnit(SECTION_BANNER_2, new com.criteo.publisher.model.AdSize(300, 250));

    public static final NativeAdUnit CRITEO_SECTION_NATIVE_1 = new NativeAdUnit(SECTION_NATIVE_1);
    public static final NativeAdUnit CRITEO_SECTION_NATIVE_2 = new NativeAdUnit(SECTION_NATIVE_2);
    public static final NativeAdUnit CRITEO_SECTION_NATIVE_3 = new NativeAdUnit(SECTION_NATIVE_3);
    public static final NativeAdUnit CRITEO_SECTION_NATIVE_4 = new NativeAdUnit(SECTION_NATIVE_4);
    public static final NativeAdUnit CRITEO_SECTION_NATIVE_5 = new NativeAdUnit(SECTION_NATIVE_5);
    public static final NativeAdUnit CRITEO_SECTION_NATIVE_6 = new NativeAdUnit(SECTION_NATIVE_6);

    public BannerAdView(final Context context, ReactApplicationContext applicationContext) {
        super(context);
        applicationContext.addLifecycleEventListener(this);
        this.createAdView();
    }

    private void createAdView() {
        if (this.adView != null) this.adView.destroy();

        final Context context = getContext();
        this.adView = new AdManagerAdView(context);
        this.adView.setAppEventListener(this);
        this.adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                int width = adView.getAdSize().getWidthInPixels(context);
                int height = adView.getAdSize().getHeightInPixels(context);
                int left = adView.getLeft();
                int top = adView.getTop();
                adView.measure(width, height);
                adView.layout(left, top, left + width, top + height);
                sendOnSizeChangeEvent();
                WritableMap ad = Arguments.createMap();
                ad.putString("type", "banner");

                WritableMap gadSize = Arguments.createMap();
                gadSize.putDouble("width", adView.getAdSize().getWidth());
                gadSize.putDouble("height", adView.getAdSize().getHeight());
                ad.putMap("gadSize", gadSize);

                sendEvent(RNAdManagerBannerViewManager.EVENT_AD_LOADED, ad);
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                String errorDomain = loadAdError.getDomain();
                int errorCode = loadAdError.getCode();
                String errorMessage = loadAdError.getMessage();
                ResponseInfo responseInfo = loadAdError.getResponseInfo();
                AdError cause = loadAdError.getCause();

                WritableMap event = Arguments.createMap();

                WritableMap error = Arguments.createMap();
                error.putString("errorDomain", errorDomain);
                error.putString("errorCode", errorCode + "");
                error.putString("message", errorMessage);
                error.putString("responseInfo", responseInfo.toString());
                error.putString("cause", cause.toString());

                event.putMap("error", error);
                sendEvent(RNAdManagerBannerViewManager.EVENT_AD_FAILED_TO_LOAD, event);
            }

            @Override
            public void onAdOpened() {
                sendEvent(RNAdManagerBannerViewManager.EVENT_AD_OPENED, null);
            }

            @Override
            public void onAdClosed() {
                sendEvent(RNAdManagerBannerViewManager.EVENT_AD_CLOSED, null);
            }
        });
    }

    private void sendOnSizeChangeEvent() {
        int width;
        int height;
        WritableMap event = Arguments.createMap();
        AdSize adSize = this.adView.getAdSize();
        width = adSize.getWidth();
        height = adSize.getHeight();
        event.putString("type", "banner");
        event.putDouble("width", width);
        event.putDouble("height", height);
        sendEvent(RNAdManagerBannerViewManager.EVENT_SIZE_CHANGE, event);
    }

    private void sendEvent(String name, @Nullable WritableMap event) {
        ReactContext reactContext = (ReactContext) getContext();
        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                getId(),
                name,
                event);
    }

    public void loadBanner() {
        ArrayList<AdSize> adSizes = new ArrayList<AdSize>();
        if (this.adSize != null) {
            adSizes.add(this.adSize);
        }
        if (this.validAdSizes != null) {
            for (int i = 0; i < this.validAdSizes.length; i++) {
                adSizes.add(this.validAdSizes[i]);
            }
        }

        if (adSizes.size() == 0) {
            adSizes.add(AdSize.BANNER);
        }

        AdSize[] adSizesArray = adSizes.toArray(new AdSize[adSizes.size()]);
        this.adView.setAdSizes(adSizesArray);

        boolean isNative = false;

        if(adUnitID == HOME_NATIVE_1 ||
                adUnitID == HOME_NATIVE_2 ||
                adUnitID == HOME_NATIVE_3 ||
                adUnitID == HOME_NATIVE_4 ||
                adUnitID == HOME_NATIVE_5 ||
                adUnitID == HOME_NATIVE_6 ||
                adUnitID == SECTION_NATIVE_1 ||
                adUnitID == SECTION_NATIVE_2 ||
                adUnitID == SECTION_NATIVE_3 ||
                adUnitID == SECTION_NATIVE_4 ||
                adUnitID == SECTION_NATIVE_5 ||
                adUnitID == SECTION_NATIVE_6
        ) {
            isNative = true;
        }

        if(isNative) {
            NativeAdUnit nativeAdUnit;
            switch (adUnitID) {
                case HOME_NATIVE_1:
                    nativeAdUnit = CRITEO_HOME_NATIVE_1;
                    break;
                case HOME_NATIVE_2:
                    nativeAdUnit = CRITEO_HOME_NATIVE_2;
                    break;
                case HOME_NATIVE_3:
                    nativeAdUnit = CRITEO_HOME_NATIVE_3;
                    break;
                case HOME_NATIVE_4:
                    nativeAdUnit = CRITEO_HOME_NATIVE_4;
                    break;
                case HOME_NATIVE_5:
                    nativeAdUnit = CRITEO_HOME_NATIVE_5;
                    break;
                case HOME_NATIVE_6:
                    nativeAdUnit = CRITEO_HOME_NATIVE_6;
                    break;
                case SECTION_NATIVE_1:
                    nativeAdUnit = CRITEO_SECTION_NATIVE_1;
                    break;
                case SECTION_NATIVE_2:
                    nativeAdUnit = CRITEO_SECTION_NATIVE_2;
                    break;
                case SECTION_NATIVE_3:
                    nativeAdUnit = CRITEO_SECTION_NATIVE_3;
                    break;
                case SECTION_NATIVE_4:
                    nativeAdUnit = CRITEO_SECTION_NATIVE_4;
                    break;
                case SECTION_NATIVE_5:
                    nativeAdUnit = CRITEO_SECTION_NATIVE_5;
                    break;
                case SECTION_NATIVE_6:
                    nativeAdUnit = CRITEO_SECTION_NATIVE_6;
                    break;
                default:
                    nativeAdUnit = CRITEO_HOME_NATIVE_1;
            }

            Criteo.getInstance().loadBid(nativeAdUnit, new BidResponseListener() {
                @Override
                public void onResponse(@Nullable Bid bid) {
                    LoadBid(bid);
                }
            });
        }
        else {
            BannerAdUnit bannerAdUnit;

            switch (adUnitID) {
                case HOME_LEADER:
                    bannerAdUnit = CRITEO_HOME_LEADER;
                    break;
                case HOME_BANNER:
                    if (this.adView.getAdSize() == AdSize.BANNER)
                        bannerAdUnit = CRITEO_HOME_BANNER_TABLET;
                    else
                        bannerAdUnit = CRITEO_HOME_BANNER;
                    break;
                case SECTION_LEADER:
                    bannerAdUnit = CRITEO_SECTION_LEADER;
                    break;
                case SECTION_BANNER_1:
                    bannerAdUnit = CRITEO_SECTION_BANNER_1;
                    break;
                case SECTION_BANNER_2:
                    bannerAdUnit = CRITEO_SECTION_BANNER_2;
                    break;
                default:
                    bannerAdUnit = CRITEO_HOME_LEADER;
            }
            Criteo.getInstance().loadBid(bannerAdUnit, new BidResponseListener() {
                @Override
                public void onResponse(@Nullable Bid bid) {
                    LoadBid(bid);
                }
            });
        }
    }

    public void LoadBid(@Nullable Bid bid) {
        AdManagerAdRequest.Builder adRequestBuilder = new AdManagerAdRequest.Builder();

        if (bid != null) {
            Criteo.getInstance().enrichAdObjectWithBid(adRequestBuilder, bid);
        }

        if (correlator == null) {
            correlator = (String) Targeting.getCorelator(adUnitID);
        }
        Bundle bundle = new Bundle();
        bundle.putString("correlator", correlator);

        adRequestBuilder.addNetworkExtrasBundle(AdMobAdapter.class, bundle);

        // Targeting
        if (hasTargeting) {
            if (customTargeting != null && customTargeting.length > 0) {
                for (int i = 0; i < customTargeting.length; i++) {
                    String key = customTargeting[i].key;
                    if (!key.isEmpty()) {
                        if (customTargeting[i].value != null && !customTargeting[i].value.isEmpty()) {
                            adRequestBuilder.addCustomTargeting(key, customTargeting[i].value);
                        } else if (customTargeting[i].values != null && !customTargeting[i].values.isEmpty()) {
                            adRequestBuilder.addCustomTargeting(key, customTargeting[i].values);
                        }
                    }
                }
            }
            if (categoryExclusions != null && categoryExclusions.length > 0) {
                for (int i = 0; i < categoryExclusions.length; i++) {
                    String categoryExclusion = categoryExclusions[i];
                    if (!categoryExclusion.isEmpty()) {
                        adRequestBuilder.addCategoryExclusion(categoryExclusion);
                    }
                }
            }
            if (keywords != null && keywords.length > 0) {
                for (int i = 0; i < keywords.length; i++) {
                    String keyword = keywords[i];
                    if (!keyword.isEmpty()) {
                        adRequestBuilder.addKeyword(keyword);
                    }
                }
            }
            if (contentURL != null) {
                adRequestBuilder.setContentUrl(contentURL);
            }
            if (publisherProvidedID != null) {
                adRequestBuilder.setPublisherProvidedId(publisherProvidedID);
            }
            if (location != null) {
                adRequestBuilder.setLocation(location);
            }
        }

        adView.loadAd(adRequestBuilder.build());
        this.addView(this.adView);
    }

    public void setAdUnitID(String adUnitID) {
        if (this.adUnitID != null) {
            // We can only set adUnitID once, so when it was previously set we have
            // to recreate the view
            this.createAdView();
        }
        this.adUnitID = adUnitID;
        this.adView.setAdUnitId(adUnitID);
    }

    public void setTestDevices(String[] testDevices) {
        this.testDevices = testDevices;
    }

    // Targeting
    public void setCustomTargeting(CustomTargeting[] customTargeting) {
        this.customTargeting = customTargeting;
    }

    public void setCategoryExclusions(String[] categoryExclusions) {
        this.categoryExclusions = categoryExclusions;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    public void setContentURL(String contentURL) {
        this.contentURL = contentURL;
    }

    public void setPublisherProvidedID(String publisherProvidedID) {
        this.publisherProvidedID = publisherProvidedID;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setAdSize(AdSize adSize) {
        this.adSize = adSize;
    }

    public void setValidAdSizes(AdSize[] adSizes) {
        this.validAdSizes = adSizes;
    }

    public void setCorrelator(String correlator) {
        this.correlator = correlator;
    }

    @Override
    public void onAppEvent(String name, String info) {
        WritableMap event = Arguments.createMap();
        event.putString("name", name);
        event.putString("info", info);
        sendEvent(RNAdManagerBannerViewManager.EVENT_APP_EVENT, event);
    }

    @Override
    public void onHostResume() {
        if (this.adView != null) {
            this.adView.resume();
        }
    }

    @Override
    public void onHostPause() {
        if (this.adView != null) {
            this.adView.pause();
        }
    }

    @Override
    public void onHostDestroy() {
        if (this.adView != null) {
            this.adView.destroy();
        }
    }
}
