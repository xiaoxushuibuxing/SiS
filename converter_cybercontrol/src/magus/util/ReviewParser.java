package magus.util;

import java.text.DecimalFormat;

public class ReviewParser
{

	public ReviewParser()
	{
	}

	public static String getLengthString(String str, int strLen, int alignment)
	{
		int len = str.length();
		if (len > strLen)
			return str;
		switch (alignment)
		{
		case 0: // '\0'
		{
			int i = strLen - len;
			if (i > 0)
			{
				StringBuffer stringbuf = new StringBuffer();
				for (int k = 0; k < i; k++)
					stringbuf.append(" ");

				stringbuf.append(str);
				str = stringbuf.toString();
				stringbuf = null;
			}
			break;
		}

		case 1: // '\001'
		{
			int i = strLen - len;
			if (i <= 0)
				break;
			StringBuffer stringbuf = new StringBuffer();
			stringbuf.append(str);
			for (int k = 0; k < i; k++)
				stringbuf.append(" ");

			str = stringbuf.toString();
			stringbuf = null;
			break;
		}

		case 2: // '\002'
		{
			int i = ((strLen - len) + 1) / alignment;
			if (i <= 0)
				break;
			StringBuffer stringbuf = new StringBuffer();
			for (int k = 0; k < i; k++)
				stringbuf.append(" ");

			stringbuf.append(str);
			str = stringbuf.toString();
			stringbuf = null;
			break;
		}

		default:
		{
			str = new String("ALIGN");
			break;
		}
		}
		return str;
	}

	public static String getDecimalString_old(double f, int strLen, int dotNum, int alignment)
	{
		int dn = dotNum;
		if (dn < 0)
			dn = 0;
		double f1 = 1.0D;
		switch (dn)
		{
		case 1: // '\001'
			f1 = 10D;
			break;

		case 2: // '\002'
			f1 = 100D;
			break;

		case 3: // '\003'
			f1 = 1000D;
			break;

		default:
			for (long j = 0L; j < (long)dn; j++)
				f1 *= 10D;

			break;

		case 0: // '\0'
			break;
		}
		long k;
		if (f >= 0.0D)
			k = (long)(f * f1 + 0.5D);
		else
			k = (long)(f * f1 - 0.5D);
		String s = String.valueOf(k);
		int len = s.length();
		String str;
		if (f >= 0.0D)
		{
			if (len > dn)
			{
				str = s.substring(0, len - dn) + "." + s.substring(len - dn);
			} else
			{
				StringBuffer stringbuf = new StringBuffer();
				for (k = 0L; k < (long)(dn - len); k++)
					stringbuf.append("0");

				stringbuf.append(s);
				str = "0." + stringbuf.toString();
				stringbuf = null;
			}
		} else
		if (len > dn + 1)
		{
			str = s.substring(0, len - dn) + "." + s.substring(len - dn);
		} else
		{
			s = s.substring(1);
			StringBuffer stringbuf = new StringBuffer();
			for (k = 0L; k < (long)((dn - len) + 1); k++)
				stringbuf.append("0");

			stringbuf.append(s);
			str = "-0." + stringbuf.toString();
			stringbuf = null;
		}
		return getLengthString(str, strLen, alignment);
	}

	public static String getDecimalString(double f, int strLen, int dotNum, int alignment)
	{
		String pattern;
		switch (dotNum)
		{
		case 0: // '\0'
			pattern = "#0";
			break;

		case 1: // '\001'
			pattern = "#0.0";
			break;

		case 2: // '\002'
			pattern = "#0.00";
			break;

		case 3: // '\003'
			pattern = "#0.000";
			break;

		case 4: // '\004'
			pattern = "#0.0000";
			break;

		case 5: // '\005'
			pattern = "#0.00000";
			break;

		default:
			pattern = "#0.00";
			break;
		}
		DecimalFormat form = new DecimalFormat(pattern);
		String str = form.format(f);
		return getLengthString(str, strLen, alignment);
	}
}
