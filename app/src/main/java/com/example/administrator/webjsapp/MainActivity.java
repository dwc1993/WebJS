package com.example.administrator.webjsapp;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

/**
 * Android调用js代码
 */
public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webView);
        Button button = findViewById(R.id.button);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.loadUrl("file:///android_asset/js.html");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //这里用于触发js中的callJS方法
                //    webView.loadUrl("javaScript:callJS()");
                //因为该方法需要判断版本，在4.4及以上版本才生效
                int sdkInt = Build.VERSION.SDK_INT;
                if (sdkInt < 18) {
                    webView.loadUrl("javaScript:callJS()");
                } else {
                    webView.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {
                            Log.e("TAG", "------message-------->" + s);
                        }
                    });
                }
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            //用于拦截callJS中的alert的信息
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("js");
                builder.setMessage(message);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        result.confirm();
                    }
                });
                builder.setCancelable(false);
                builder.create().show();
                return true;
            }
        });
    }
}
