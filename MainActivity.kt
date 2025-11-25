package com.smartonmidia.smartapp

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webView = WebView(this)
        setContentView(webView)

        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.allowFileAccess = true
        settings.allowContentAccess = true

        // se precisar lidar com mixed content (HTTP dentro de HTTPS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

        // habilita debugging remoto do WebView (útil durante desenvolvimento)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }

        webView.webChromeClient = WebChromeClient()

        // Evita abrir link no browser externo
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                // abre tudo no próprio WebView
                view.loadUrl(request.url.toString())
                return true
            }

            // opcional: tratar erros / fallback
            override fun onReceivedError(view: WebView, errorCode: Int, description: String?, failingUrl: String?) {
                super.onReceivedError(view, errorCode, description, failingUrl)
                // aqui você pode carregar uma página local de erro (file:///android_asset/erro.html)
            }
        }

        // 1) Carregar a página hospedada no GitHub Pages (HTTPS)
        webView.loadUrl("https://smartonmidia.github.io/smartapp/App/painelexibidor1.html")

        // 2) OU, se preferir rodar OFFLINE com assets embutidos:
        // webView.loadUrl("file:///android_asset/App/painelexibidor1.html")
    }

    override fun onBackPressed() {
        val webView = findViewById<WebView>(android.R.id.content).getChildAt(0) as? WebView
        if (webView != null && webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
