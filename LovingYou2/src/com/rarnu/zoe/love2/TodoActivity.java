package com.rarnu.zoe.love2;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rarnu.zoe.love2.common.Consts;

public class TodoActivity extends Activity implements OnClickListener {

	TextView tvTodoToday, tvTodo, tvDesc;
	ImageView imgPhoto, imgDays;
	RelativeLayout layBackArea;
	ImageView imgShare;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		Global.initDatabase(this);
		Global.doUpdateTokenT(this);
		Consts.setTaskTexts(this);

		int index = getIntent().getIntExtra("index", -1);
		int day = Global.database.getDay();

		if (getIntent().getAction() != null) {
			String action = getIntent().getAction();
			if (action.equals(Consts.NOTIFY_ACTION)) {
				NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
				manager.cancel(Consts.NOTIFY_ID);
				index = day - 1;
			}
		}

		tvTodoToday = (TextView) findViewById(R.id.tvTodoToday);
		tvTodo = (TextView) findViewById(R.id.tvTodo);
		imgDays = (ImageView) findViewById(R.id.imgDays);
		tvDesc = (TextView) findViewById(R.id.tvDesc);
		imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
		imgShare = (ImageView) findViewById(R.id.imgShare);

		layBackArea = (RelativeLayout) findViewById(R.id.layBackArea);

		tvTodoToday.setText(String.format(getString(R.string.todo_today), index + 1));

		tvTodo.setText(Consts.taskTitle[index]);
		tvDesc.setText(Consts.taskText[index]);

		BitmapFactory.Options bop = new BitmapFactory.Options();
		bop.inSampleSize = 2;
		imgPhoto.setImageBitmap(BitmapFactory.decodeResource(getResources(),
				Consts.bpImgs[index], bop));

		layBackArea.setOnClickListener(this);
		imgDays.setOnClickListener(this);
		imgShare.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgDays:
			Intent inRecord = new Intent(this, RecordActivity.class);
			startActivity(inRecord);
			break;
		case R.id.layBackArea:
			finish();
			break;
		case R.id.imgShare:
			// TODO: share
			break;
		}

	}
}
