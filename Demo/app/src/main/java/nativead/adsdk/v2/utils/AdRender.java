package nativead.adsdk.v2.utils;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import az.nativead.v2.AzNativeAd;
import az.nativead.v2.internal.utils.Utils;
import nativead.adsdk.v2.R;

/**
 * Created by Jerry on 16/7/6.
 */
public class AdRender {

    public static void inflateAsBanner(AzNativeAd ad, ViewGroup root) {
        final View view = LayoutInflater.from(root.getContext()).inflate(R.layout.layout_ad,
                root, false);
        root.addView(view);

        TextView title = (TextView) root.findViewById(R.id.text_title);
        ImageView icon = (ImageView) root.findViewById(R.id.image_icon);
        TextView content = (TextView) root.findViewById(R.id.text_content);
        TextView action = (TextView) root.findViewById(R.id.text_action);

        title.setText(ad.getAdTitle());
        Picasso.with(root.getContext().getApplicationContext()).load(ad.getAdIconUrl()).into(icon);

        content.setText(ad.getShortDesc());
        ad.registerViewForInteraction(view);
        action.setText(ad.getAdCallToAction());

        View adChoice = ad.getAdChoiceView(root.getContext());
        if (adChoice != null) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(Utils.dp2px(root.getContext(), 18),
                    Utils.dp2px(root.getContext(), 18));
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            root.addView(adChoice, 0, params);
        }
    }

    public static void inflateAsCover(AzNativeAd ad, ViewGroup root) {
        final View view = LayoutInflater.from(root.getContext()).inflate(R.layout.layout_cover,
                root, false);
        root.addView(view);

        TextView title = (TextView) root.findViewById(R.id.text_title);
        ImageView icon = (ImageView) root.findViewById(R.id.image_icon);
        TextView content = (TextView) root.findViewById(R.id.text_content);
        TextView action = (TextView) root.findViewById(R.id.text_action);
        ImageView cover = (ImageView) root.findViewById(R.id.image_cover);

        title.setText(ad.getAdTitle());
        Picasso.with(root.getContext().getApplicationContext()).load(ad.getAdIconUrl()).into(icon);

        content.setText(ad.getShortDesc());
        ad.registerViewForInteraction(view);
        action.setText(ad.getAdCallToAction());
        View adChoice = ad.getAdChoiceView(root.getContext());
        if (adChoice != null) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(Utils.dp2px(root.getContext(), 18),
                    Utils.dp2px(root.getContext(), 18));
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            root.addView(adChoice, 0, params);
        }

        if (ad.getAdCoverImageUrl() != null) {
            Log.d("nativead", "inflateAsCover: start load cover image");
            Picasso.with(root.getContext().getApplicationContext()).load(ad.getAdCoverImageUrl()).into(cover);
        }


    }
}
