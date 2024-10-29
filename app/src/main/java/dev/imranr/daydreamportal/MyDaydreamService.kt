package dev.imranr.daydreamportal

import android.annotation.SuppressLint
import android.service.dreams.DreamService
import android.webkit.WebView
import android.webkit.WebViewClient

class MyDaydreamService : DreamService() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        isInteractive = false // Disable interaction to avoid accidental touches
        isFullscreen = true   // Make it fullscreen

        // Initialize the WebView here to set it up for the screensaver
        webView = WebView(this).apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()

            // Load the URL from SharedPreferences
            val url = getSharedPreferences("prefs", MODE_PRIVATE)
                .getString("url", "https://example.org") ?: "https://example.org"
            loadUrl(url)
        }

        setContentView(webView)
    }

    override fun onDreamingStarted() {
        super.onDreamingStarted()
        // Code that should execute when screensaver starts
        webView.onResume()
    }

    override fun onDreamingStopped() {
        super.onDreamingStopped()
        // Clean up when screensaver stops
        webView.onPause()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        // Additional cleanup if necessary when service detaches
        webView.destroy()
    }
}
