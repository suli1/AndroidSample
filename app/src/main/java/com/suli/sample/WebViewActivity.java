package com.suli.sample;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.suli.lib.utils.LogUtils;
import com.suli.lib.utils.ToastUtils;

/**
 * Created by suli690 on 2017/2/24.
 */

public class WebViewActivity extends FragmentActivity {
  private final static String DEFAULT_URL = "http://image.baidu.com/";
  private final static String EXTRA_URL = "url";

  @Bind(R.id.et_url) EditText etUrl;
  @Bind(R.id.web_view) WebView webView;

  public static Intent newIntent(Context context, @Nullable String url) {
    Intent intent = new Intent(context, WebViewActivity.class);
    if (!TextUtils.isEmpty(url)) {
      intent.putExtra(EXTRA_URL, url);
    }
    return intent;
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.webview_layout);
    ButterKnife.bind(this);

    webView.setWebViewClient(new WebViewClient() {

      @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        LogUtils.d("onPageStarted:" + url);
      }

      @Override public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
        LogUtils.d("onPageResource:" + url);
      }

      @Override public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        LogUtils.d("onPageFinished:" + url);
      }

      @Override public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return super.shouldOverrideUrlLoading(view, request);
      }
    });

    String url = getIntent().getStringExtra(EXTRA_URL);
    if (TextUtils.isEmpty(url)) {
      url = DEFAULT_URL;
    }
    webView.loadUrl(url);
  }

  @OnClick(R.id.btn_go) void onGoUrlClick() {
    CharSequence url = etUrl.getText();
    if (TextUtils.isEmpty(url)) {
      ToastUtils.showShortToast("Please input your url!");
      return;
    }
    webView.loadUrl(url.toString());
  }
}
