package com.example.administrator.webjsapp

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.JavascriptInterface
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main2.*

/**
 * js调用Android代码
 */
class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val settings = webView2.settings
        settings.javaScriptEnabled = true
        //通过addJavascriptInterface（）方法将Java对象AndroidtoJs映射到js中
        //参数1：Java对象，参数2：Java对象名，在js可以直接使用该名字进行调用该类中的方法
        webView2.addJavascriptInterface(AndroidtoJs(this), "test")
        //加载本地assets中的html，格式固定是：file:///android_asset/html文件名.html
        webView2.loadUrl("file:///android_asset/js2.html")

    }

    class AndroidtoJs(private val context: Context) : Any() {
        //订阅js需要调用的方法，被js调用的方法必须加入注解JavascriptInterface
        @JavascriptInterface
        fun helloJs(message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}
