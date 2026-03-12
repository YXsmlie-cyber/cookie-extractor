# 🍪 Cookie Extractor - Android APK

一个用于自动获取抖音、B站、小黑盒Cookie的安卓应用。

## 📱 功能特性

- ✅ **抖音Cookie提取** - 自动获取抖音登录Cookie
- ✅ **B站Cookie提取** - 自动获取哔哩哔哩登录Cookie  
- ✅ **小黑盒Cookie提取** - 自动获取小黑盒登录Cookie
- ✅ **一键复制** - 点击Cookie文本即可复制到剪贴板
- ✅ **本地存储** - Cookie自动保存在本地，重启不丢失
- ✅ **批量获取** - 支持一键获取所有平台Cookie

## 🛠️ 技术栈

- **语言**: Kotlin
- **最低SDK**: Android 7.0 (API 24)
- **目标SDK**: Android 14 (API 34)
- **构建工具**: Gradle 8.2
- **架构**: 单Activity + WebView

## 📦 项目结构

```
cookie-extractor/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/cookie/extractor/
│   │       │   ├── MainActivity.kt          # 主界面
│   │       │   ├── WebViewActivity.kt       # WebView页面
│   │       │   └── CookieManager.kt         # Cookie管理工具
│   │       ├── res/
│   │       │   ├── layout/
│   │       │   │   ├── activity_main.xml    # 主界面布局
│   │       │   │   └── activity_webview.xml # WebView布局
│   │       │   └── values/
│   │       │       ├── strings.xml          # 字符串资源
│   │       │       └── themes.xml           # 主题样式
│   │       └── AndroidManifest.xml          # 清单文件
│   └── build.gradle                         # 模块配置
├── build.gradle                             # 项目配置
├── settings.gradle                          # 项目设置
└── README.md                                # 说明文档
```

## 🚀 编译运行

### 前置要求

1. Android Studio Hedgehog | 2023.1.1 或更高版本
2. JDK 17 或更高版本
3. Android SDK 34

### 编译步骤

1. **克隆或导入项目**
   ```bash
   cd /tmp/cookie-extractor
   ```

2. **使用Android Studio打开项目**
   - File → Open → 选择项目目录

3. **同步Gradle**
   - Android Studio会自动提示同步，点击"Sync Now"

4. **编译APK**
   ```bash
   ./gradlew assembleDebug
   ```
   
   或在Android Studio中: Build → Build Bundle(s) / APK(s) → Build APK(s)

5. **生成的APK位置**
   ```
   app/build/outputs/apk/debug/app-debug.apk
   ```

## 📲 安装使用

1. **安装APK到手机**
   ```bash
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

2. **使用步骤**
   - 打开应用
   - 点击要获取Cookie的平台按钮
   - 在WebView中登录对应账号
   - 登录成功后点击"提取Cookie"
   - Cookie自动保存并显示在主界面
   - 点击Cookie文本可复制到剪贴板

## ⚙️ 核心功能实现

### Cookie提取原理

1. **WebView加载** - 使用WebView加载目标平台网页
2. **Cookie监听** - 监听WebView的网络请求
3. **自动提取** - 在页面加载完成后自动提取Cookie
4. **本地存储** - 使用SharedPreferences保存Cookie

### 关键代码

```kotlin
// 提取Cookie
val cookieManager = CookieManager.getInstance()
val cookies = cookieManager.getCookie(url)

// 保存Cookie
sharedPreferences.edit()
    .putString("cookie_$platform", cookie)
    .apply()
```

## 🔐 权限说明

| 权限 | 用途 |
|------|------|
| INTERNET | 网络访问，加载网页 |
| ACCESS_NETWORK_STATE | 检查网络状态 |
| WRITE_EXTERNAL_STORAGE | 保存Cookie文件（可选） |

## 📝 注意事项

⚠️ **安全提示**
- Cookie包含敏感信息，请勿分享给他人
- 仅用于个人学习和研究目的
- 请遵守各平台的使用条款

⚠️ **使用限制**
- Cookie有效期由各平台控制
- 建议定期更新Cookie
- 某些平台可能有反爬机制

## 🐛 常见问题

**Q: 提取Cookie失败？**
A: 确保已成功登录，等待页面完全加载后再提取

**Q: Cookie过期很快？**
A: 部分平台Cookie有效期较短，需要重新登录获取

**Q: 某些网站无法加载？**
A: 检查网络连接，部分网站可能需要特殊处理

## 📄 开源协议

MIT License

## 👨‍💻 作者

OpenClaw AI Assistant

## 🎉 版本历史

- **v1.0** (2026-03-12)
  - 初始版本
  - 支持抖音、B站、小黑盒Cookie提取
  - 一键复制功能
  - 本地存储功能

---

**Made with ❤️ by OpenClaw**
