package com.merpyzf.xmshare.ui.view.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.merpyzf.transfermanager.entity.FileInfo;
import com.merpyzf.transfermanager.entity.PicFile;
import com.merpyzf.xmshare.R;
import com.merpyzf.xmshare.ui.view.activity.OnFileSelectListener;
import com.merpyzf.xmshare.ui.widget.FileSelectIndicatorImp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoFragment extends Fragment {


    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.checkbox_all)
    CheckBox mCheckbox;
    @BindView(R.id.fileSelectIndicator)
    FileSelectIndicatorImp mFileSelectIndicator;
    private Unbinder mUnbinder;
    private OnFileSelectListener mFileSelectListener;


    public PhotoFragment() {

    }

    @SuppressLint("ValidFragment")
    public PhotoFragment(OnFileSelectListener<FileInfo> fileSelectListener) {
        mFileSelectListener = fileSelectListener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_image, container, false);


        mUnbinder = ButterKnife.bind(this, rootView);

        initUI();


        return rootView;
    }

    private void initUI() {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_container, new PhotoDirsFragment());
        fragmentTransaction.commit();

    }

    public TextView getTvTitle() {
        return mTvTitle;
    }

    public CheckBox getCheckbox() {
        return mCheckbox;
    }

    public FileSelectIndicatorImp getFileSelectIndicator() {

        if (mFileSelectIndicator == null) {

            Log.i("wk", "mFileSelectIndicator为null");
        }

        return mFileSelectIndicator;


    }


    /**
     * 选择所有的图片
     */
    public void checkAllPhoto(List<FileInfo> fileInfoList) {


        if (mFileSelectListener == null) {
            return;
        }

        mFileSelectListener.onCheckedAll(fileInfoList);
    }

    /**
     * 选择所有的图片
     */
    public void cancelCheckAllPhoto(List<FileInfo> fileInfoList) {


        if (mFileSelectListener == null) {
            return;
        }
        mFileSelectListener.onCancelCheckedAll(fileInfoList);
    }

    /**
     * 选择一个图片
     *
     * @param picFile
     */
    public void selectPhoto(PicFile picFile) {

        if (mFileSelectListener == null) {
            return;
        }

        mFileSelectListener.onSelected(picFile);
    }


    /**
     * 取消选择
     *
     * @param picFile
     */
    public void cancelSelectPhoto(PicFile picFile) {

        if (mFileSelectListener == null) {
            return;
        }
        mFileSelectListener.onCancelSelected(picFile);
    }


    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
