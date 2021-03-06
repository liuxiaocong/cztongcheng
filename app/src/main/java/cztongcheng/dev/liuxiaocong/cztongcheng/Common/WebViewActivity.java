package cztongcheng.dev.liuxiaocong.cztongcheng.Common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.jude.swipbackhelper.SwipeBackHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;

import butterknife.ButterKnife;
import cztongcheng.dev.liuxiaocong.cztongcheng.Home.News.ENewsType;
import cztongcheng.dev.liuxiaocong.cztongcheng.Home.News.TitleModel;
import cztongcheng.dev.liuxiaocong.cztongcheng.R;

/**
 * Created by LiuXiaocong on 8/19/2016.
 */
public class WebViewActivity extends AppCompatActivity implements View.OnClickListener {
    WebView mWebView;
    public static final String HTML_DATA = "HTML_DATA";
    public static final String TITLE = "TITLE";
    public static final String TITLE_MODEL = "TITLE_MODEL";
    private final static String regxpForFontStartTag = "<font[^>]*?>";
    private final static String regxpForFontEndTag = "</font>";
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.back)
    View mBack;


    public static void startWithTitleModel(Context context, TitleModel titleModel) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(TITLE_MODEL, titleModel);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this);
        setContentView(R.layout.web_view);
        mWebView = (WebView) findViewById(R.id.webview);
        Intent i = getIntent();
        TitleModel titleModel = (TitleModel) i.getSerializableExtra(TITLE_MODEL);
        String data = titleModel.getContent();
        String title = titleModel.getTitle();
        ButterKnife.bind(this);
        mTitle.setText(title);
        mBack.setOnClickListener(this);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html>");
        stringBuilder.append("<head>");
        stringBuilder.append("<style>.MsoNormal{font-size:14px !important;line-height:150% !important;margin-right:5px !important;margin-left:5px !important;text-indent:0 !important}a{word-break: break-all !important;}img{max-width:100% !important;height:auto !important}body{background-color: #fffff !important; width:auto; height: auto;font:14px/20px Calibri,Microsoft YaHei,verdana,Arial,Helvetica,sans-serif !important}}</style>");
        stringBuilder.append("<meta http-equiv=\"Content-type\" content=\"text/html; charset=utf-8\">");
        stringBuilder.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0\"/>");
        if (titleModel.getItemName().equals("EShantou")) {
            stringBuilder.append("<base href=\"http://dahuawang.com/\" target=\"_self\">");
            stringBuilder.append("<style>.c2 .content{padding:0 !important;}</style>");
        }
        stringBuilder.append("</head>");
        stringBuilder.append("<body>");

        Pattern p_script = Pattern.compile(regxpForFontStartTag, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(data);
        data = m_script.replaceAll("");

        p_script = Pattern.compile(regxpForFontEndTag, Pattern.CASE_INSENSITIVE);
        m_script = p_script.matcher(data);
        data = m_script.replaceAll("");

        stringBuilder.append(data);
        stringBuilder.append("</body></html>");

        mWebView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.requestFocus();
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.setBackgroundColor(0);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl("javascript:window.local_obj.showSource('<head>'+"
                        + "document.getElementsByTagName('html')[0].innerHTML+'</head>');");

            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

        });
        mWebView.loadDataWithBaseURL(null, stringBuilder.toString(), "text/html", "UTF-8", null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back: {
                finish();
            }
            break;
        }
    }

    final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void showSource(String html) {
            System.out.println("====>html=" + html);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
    }
}
