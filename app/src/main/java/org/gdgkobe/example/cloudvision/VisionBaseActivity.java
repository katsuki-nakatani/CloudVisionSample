package org.gdgkobe.example.cloudvision;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.gdgkobe.example.cloudvision.util.ImageUtil;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import icepick.Icepick;
import icepick.State;

public class VisionBaseActivity extends AppCompatActivity {

    private static final String TAG = VisionBaseActivity.class.getSimpleName();
    protected static final String API_BASE_URL = "https://vision.googleapis.com/";
    @State
    Bitmap mBitmap;
    @State
    String mOriginalJson;
    @Bind(R.id.picture_image)
    ImageView mImage;
    @Bind(R.id.bottom_sheet)
    View mBottomSheet;
    @Bind(R.id.coordinator_layout)
    CoordinatorLayout mCoordinator;

    private static final int GALLERY_IMAGE_REQUEST = 1;
    public static final int CAMERA_IMAGE_REQUEST = 3;
    public static final int ASSET_IMAGE_REQUEST = 4;
    public static final String FILE_NAME = "temp.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    void restoreView(){
        if (mBitmap != null)
            mImage.setImageBitmap(mBitmap);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.base_vision_menu, menu);
        if (TextUtils.isEmpty(mOriginalJson)) {
            menu.removeItem(R.id.menu_show_json);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_asset:
                startAsset();
                return true;
            case R.id.menu_camera:
                startCamera();
                return true;
            case R.id.menu_gallery:
                startGalleryChooser();
                return true;
            case R.id.menu_show_json:
                startShowJson();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            loadImage(data.getData());
        } else if (requestCode == CAMERA_IMAGE_REQUEST && resultCode == RESULT_OK) {
            loadImage(getCameraUri());
        } else if (requestCode == ASSET_IMAGE_REQUEST && resultCode == RESULT_OK && data.hasExtra(AssetsActivity.PARAM_ASSET_NAME)) {
            loadAssetImage(data.getStringExtra(AssetsActivity.PARAM_ASSET_NAME));
        }
    }

    /**
     * URIから画像のロード(カメラのケースとギャラリーのケース)
     *
     * @param uri
     */
    public void loadImage(Uri uri) {
        if (uri != null) {
            try {
                mBitmap = ImageUtil.scaleBitmapDown(MediaStore.Images.Media.getBitmap(getContentResolver(), uri), 1200);
                mImage.setImageBitmap(mBitmap);
                mOriginalJson = "";
            } catch (IOException e) {
                Log.d(TAG, "Image picking failed because " + e.getMessage());
                Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d(TAG, "Image picker gave us a null image.");
            Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Assetから画像のロード
     *
     * @param assetName
     */
    private void loadAssetImage(String assetName) {
        try {
            mBitmap = ImageUtil.scaleBitmapDown(BitmapFactory.decodeStream(getResources().getAssets().open(assetName)), 1200);
            mImage.setImageBitmap(mBitmap);
        } catch (IOException e) {
            Snackbar.make(mCoordinator, R.string.image_picker_error, Snackbar.LENGTH_LONG).show();
            Log.d("Assets", "Error:/" + assetName);
        }
    }


    /**
     * Galleryアプリを開く
     */
    void startGalleryChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a photo"),
                GALLERY_IMAGE_REQUEST);
    }

    /**
     * JsonText表示Activityを開く
     *
     * @return
     */
    void startShowJson() {
        startActivity(JsonActivity.createIntent(this, mOriginalJson));
    }

    /**
     * カメラアプリを開く
     */
    void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getCameraUri());
        startActivityForResult(intent, CAMERA_IMAGE_REQUEST);
    }

    /**
     * AssetActivityを開く
     */
    void startAsset() {
        Intent intent = AssetsActivity.createIntent(this);
        startActivityForResult(intent, ASSET_IMAGE_REQUEST);
    }

    /**
     * カメラ撮影データのURIを返却
     *
     * @return
     */
    public Uri getCameraUri() {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return Uri.fromFile(new File(dir, FILE_NAME));
    }

}
