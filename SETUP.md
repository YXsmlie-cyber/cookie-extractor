# 🚀 GitHub Actions 自动编译APK指南

## 📋 操作步骤（5分钟完成）

### 第一步：创建GitHub仓库

1. 访问 https://github.com/new
2. 填写信息：
   - Repository name: `cookie-extractor`
   - Description: `抖音、B站、小黑盒Cookie提取器`
   - 选择 Public 或 Private
   - ✅ 勾选 "Add a README file"
3. 点击 "Create repository"

### 第二步：上传项目文件

**方法1：网页上传（最简单）**

1. 在新建的仓库页面，点击 "Add file" → "Upload files"
2. 将 `cookie-extractor.zip` 解压后的所有文件拖拽上传
3. 点击 "Commit changes"

**方法2：使用Git命令**

```bash
# 解压文件
unzip cookie-extractor.zip

# 进入项目目录
cd cookie-extractor

# 初始化git仓库
git init
git add .
git commit -m "Initial commit: Cookie Extractor App"

# 添加远程仓库（替换YOUR_USERNAME为你的GitHub用户名）
git remote add origin https://github.com/YOUR_USERNAME/cookie-extractor.git
git branch -M main
git push -u origin main
```

### 第三步：自动编译

上传完成后，GitHub Actions会自动开始编译：

1. 进入仓库页面
2. 点击 "Actions" 标签
3. 查看编译进度（约3-5分钟）

### 第四步：下载APK

编译完成后，有两种下载方式：

**方法1：从Actions下载**

1. 点击 Actions → 对应的workflow
2. 滚动到底部 "Artifacts"
3. 下载 `app-debug` 或 `app-release`

**方法2：从Releases下载**

1. 点击仓库右侧的 "Releases"
2. 下载最新的APK文件

---

## ✅ 编译状态查看

编译成功后，你会看到：
- ✅ 绿色的对勾图标
- 📦 APK文件（约3-5MB）
- 📝 编译日志

---

## ⚙️ 手动触发编译

如果需要重新编译：

1. Actions → Android CI Build
2. 点击 "Run workflow"
3. 选择分支并运行

---

## 🔧 配置签名（可选）

如果要发布到应用商店，需要配置签名：

1. 生成keystore文件
2. 添加到项目
3. 配置build.gradle
4. 在GitHub Secrets中添加签名信息

详细步骤见项目中的 `BUILD.md` 文件。

---

## 📱 APK信息

- **文件名:** app-debug.apk 或 app-release.apk
- **大小:** 约3-5MB
- **最低支持:** Android 7.0 (API 24)
- **目标版本:** Android 14 (API 34)

---

## 🐛 常见问题

**Q: 编译失败怎么办？**

A: 检查Actions日志，常见原因：
- 文件上传不完整
- 网络问题导致依赖下载失败
- 点击 "Re-run jobs" 重试

**Q: 如何修改应用信息？**

A: 编辑以下文件：
- `app/src/main/res/values/strings.xml` - 应用名称
- `app/build.gradle` - 版本号、包名

**Q: 如何添加新功能？**

A: 修改代码后push到GitHub，会自动重新编译

---

## 📞 需要帮助？

- GitHub Actions文档: https://docs.github.com/actions
- Android开发文档: https://developer.android.com

---

**祝编译成功！🎉**
