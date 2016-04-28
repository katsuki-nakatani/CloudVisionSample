package org.gdgkobe.example.cloudvision.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.gdgkobe.example.cloudvision.R;
import org.gdgkobe.example.cloudvision.entity.Landmark;
import org.gdgkobe.example.cloudvision.listener.RecyclerItemClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LandmarkAdapter extends RecyclerView.Adapter<LandmarkAdapter.ViewHolder> {
    private RecyclerItemClickListener mClickListener;
    private LayoutInflater mInflater;
    private List<Landmark> mItems;
    private Context mContext;


    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        public TextView title;
        @Bind(R.id.value)
        public TextView value;

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
    public LandmarkAdapter(Context context, List<Landmark> items, RecyclerItemClickListener listener) {
        this.mItems = items;
        this.mClickListener = listener;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_randmark, parent, false);
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
        //リソースがNULLの場合おそらく本体電話帳のため名前を変える
        //値のセット
        holder.title.setText(mItems.get(position).getTypeString());
        holder.value.setText(
                mContext.getString(R.string.position_text,
                        mItems.get(position).getPosition().getX(),
                        mItems.get(position).getPosition().getY(),
                        mItems.get(position).getPosition().getZ()));
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

    public Landmark getItem(int position) {
        return mItems.get(position);
    }

}
