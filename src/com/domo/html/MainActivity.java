package com.domo.html;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.html.R;

public class MainActivity extends Activity {

	private TextView mHtmlText;
	private EditText mInputEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_main);
		 mHtmlText = (TextView) findViewById(R.id.text);
		 mInputEditText = (EditText) findViewById(R.id.edit);
		 
		 mInputEditText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
             
             @Override
             public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                     return false;
             }
             
             @Override
             public void onDestroyActionMode(ActionMode mode) {
                     
             }
             
             @Override
             public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                     return false;
             }
             
             @Override
             public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                     return false;
             }
     });
		 initTextView();
	}
	
	/**
	 * TextView
	 */
	private void initTextView(){
		String content = "我看<a href=\"http://topic_this/\">点一点</a>哈哈<a href=\"woqu\">提示</a>";// 了解一下href属性，不过这里属性值可以随便写

		mHtmlText.setText(Html.fromHtml(content));
		mHtmlText.setMovementMethod(LinkMovementMethod.getInstance());// 必须移除方法
		mHtmlText.setClickable(false);// 设置不可点击
		CharSequence text = mHtmlText.getText();
		if (text instanceof Spannable) {
			int end = text.length();
			Spannable sp = (Spannable) mHtmlText.getText();
			URLSpan[] urls = sp.getSpans(0, end, URLSpan.class);
			SpannableStringBuilder style = new SpannableStringBuilder(text);
			style.clearSpans();// should clear old spans
			for (URLSpan url : urls) {
				MyURLSpan myURLSpan = new MyURLSpan(url.getURL());
				style.setSpan(myURLSpan, sp.getSpanStart(url),
						sp.getSpanEnd(url), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			}
			mHtmlText.setText(style);
		}
	}

	/**
	 * 处理点击或者更改字体颜色等
	 * @author dingtao
	 *
	 */
	private class MyURLSpan extends ClickableSpan {
		private String mUrl;

		MyURLSpan(String url) {
			mUrl = url;
		}

		@Override
		public void onClick(View v) {
			mUrl = mUrl.toLowerCase();
			if ("http://topic_this/".equals(mUrl)) {// 响应你的点击事件
				AlertDialog.Builder builder = new Builder(MainActivity.this);
				builder.setMessage("我的弹出框");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
				builder.create().show();
			} else if ("woqu".equals(mUrl)) {
				Toast.makeText(MainActivity.this, "提示", Toast.LENGTH_SHORT)
						.show();
			}
		}
		
		@Override
		public void updateDrawState(TextPaint ds) {
			ds.setColor(android.graphics.Color.WHITE);
			ds.setUnderlineText(true);
		}
	}
}
