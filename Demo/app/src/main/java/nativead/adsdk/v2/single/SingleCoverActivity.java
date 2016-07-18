package nativead.adsdk.v2.single;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import az.nativead.v2.AzAdError;
import az.nativead.v2.AzAdListener;
import az.nativead.v2.AzNativeAd;
import nativead.adsdk.v2.R;
import nativead.adsdk.v2.utils.AccountHelper;
import nativead.adsdk.v2.utils.AdRender;

/**
 * Created by Jerry on 16/7/5.
 */
public class SingleCoverActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "nativead";
    private AccountHelper mAccountHelper;

    private ViewGroup mAdRoot;
    private View mLoadButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_cover);
        setTitle("Single Cover Image");

        mLoadButton = findViewById(R.id.btn_load_ad);
        mLoadButton.setOnClickListener(this);
        mAccountHelper = new AccountHelper(findViewById(R.id.root_account));
        mAdRoot = (ViewGroup) findViewById(R.id.root_ad);
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
        AzNativeAd ad = new AzNativeAd(this, mAccountHelper.getFacebookPlacementId(),
                mAccountHelper.getApxSourceId());
        ad.setAzAdListener(new AzAdListener() {
            @Override
            public void onError(AzNativeAd ad, AzAdError error) {
                mLoadButton.setEnabled(true);
                Log.d(TAG, "onError: ");
                Toast.makeText(SingleCoverActivity.this, error.getErrorMessage(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClicked(AzNativeAd ad) {
                Log.d(TAG, "onAdClicked: ");
                Toast.makeText(SingleCoverActivity.this, "ad clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded(AzNativeAd ad) {
                mLoadButton.setEnabled(true);
                mAdRoot.removeAllViews();
                AdRender.inflateAsCover(ad, mAdRoot);
            }
        });
        ad.loadAd();
    }
}
