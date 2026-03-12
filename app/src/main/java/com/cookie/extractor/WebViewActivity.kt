package com.cookie.extractor

import android.content.Intent
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WebViewActivity : AppCompatActivity() {
    
    private lateinit var webView: WebView
    private lateinit var tvUrl: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var btnExtractCookie: Button
    private lateinit var btnBack: Button
    
    private var platform: String? = null
    private var targetUrl: String? = null
    private var extractedCookies: String? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        
        platform = intent.getStringExtra("platform")
        targetUrl = intent.getStringExtra("url")
        
        initViews()
        setupWebView()
        loadUrl()
    }
    
    private fun initViews() {
        webView = findViewById(R.id.webView)
        tvUrl = findViewById(R.id.tv_url)
        progressBar = findViewById(R.id.progressBar)
        btnExtractCookie = findViewById(R.id.btn_extract_cookie)
        btnBack = findViewById(R.id.btn_back)
        
        btnExtractCookie.setOnClickListener { extractAndReturnCookie() }
        btnBack.setOnClickListener { finish() }
    }
    
    private fun setupWebView() {
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
            useWideViewPort = true
            loadWithOverviewMode = true
            mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        
        // 允许第三方Cookie
        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)
        
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                request?.url?.let {
                    tvUrl.text = it.toString()
                }
                return false
            }
            
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.visibility = android.view.View.GONE
                tvUrl.text = url
                
                // 自动提取Cookie
                extractCookieFromWebView()
            }
        }
    }
    
    private fun loadUrl() {
        targetUrl?.let {
            tvUrl.text = it
            progressBar.visibility = android.view.View.VISIBLE
            webView.loadUrl(it)
        }
    }
    
    private fun extractCookieFromWebView() {
        val url = webView.url ?: return
        val cookieManager = CookieManager.getInstance()
        val cookies = cookieManager.getCookie(url)
        
        cookies?.let {
            extractedCookies = it
            runOnUiThread {
                btnExtractCookie.isEnabled = true
            }
        }
    }
    
    private fun extractAndReturnCookie() {
        extractedCookies?.let { cookie ->
            val resultIntent = Intent().apply {
                putExtra("platform", platform)
                putExtra("cookie", cookie)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        } ?: run {
            android.widget.Toast.makeText(
                this,
                "未检测到Cookie，请先登录",
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }
    }
    
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
