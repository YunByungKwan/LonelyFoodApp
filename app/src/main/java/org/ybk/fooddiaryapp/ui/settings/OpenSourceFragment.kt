package org.ybk.fooddiaryapp.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.databinding.OpenSourceFragBinding

class OpenSourceFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<OpenSourceFragBinding>(
            inflater, R.layout.open_source_frag, container, false).apply {
            fragment = this@OpenSourceFragment
        }

        binding.webView.loadUrl("file:///android_asset/openSource.html")
        binding.webView.webViewClient = MyWebViewClient()
        binding.webView.webChromeClient = WebChromeClient()
        return binding.root
    }

    inner class MyWebViewClient: WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return true
        }
    }
}