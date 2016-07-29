
package com.example.wdc.ui.activity.picker;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.wdc.adapter.ListViewDataAdapter;
import com.example.wdc.adapter.ViewHolderBase;
import com.example.wdc.adapter.ViewHolderCreator;
import com.example.wdc.ms.R;
import com.example.wdc.ui.activity.base.BaseActivity;
import com.example.wdc.utils.CommonUtils;
import com.example.wdc.utils.NetUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommonImagePickerListActivity extends BaseActivity {

    private static final int IMAGE_PICKER_DETAIL_REQUEST_CODE = 200;

    public static final String KEY_BUNDLE_ALBUM_PATH = "KEY_BUNDLE_ALBUM_PATH";
    public static final String KEY_BUNDLE_ALBUM_NAME = "KEY_BUNDLE_ALBUM_NAME";

    @BindView(R.id.common_image_picker_list_view)
    protected ListView mImagePickerListView;
    @BindView(R.id.common_toolbar)
    protected Toolbar toolbar;

    private ListViewDataAdapter<ImageBucket> mListViewAdapter = null;
    private AsyncTask<Void, Void, List<ImageBucket>> mAlbumLoadTask = null;



    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_common_image_picker_list;
    }


    @Override
    protected View getLoadingTargetView() {
        return mImagePickerListView;
    }

    @Override
    protected void initViewsAndEvents() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("本地相册");
        mListViewAdapter = new ListViewDataAdapter<ImageBucket>(new ViewHolderCreator<ImageBucket>() {
            @Override
            public ViewHolderBase<ImageBucket> createViewHolder(int position) {
                return new ViewHolderBase<ImageBucket>() {

                    ImageView mItemImage;
                    TextView mItemTitle;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View convertView = layoutInflater.inflate(R.layout
                                .list_item_common_image_picker, null);
                        mItemImage = ButterKnife.findById(convertView, R.id
                                .list_item_common_image_picker_thumbnail);
                        mItemTitle = ButterKnife.findById(convertView, R.id
                                .list_item_common_image_picker_title);
                        return convertView;
                    }

                    @Override
                    public void showData(int position, ImageBucket itemData) {
                        if (null != itemData) {
                            String imagePath = itemData.bucketList.get(0).getImagePath();
                            // TODO: 2016/7/27  将imageloader换成了glide
                            if (!CommonUtils.isEmpty(imagePath)) {
//                                ImageLoader.getInstance().displayImage("file://" + imagePath,
//                                        mItemImage,
//                                        ImageLoaderHelper.getInstance(mContext).getDisplayOptions());
                                Glide.with(CommonImagePickerListActivity.this).load("file://" + imagePath).into(mItemImage);
                            }

                            int count = itemData.count;
                            String title = itemData.bucketName;

                            if (!CommonUtils.isEmpty(title)) {
                                mItemTitle.setText(title + "(" + count + ")");
                            }
                        }
                    }
                };
            }
        });
        mImagePickerListView.setAdapter(mListViewAdapter);

        mImagePickerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null != mListViewAdapter && null != mListViewAdapter.getDataList() &&
                        !mListViewAdapter.getDataList().isEmpty() &&
                        position < mListViewAdapter.getDataList().size()) {

                    Bundle extras = new Bundle();
                    extras.putParcelableArrayList(KEY_BUNDLE_ALBUM_PATH, mListViewAdapter
                            .getDataList().get(position).bucketList);
                    extras.putString(KEY_BUNDLE_ALBUM_NAME, mListViewAdapter.getDataList().get
                            (position).bucketName);

                    readyGoForResult(CommonImagePickerDetailActivity.class,
                            IMAGE_PICKER_DETAIL_REQUEST_CODE, extras);
                }
            }
        });

        mAlbumLoadTask = new AsyncTask<Void, Void, List<ImageBucket>>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                toggleShowLoading(true, null);
                ImagePickerHelper.getHelper().init(mContext);
            }

            @Override
            protected List<ImageBucket> doInBackground(Void... params) {
                return ImagePickerHelper.getHelper().getImagesBucketList();
            }

            @Override
            protected void onPostExecute(List<ImageBucket> list) {
                toggleShowLoading(false, null);

                mListViewAdapter.getDataList().addAll(list);
                mListViewAdapter.notifyDataSetChanged();
            }
        };

        mAlbumLoadTask.execute();
    }


    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return true;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.RIGHT;
    }

    @Override
    protected Boolean isAddStatusHeight() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mAlbumLoadTask && !mAlbumLoadTask.isCancelled()) {
            mAlbumLoadTask.cancel(true);
            mAlbumLoadTask = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == IMAGE_PICKER_DETAIL_REQUEST_CODE) {
            setResult(RESULT_OK, data);
            finish();
        }
    }

}
