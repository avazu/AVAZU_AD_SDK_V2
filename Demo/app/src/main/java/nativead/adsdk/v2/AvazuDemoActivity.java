package nativead.adsdk.v2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import nativead.adsdk.v2.list.ListBannerActivity;
import nativead.adsdk.v2.list.ListCoverActivity;
import nativead.adsdk.v2.single.SingleBannerActivity;
import nativead.adsdk.v2.single.SingleCoverActivity;

/**
 * Created by Jerry on 16/7/5.
 */
public class AvazuDemoActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        setTitle("Avazu Native Ad SDK Demo");

        findViewById(R.id.item_single_banner).setOnClickListener(this);
        findViewById(R.id.item_single_cover).setOnClickListener(this);
        findViewById(R.id.item_list_banner).setOnClickListener(this);
        findViewById(R.id.item_list_cover).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_single_banner:
                startActivity(new Intent(this, SingleBannerActivity.class));
                break;
            case R.id.item_single_cover:
                startActivity(new Intent(this, SingleCoverActivity.class));
                break;
            case R.id.item_list_banner:
                startActivity(new Intent(this, ListBannerActivity.class));
                break;
            case R.id.item_list_cover:
                startActivity(new Intent(this, ListCoverActivity.class));
                break;
        }
    }
}
