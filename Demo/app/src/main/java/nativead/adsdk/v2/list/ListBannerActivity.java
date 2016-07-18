package nativead.adsdk.v2.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import az.nativead.v2.AzAdError;
import az.nativead.v2.AzAdListener;
import az.nativead.v2.AzNativeAd;
import az.nativead.v2.AzNativeAdsManager;
import nativead.adsdk.v2.DotView;
import nativead.adsdk.v2.R;
import nativead.adsdk.v2.utils.AccountHelper;
import nativead.adsdk.v2.utils.AdRender;

/**
 * Created by Jerry on 16/7/5.
 */
public class ListBannerActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "nativead";
    private AccountHelper mAccountHelper;
    private ViewPager mViewPager;
    private ListBannerAdapter mListBannerAdapter;
    private View mLoadButton;
    private DotView mDotView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ad);

        setTitle("List Banner");
        mLoadButton = findViewById(R.id.btn_load_ad);
        mLoadButton.setOnClickListener(this);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mListBannerAdapter = new ListBannerAdapter();
        mViewPager.setAdapter(mListBannerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mDotView.setIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mDotView = (DotView) findViewById(R.id.dot);
        mAccountHelper = new AccountHelper(findViewById(R.id.root_account));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_load_ad:
                loadAd();
                break;
        }
    }

    private void loadAd() {
        mLoadButton.setEnabled(false);
        final AzNativeAdsManager adsManager = new AzNativeAdsManager(this, mAccountHelper.getFacebookPlacementId(),
                mAccountHelper.getApxSourceId(), 6);
        adsManager.setListener(new AzNativeAdsManager.Listener() {
            @Override
            public void onAdsLoaded() {
                mLoadButton.setEnabled(true);
                Log.d(TAG, "loadAd onAdsLoaded: " + adsManager.getUniqueNativeAdCount());
                ArrayList<AzNativeAd> ads = adsManager.getNativeAds();
                for (AzNativeAd ad : ads) {
                    Log.d(TAG, "onAdsLoaded: " + ad.getAdSource() + " " + ad.getAdTitle());
                }
                mDotView.setSize(adsManager.getUniqueNativeAdCount());
                mListBannerAdapter.setAds(adsManager.getNativeAds());
            }

            @Override
            public void onAdError(AzAdError adError) {
                mLoadButton.setEnabled(true);
                Log.d(TAG, "loadAd onAdError: ");
                Toast.makeText(ListBannerActivity.this, adError.getErrorMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        adsManager.loadAds();
    }

    private static class ListBannerAdapter extends PagerAdapter {

        public ArrayList<AzNativeAd> mAds;

        public ListBannerAdapter() {
            mAds = new ArrayList<>();
        }

        public void setAds(ArrayList<AzNativeAd> ads) {
            mAds.clear();
            mAds.addAll(ads);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mAds.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(final ViewGroup container, int position) {
            RelativeLayout relativeLayout = new RelativeLayout(container.getContext());
            container.addView(relativeLayout);
            AdRender.inflateAsBanner(mAds.get(position), relativeLayout);
            mAds.get(position).setAzAdListener(new AzAdListener() {
                @Override
                public void onError(AzNativeAd ad, AzAdError error) {

                }

                @Override
                public void onAdClicked(AzNativeAd ad) {
                    Toast.makeText(container.getContext(), "ad click",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdLoaded(AzNativeAd ad) {

                }
            });
            return relativeLayout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
