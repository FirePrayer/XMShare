package com.merpyzf.xmshare.ui.adapter;

import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.merpyzf.transfermanager.constant.Constant;
import com.merpyzf.transfermanager.entity.ApkFile;
import com.merpyzf.transfermanager.entity.FileInfo;
import com.merpyzf.transfermanager.entity.MusicFile;
import com.merpyzf.transfermanager.entity.PicFile;
import com.merpyzf.transfermanager.entity.VideoFile;
import com.merpyzf.transfermanager.receive.ReceiverManager;
import com.merpyzf.xmshare.R;

import java.io.File;
import java.util.List;

/**
 * Created by wangke on 2018/1/18.
 */

public class FileTransferAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    private List<FileInfo> mFileLists;

    public FileTransferAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
        this.mFileLists = (List<FileInfo>) data;
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {

        FileInfo file = (FileInfo) item;
        String fileName = file.getName();


        if (item instanceof ApkFile) {

            ApkFile apkFile = (ApkFile) item;

        } else if (item instanceof MusicFile) {


            MusicFile musicFile = (MusicFile) item;


        } else if (item instanceof PicFile) {


            PicFile picFile = (PicFile) item;


        } else if (item instanceof VideoFile) {

            VideoFile videoFile = (VideoFile) item;


        }

        TextView tvTitle = helper.getView(R.id.tv_title);
        tvTitle.setText(file.getName());

        ImageView civ = helper.getView(R.id.iv_file_thumb);
        File thumbFile = new File(Environment.getExternalStorageDirectory().getPath()
                + com.merpyzf.transfermanager.constant.Constant.THUMB_RECEIVE, file.getName());


        ProgressBar progressBar = helper.getView(R.id.progress);

        TextView tvProgress = helper.getView(R.id.tv_progress);

        if (file.getFileTransferStatus() == Constant.TransferStatus.TRANSFER_WAITING) {

            progressBar.setProgress(0);
            tvProgress.setText("等待中");
        }


        if (thumbFile.exists()) {
            //设置封面图片
            Glide.with(mContext)
                    .load(thumbFile)
                    .crossFade()
                    .centerCrop()
                    .into(civ);
        }


        ReceiverManager.getInstance().register(new ReceiverManager.ReceiveObserver() {
            @Override
            public void onReceiveListCompleted(List<FileInfo> receiveFileList) {

            }

            // 这边只有一种状态，传输中
            @Override
            public void onReceiveProgress(FileInfo fileInfo) {


                if (file.getFileTransferStatus() == Constant.TransferStatus.TRANSFING) {

                    Log.i("w2k", fileInfo.getName() + "传输中");
                    if (fileName.equals(fileInfo.getName())) {

                        int currentProgress = (int) (file.getProgress() * 100);
                        progressBar.setProgress(currentProgress);
                        tvProgress.setText("传输进度:" + currentProgress + " %");

                    }

                } else if (file.getFileTransferStatus() == Constant.TransferStatus.TRANSFER_WAITING) {


                    progressBar.setProgress(0);
                    tvProgress.setText("等待中");

                } else if (file.getFileTransferStatus() == Constant.TransferStatus.TRANSFER_SUCCESS) {
                    progressBar.setProgress(100);
                    tvProgress.setText("传输完毕");
                }

            }

            @Override
            public void onReceiveStatus(FileInfo fileInfo) {

                if (fileInfo.getName().equals(file.getName())) {


                    if (fileInfo.getFileTransferStatus() == Constant.TransferStatus.TRANSFER_SUCCESS) {

                        tvProgress.setText("传输成功");
                        progressBar.setProgress(100);

                    } else if (fileInfo.getFileTransferStatus() == Constant.TransferStatus.TRANSFER_FAILED) {

                        tvProgress.setText("传输失败");

                    }
                }
            }
        });

    }


}
