package com.LeagueWatch;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class WatchStreamer extends Fragment {

	private WebView mWebView;
    private boolean mIsWebViewAvailable;
    private String mUrl = null;

    
    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
        	mUrl = (args.getString("url"));
        }
        
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        
        try {
    	  PackageManager pm = getActivity().getPackageManager();
    	  ApplicationInfo ai = pm.getApplicationInfo("com.adobe.flashplayer", 0);
    	  if (ai == null)
    	        mWebView.setWebChromeClient(new WebChromeClient());
        } catch (NameNotFoundException e) {
	        mWebView.setWebChromeClient(new WebChromeClient());
        }
        
        mWebView.loadUrl(mUrl);
        mIsWebViewAvailable = true;
    }
    
    /**
     * Called to instantiate the view. Creates and returns the WebView.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        if (mWebView != null) {
            mWebView.destroy();
        }
        
        mWebView = (WebView) inflater.inflate(R.layout.videoplayer, container, false);
        
        return mWebView;
    }
    
    /**
     * Convenience method for loading a url. Will fail if {@link View} is not initialised (but won't throw an {@link Exception})
     * @param url
     */
    public void loadUrl(String url) {
    	if (mIsWebViewAvailable) getWebView().loadUrl(mUrl = url);
    	else Log.w("ImprovedWebViewFragment", "WebView cannot be found. Check the view and fragment have been loaded.");
    }

    /**
     * Called when the fragment is visible to the user and actively running. Resumes the WebView.
     */
    @Override
    public void onPause() {
        super.onPause();
        //mWebView.onPause();
    }

    /**
     * Called when the fragment is no longer resumed. Pauses the WebView.
     */
    @Override
    public void onResume() {
        //mWebView.onResume();
        super.onResume();
    }

    /**
     * Called when the WebView has been detached from the fragment.
     * The WebView is no longer available after this time.
     */
    @Override
    public void onDestroyView() {
        mIsWebViewAvailable = false;
        super.onDestroyView();
    }

    /**
     * Called when the fragment is no longer in use. Destroys the internal state of the WebView.
     */
    @Override
    public void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    /**
     * Gets the WebView.
     */
    public WebView getWebView() {
        return mIsWebViewAvailable ? mWebView : null;
    }
	
}