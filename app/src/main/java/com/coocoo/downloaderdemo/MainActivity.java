package com.coocoo.downloaderdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.coocoo.downloaderdemo.downloader.DownloadConfig;
import com.coocoo.downloaderdemo.downloader.Downloader;
import com.coocoo.downloaderdemo.downloader.IDownloadCallback;
import com.coocoo.downloaderdemo.downloader.IDownloadTask;
import com.coocoo.downloaderdemo.downloader.model.bean.Data;
import com.coocoo.downloaderdemo.downloader.widget.DownloadProgressBar;

public class MainActivity extends AppCompatActivity {

    private Downloader mDownloader;
    private View one;
    private View two;
    private View three;
    private View four;
    private String[] mDatas;
    private Button mOneDownload;
    private Button mOneCancel;
    private DownloadProgressBar mOneProgress;
    private ImageView mOneImage;
    private IDownloadTask oneDownloadTask;
    private Button mTwoDownload;
    private Button mTwoCancel;
    private DownloadProgressBar mTwoProgress;
    private ImageView mTwoImage;
    private Button mThreeDownload;
    private Button mThreeCancel;
    private DownloadProgressBar mThreeProgress;
    private ImageView mThreeImage;
    private Button mFourDownload;
    private Button mFourCancel;
    private DownloadProgressBar mFourProgress;
    private ImageView mFourImage;
    private IDownloadTask twoDownloadTask;
    private IDownloadTask threeDownloadTask;
    private IDownloadTask fourDownloadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDownloader = new Downloader(this, new DownloadConfig.Builder().build());
        initData();
        initView();
    }

    private void initData() {
      mDatas = new String[] {
        "http://192.168.31.29:8070/files/images/a.jpg",
        "http://192.168.31.29:8070/files/images/b.jpg",
        "http://192.168.31.29:8070/files/images/c.jpg",
        "http://192.168.31.29:8070/files/images/d.jpg",
      };
    }

    private void initView() {
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        initOneElement(one);
        initTwoElement(two);
        initThreeElement(three);
        initFourElement(four);
    }

    private void initOneElement(View view) {
        final String url = mDatas[0];
        mOneDownload = ((Button) view.findViewById(R.id.download));
        mOneDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOneDownload.getText().equals("DOWNLOAD")) {
                    oneDownloadTask = download(url, mOneDownload, mOneCancel, mOneImage, mOneProgress);
                } else if (mOneDownload.getText().equals("DOWNLOADING...")) {
                    oneDownloadTask.pause();
                } else if (mOneDownload.getText().equals("PAUSE")) {
                    oneDownloadTask.resume();
                }
            }
        });
        mOneCancel = ((Button) view.findViewById(R.id.cancel));
        mOneCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oneDownloadTask.cancel();
            }
        });
        mOneCancel.setVisibility(View.INVISIBLE);
        mOneProgress = ((DownloadProgressBar) view.findViewById(R.id.progress));
        mOneImage = ((ImageView) view.findViewById(R.id.image));
        mOneImage.setVisibility(View.GONE);
    }

    private void initTwoElement(View view) {
        final String url = mDatas[1];
        mTwoDownload = ((Button) view.findViewById(R.id.download));
        mTwoDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTwoDownload.getText().equals("DOWNLOAD")) {
                    twoDownloadTask = download(url, mTwoDownload, mTwoCancel, mTwoImage, mTwoProgress);
                } else if (mTwoDownload.getText().equals("DOWNLOADING...")) {
                    twoDownloadTask.pause();
                } else if (mTwoDownload.getText().equals("PAUSE")) {
                    twoDownloadTask.resume();
                }
            }
        });
        mTwoCancel = ((Button) view.findViewById(R.id.cancel));
        mTwoCancel.setVisibility(View.INVISIBLE);
        mTwoCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                twoDownloadTask.cancel();
            }
        });
        mTwoProgress = ((DownloadProgressBar) view.findViewById(R.id.progress));
        mTwoImage = ((ImageView) view.findViewById(R.id.image));
        mTwoImage.setVisibility(View.GONE);
    }

    private void initThreeElement(View view) {
        final String url = mDatas[2];
        mThreeDownload = ((Button) view.findViewById(R.id.download));
        mThreeDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mThreeDownload.getText().equals("DOWNLOAD")) {
                    threeDownloadTask = download(url, mThreeDownload, mThreeCancel, mThreeImage, mThreeProgress);
                } else if (mThreeDownload.getText().equals("DOWNLOADING...")) {
                    threeDownloadTask.pause();
                } else if (mThreeDownload.getText().equals("PAUSE")) {
                    threeDownloadTask.resume();
                }
            }
        });
        mThreeCancel = ((Button) view.findViewById(R.id.cancel));
        mThreeCancel.setVisibility(View.INVISIBLE);
        mThreeCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                threeDownloadTask.cancel();
            }
        });
        mThreeProgress = ((DownloadProgressBar) view.findViewById(R.id.progress));
        mThreeImage = ((ImageView) view.findViewById(R.id.image));
        mThreeImage.setVisibility(View.GONE);
    }

    private void initFourElement(View view) {
        final String url = mDatas[3];
        mFourDownload = ((Button) view.findViewById(R.id.download));
        mFourDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFourDownload.getText().equals("DOWNLOAD")) {
                    fourDownloadTask = download(url, mFourDownload, mFourCancel, mFourImage, mFourProgress);
                } else if (mFourDownload.getText().equals("DOWNLOADING...")) {
                    fourDownloadTask.pause();
                } else if (mFourDownload.getText().equals("PAUSE")) {
                    fourDownloadTask.resume();
                }
            }
        });
        mFourCancel = ((Button) view.findViewById(R.id.cancel));
        mFourCancel.setVisibility(View.INVISIBLE);
        mFourCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fourDownloadTask.cancel();
            }
        });
        mFourProgress = ((DownloadProgressBar) view.findViewById(R.id.progress));
        mFourImage = ((ImageView) view.findViewById(R.id.image));
        mFourImage.setVisibility(View.GONE);
    }

    private IDownloadTask download(String url, final Button download, final Button cancel, final ImageView image, final DownloadProgressBar progressBar) {
        download.setText("DOWNLOADING...");
         return mDownloader.download(url, new IDownloadCallback() {
            @Override
            public void onBefore() {

            }

            @Override
            public void onProcess(final int process) {
                if (cancel.getVisibility() == View.INVISIBLE) {
                    cancel.setVisibility(View.VISIBLE);
                }
                progressBar.setProgress(process);
            }

            @Override
            public void onComplete(Data data) {
                Toast.makeText(MainActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
                if (data.getLocalUrl().endsWith("png") || data.getLocalUrl().endsWith("jpg")) {
                    Bitmap bitmap = BitmapFactory.decodeFile(data.getLocalUrl());
                    image.setVisibility(View.VISIBLE);
                    image.setImageBitmap(bitmap);
                }
                download.setText("COMPLETE");
                cancel.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailed(final String error) {
                Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                download.setText("DOWNLOAD");
                cancel.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCanceled() {
                Toast.makeText(MainActivity.this, "已取消", Toast.LENGTH_SHORT).show();
                download.setText("DOWNLOAD");
                progressBar.setProgress(0);
                cancel.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onPause() {
                Toast.makeText(MainActivity.this, "已暂停", Toast.LENGTH_SHORT).show();
                download.setText("PAUSE");
                cancel.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onResume() {
                Toast.makeText(MainActivity.this, "已恢复", Toast.LENGTH_SHORT).show();
                download.setText("DOWNLOADING...");
            }
        });
    }
}
