package com.cookie.extractor

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    
    private lateinit var tvDouyinCookie: TextView
    private lateinit var tvBilibiliCookie: TextView
    private lateinit var tvXiaoheiheCookie: TextView
    private lateinit var btnGetDouyin: Button
    private lateinit var btnGetBilibili: Button
    private lateinit var btnGetXiaoheihe: Button
    private lateinit var btnGetAll: Button
    
    private lateinit var cookieManager: CookieManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        cookieManager = CookieManager(this)
        
        initViews()
        loadSavedCookies()
        setClickListeners()
    }
    
    private fun initViews() {
        tvDouyinCookie = findViewById(R.id.tv_douyin_cookie)
        tvBilibiliCookie = findViewById(R.id.tv_bilibili_cookie)
        tvXiaoheiheCookie = findViewById(R.id.tv_xiaoheihe_cookie)
        btnGetDouyin = findViewById(R.id.btn_get_douyin)
        btnGetBilibili = findViewById(R.id.btn_get_bilibili)
        btnGetXiaoheihe = findViewById(R.id.btn_get_xiaoheihe)
        btnGetAll = findViewById(R.id.btn_get_all)
    }
    
    private fun loadSavedCookies() {
        tvDouyinCookie.text = cookieManager.getCookie("douyin") ?: "未获取"
        tvBilibiliCookie.text = cookieManager.getCookie("bilibili") ?: "未获取"
        tvXiaoheiheCookie.text = cookieManager.getCookie("xiaoheihe") ?: "未获取"
    }
    
    private fun setClickListeners() {
        btnGetDouyin.setOnClickListener {
            openWebView("douyin", "https://www.douyin.com")
        }
        
        btnGetBilibili.setOnClickListener {
            openWebView("bilibili", "https://www.bilibili.com")
        }
        
        btnGetXiaoheihe.setOnClickListener {
            openWebView("xiaoheihe", "https://api.xiaoheihe.cn")
        }
        
        btnGetAll.setOnClickListener {
            // 依次获取所有cookie
            openWebView("all", "https://www.douyin.com")
        }
        
        // 点击复制cookie
        tvDouyinCookie.setOnClickListener { copyCookie("douyin", tvDouyinCookie.text.toString()) }
        tvBilibiliCookie.setOnClickListener { copyCookie("bilibili", tvBilibiliCookie.text.toString()) }
        tvXiaoheiheCookie.setOnClickListener { copyCookie("xiaoheihe", tvXiaoheiheCookie.text.toString()) }
    }
    
    private fun openWebView(platform: String, url: String) {
        val intent = Intent(this, WebViewActivity::class.java).apply {
            putExtra("platform", platform)
            putExtra("url", url)
        }
        startActivityForResult(intent, REQUEST_CODE_GET_COOKIE)
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_GET_COOKIE && resultCode == RESULT_OK) {
            data?.let {
                val platform = it.getStringExtra("platform") ?: return
                val cookie = it.getStringExtra("cookie") ?: return
                
                saveCookie(platform, cookie)
            }
        }
    }
    
    private fun saveCookie(platform: String, cookie: String) {
        cookieManager.saveCookie(platform, cookie)
        
        when (platform) {
            "douyin" -> tvDouyinCookie.text = cookie
            "bilibili" -> tvBilibiliCookie.text = cookie
            "xiaoheihe" -> tvXiaoheiheCookie.text = cookie
        }
        
        Toast.makeText(this, "$platform Cookie已保存", Toast.LENGTH_SHORT).show()
    }
    
    private fun copyCookie(platform: String, cookie: String) {
        if (cookie == "未获取") {
            Toast.makeText(this, "请先获取$platform Cookie", Toast.LENGTH_SHORT).show()
            return
        }
        
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("$platform cookie", cookie)
        clipboard.setPrimaryClip(clip)
        
        Toast.makeText(this, "$platform Cookie已复制到剪贴板", Toast.LENGTH_SHORT).show()
    }
    
    companion object {
        const val REQUEST_CODE_GET_COOKIE = 1001
    }
}
