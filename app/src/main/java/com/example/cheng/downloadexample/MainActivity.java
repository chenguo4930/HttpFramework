package com.example.cheng.downloadexample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.filedownload.file.FileStorageManager;
import com.example.filedownload.utils.Logger;
import com.example.service.HttpApiProvider;
import com.example.service.HttpEntityResponse;
import com.example.service.HttpRequestEntity;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Map<String ,String> map = new HashMap<>();
        map.put("usrname","seven");
        map.put("userage","12");
       HttpApiProvider.helloWord("http://192.168.1.12:8080/web/HelloServlet", map, new HttpEntityResponse<String>() {
           @Override
           public void success(HttpRequestEntity requestEntity, String data) {
               Logger.debug("nate", data.toString());
           }

           @Override
           public void fail(int errorCode, String errorMsg) {

           }
       });


        File file = FileStorageManager.getInstance().getFileByName("http://www.imooc.com");

        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
//        final String url = "http://szimg.mukewang.com/5763765d0001352105400300-360-202.jpg";
        final String url = "http://shouji.360tpcdn.com/160901/84c090897cbf0158b498da0f42f73308/com.icoolme.android.weather_2016090200.apk";
     /*   HttpManager.getInstance().asyncRequest(url, new DownloadCallBack() {
            @Override
            public void success(File file) {
                LogUtils.e("---success "+file.getAbsolutePath());
                final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }

            @Override
            public void fail(int errorCode, String errorMessage) {

            }

            @Override
            public void progress(int progress) {

            }
        });*/

      /*  DownloadManager.getInstance().download(url, new DownloadCallBack() {
            @Override
            public void success(File file) {
          //两个线程下载会回调两次
                if (count < 1) {
                    count++;
                    return;
                }
                installApk(file);
                LogUtils.d("success:" + file.getAbsolutePath());
            }

            @Override
            public void fail(int errorCode, String errorMessage) {

            }

            @Override
            public void progress(int progress) {
                LogUtils.d("-----------progress:" + progress);
                ((ProgressBar) findViewById(R.id.progressBar)).setProgress(progress);
            }
        });*/
    }

    private void installApk(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + file.getAbsoluteFile().toString()), "application/vnd.android.package-archive");
        MainActivity.this.startActivity(intent);
    }
}
