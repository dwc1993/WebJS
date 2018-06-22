package com.example.administrator.webjsapp

import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main3.*


/**
 * js调用Android代码
 */
class MainActivity3 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        val settings = webView3.settings
        settings.javaScriptEnabled = true//js交互
        settings.javaScriptCanOpenWindowsAutomatically = true//允许弹窗
        //第一步加载js
        webView3.loadUrl("file:///android_asset/js3.html")
        webView3.webViewClient = WebClient(this, webView3)

        webView3.webChromeClient = object : WebChromeClient() {
            //用于显示returnResult中的接受到的信息
            //WebChromeClient的onJsAlert、onJSConfirm、onJsPrompt方法分别拦截js对话框alert()、confirm()、prompt()消息
            override fun onJsAlert(view: WebView, url: String, message: String, result: JsResult): Boolean {
                val builder = AlertDialog.Builder(this@MainActivity3)
                builder.setTitle("js")
                builder.setMessage(message)
                builder.setPositiveButton(android.R.string.ok) { _, _ -> result.confirm() }
                builder.setCancelable(false)
                builder.create().show()
                return true
            }
        }
    }

    class WebClient(private var context: Context, val webView: WebView) : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            //第二步：根据协议判断是否是所需要的url
            // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
            //假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）
            val uri = Uri.parse(url)
            if (uri.scheme == "js") {
                //如果authority 等于原先webview约定的协议，拦截url，js开始执行Android需要的方法
                if (uri.authority == "webview") {
                    //第三步：执行js需要的逻辑
                    Toast.makeText(context, "js调用Android方法", Toast.LENGTH_SHORT).show()
                    //将得到的值返回给js的函数
                    webView.loadUrl("javascript:returnResult(" + 1235647 + ")")
                    /*   var params = HashMap<String, String>()
                       val queryParameterNames = uri.queryParameterNames*/
                }
                return true
            }
            return super.shouldOverrideUrlLoading(view, url)
        }
    }
}
