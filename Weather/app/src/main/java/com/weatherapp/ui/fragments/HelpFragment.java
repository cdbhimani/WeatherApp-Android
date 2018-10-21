

package com.weatherapp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.weatherapp.R;

public class HelpFragment extends Fragment {

    public static final String TAG = "Help Fragments";
    WebView webview;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.help_fragment,
                container, false);
        webview = view.findViewById(R.id.webview);
        String customHtml = "<html><body><h1>Hello, AbhiAndroid</h1>" +
                "<h1>Heading 1</h1><h2>Heading 2</h2><h3>Heading 3</h3>" +
                "<p>This is a sample paragraph of static HTML In Web view</p>" +
                "</body></html>";
// load static html data on a web view
        webview.loadData(customHtml, "text/html", "UTF-8");

        return view;
    }

}
