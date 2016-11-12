package com.kbi;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private WebView wb;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        wb = (WebView)findViewById(R.id.webView);

        wb.setVisibility(View.INVISIBLE);
        WebSettings webSettings = wb.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wb.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String url){
                //Inject javascript code to the url given
                //Not display the element
                wb.loadUrl("javascript:(function(){"+"document.getElementById('show').style.display ='none';"+"})()");
                //Call to a function defined on my myJavaScriptInterface
                wb.loadUrl("javascript: window.CallToAnAndroidFunction.setVisible()");
            }
        });

        //Add a JavaScriptInterface, so I can make calls from the web to Java methods
        wb.addJavascriptInterface(new myJavaScriptInterface(), "CallToAnAndroidFunction");
        wb.loadUrl("http://mr-techs.16mb.com/BTA/asd.html");







    }





    public class myJavaScriptInterface {
        @JavascriptInterface
        public void setVisible(){
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    wb.setVisibility(View.VISIBLE);
                }
            });
        }

    }



}
