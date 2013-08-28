package com.isuru.ebill;

public class Calculator {

	double[] pGroups, fixed, txRate;
	double fxAmt;

	public Calculator() {
		this.fixed = new double[] { 30, 60, 90, 315 };
		pGroups = new double[] { 3, 4.7, 7.5, 21, 24, 24, 36 };
		this.txRate = new double[] { 25, 35, 40 };
	}

	public Calculator(double tR1, double tR2, double tR3, double fx1,
			double fx2, double fx3, double fx4, double e0, double e1,
			double e2, double e3, double e4, double e5) {
		fixed = new double[] { fx1, fx2, fx3, fx4 };
		pGroups = new double[] { e0, e1, e2, e3, e4, e4, e5 };
		this.txRate = new double[] { tR1, tR2, tR3 };
	}

	public double calConsFee(int units, int tPeriod) {

		int i, rUnits, noGroups;
		double intAmt, outAmt, woTax, wiTax, finalPay;

		if (units <= 0 || tPeriod <= 0) {
			return 0;
		}

		intAmt = outAmt = woTax = wiTax = finalPay = i = 0;
		rUnits = units % tPeriod;
		noGroups = units / tPeriod;

		if (noGroups > 6) {
			noGroups = 6;
			rUnits = units - (6 * tPeriod);
		}

		if (noGroups > 0) {
			while (i < (noGroups)) {
				intAmt += tPeriod * pGroups[i];
				i++;
			}
		}

		outAmt = rUnits * pGroups[i];
		woTax = intAmt + outAmt;

		switch (noGroups) {
		case 0:
			wiTax = ((100 + txRate[0]) * woTax) / 100;
			fxAmt = fixed[0];
			break;
		case 1:
			wiTax = ((100 + txRate[1]) * woTax) / 100;
			fxAmt = fixed[1];

			break;
		case 2:
			wiTax = ((100 + txRate[2]) * woTax) / 100;
			fxAmt = fixed[2];

			break;
		default:
			wiTax = ((100 + txRate[2]) * woTax) / 100;
			fxAmt = fixed[3];
			break;
		}

		finalPay = wiTax + fxAmt;

		if (finalPay > 0) {
			return finalPay;
		} else {
			return 0;
		}
	}
}
