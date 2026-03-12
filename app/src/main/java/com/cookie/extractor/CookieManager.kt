package com.cookie.extractor

import android.content.Context
import android.content.SharedPreferences

/**
 * Cookie管理工具类
 * 负责Cookie的保存、读取和删除
 */
class CookieManager(context: Context) {
    
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    
    /**
     * 保存Cookie
     * @param platform 平台名称（douyin, bilibili, xiaoheihe）
     * @param cookie Cookie字符串
     */
    fun saveCookie(platform: String, cookie: String) {
        sharedPreferences.edit()
            .putString(getKey(platform), cookie)
            .putLong(getTimeKey(platform), System.currentTimeMillis())
            .apply()
    }
    
    /**
     * 获取Cookie
     * @param platform 平台名称
     * @return Cookie字符串，如果不存在返回null
     */
    fun getCookie(platform: String): String? {
        return sharedPreferences.getString(getKey(platform), null)
    }
    
    /**
     * 获取Cookie获取时间
     * @param platform 平台名称
     * @return 时间戳，如果不存在返回0
     */
    fun getCookieTime(platform: String): Long {
        return sharedPreferences.getLong(getTimeKey(platform), 0)
    }
    
    /**
     * 删除Cookie
     * @param platform 平台名称
     */
    fun deleteCookie(platform: String) {
        sharedPreferences.edit()
            .remove(getKey(platform))
            .remove(getTimeKey(platform))
            .apply()
    }
    
    /**
     * 清空所有Cookie
     */
    fun clearAllCookies() {
        sharedPreferences.edit().clear().apply()
    }
    
    /**
     * 检查Cookie是否存在
     * @param platform 平台名称
     * @return true表示存在
     */
    fun hasCookie(platform: String): Boolean {
        return getCookie(platform) != null
    }
    
    /**
     * 获取所有已保存的平台Cookie
     * @return Map<平台名, Cookie>
     */
    fun getAllCookies(): Map<String, String> {
        val cookies = mutableMapOf<String, String>()
        PLATFORMS.forEach { platform ->
            getCookie(platform)?.let { cookie ->
                cookies[platform] = cookie
            }
        }
        return cookies
    }
    
    private fun getKey(platform: String): String = "cookie_$platform"
    private fun getTimeKey(platform: String): String = "cookie_time_$platform"
    
    companion object {
        private const val PREF_NAME = "cookie_extractor_prefs"
        
        val PLATFORMS = listOf("douyin", "bilibili", "xiaoheihe")
        
        /**
         * 解析Cookie字符串中的特定字段
         * @param cookieString 完整的Cookie字符串
         * @param key 要提取的键名
         * @return 对应的值，如果不存在返回null
         */
        fun parseCookieValue(cookieString: String, key: String): String? {
            val cookies = cookieString.split(";")
            for (cookie in cookies) {
                val trimmed = cookie.trim()
                if (trimmed.startsWith("$key=")) {
                    return trimmed.substring(key.length + 1)
                }
            }
            return null
        }
        
        /**
         * 格式化Cookie时间
         * @param timestamp 时间戳
         * @return 格式化的时间字符串
         */
        fun formatCookieTime(timestamp: Long): String {
            if (timestamp == 0L) return "未获取"
            val date = java.util.Date(timestamp)
            val format = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault())
            return format.format(date)
        }
    }
}
