# 📦 APK编译指南

## 🎯 快速开始

本项目已创建完成！按照以下步骤编译APK：

## 📋 编译前准备

### 1. 安装必需软件

**选项A：使用Android Studio（推荐）**
1. 下载并安装 [Android Studio](https://developer.android.com/studio)
2. 安装Android SDK 34
3. 配置JDK 17

**选项B：仅使用命令行**
1. 安装JDK 17+
   ```bash
   # Ubuntu/Debian
   sudo apt install openjdk-17-jdk
   
   # macOS
   brew install openjdk@17
   ```

2. 设置JAVA_HOME
   ```bash
   export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
   export PATH=$PATH:$JAVA_HOME/bin
   ```

3. 下载并配置Android SDK
   ```bash
   # 下载Android SDK command-line tools
   wget https://dl.google.com/android/repository/commandlinetools-linux-9477386_latest.zip
   unzip commandlinetools-linux-9477386_latest.zip -d ~/android-sdk
   
   # 配置环境变量
   export ANDROID_HOME=~/android-sdk
   export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin
   export PATH=$PATH:$ANDROID_HOME/platform-tools
   
   # 安装必需组件
   sdkmanager "platforms;android-34"
   sdkmanager "build-tools;34.0.0"
   ```

### 2. 设置Gradle Wrapper（项目已配置）

项目已包含gradle wrapper配置：
```properties
distributionUrl=https\://services.gradle.org/distributions/gradle-8.2-bin.zip
```

首次编译时会自动下载Gradle 8.2。

## 🔨 编译步骤

### 方法1：使用Gradle命令行

```bash
# 进入项目目录
cd /tmp/cookie-extractor

# 添加执行权限
chmod +x gradlew

# 编译Debug APK
./gradlew assembleDebug

# 编译Release APK（需要签名配置）
./gradlew assembleRelease
```

### 方法2：使用Android Studio

1. 打开Android Studio
2. File → Open → 选择 `/tmp/cookie-extractor`
3. 等待Gradle同步完成
4. Build → Build Bundle(s) / APK(s) → Build APK(s)
5. APK生成后会自动提示位置

## 📍 APK输出位置

编译成功后，APK文件位于：

```
app/build/outputs/apk/debug/app-debug.apk
```

文件大小约：**3-5 MB**

## 📲 安装到手机

### 通过ADB安装

```bash
# 连接手机（开启USB调试模式）
adb devices

# 安装APK
adb install app/build/outputs/apk/debug/app-debug.apk

# 或强制安装（覆盖已安装版本）
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

### 直接传输安装

1. 将APK文件传输到手机
   - 通过数据线复制
   - 通过网络传输（微信/QQ/邮件等）
   
2. 在手机上打开APK文件安装
   - 可能需要允许"安装未知来源应用"

## ⚠️ 常见编译问题

### 问题1：Gradle下载失败

**解决方案：**
```bash
# 手动下载Gradle
wget https://services.gradle.org/distributions/gradle-8.2-bin.zip
unzip gradle-8.2-bin.zip -d ~/.gradle/wrapper/dists

# 或使用国内镜像
# 编辑 gradle/wrapper/gradle-wrapper.properties
distributionUrl=https\://mirrors.cloud.tencent.com/gradle/gradle-8.2-bin.zip
```

### 问题2：SDK未找到

**解决方案：**
```bash
# 创建 local.properties 文件
echo "sdk.dir=$ANDROID_HOME" > local.properties

# 或在Android Studio中配置SDK路径
```

### 问题3：JDK版本不匹配

**解决方案：**
```bash
# 检查JDK版本
java -version

# 应该显示 17.x.x
# 如果不是，安装JDK 17
```

### 问题4：依赖下载慢

**解决方案：**
编辑 `build.gradle`，添加国内镜像：
```gradle
repositories {
    maven { url 'https://maven.aliyun.com/repository/google' }
    maven { url 'https://maven.aliyun.com/repository/central' }
    google()
    mavenCentral()
}
```

## 🚀 编译优化建议

### 加速编译

在 `gradle.properties` 中添加：
```properties
org.gradle.jvmargs=-Xmx4096m -XX:+UseParallelGC
org.gradle.parallel=true
org.gradle.caching=true
android.enableBuildCache=true
```

### 减小APK体积

1. 启用混淆和优化：
```gradle
buildTypes {
    release {
        minifyEnabled true
        shrinkResources true
        proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
    }
}
```

2. 启用R8编译器：
```gradle
android {
    buildFeatures {
        buildConfig = false
        aidl = false
        renderScript = false
        resValues = false
        shaders = false
    }
}
```

## 📊 编译时间预估

| 配置 | 首次编译 | 后续编译 |
|------|---------|---------|
| 高配置PC (16GB RAM, SSD) | 2-5分钟 | 10-30秒 |
| 中等配置 (8GB RAM, HDD) | 5-10分钟 | 30-60秒 |
| 低配置 (<8GB RAM) | 10-20分钟 | 1-2分钟 |

## ✅ 验证编译成功

编译成功后会看到：
```
BUILD SUCCESSFUL in Xm XXs
```

APK文件会自动生成在：
```
app/build/outputs/apk/debug/app-debug.apk
```

检查APK：
```bash
# 查看APK信息
aapt dump badging app/build/outputs/apk/debug/app-debug.apk

# 查看APK内容
unzip -l app/build/outputs/apk/debug/app-debug.apk
```

## 🎉 编译完成后

1. **安装测试**
   ```bash
   adb install -r app/build/outputs/apk/debug/app-debug.apk
   ```

2. **运行应用**
   ```bash
   adb shell am start -n com.cookie.extractor/.MainActivity
   ```

3. **查看日志**
   ```bash
   adb logcat | grep CookieExtractor
   ```

---

**祝你编译成功！🚀**
