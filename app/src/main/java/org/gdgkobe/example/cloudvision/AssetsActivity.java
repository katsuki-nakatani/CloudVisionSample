package org.gdgkobe.example.cloudvision;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.gdgkobe.example.cloudvision.listener.AssetItemClickListener;

public class AssetsActivity extends AppCompatActivity implements AssetItemClickListener {

    public static final String PARAM_ASSET_NAME = "assetName";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets);
        if (getSupportFragmentManager().findFragmentById(R.id.fragment) == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment, AssetsFragment.newInstance())
                    .commitAllowingStateLoss();
        }

    }

    /**
     * Activity表示用Intentを作成する
     *
     * @param context Context
     * @return intent
     */
    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, AssetsActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        return intent;
    }

    @Override
    public void selection(String assetName) {
        Intent intent = new Intent();
        intent.putExtra(PARAM_ASSET_NAME, assetName);
        setResult(RESULT_OK, intent);
        finish();
    }
}
