package nativead.adsdk.v2.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;

import nativead.adsdk.v2.R;

/**
 * Created by Jerry on 16/7/6.
 */
public class AccountHelper implements View.OnClickListener {

    private static final String TEXT = "account";
    private static String FACEBOOK_PLACEMENT_ID = "";
    private static final String APX_SOURCE_ID = "";

    private EditText mFbEdit;
    private EditText mApxEdit;

    public AccountHelper(View view) {

        final EditText fbEdit = (EditText) view.findViewById(R.id.facebook_id);
        final EditText apxEdit = (EditText) view.findViewById(R.id.apx_id);
        final Context context = view.getContext();

        mFbEdit = fbEdit;
        mApxEdit = apxEdit;

        final SharedPreferences sharedPreferences = context.getSharedPreferences("account",
                Context.MODE_PRIVATE);
        String facebookPlacementId = sharedPreferences.getString("facebook", FACEBOOK_PLACEMENT_ID);
        fbEdit.setText(facebookPlacementId);
        String apxSourceId = sharedPreferences.getString("apx", APX_SOURCE_ID);
        apxEdit.setText(apxSourceId);

        view.findViewById(R.id.btn_update_id).setOnClickListener(this);
    }

    public String getFacebookPlacementId() {
        return mFbEdit.getText().toString();
    }

    public String getApxSourceId() {
        return mApxEdit.getText().toString();
    }

    @Override
    public void onClick(View view) {
        final SharedPreferences.Editor editor = view.getContext().getSharedPreferences("account",
                Context.MODE_PRIVATE).edit();
        editor.putString("facebook", mFbEdit.getText().toString())
                .putString("apx", mApxEdit.getText().toString()).apply();
    }
}
