package aaa.xplo.ebalance;

import android.net.Uri;

public class MobileOperatorBD {

	private static String opdGP = "47001";
	private static String opdRobi = "47002";
	private static String opdBanglalink = "47003";
	private static String opdTeletalk = "47004";
	private static String opdCitycell = "47005";
	private static String opdAirtel = "47007";

	private static String balanceUssdGP = "*566" + Uri.encode("#");
	private static String balanceUssdRobi = "*222" + Uri.encode("#");
	private static String balanceUssdBanglalink = "*124" + Uri.encode("#");
	private static String balanceUssdTeletalk = "*152" + Uri.encode("#");
	private static String balanceUssdCitycell = "*8111" + Uri.encode("#");
	private static String balanceUssdAirtel = "*778" + Uri.encode("#");

	private static String ebGetUssdGP = "*1010*1" + Uri.encode("#");
	
	//sachroye and onnonna...
	private static String ebGetUssdRobi = "*8666" + Uri.encode("#") + "2"
			+ Uri.encode("#");
	private static String ebGetUssdBanglalink = "*874" + Uri.encode("#");
	private static String ebGetUssdTeletalk = null;
	private static String ebGetUssdCitycell = null;
	private static String ebGetUssdAirtel = null;

	private static String ebCheckUssdGP = "*566*28" + Uri.encode("#");
	private static String ebCheckUssdRobi = "*8666" + Uri.encode("#");
	private static String ebCheckUssdBanglalink = "*124" + Uri.encode("#");
	private static String ebCheckUssdTeletalk = null;
	private static String ebCheckUssdCitycell = null;
	private static String ebCheckUssdAirtel = null;

	MobileOperatorBD() {

	}

	public String getEbGettingUssd(String opDigit) {

		if (opDigit.equals(opdGP)) {

			return ebGetUssdGP;

		} else if (opDigit.equals(opdRobi)) {

			return ebGetUssdRobi;

		} else if (opDigit.equals(opdBanglalink)) {

			return ebGetUssdBanglalink;

		} else if (opDigit.equals(opdTeletalk)) {

			return ebGetUssdTeletalk;

		} else if (opDigit.equals(opdCitycell)) {

			return ebGetUssdCitycell;

		} else if (opDigit.equals(opdAirtel)) {

			return ebGetUssdAirtel;

		}

		return null;

	}

	public String getEbCheckingUssd(String opDigit) {

		if (opDigit.equals(opdGP)) {

			return ebCheckUssdGP;

		} else if (opDigit.equals(opdRobi)) {

			return ebCheckUssdRobi;

		} else if (opDigit.equals(opdBanglalink)) {

			return ebCheckUssdBanglalink;

		} else if (opDigit.equals(opdTeletalk)) {

			return ebCheckUssdTeletalk;

		} else if (opDigit.equals(opdCitycell)) {

			return ebCheckUssdCitycell;

		} else if (opDigit.equals(opdAirtel)) {

			return ebCheckUssdAirtel;

		}

		return null;

	}

	public String getBalanceCheckingUssd(String opDigit) {

		
		if (opDigit.equals(opdGP)) {

			return balanceUssdGP;

		} else if (opDigit.equals(opdRobi)) {

			return balanceUssdRobi;

		} else if (opDigit.equals(opdBanglalink)) {

			return balanceUssdBanglalink;

		} else if (opDigit.equals(opdTeletalk)) {

			return balanceUssdTeletalk;

		} else if (opDigit.equals(opdCitycell)) {

			return balanceUssdCitycell;

		} else if (opDigit.equals(opdAirtel)) {

			return balanceUssdAirtel;

		}

		return null;

	}

	public static String getEbUssdGP() {
		return ebGetUssdGP;
	}

	public static String getEbUssdRobi() {
		return ebGetUssdRobi;
	}

	public static String getEbUssdBan() {
		return ebGetUssdBanglalink;
	}

	public static String getEbUssdTeletalk() {
		return ebGetUssdTeletalk;
	}

	public static String getEbUssdCitycell() {
		return ebGetUssdCitycell;
	}

	public static String getEbUssdAirtel() {
		return ebGetUssdAirtel;
	}

	public static String getBalanceUssdGP() {
		return balanceUssdGP;
	}

	public static String getBalanceUssdRobi() {
		return balanceUssdRobi;
	}

	public static String getBalanceUssdBan() {
		return balanceUssdBanglalink;
	}

	public static String getBalanceUssdTeletalk() {
		return balanceUssdTeletalk;
	}

	public static String getBalanceUssdCitycell() {
		return balanceUssdCitycell;
	}

	public static String getBalanceUssdAirtel() {
		return balanceUssdAirtel;
	}

	public static String getOpdGP() {
		return opdGP;
	}

	public static String getOpdRobi() {
		return opdRobi;
	}

	public static String getOpdBanglalink() {
		return opdBanglalink;
	}

	public static String getOpdTeletalk() {
		return opdTeletalk;
	}

	public static String getOpdCitycell() {
		return opdCitycell;
	}

	public static String getOpdAirtel() {
		return opdAirtel;
	}

}
