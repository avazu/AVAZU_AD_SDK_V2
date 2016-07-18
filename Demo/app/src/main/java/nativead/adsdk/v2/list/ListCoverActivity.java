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

import java.util.ArrayList;

import az.nativead.v2.AzAdError;
import az.nativead.v2.AzNativeAd;
import az.nativead.v2.AzNativeAdsManager;
import nativead.adsdk.v2.DotView;
import nativead.adsdk.v2.R;
import nativead.adsdk.v2.utils.AccountHelper;
import nativead.adsdk.v2.utils.AdRender;

/**
 * Created by Jerry on 16/7/5.
 */
public class ListCoverActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "nativead";
    private AccountHelper mAccountHelper;
    private ViewPager mViewPager;
    private ListBannerAdapter mListBannerAdapter;
    private View mLoadButton;
    private DotView mDotView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cover);

        setTitle("List Cover");
        mLoadButton = findViewById(R.id.btn_load_ad);
        mLoadButton.setOnClickListener(this);
        mViewPager = (ViewPager) findViewById(R.id.pager);
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
        mListBannerAdapter = new ListBannerAdapter();
        mViewPager.setAdapter(mListBannerAdapter);
        mAccountHelper = new AccountHelper(findViewById(R.id.root_account));
        mDotView = (DotView) findViewById(R.id.dot);

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
        public Object instantiateItem(ViewGroup container, int position) {
            RelativeLayout relativeLayout = new RelativeLayout(container.getContext());
            container.addView(relativeLayout);
            AdRender.inflateAsCover(mAds.get(position), relativeLayout);
            return relativeLayout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
