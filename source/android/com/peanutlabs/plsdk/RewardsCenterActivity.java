package com.peanutlabs.plsdk;

import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Display;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RewardsCenterActivity extends Activity implements OnClickListener {
	private String _baseUrl;
	private Button _doneButton;
	private Button _rewardsCenterButton;
	private ImageButton _backButton;
	private ImageButton _forwardButton;
	private RelativeLayout _progressIndicatorView;
	private FrameLayout _webViewPlaceholder;
	private WebView _webView;
	private final int PORTRAIT_WIDTH_THRESHOLD = 760;
	public static IToolbarEventsHandler TOOLBAR_EVENTS_HANDLER;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_rewards_center);
		_baseUrl = PeanutLabsManager.getInstance().getRewardCenterUrl();
        setOrientationPermission();
        initUI();
	}
	@SuppressLint("SetJavaScriptEnabled") protected void initUI() {
		_progressIndicatorView = (RelativeLayout)findViewById(R.id.pl_ProgressView);		
		_doneButton = (Button)findViewById(R.id.pl_txtbtnCancel);
		_backButton = (ImageButton)findViewById(R.id.pl_btnBack);
		_forwardButton = (ImageButton)findViewById(R.id.pl_btnForward);
		_rewardsCenterButton = (Button)findViewById(R.id.pl_btnAccept);
		_doneButton.setOnClickListener(this);
		_backButton.setOnClickListener(this);
		_forwardButton.setOnClickListener(this);
		_rewardsCenterButton.setOnClickListener(this);
		_webViewPlaceholder = ((FrameLayout)findViewById(R.id.webViewPlaceholder));	 
	    if (_webView == null) {
	    	_webView = new WebView(this);
	    	_webView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	    	_webView.getSettings().setJavaScriptEnabled(true);	      
	    	_webView.setWebChromeClient(new RewardsCenterWebChromeClient());
	    	_webView.setWebViewClient(new RewardsCenterWebViewClient());
	    	_webView.loadUrl(_baseUrl);
	    }
	    else {
	    	if(_webView.getProgress() >= 100) {
	    		_progressIndicatorView.setVisibility(View.GONE);
	    	}
	    	setNavigationButtonsState();
	    }
	    _webViewPlaceholder.addView(_webView);
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		if (_webView != null) {
			_webViewPlaceholder.removeView(_webView);
		}
		super.onConfigurationChanged(newConfig);
		setContentView(R.layout.activity_rewards_center);
		initUI();
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		_webView.saveState(outState);
	}
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		_webView.restoreState(savedInstanceState);
	}
	private void setOrientationPermission() {
		WindowManager window_manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		Display display = window_manager.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		int width = metrics.widthPixels;
		if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			width = metrics.heightPixels;
		}
		if(width < PORTRAIT_WIDTH_THRESHOLD) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
	}
    public void setViewEventsHandler(IToolbarEventsHandler handler) {
    	RewardsCenterActivity.TOOLBAR_EVENTS_HANDLER = handler;
    }
    @Override
    protected void onResume() {
    	super.onResume();
    }
	@Override
	public void onClick(View v) {	
		if(v.getId() == _backButton.getId()) {
			_webView.goBack();
		}
		else if(v.getId() == _forwardButton.getId()) {
			_webView.goForward();
		}
		else if(v.getId() == _doneButton.getId()) {
			finish();
			if(RewardsCenterActivity.TOOLBAR_EVENTS_HANDLER != null) {
				RewardsCenterActivity.TOOLBAR_EVENTS_HANDLER.donePushed();
			}
			return;
		}
		else if(v.getId() == _rewardsCenterButton.getId()) {
			if (_baseUrl != null) { 
				_webView.loadUrl(_baseUrl);
			}
		}
	}
	@Override
	public void onBackPressed() {
	}	
	private void setNavigationButtonsState() {
		WebView view = RewardsCenterActivity.this._webView;
		URL url = null;
		try {
			url = new URL(view.getUrl());
		} 
		catch (MalformedURLException e) {
		}
		String path = url.getPath();
		if(url.getHost().equals("www.peanutlabs.com") || url.getHost().equals("peanutlabs.com")) { 
			if(path.equals("/userGreeting.php")) {
				RewardsCenterActivity.this._doneButton.setVisibility(View.VISIBLE); 
				RewardsCenterActivity.this._backButton.setVisibility(View.GONE);
				RewardsCenterActivity.this._forwardButton.setVisibility(View.GONE);
				RewardsCenterActivity.this._rewardsCenterButton.setVisibility(View.GONE);				
			}
			else {
				RewardsCenterActivity.this._doneButton.setVisibility(View.GONE); 
				RewardsCenterActivity.this._backButton.setVisibility(View.GONE);
				RewardsCenterActivity.this._forwardButton.setVisibility(View.GONE);
				RewardsCenterActivity.this._rewardsCenterButton.setVisibility(View.VISIBLE);				
			}
					
		}
		else {
			RewardsCenterActivity.this._backButton.setVisibility(View.VISIBLE);
			RewardsCenterActivity.this._forwardButton.setVisibility(View.VISIBLE);
			RewardsCenterActivity.this._rewardsCenterButton.setVisibility(View.VISIBLE);
			RewardsCenterActivity.this._doneButton.setVisibility(View.GONE); 
		}	
		if(view.canGoBack()) {
			RewardsCenterActivity.this._backButton.setAlpha(1.0f);
		}
		else {
			RewardsCenterActivity.this._backButton.setAlpha(0.25f);
		}
		if(view.canGoForward()) {
			RewardsCenterActivity.this._forwardButton.setAlpha(1.0f);
		}
		else {
			RewardsCenterActivity.this._forwardButton.setAlpha(0.25f);
		}
		RewardsCenterActivity.this._backButton.setEnabled(view.canGoBack());
		RewardsCenterActivity.this._forwardButton.setEnabled(view.canGoForward());		
	}

	class RewardsCenterWebChromeClient extends WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			super.onProgressChanged(view, newProgress);
			if(newProgress >= 100 && view.getUrl() != null ) {				
				RewardsCenterActivity.this._progressIndicatorView.setVisibility(View.GONE);	
				setNavigationButtonsState();
			}
		}
	}	
	class RewardsCenterWebViewClient extends WebViewClient {
		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			if(errorCode == -2) {
				Toast.makeText(RewardsCenterActivity.this.getApplicationContext(),
					"The internet connection has been lost.", Toast.LENGTH_LONG).show();
			}
		}
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			RewardsCenterActivity.this._progressIndicatorView.setVisibility(View.VISIBLE);
		}
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return false;
		}
	}
}
