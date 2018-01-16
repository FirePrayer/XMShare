package com.merpyzf.xmshare.ui.adapter;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.merpyzf.transfermanager.entity.FileInfo;
import com.merpyzf.xmshare.R;
import com.merpyzf.xmshare.common.Constant;
import com.merpyzf.xmshare.common.base.App;
import com.merpyzf.xmshare.ui.entity.ApkFile;
import com.merpyzf.xmshare.ui.entity.MusicFile;
import com.merpyzf.xmshare.ui.entity.PicFile;
import com.merpyzf.xmshare.ui.entity.VideoFile;
import com.merpyzf.xmshare.util.FormatUtils;

import java.io.File;
import java.util.List;

/**
 * Created by wangke on 2017/12/24.
 */

public class FileAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {
    private Context mContext;

    public FileAdapter(Context context, int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final T item) {


        if (item instanceof ApkFile) {

            ApkFile apkFile = (ApkFile) item;
            ImageView imageView = helper.getView(R.id.iv_apk_ico);
            helper.setText(R.id.tv_apk_name, apkFile.getName());
            imageView.setImageDrawable(apkFile.getApkDrawable());

        } else if (item instanceof MusicFile) {


            MusicFile musicFile = (MusicFile) item;

            helper.setText(R.id.tv_title, musicFile.getName());
            helper.setText(R.id.tv_artist, "artist: " + musicFile.getArtist());
            helper.setText(R.id.tv_size, "size:" + FormatUtils.convert2Mb(musicFile.getSize()) + " MB");
            File albumFile = new File(Environment.getExternalStorageDirectory().getPath()
                    + Constant.THUMB_MUSIC, String.valueOf(musicFile.getAlbumId()));
            if (albumFile.exists()) {
                //设置封面图片
                Glide.with(mContext)
                        .load(albumFile)
                        .crossFade()
                        .centerCrop()
                        .into((ImageView) helper.getView(R.id.iv_music_album));
            }


        } else if (item instanceof PicFile) {


            PicFile picFile = (PicFile) item;
            Glide.with(mContext)
                    .load(picFile.getPath())
                    .crossFade()
                    .centerCrop()
                    .into((ImageView) helper.getView(R.id.iv_gallery));

        } else if (item instanceof VideoFile) {


            VideoFile videoFile = (VideoFile) item;

            helper.setText(R.id.tv_title, "视频名:" + videoFile.getName());

            helper.setText(R.id.tv_size, "文件大小:" + FormatUtils.convert2Mb(videoFile.getSize()) + " MB");

            ImageView ivVideoThumb = helper.getView(R.id.iv_video_album);
            String videoThumbPath = Environment.getExternalStorageDirectory() + Constant.THUMB_VIDEO + "/" + videoFile.getName();
            Log.i("wk", "videoThumbPath:" + videoThumbPath);

            Glide.with(mContext)
                    .load(new File(videoThumbPath))
                    .crossFade()
                    .centerCrop()
                    .into(ivVideoThumb);

        }

        FileInfo fileInfo = (FileInfo) item;
        ImageView ivSelect = helper.getView(R.id.iv_select);

        if (App.getSendFileList().contains(fileInfo)) {
            ivSelect.setVisibility(View.VISIBLE);

        } else {
            ivSelect.setVisibility(View.INVISIBLE);
        }


    }


}
