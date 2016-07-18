package nativead.adsdk.v2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import az.nativead.v2.AzAdError;
import az.nativead.v2.AzAdListener;
import az.nativead.v2.AzNativeAd;
import az.nativead.v2.internal.utils.NetworkUtils;

/**
 * Created by Jerry on 16/7/1.
 */
public class MainActivity extends AppCompatActivity {

    private static String facebookPlacementId = "195397060815689_255898501432211";
    private static String facebookPlacementId_error = "195397060815689_999856706536391";
    private static final String apxPlacementId = "15887";

    private static final String TAG = "ad_demo";

    private TextView mTitle;
    private ImageView mImage;
    private TextView mContent;
    Switch mAccount;

    EditText mFbEdit;
    EditText mApxEdit;
    ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Avazu Native Ad Demo");


        Log.d("network", "fetchAd: " + NetworkUtils.FORCE_CREATIVES);
        NetworkUtils.FORCE_CREATIVES = true;
        Log.d("network", "fetchAd: " + NetworkUtils.FORCE_CREATIVES);
        mTitle = (TextView) findViewById(R.id.title);
        mImage = (ImageView) findViewById(R.id.image);
        mContent = (TextView) findViewById(R.id.content);
        Switch account = (Switch) findViewById(R.id.switcher);
        mProgress = (ProgressBar) findViewById(R.id.progress);
        mAccount = account;
        account.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Toast.makeText(MainActivity.this, "use error facebook placementid",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        final EditText fbEdit = (EditText) findViewById(R.id.facebook_id);
        mFbEdit = fbEdit;
        final EditText apxEdit = (EditText) findViewById(R.id.text_apx);
        mApxEdit = apxEdit;
        final SharedPreferences sharedPrefes = getSharedPreferences("account", MODE_PRIVATE);
        String facebookdId = sharedPrefes.getString("fb", facebookPlacementId);
        fbEdit.setText(facebookdId);
        String apx = sharedPrefes.getString("apx", apxPlacementId);
        apxEdit.setText(apx);

        findViewById(R.id.update_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefes.edit().putString("apx", apxEdit.getText().toString())
                        .putString("fb", fbEdit.getText().toString()).apply();
            }
        });

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_click:
                onClickOk();
                break;
        }
    }

    private void onClickOk() {
        mProgress.setVisibility(View.VISIBLE);
        AzNativeAd nativeAd;
        if (mAccount.isChecked()) {
            nativeAd = new AzNativeAd(getApplicationContext(), facebookPlacementId_error,
                    mApxEdit.getText().toString());
        } else {
            nativeAd = new AzNativeAd(getApplicationContext(), mFbEdit.getText().toString(),
                    mApxEdit.getText().toString());
        }
        nativeAd.setAzAdListener(new AzAdListener() {
            @Override
            public void onError(AzNativeAd ad, AzAdError error) {
                mProgress.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "AdLoad Failed.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClicked(AzNativeAd ad) {
                Log.d(TAG, "onAdClicked: ");
                Toast.makeText(MainActivity.this, "Ad Click.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded(AzNativeAd ad) {
                mProgress.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "AdLoad Success.", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onAdLoaded: ");
                mTitle.setText(ad.getAdTitle());
                mContent.setText(ad.getAdBody());
                Picasso.with(getApplicationContext()).load(ad.getAdIconUrl()).into(mImage);
                ad.registerViewForInteraction(findViewById(R.id.ad_root));
            }
        });
        nativeAd.loadAd();
    }
}
