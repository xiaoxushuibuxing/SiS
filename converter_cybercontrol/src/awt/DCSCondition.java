package awt;

import magus.net.provide.OPDataProvider;

import java.io.PrintStream;
import java.util.Vector;

public class DCSCondition
		implements ICondition
{
	static int MAX_CONDITION = 50;
	int numCond = 0;
	int[] operator;
	ICondition[] conds;

	public int[] getOperator()
	{
		return this.operator;
	}

	public ICondition[] getConds()
	{
		return this.conds;
	}

	public DCSCondition()
	{
	}

	public DCSCondition(String paramString)
	{
		parse(paramString);
	}

	public int getConditionNumber()
	{
		return this.numCond;
	}

	public Vector getProcessPoints()
	{
		Vector localVector1 = new Vector();
		for (int i = 0; i < this.numCond; i++)
		{
			Vector localVector2 = this.conds[i].getProcessPoints();
			if (localVector2 != null)
				for (int j = 0; j < localVector2.size(); j++)
				{
					String str = (String)localVector2.elementAt(j);
					if (!localVector1.contains(str))
						localVector1.add(str);
				}
		}
		return localVector1;
	}

	public String toString()
	{
		String str = "";
		if (this.numCond > 1)
			str = str + "(";
		for (int i = 0; i < this.numCond; i++)
		{
			str = str + this.conds[i].toString();
			if (i < this.numCond - 1)
				str = str + " " + getOperator(this.operator[i]) + " ";
		}
		if (this.numCond > 1)
			str = str + ")";
		return str;
	}

	@Override
	public int getCondition(OPDataProvider opdataprovider) {
		return 0;
	}

	public boolean parse(String paramString)
	{ try {
		paramString = paramString.trim().toUpperCase();
		int i = paramString.length();
		if (i == 0)
			return false;
		int j = 0;
		int k = 0;
		int m = 0;
		int n = 0;
		int i1 = 0;
		int i2 = 0;
		int[] arrayOfInt = new int[MAX_CONDITION];
		ICondition[] arrayOfICondition = new ICondition[MAX_CONDITION];
		while (j < i) {
			if (paramString.charAt(j) == '(') {
				m++;
			} else if (paramString.charAt(j) == ')') {
				n++;
				if (m == n) {
					String str;
					if (k > 0) {
						str = paramString.substring(i1, i2).trim();
						arrayOfInt[(k - 1)] = getOperatorType(str);
						if (arrayOfInt[(k - 1)] < 0) {
							errorHandle(paramString, 2);
							return false;
						}
					}
					if (m > 1) {
						str = paramString.substring(i2, j + 1).trim();
						str = str.substring(1, str.length() - 1);
						arrayOfICondition[k] = new DCSCondition(str);
					} else {
						str = paramString.substring(i2, j + 1).trim();
						arrayOfICondition[k] = new SimpleCondition(str);
					}
					k++;
					m = n = 0;
					j++;
					i1 = j;
					while ((j < i) && (paramString.charAt(j) != '('))
						j++;
					i2 = j;
					continue;
				}
			}
			j++;
		}
		if (i1 < i - 1) {
			errorHandle(paramString, 6);
			return false;
		}
		//System.out.println("Num cond: " + k);
		this.numCond = k;
		this.conds = new ICondition[this.numCond];
		this.operator = new int[this.numCond];
		for (j = 0; j < this.numCond; j++) {
			this.conds[j] = arrayOfICondition[j];
			this.operator[j] = arrayOfInt[j];
		}
		return true;
	}catch (Exception e){
		return false;
	}
	}

	private int getOperatorType(String paramString)
	{
		int i = -1;
		if (paramString.equalsIgnoreCase("AND"))
			i = 0;
		else if (paramString.equalsIgnoreCase("OR"))
			i = 1;
		else if (paramString.equalsIgnoreCase("XOR"))
			i = 2;
		return i;
	}

	String getOperator(int paramInt)
	{
		String str = "";
		switch (paramInt)
		{
			case 0:
				str = "AND";
				break;
			case 1:
				str = "OR";
				break;
			case 2:
				str = "XOR";
				break;
		}
		return str;
	}

	private void errorHandle(String paramString, int paramInt)
	{
		this.numCond = -1;
		System.out.println("Error: " + paramInt + ". Please check the condition string!");
		System.out.println("Conition: " + paramString + "]");
	}

	private float getValueFloat(String paramString1, String paramString2)
	{
		float f = (float)(100.0D * Math.random());
		return f;
	}

	private int getValueInt(String paramString1, String paramString2)
	{
		int i = (int)(100.0D * Math.random());
		return i;
	}

	public int getCondition()
	{
		if (this.numCond <= 0)
			return 0;
		int[] arrayOfInt = new int[this.numCond];
		for (int i = 0; i < this.numCond; i++)
			arrayOfInt[i] = this.conds[i].getCondition();
		int i = arrayOfInt[0];
		for (int j = 1; j < this.numCond; j++)
			switch (this.operator[(j - 1)])
			{
				case 0:
					if (arrayOfInt[j] == 0)
						i = 0;
					break;
				case 1:
					if (arrayOfInt[j] == 1)
					{
						i = 1;
						return i;
					}
					break;
				case 2:
					if (i + arrayOfInt[j] == 1)
						i = 1;
					else
						i = 0;
					return i;
			}
		return i;
	}

	public static void main(String[] paramArrayOfString)
	{
		String str = new String("DI$MEP2B1COM_18$iVal==0");
		DCSCondition localDCSCondition = new DCSCondition();
		localDCSCondition.parse(str);
		System.out.println("**********************");
		System.out.println("CONDITION: " + localDCSCondition.toString());
		System.out.println("**********************");
		int i = localDCSCondition.getCondition();
		System.out.println("Condition is: " + i);
	}
}