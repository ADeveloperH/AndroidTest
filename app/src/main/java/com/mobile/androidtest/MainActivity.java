package com.mobile.androidtest;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;

import com.mobile.androidtest.ui.ProgressWebView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 参考：http://www.jianshu.com/p/6e38e1ef203a
 * <p>
 * 注意：WebViewClient需要重写shouldOverrideUrlLoading返回true表示自己处理
 * 否则系统点击会默认打开外部浏览器
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.webview)
    ProgressWebView webview;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    private String TAG = "huang";

    /**
     * 1、跳转浏览器下载
     * 2、使用系统的下载服务
     * 3、自定义下载任务
     */
    public static int FLAG = 1;
    private DownloadCompleteReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // 使用
        registerReceiver();

        webview.loadUrl("http://online.10086.cn/bbs/thread/4afd5954-de63-4769-ae5a-8c7b1a6a2e2a.html?tid=4afd5954-de63-4769-ae5a-8c7b1a6a2e2a");

        webview.getWebView().setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition,
                                        String mimetype, long contentLength) {
                Log.d(TAG, "onDownloadStart: url:" + url);
                Log.d(TAG, "onDownloadStart: userAgent:" + userAgent);
                Log.d(TAG, "onDownloadStart: contentDisposition:" + contentDisposition);
                Log.d(TAG, "onDownloadStart: mimetype:" + mimetype);
                Log.d(TAG, "onDownloadStart: contentLength:" + contentLength);
                switch (FLAG) {
                    case 1://跳转浏览器下载
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                        break;
                    case 2://使用系统的下载服务
                        downloadBySystem(url, contentDisposition, mimetype);
                        break;
                    case 3:
                        String fileName = URLUtil.guessFileName(url, contentDisposition, mimetype);
                        String destPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                                .getAbsolutePath() + File.separator + fileName;
                        File downFile = new File(destPath);
                        if (downFile.exists() && downFile.length() == contentLength) {
                            //以前下载过直接打开
                            Log.d(TAG, "onDownloadStart: 以前下载过直接打开");
                            Intent handlerIntent = new Intent(Intent.ACTION_VIEW);
                            String mimeType = getMIMEType(url);
                            Uri uri = Uri.fromFile(downFile);
                            handlerIntent.setDataAndType(uri, mimeType);
                            startActivity(handlerIntent);
                        } else {
                            Log.d(TAG, "onDownloadStart: 没有下载过");
                            new DownloadTask().execute(url, destPath);
                        }
                        break;
                }
            }
        });
    }

    private void registerReceiver() {
        receiver = new DownloadCompleteReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(receiver, intentFilter);
    }


    private void downloadBySystem(String url, String contentDisposition, String mimeType) {
        // 指定下载地址
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        // 允许媒体扫描，根据下载的文件类型被加入相册、音乐等媒体库
        request.allowScanningByMediaScanner();
        // 设置通知的显示类型，下载进行时和完成后显示通知
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        // 设置通知栏的标题，如果不设置，默认使用文件名
//        request.setTitle("This is title");
        // 设置通知栏的描述
//        request.setDescription("This is description");
//        // 允许在计费流量下下载
//        request.setAllowedOverMetered(false);
        // 允许该记录在下载管理界面可见
        request.setVisibleInDownloadsUi(false);
        // 允许漫游时下载
        request.setAllowedOverRoaming(true);
        // 允许下载的网路类型
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        // 设置下载文件保存的路径和文件名
        String fileName = URLUtil.guessFileName(url, contentDisposition, mimeType);
        Log.d(TAG, "fileName: " + fileName);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
//        另外可选一下方法，自定义下载路径
//        request.setDestinationUri()
//        request.setDestinationInExternalFilesDir()
        final DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        // 添加一个下载任务
        long downloadId = downloadManager.enqueue(request);
        Log.d(TAG, "downloadId: " + downloadId);
    }


    private class DownloadTask extends AsyncTask<String, Void, Void> {
        // 传递两个参数：URL 和 目标路径
        private String url;
        private String destPath;

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute: 开始下载");
        }

        @Override
        protected Void doInBackground(String... params) {
            Log.d(TAG, "doInBackground: params[0]:" + params[0] + " params[1]:" + params[1]);
            url = params[0];
            destPath = params[1];
            OutputStream out = null;
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(15000);
                urlConnection.setReadTimeout(15000);
                InputStream in = urlConnection.getInputStream();
                out = new FileOutputStream(params[1]);
                byte[] buffer = new byte[10 * 1024];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                in.close();
            } catch (IOException e) {
                Log.d(TAG, "doInBackground: e:" + e.getLocalizedMessage());
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        Log.d(TAG, "doInBackground: e:" + e.getLocalizedMessage());
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d(TAG, "完成下载: ");
            Intent handlerIntent = new Intent(Intent.ACTION_VIEW);
            String mimeType = getMIMEType(url);
            Uri uri = Uri.fromFile(new File(destPath));
            Log.d(TAG, "onPostExecute: mimeType:" + mimeType + "uri:" + uri);
            handlerIntent.setDataAndType(uri, mimeType);
            startActivity(handlerIntent);
        }
    }

    private String getMIMEType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        Log.d(TAG, "extension: " + extension);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                FLAG = 1;
                break;
            case R.id.btn2:
                FLAG = 2;
                break;
            case R.id.btn3:
                FLAG = 3;
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }
}
