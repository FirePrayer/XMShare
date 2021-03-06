package com.merpyzf.xmshare.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.util.Log;

import com.merpyzf.transfermanager.entity.FileInfo;
import com.merpyzf.transfermanager.entity.VideoFile;
import com.merpyzf.xmshare.R;
import com.merpyzf.xmshare.common.Const;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangke on 2018/1/14.
 */

public class VideoUtils {

    public static Bitmap getVideoThumbnail(String filePath) {
        Bitmap bmp = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bmp = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();

        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return bmp;
    }


    /**
     * 更新封面图片
     */
    public static void updateThumbImg(Context context, List<FileInfo> fileInfoList) {

        List<FileInfo> copyFileInfoList = new ArrayList<>();
        copyFileInfoList.addAll(fileInfoList);
        writeThumbImg2local(context, copyFileInfoList);

    }


    public static void writeThumbImg2local(Context context, List<FileInfo> videoList) {


        Observable.fromIterable(videoList)
                .filter(videoFile -> {

                    if (videoFile instanceof VideoFile) {

                        if (Const.PIC_CACHES_DIR.canWrite() && !isContain(Const.PIC_CACHES_DIR, (VideoFile) videoFile)) {
                            return true;
                        }
                    }
                    return false;

                }).flatMap(videoFile -> Observable.just(videoFile.getPath()))
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(videoPath -> {

                    // TODO: 2018/1/15 解决视频第一帧图片过慢的问题
                    Bitmap bitmap = getVideoThumbnail(videoPath);
                    BufferedOutputStream bos = null;

                    String fileName = new File(videoPath).getName();

                    int dotIndex = fileName.lastIndexOf('.');

                    fileName = fileName.substring(0, dotIndex);


                    Log.i("w2k", "写入本地存储的视频封面截图:" + fileName);

                    try {
                        bos = new BufferedOutputStream(new FileOutputStream(new File(Const.PIC_CACHES_DIR, Md5Utils.getMd5(videoPath))));
                        if (bitmap == null) {

                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_thumb_empty);
                        }
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            bos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private static synchronized boolean isContain(File parent, VideoFile videoFile) {
        String[] thumbs = parent.list();

        for (int i = 0; i < thumbs.length; i++) {
            if (Md5Utils.getMd5(videoFile.getPath()).equals(thumbs[i])) {
                return true;
            }
        }
        return false;
    }

}
