package com.mobile.androidtest;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.disklrucache.DiskLruCache;
import com.mobile.androidtest.utils.DiskLruCacheUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_download)
    Button btnDownload;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.btn_clearcache)
    Button btnClearcache;
    @BindView(R.id.tv_cachesize)
    TextView tvCachesize;
    @BindView(R.id.btn_clearcachebyurl)
    Button btnClearcachebyurl;
    @BindView(R.id.edit)
    EditText edit;
    private String TAG = "huang";
    private String IMG_URL = "https://avatars2.githubusercontent.com/u/13491489?v=4&u=ad4dd532d5cd5302a4bf71ef02ee0113349db435&s=400";//下载的链接
    private File cacheDir;

    /**
     * 初始化DiskLruCache
     */
    private static final int MAX_SIZE = 10 * 1024 * 1024;//10MB
    private DiskLruCache diskLruCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initCacheFilePath();
        openDiskLruCache();
        updateShowCacheSize();
        updateImageUrl();
    }

    private void updateImageUrl() {
        String imageUrl = edit.getText().toString().trim();
        if (!TextUtils.isEmpty(imageUrl)) {
            IMG_URL = imageUrl;
        } else {
            IMG_URL = "https://avatars2.githubusercontent.com/u/13491489?v=4&u=ad4dd532d5cd5302a4bf71ef02ee0113349db435&s=400";
        }
    }

    /**
     * 设置缓存的目录
     *
     * @return
     */
    private File initCacheFilePath() {
        cacheDir = DiskLruCacheUtils.getDiskCacheDir(this, "DiskLruCache");
        Log.d(TAG, "openDiskLruCache: path:" + cacheDir.getAbsolutePath());
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        return cacheDir;
    }

    /**
     * 打开DiskLruCache
     */
    private void openDiskLruCache() {
        if (diskLruCache == null || diskLruCache.isClosed()) {
            try {
                //初始化DiskLruCache
                /**
                 * directory：指定数据缓存地址
                 * appVersion：APP版本号，当版本号改变时，缓存数据会被清除
                 * valueCount：同一个key可以对应多少文件
                 * maxSize：最大可以缓存的数据量
                 */
                diskLruCache = DiskLruCache.open(cacheDir, DiskLruCacheUtils.getAppVersion(this),
                        1, MAX_SIZE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick({R.id.btn_download, R.id.btn_clearcache, R.id.btn_clearcachebyurl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_download:
                updateImageUrl();
                if (diskLruCache != null && diskLruCache.isClosed()) {
                    openDiskLruCache();
                }
                Bitmap bitmap = DiskLruCacheUtils.getCache(diskLruCache, IMG_URL);
                if (bitmap != null) {
                    showToast("缓存中已经存在了");
                    image.setImageBitmap(bitmap);
                } else {
                    showToast("缓存中没有存在");
                    new DownloadTask().execute();
                }
                break;
            case R.id.btn_clearcache://清除所有缓存
                DiskLruCacheUtils.deleteCache(diskLruCache);
                showToast("清除所有缓存完成了");
                updateShowCacheSize();
                break;
            case R.id.btn_clearcachebyurl://清除当前链接的缓存
                updateImageUrl();
                DiskLruCacheUtils.removeCache(diskLruCache, IMG_URL);
                showToast("清除当前链接的缓存");
                updateShowCacheSize();
                break;
        }
    }

    /**
     * 第一个泛型参数指定为Void，表示在执行AsyncTask的时候不需要传入参数给后台任务
     * 第二个泛型参数指定为Integer，表示使用整型数据来作为进度显示单位
     * 第三个泛型参数指定为Boolean，则表示使用布尔型数据来反馈执行结果
     * <p>
     * 总结：
     * 1.AsyncTask的第一个参数类型决定了doInBackground方法、execute方法的参数类型
     * 2.AsyncTask的第二个参数类型决定了publishProgress方法、onProgressUpdate方法的参数类型
     * 3.AsyncTask的第三个参数类型决定了doInBackground方法的返回值类型、onPostExecute方法的参数类型
     * 注：如果不需要参数传递，那么AsyncTask的参数列表可以写成：AsyncTask<Void, Void, Void>
     * <p>
     * 1.AsyncTask类必须在UI thread加载
     * 2.Task实例必须在UI thread中创建
     * 3.execute方法必须在UI thread中调用
     * 4.不要手动调用onPreExecute、onPostExecute、doInBackground、onProgressUpdate方法
     * 5.该Task只能执行一次，多次调用将会抛出异常
     * java.lang.IllegalStateException: Cannot execute task:
     * the task has already been executed (a task can be executed only once)
     */
    class DownloadTask extends AsyncTask<Void, Integer, Boolean> {

        /**
         * 这个方法会在后台任务开始执行之间调用，用于进行一些界面上的初始化操作，比如显示一个进度条对话框等。
         * 运行在UI线程，可以设置或修改UI控件，如显示一个进度条
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute: ");
        }

        /**
         * 运行在后台线程，不可以设置或修改UI控件，该方法的执行时机是：
         * onPreExecute()执行完毕立即调用该方法，在方法中进行耗时操作，可以在该方法中调用publishProgress方法
         * 来发布当执行的进度，调用publishProgress方法后就会立即触发onProgressUpdate方法
         * <p>
         * 形参对应的是AsyncTask的第一个参数，即参数类型要相同。doInBackground方法
         * 接收实参的时机是：在UI线程中调用execute方法时为execute方法设置值，调用execute方法时
         * 这个值就会通过参数形式传给doInBackground方法，
         * doInBackground方法返回的结果对应的是AsyncTask的第三个参数，即参数类型要相同，
         *
         * @param params
         * @return
         */
        @Override
        protected Boolean doInBackground(Void... params) {
            Log.d(TAG, "doInBackground: ");
//            publishProgress();
            try {
                String key = DiskLruCacheUtils.hashKeyForDisk(IMG_URL);
                //得到DiskLruCache.Editor
                DiskLruCache.Editor editor = diskLruCache.edit(key);
                if (editor != null) {
                    OutputStream outputStream = editor.newOutputStream(0);
                    if (downloadUrlToStream(IMG_URL, outputStream)) {
                        //写入缓存
                        editor.commit();
                    } else {
                        //写入失败
                        editor.abort();
                    }
                }
                diskLruCache.flush();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        /**
         * 当在后台任务中调用了publishProgress(Progress...)方法后，这个方法就很快会被调用，
         * 方法中携带的参数就是在后台任务中传递过来的。在这个方法中可以对UI进行操作，
         * 利用参数中的数值就可以对界面元素进行相应的更新。
         * 运行在UI线程，可以设置或修改UI控件
         * <p>
         * 参数类型与AsyncTask的第二个参数对应，即参数类型要相同，在doInBackground方法中可以调用
         *
         * @param values
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d(TAG, "onProgressUpdate: progress:" + values[0]);
        }

        /**
         * 当后台任务执行完毕并通过return语句进行返回时，这个方法就很快会被调用。
         * 返回的数据会作为参数传递到此方法中，可以利用返回的数据来进行一些UI操作，
         * 比如说提醒任务执行的结果，以及关闭掉进度条对话框等。
         * <p>
         * 运行在UI线程，可以设置或修改UI控件，该方法在doInBackground方法执行完毕
         * 后就会调用，该方法的参数就是doInBackground方法返回的结果，可以这样思考：doInBackground方法
         * 既然有一个返回结果，那么这个返回结果到底返回给谁呢？答案就是：通过参数形式传递给onPostExecute方法
         * <p>
         * 参数值就是doInBackground方法返回的结果传递过来的。因此onPostExecute方法的参数
         * 类型与AsyncTask的第三个参数也对应。即相同
         *
         * @param aBoolean
         */
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Log.d(TAG, "onPostExecute: result:" + aBoolean);
            if (aBoolean) {
                //缓存完成了
                showToast("缓存完成了");
                Bitmap bitmap = DiskLruCacheUtils.getCache(diskLruCache, IMG_URL);
                if (bitmap != null) {
                    image.setImageBitmap(bitmap);
                }
                updateShowCacheSize();
            }
        }

    }

    /**
     * 根据链接下载图片
     *
     * @param urlString
     * @param outputStream
     * @return
     */
    private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;

        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            int IO_BUFFER_SIZE = 8 * 1024;
            in = new BufferedInputStream(urlConnection.getInputStream(), IO_BUFFER_SIZE);
            out = new BufferedOutputStream(outputStream, IO_BUFFER_SIZE);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error in downloadBitmap - " + e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
            }
        }
        return false;
    }

    private void updateShowCacheSize() {
        openDiskLruCache();
        tvCachesize.setText("当前缓存大小" + DiskLruCacheUtils.getCacheSize(diskLruCache) + "B");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 这个方法用于将DiskLruCache关闭掉，是和open()方法对应的一个方法。
         * 关闭掉了之后就不能再调用DiskLruCache中任何操作缓存数据的方法，
         * 通常只应该在Activity的onDestroy()方法中去调用close()方法。
         */
        try {
            diskLruCache.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
