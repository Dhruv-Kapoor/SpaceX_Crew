package com.example.spacexcrew.util;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.spacexcrew.MainActivityViewModel;
import com.example.spacexcrew.R;
import com.example.spacexcrew.adapters.CrewAdapter;
import com.example.spacexcrew.dataClasses.Crew;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BindingUtils {

    @BindingAdapter("image")
    public static void setImage(ImageView iv, String url) {
        Picasso.get().load(url).into(iv);
    }

    @BindingAdapter("setStatus")
    public static void setStatus(TextView tv, String status) {
        if (status.equalsIgnoreCase("Active")) {
            tv.setTextColor(tv.getContext().getColor(R.color.green));
        } else {
            tv.setTextColor(tv.getContext().getColor(R.color.grey));
        }
        tv.setText(status);
    }

    @BindingAdapter("setList")
    public static void setList(RecyclerView rv, List<Crew> list) {
        RecyclerView.Adapter adapter;
        if (rv.getAdapter() == null) {
            adapter = new CrewAdapter();
            rv.setAdapter(adapter);
        } else {
            adapter = rv.getAdapter();
        }
        if (list == null) {
            return;
        }
        if (adapter instanceof CrewAdapter) {
            ((CrewAdapter) adapter).setList(list);
        }
    }

    @BindingAdapter("attachSnapHelper")
    public static void attachSnapHelper(RecyclerView rv, Boolean ignored) {
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(rv);
    }

    @BindingAdapter("isRefreshing")
    public static void isRefreshing(SwipeRefreshLayout srl, Boolean isRefreshing) {
        if (isRefreshing == null) return;
        srl.setRefreshing(isRefreshing);
    }

    @BindingAdapter("onRefresh")
    public static void onRefresh(SwipeRefreshLayout srl, MainActivityViewModel viewModel) {
        srl.setOnRefreshListener(viewModel::refresh);
    }

    @BindingAdapter("offset")
    public static void setOffset(SwipeRefreshLayout srl, int offset) {
        srl.setProgressViewOffset(false, offset, offset + srl.getProgressCircleDiameter());
    }

    @BindingAdapter("loadUrl")
    public static void loadUrl(WebView webView, String url) {
        webView.loadUrl(url);
    }

    @BindingAdapter("setClient")
    public static void setClient(WebView webView, WebViewClient client) {
        webView.setWebViewClient(client);
    }

}
