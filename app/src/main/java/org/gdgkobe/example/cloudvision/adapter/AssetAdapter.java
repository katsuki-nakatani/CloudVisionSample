package org.gdgkobe.example.cloudvision.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.gdgkobe.example.cloudvision.R;
import org.gdgkobe.example.cloudvision.entity.Asset;
import org.gdgkobe.example.cloudvision.listener.RecyclerItemClickListener;
import org.gdgkobe.example.cloudvision.util.ImageUtil;

import java.io.InputStream;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AssetAdapter extends RecyclerView.Adapter<AssetAdapter.ViewHolder> {

    private static final String TAG = AssetAdapter.class.getSimpleName();
    //サムネイル表示する画像のスケールサイズ
    private static final int THUMB_SCALE_SIZE = 500;
    private RecyclerItemClickListener mClickListener;
    private LayoutInflater mInflater;
    private List<Asset> mItems;
    private Context mContext;


    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.picture_image)
        public ImageView image;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    /**
     * コンストラクタ
     *
     * @param context
     * @param items
     */
    public AssetAdapter(Context context, List<Asset> items, RecyclerItemClickListener listener) {
        this.mItems = items;
        this.mClickListener = listener;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_asset, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickListener != null)
                    mClickListener.onItemClick(view);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (mItems.get(position).getBitmap() == null) {
            try {
                InputStream inputStream = mContext.getResources().getAssets().open(mItems.get(position).getFileName());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                mItems.get(position).setBitmap(ImageUtil.scaleBitmapDown(bitmap, THUMB_SCALE_SIZE));
            } catch (Exception e) {
                Log.d(TAG, "asset load error");
            }
        }
        holder.image.setImageBitmap(mItems.get(position).getBitmap());
    }

    @Override
    public long getItemId(int arg0) {
        // TODO 自動生成されたメソッド・スタブ
        return 0;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public Asset getItem(int position) {
        return mItems.get(position);
    }

}
