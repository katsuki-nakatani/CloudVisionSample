package org.gdgkobe.example.cloudvision;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.gdgkobe.example.cloudvision.adapter.AssetAdapter;
import org.gdgkobe.example.cloudvision.entity.Asset;
import org.gdgkobe.example.cloudvision.listener.AssetItemClickListener;
import org.gdgkobe.example.cloudvision.listener.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AssetsFragment extends Fragment implements RecyclerItemClickListener {

    RecyclerView.LayoutManager mLayoutManager;

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    List<Asset> mAssetList;

    AssetItemClickListener mListener;

    public static AssetsFragment newInstance() {
        AssetsFragment fragment = new AssetsFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assets, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAssetList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            mAssetList.add(new Asset(i));
//        }
        bindRecycler();
    }

    /**
     * リサイクラービューのバインド
     */
    private void bindRecycler() {
        if (mRecyclerView.getLayoutManager() == null) {
            mLayoutManager = new GridLayoutManager(getActivity(), 3);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setHasFixedSize(false);
            mRecyclerView.setLayoutManager(mLayoutManager);
        }
        if (mRecyclerView.getAdapter() == null) {
            mRecyclerView.setAdapter(new AssetAdapter(getActivity(),
                    mAssetList,
                    this));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof AssetItemClickListener)
            mListener = (AssetItemClickListener) getActivity();
    }

    @Override
    public void onItemClick(View view) {
        AssetAdapter adapter = (AssetAdapter) mRecyclerView.getAdapter();
        if (mListener != null)
            mListener.selection(adapter.getItem(mRecyclerView.getChildAdapterPosition(view)).getFileName());
    }
}
