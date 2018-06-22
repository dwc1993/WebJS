package com.example.administrator.webjsapp

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.JsPromptResult
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main4.*

/**
 * js调用Android代码
 */
class MainActivity4 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
        val settings = webView4.settings
        settings.javaScriptEnabled=true//js 交互
        settings.javaScriptCanOpenWindowsAutomatically=true//允许js弹窗
        //加载js
        webView4.loadUrl("file:///android_asset/js4.html")
        webView4.webChromeClient=WebClient()
    }
    inner class WebClient:WebChromeClient(){
        /**
         * 拦截js输入框
         * 参数message：代表prompt()的内容，不是url
         * 参数 result：代表输入框的返回值
         */
        override fun onJsPrompt(view: WebView?, url: String?, message: String?, defaultValue: String?, result: JsPromptResult?): Boolean {
            val uri = Uri.parse(message)
            if (uri.scheme=="js"){
                Log.e("TAG","------uri.authority--------->"+uri.authority)
                if (uri.authority=="demo"){
                    Toast.makeText(this@MainActivity4,"js调用Android方法",Toast.LENGTH_SHORT).show()
                    var params=HashMap<String,String>()
                    val names = uri.queryParameterNames
                    result!!.confirm("js调用Android方法成功了")
                }
                return true
            }
            return super.onJsPrompt(view, url, message, defaultValue, result)
        }
          //拦截alert警告框
        override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
            return super.onJsAlert(view, url, message, result)
        }
        //拦截js确认框
        override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
            return super.onJsConfirm(view, url, message, result)
        }

    }
}
