package com.project.edukasidiabetesmilitus.video_media

import android.app.DownloadManager
import android.content.DialogInterface
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.project.edukasidiabetesmilitus.databinding.ActivityVideoDetailBinding


class VideoDetailActivity : AppCompatActivity() {

    private var _binding : ActivityVideoDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityVideoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val link = intent.getStringExtra(VIDEO)

        binding.apply {

            backButton.setOnClickListener {
                onBackPressed()
            }

            CookieManager.getInstance().setAcceptCookie(true);
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                CookieManager.getInstance().setAcceptThirdPartyCookies(webview, true)
            }

            // add your link here
            webview.loadUrl(link!!)
            val ws: WebSettings = webview.settings

            // Enabling javascript
            ws.javaScriptEnabled = true;
            ws.domStorageEnabled = true;
          //  ws.mediaPlaybackRequiresUserGesture = false;

      //      webview.settings.javaScriptCanOpenWindowsAutomatically = true
//            webview.clearCache(true)
//            webview.clearHistory()

            webview.webViewClient = Client()
            webview.webChromeClient = WebChromeClient()

            // download manager is a service that can be used to handle downloads
            webview.setDownloadListener(DownloadListener { url, s1, s2, s3, l ->
                val req = DownloadManager.Request(Uri.parse(url))
                req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                dm.enqueue(req)
                Toast.makeText(this@VideoDetailActivity, "Downloading....", Toast.LENGTH_SHORT).show()
            })
        }
    }

    private class Client : WebViewClient() {
        // on page started load start loading the url
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }

        // load the url of our drive
        @Deprecated("Deprecated in Java")
        override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
            view.loadUrl(url!!)
            return true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
        }

        @Deprecated("Deprecated in Java")
        override fun onReceivedError(
            webView: WebView,
            errorCode: Int,
            description: String?,
            failingUrl: String?
        ) {
            // if stop loading
            try {
                webView.stopLoading()
            } catch (e: Exception) {
            }
            if (webView.canGoBack()) {
                webView.goBack()
            }

            // if loaded blank then show error
            // to check internet connection using
            // alert dialog
            webView.loadUrl("about:blank")
            val alertDialog: AlertDialog = AlertDialog.Builder(webView.context).create()
            alertDialog.setTitle("Error")
            alertDialog.setMessage("Check your internet connection and Try again.")
            alertDialog.setButton(
                DialogInterface.BUTTON_POSITIVE, "Try Again"
            ) { dialog, _ ->
                dialog.dismiss()
            }
            alertDialog.show()
            super.onReceivedError(webView, errorCode, description, failingUrl)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val VIDEO = "video"
    }
}