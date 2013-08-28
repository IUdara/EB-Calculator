package com.isuru.ebill;

import java.text.DecimalFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class EBCalculatorActivity extends Activity implements OnClickListener {

	/** Called when the activity is first created. */

	EditText units;
	Button calc;
	TextView result;
	DatePicker lastD, currentD;
	Calculator ebilCal;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		intVars();
		calc.setOnClickListener(this);

		if (savedInstanceState != null) {
			lastD.updateDate(savedInstanceState.getInt("lYear"),
					savedInstanceState.getInt("lMonth"),
					savedInstanceState.getInt("lDate"));
			currentD.updateDate(savedInstanceState.getInt("cYear"),
					savedInstanceState.getInt("cMonth"),
					savedInstanceState.getInt("cDate"));
		}

	}

	private void intVars() {
		// TODO Auto-generated method stub
		units = (EditText) findViewById(R.id.etUnits);
		calc = (Button) findViewById(R.id.bCalculate);
		result = (TextView) findViewById(R.id.tvBill);
		lastD = (DatePicker) findViewById(R.id.dpLast);
		currentD = (DatePicker) findViewById(R.id.dpCurrent);
		ebilCal = new Calculator(25, 35, 40, 30, 60, 90, 315, 3, 4.7, 7.5, 21,
				24, 36);
	}

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater popUp = getMenuInflater();
		popUp.inflate(R.menu.popup, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.iAbout:
			Intent ablnch = new Intent("com.ibsolutions.isuru.ABOUT");
			startActivity(ablnch);
			break;
		case R.id.iExit:
			finish();
			break;
		default:
			break;
		}
		return true;
	}

	public int getPeriod() {
		Date last = new Date(lastD.getYear(), lastD.getMonth(),
				lastD.getDayOfMonth());
		Date current = new Date(currentD.getYear(), currentD.getMonth(),
				currentD.getDayOfMonth());
		int tPeriod = Days.daysBetween(new DateTime(last),
				new DateTime(current)).getDays();
		return tPeriod;
	}

	public int getUnits() {
		int nUnits = Integer.parseInt(units.getText().toString());
		return nUnits;
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.bCalculate:
			Double billAmt = 0.0;
			billAmt = ebilCal.calConsFee(this.getUnits(), this.getPeriod());
			DecimalFormat twoDec = new DecimalFormat("#.00");
			if (billAmt == 0) {
				result.setText("N/A");
			} else {
				result.setText(twoDec.format(billAmt));
			}
			break;

		default:
			break;
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putInt("lDate", lastD.getDayOfMonth());
		outState.putInt("lMonth", lastD.getMonth());
		outState.putInt("lYear", lastD.getYear());
		outState.putInt("cDate", currentD.getDayOfMonth());
		outState.putInt("cMonth", currentD.getMonth());
		outState.putInt("cYear", currentD.getYear());
	}

}