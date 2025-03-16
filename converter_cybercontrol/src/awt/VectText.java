// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   VectText.java

package awt;

import java.awt.Graphics;

public class VectText
{

	byte xCoords[][][];
	byte yCoords[][][];

	public VectText()
	{
		xCoords = new byte[256][][];
		yCoords = new byte[256][][];
	}

	public synchronized void draw(Graphics g, String s, int sx, int sy, int bcw, int bch, int direct)
	{
		int strLen = s.length();
		int charWidth = 80;
		int charHeight = 120;
		int k3 = sx;
		int l3 = sy;
		for (int i4 = 0; i4 < strLen; i4++)
		{
			char c = s.charAt(i4);
			if (c > '\377')
				c = '?';
			if (xCoords[c] == null)
				initializeChar(c);
			if (xCoords[c] != null)
			{
				byte abyte0[][] = xCoords[c];
				byte abyte1[][] = yCoords[c];
				int charLen = abyte0.length;
				for (int k4 = 0; k4 < charLen; k4++)
				{
					byte abyte2[] = abyte0[k4];
					byte abyte3[] = abyte1[k4];
					int pointNum = abyte2.length;
					int ai[] = new int[pointNum];
					int ai1[] = new int[pointNum];
					for (int i5 = 0; i5 < pointNum; i5++)
					{
						ai[i5] = k3 + (abyte2[i5] * bcw) / charWidth;
						ai1[i5] = l3 + ((120 - abyte3[i5]) * bch) / charHeight;
					}

					g.drawPolyline(ai, ai1, pointNum);
				}

			}
			if (direct == 0)
				k3 += bcw;
			else
				l3 += bch;
		}

	}

	public void initializeChar(int i)
	{
		if (i < 64)
			switch (i)
			{
			case 33: // '!'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte0[] = {
					25, 25, 25
				};
				byte abyte189[] = {
					110, 50, 110
				};
				xCoords[i][0] = abyte0;
				yCoords[i][0] = abyte189;
				byte abyte378[] = {
					25, 25, 25
				};
				byte abyte512[] = {
					40, 30, 40
				};
				xCoords[i][1] = abyte378;
				yCoords[i][1] = abyte512;
				return;

			case 34: // '"'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte1[] = {
					14, 14, 14
				};
				byte abyte190[] = {
					110, 90, 110
				};
				xCoords[i][0] = abyte1;
				yCoords[i][0] = abyte190;
				byte abyte379[] = {
					34, 34, 34
				};
				byte abyte513[] = {
					110, 90, 110
				};
				xCoords[i][1] = abyte379;
				yCoords[i][1] = abyte513;
				return;

			case 35: // '#'
				xCoords[i] = new byte[4][];
				yCoords[i] = new byte[4][];
				byte abyte2[] = {
					20, 10, 20
				};
				byte abyte191[] = {
					100, 40, 100
				};
				xCoords[i][0] = abyte2;
				yCoords[i][0] = abyte191;
				byte abyte380[] = {
					40, 30, 40
				};
				byte abyte514[] = {
					100, 40, 100
				};
				xCoords[i][1] = abyte380;
				yCoords[i][1] = abyte514;
				byte abyte646[] = {
					50, 10, 50
				};
				byte abyte699[] = {
					80, 80, 80
				};
				xCoords[i][2] = abyte646;
				yCoords[i][2] = abyte699;
				byte abyte752[] = {
					40, 1, 40
				};
				byte abyte768[] = {
					60, 60, 60
				};
				xCoords[i][3] = abyte752;
				yCoords[i][3] = abyte768;
				return;

			case 36: // '$'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte3[] = {
					50, 40, 10, 1, 1, 10, 40, 50, 50, 40, 
					10, 1, 10
				};
				byte abyte192[] = {
					100, 110, 110, 100, 80, 70, 70, 60, 40, 30, 
					30, 40, 30
				};
				xCoords[i][0] = abyte3;
				yCoords[i][0] = abyte192;
				byte abyte381[] = {
					25, 25, 25
				};
				byte abyte515[] = {
					116, 24, 116
				};
				xCoords[i][1] = abyte381;
				yCoords[i][1] = abyte515;
				return;

			case 37: // '%'
				xCoords[i] = new byte[3][];
				yCoords[i] = new byte[3][];
				byte abyte4[] = {
					1, 1, 10, 20, 30, 30, 20, 10, 1, 10
				};
				byte abyte193[] = {
					100, 90, 80, 80, 90, 100, 110, 110, 100, 110
				};
				xCoords[i][0] = abyte4;
				yCoords[i][0] = abyte193;
				byte abyte382[] = {
					21, 21, 30, 40, 50, 50, 40, 30, 21, 30
				};
				byte abyte516[] = {
					50, 40, 30, 30, 40, 50, 60, 60, 50, 60
				};
				xCoords[i][1] = abyte382;
				yCoords[i][1] = abyte516;
				byte abyte647[] = {
					50, 1, 50
				};
				byte abyte700[] = {
					110, 30, 110
				};
				xCoords[i][2] = abyte647;
				yCoords[i][2] = abyte700;
				return;

			case 38: // '&'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte5[] = {
					50, 41, 10, 10, 15, 25, 30, 30, 25, 1, 
					1, 10, 35, 50, 35
				};
				byte abyte194[] = {
					30, 40, 90, 100, 110, 110, 100, 90, 80, 60, 
					40, 30, 30, 50, 30
				};
				xCoords[i][0] = abyte5;
				yCoords[i][0] = abyte194;
				return;

			case 39: // '\''
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte6[] = {
					25, 25, 15, 25
				};
				byte abyte195[] = {
					110, 95, 80, 95
				};
				xCoords[i][0] = abyte6;
				yCoords[i][0] = abyte195;
				return;

			case 40: // '('
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte7[] = {
					21, 1, 1, 21, 1
				};
				byte abyte196[] = {
					110, 90, 50, 30, 50
				};
				xCoords[i][0] = abyte7;
				yCoords[i][0] = abyte196;
				return;

			case 41: // ')'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte8[] = {
					1, 21, 21, 1, 21
				};
				byte abyte197[] = {
					110, 90, 50, 30, 50
				};
				xCoords[i][0] = abyte8;
				yCoords[i][0] = abyte197;
				return;

			case 42: // '*'
				xCoords[i] = new byte[3][];
				yCoords[i] = new byte[3][];
				byte abyte9[] = {
					21, 21, 21
				};
				byte abyte198[] = {
					100, 40, 100
				};
				xCoords[i][0] = abyte9;
				yCoords[i][0] = abyte198;
				byte abyte383[] = {
					1, 41, 1
				};
				byte abyte517[] = {
					90, 50, 90
				};
				xCoords[i][1] = abyte383;
				yCoords[i][1] = abyte517;
				byte abyte648[] = {
					41, 1, 41
				};
				byte abyte701[] = {
					90, 50, 90
				};
				xCoords[i][2] = abyte648;
				yCoords[i][2] = abyte701;
				return;

			case 43: // '+'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte10[] = {
					25, 25, 25
				};
				byte abyte199[] = {
					90, 40, 90
				};
				xCoords[i][0] = abyte10;
				yCoords[i][0] = abyte199;
				byte abyte384[] = {
					49, 1, 49
				};
				byte abyte518[] = {
					65, 65, 65
				};
				xCoords[i][1] = abyte384;
				yCoords[i][1] = abyte518;
				return;

			case 44: // ','
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte11[] = {
					26, 26, 23, 23, 23, 26, 16, 26
				};
				byte abyte200[] = {
					30, 35, 35, 30, 20, 30, 10, 30
				};
				xCoords[i][0] = abyte11;
				yCoords[i][0] = abyte200;
				return;

			case 45: // '-'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte12[] = {
					1, 41, 1
				};
				byte abyte201[] = {
					60, 60, 60
				};
				xCoords[i][0] = abyte12;
				yCoords[i][0] = abyte201;
				return;

			case 46: // '.'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte13[] = {
					23, 23, 27, 27, 23, 27
				};
				byte abyte202[] = {
					30, 35, 35, 30, 30, 30
				};
				xCoords[i][0] = abyte13;
				yCoords[i][0] = abyte202;
				return;

			case 47: // '/'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte14[] = {
					42, 2, 42
				};
				byte abyte203[] = {
					110, 30, 110
				};
				xCoords[i][0] = abyte14;
				yCoords[i][0] = abyte203;
				return;

			case 48: // '0'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte15[] = {
					11, 1, 1, 11, 31, 41, 41, 31, 11, 31
				};
				byte abyte204[] = {
					30, 40, 100, 110, 110, 100, 40, 30, 30, 30
				};
				xCoords[i][0] = abyte15;
				yCoords[i][0] = abyte204;
				return;

			case 49: // '1'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte16[] = {
					2, 17, 17, 17
				};
				byte abyte205[] = {
					100, 110, 30, 110
				};
				xCoords[i][0] = abyte16;
				yCoords[i][0] = abyte205;
				byte abyte385[] = {
					32, 2, 32
				};
				byte abyte519[] = {
					30, 30, 30
				};
				xCoords[i][1] = abyte385;
				yCoords[i][1] = abyte519;
				return;

			case 50: // '2'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte17[] = {
					6, 16, 36, 46, 46, 36, 1, 1, 46, 1
				};
				byte abyte206[] = {
					100, 110, 110, 100, 86, 75, 40, 30, 30, 30
				};
				xCoords[i][0] = abyte17;
				yCoords[i][0] = abyte206;
				return;

			case 51: // '3'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte18[] = {
					1, 11, 31, 41, 41, 31, 41, 41, 31, 11, 
					1, 11
				};
				byte abyte207[] = {
					100, 110, 110, 100, 80, 70, 60, 40, 30, 30, 
					40, 30
				};
				xCoords[i][0] = abyte18;
				yCoords[i][0] = abyte207;
				byte abyte386[] = {
					31, 11, 31
				};
				byte abyte520[] = {
					70, 70, 70
				};
				xCoords[i][1] = abyte386;
				yCoords[i][1] = abyte520;
				return;

			case 52: // '4'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte19[] = {
					40, 40, 4, 50, 4
				};
				byte abyte208[] = {
					30, 110, 60, 60, 60
				};
				xCoords[i][0] = abyte19;
				yCoords[i][0] = abyte208;
				return;

			case 53: // '5'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte20[] = {
					42, 2, 2, 32, 42, 42, 32, 12, 2, 12
				};
				byte abyte209[] = {
					109, 109, 75, 75, 65, 40, 30, 30, 40, 30
				};
				xCoords[i][0] = abyte20;
				yCoords[i][0] = abyte209;
				return;

			case 54: // '6'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte21[] = {
					50, 40, 10, 1, 1, 1, 10, 40, 50, 50, 
					40, 10, 1, 10
				};
				byte abyte210[] = {
					100, 110, 110, 100, 80, 40, 30, 30, 40, 60, 
					70, 70, 60, 70
				};
				xCoords[i][0] = abyte21;
				yCoords[i][0] = abyte210;
				return;

			case 55: // '7'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte22[] = {
					1, 50, 20, 50
				};
				byte abyte211[] = {
					110, 110, 30, 110
				};
				xCoords[i][0] = abyte22;
				yCoords[i][0] = abyte211;
				return;

			case 56: // '8'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte23[] = {
					1, 10, 40, 50, 50, 40, 50, 50, 40, 10, 
					1, 1, 10, 1, 1, 1
				};
				byte abyte212[] = {
					100, 110, 110, 100, 80, 70, 60, 40, 30, 30, 
					40, 60, 70, 80, 100, 80
				};
				xCoords[i][0] = abyte23;
				yCoords[i][0] = abyte212;
				byte abyte387[] = {
					10, 40, 10
				};
				byte abyte521[] = {
					70, 70, 70
				};
				xCoords[i][1] = abyte387;
				yCoords[i][1] = abyte521;
				return;

			case 57: // '9'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte24[] = {
					1, 11, 41, 50, 50, 50, 41, 11, 1, 1, 
					11, 41, 50, 41
				};
				byte abyte213[] = {
					40, 30, 30, 40, 60, 100, 110, 110, 100, 80, 
					70, 70, 80, 70
				};
				xCoords[i][0] = abyte24;
				yCoords[i][0] = abyte213;
				return;

			case 58: // ':'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte25[] = {
					23, 23, 27, 27, 23, 27
				};
				byte abyte214[] = {
					70, 75, 75, 70, 70, 70
				};
				xCoords[i][0] = abyte25;
				yCoords[i][0] = abyte214;
				byte abyte388[] = {
					23, 23, 27, 27, 23, 27
				};
				byte abyte522[] = {
					40, 45, 45, 40, 40, 40
				};
				xCoords[i][1] = abyte388;
				yCoords[i][1] = abyte522;
				return;

			case 59: // ';'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte26[] = {
					27, 27, 23, 23, 27, 23
				};
				byte abyte215[] = {
					30, 35, 35, 30, 30, 30
				};
				xCoords[i][0] = abyte26;
				yCoords[i][0] = abyte215;
				byte abyte389[] = {
					26, 23, 16, 23
				};
				byte abyte523[] = {
					30, 20, 10, 20
				};
				xCoords[i][1] = abyte389;
				yCoords[i][1] = abyte523;
				return;

			case 60: // '<'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte27[] = {
					50, 1, 50, 1
				};
				byte abyte216[] = {
					100, 65, 30, 65
				};
				xCoords[i][0] = abyte27;
				yCoords[i][0] = abyte216;
				return;

			case 61: // '='
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte28[] = {
					1, 41, 1
				};
				byte abyte217[] = {
					50, 50, 50
				};
				xCoords[i][0] = abyte28;
				yCoords[i][0] = abyte217;
				byte abyte390[] = {
					1, 41, 1
				};
				byte abyte524[] = {
					80, 80, 80
				};
				xCoords[i][1] = abyte390;
				yCoords[i][1] = abyte524;
				return;

			case 62: // '>'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte29[] = {
					1, 50, 1, 50
				};
				byte abyte218[] = {
					100, 65, 30, 65
				};
				xCoords[i][0] = abyte29;
				yCoords[i][0] = abyte218;
				return;

			case 63: // '?'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte30[] = {
					1, 11, 31, 41, 41, 21, 21, 21
				};
				byte abyte219[] = {
					100, 110, 110, 100, 80, 70, 48, 70
				};
				xCoords[i][0] = abyte30;
				yCoords[i][0] = abyte219;
				byte abyte391[] = {
					21, 21, 21
				};
				byte abyte525[] = {
					39, 30, 39
				};
				xCoords[i][1] = abyte391;
				yCoords[i][1] = abyte525;
				return;

			case 0: // '\0'
			case 1: // '\001'
			case 2: // '\002'
			case 3: // '\003'
			case 4: // '\004'
			case 5: // '\005'
			case 6: // '\006'
			case 7: // '\007'
			case 8: // '\b'
			case 9: // '\t'
			case 10: // '\n'
			case 11: // '\013'
			case 12: // '\f'
			case 13: // '\r'
			case 14: // '\016'
			case 15: // '\017'
			case 16: // '\020'
			case 17: // '\021'
			case 18: // '\022'
			case 19: // '\023'
			case 20: // '\024'
			case 21: // '\025'
			case 22: // '\026'
			case 23: // '\027'
			case 24: // '\030'
			case 25: // '\031'
			case 26: // '\032'
			case 27: // '\033'
			case 28: // '\034'
			case 29: // '\035'
			case 30: // '\036'
			case 31: // '\037'
			case 32: // ' '
			default:
				return;
			}
		if (i < 128)
			switch (i)
			{
			case 64: // '@'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte31[] = {
					50, 50, 40, 30, 20, 20, 30, 40, 30
				};
				byte abyte220[] = {
					60, 80, 90, 90, 80, 60, 50, 50, 50
				};
				xCoords[i][0] = abyte31;
				yCoords[i][0] = abyte220;
				byte abyte392[] = {
					50, 50, 40, 10, 1, 1, 10, 40, 50, 40
				};
				byte abyte526[] = {
					80, 90, 100, 100, 90, 40, 30, 30, 40, 30
				};
				xCoords[i][1] = abyte392;
				yCoords[i][1] = abyte526;
				return;

			case 65: // 'A'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte32[] = {
					1, 25, 50, 25
				};
				byte abyte221[] = {
					30, 109, 30, 109
				};
				xCoords[i][0] = abyte32;
				yCoords[i][0] = abyte221;
				byte abyte393[] = {
					11, 39, 11
				};
				byte abyte527[] = {
					64, 64, 64
				};
				xCoords[i][1] = abyte393;
				yCoords[i][1] = abyte527;
				return;

			case 66: // 'B'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte33[] = {
					1, 1, 40, 50, 50, 41, 50, 50, 40, 1, 
					40
				};
				byte abyte222[] = {
					30, 110, 110, 100, 80, 70, 60, 40, 30, 30, 
					30
				};
				xCoords[i][0] = abyte33;
				yCoords[i][0] = abyte222;
				byte abyte394[] = {
					41, 1, 41
				};
				byte abyte528[] = {
					70, 70, 70
				};
				xCoords[i][1] = abyte394;
				yCoords[i][1] = abyte528;
				return;

			case 67: // 'C'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte34[] = {
					50, 40, 10, 1, 1, 10, 40, 50, 40
				};
				byte abyte223[] = {
					40, 30, 30, 40, 100, 110, 110, 100, 110
				};
				xCoords[i][0] = abyte34;
				yCoords[i][0] = abyte223;
				return;

			case 68: // 'D'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte35[] = {
					1, 1, 40, 50, 50, 50, 50, 50, 40, 1, 
					40
				};
				byte abyte224[] = {
					30, 110, 110, 100, 80, 80, 60, 40, 30, 30, 
					30
				};
				xCoords[i][0] = abyte35;
				yCoords[i][0] = abyte224;
				return;

			case 69: // 'E'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte36[] = {
					50, 1, 1, 50, 1
				};
				byte abyte225[] = {
					30, 30, 110, 110, 110
				};
				xCoords[i][0] = abyte36;
				yCoords[i][0] = abyte225;
				byte abyte395[] = {
					40, 1, 40
				};
				byte abyte529[] = {
					70, 70, 70
				};
				xCoords[i][1] = abyte395;
				yCoords[i][1] = abyte529;
				return;

			case 70: // 'F'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte37[] = {
					50, 1, 1, 1
				};
				byte abyte226[] = {
					110, 110, 30, 110
				};
				xCoords[i][0] = abyte37;
				yCoords[i][0] = abyte226;
				byte abyte396[] = {
					40, 1, 40
				};
				byte abyte530[] = {
					70, 70, 70
				};
				xCoords[i][1] = abyte396;
				yCoords[i][1] = abyte530;
				return;

			case 71: // 'G'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte38[] = {
					50, 40, 10, 1, 1, 10, 50, 50, 30, 50
				};
				byte abyte227[] = {
					100, 110, 110, 100, 40, 30, 30, 60, 60, 60
				};
				xCoords[i][0] = abyte38;
				yCoords[i][0] = abyte227;
				return;

			case 72: // 'H'
				xCoords[i] = new byte[3][];
				yCoords[i] = new byte[3][];
				byte abyte39[] = {
					1, 1, 1
				};
				byte abyte228[] = {
					110, 30, 110
				};
				xCoords[i][0] = abyte39;
				yCoords[i][0] = abyte228;
				byte abyte397[] = {
					50, 50, 50
				};
				byte abyte531[] = {
					110, 30, 110
				};
				xCoords[i][1] = abyte397;
				yCoords[i][1] = abyte531;
				byte abyte649[] = {
					50, 1, 50
				};
				byte abyte702[] = {
					70, 70, 70
				};
				xCoords[i][2] = abyte649;
				yCoords[i][2] = abyte702;
				return;

			case 73: // 'I'
				xCoords[i] = new byte[3][];
				yCoords[i] = new byte[3][];
				byte abyte40[] = {
					21, 21, 21
				};
				byte abyte229[] = {
					110, 30, 110
				};
				xCoords[i][0] = abyte40;
				yCoords[i][0] = abyte229;
				byte abyte398[] = {
					1, 41, 1
				};
				byte abyte532[] = {
					30, 30, 30
				};
				xCoords[i][1] = abyte398;
				yCoords[i][1] = abyte532;
				byte abyte650[] = {
					1, 41, 1
				};
				byte abyte703[] = {
					110, 110, 110
				};
				xCoords[i][2] = abyte650;
				yCoords[i][2] = abyte703;
				return;

			case 74: // 'J'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte41[] = {
					50, 19, 50
				};
				byte abyte230[] = {
					110, 110, 110
				};
				xCoords[i][0] = abyte41;
				yCoords[i][0] = abyte230;
				byte abyte399[] = {
					40, 40, 30, 10, 0, 10
				};
				byte abyte533[] = {
					110, 40, 30, 30, 40, 30
				};
				xCoords[i][1] = abyte399;
				yCoords[i][1] = abyte533;
				return;

			case 75: // 'K'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte42[] = {
					1, 1, 1
				};
				byte abyte231[] = {
					110, 30, 110
				};
				xCoords[i][0] = abyte42;
				yCoords[i][0] = abyte231;
				byte abyte400[] = {
					40, 2, 40, 2
				};
				byte abyte534[] = {
					110, 70, 30, 70
				};
				xCoords[i][1] = abyte400;
				yCoords[i][1] = abyte534;
				return;

			case 76: // 'L'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte43[] = {
					1, 1, 50, 1
				};
				byte abyte232[] = {
					110, 30, 30, 30
				};
				xCoords[i][0] = abyte43;
				yCoords[i][0] = abyte232;
				return;

			case 77: // 'M'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte44[] = {
					1, 1, 25, 50, 50, 50
				};
				byte abyte233[] = {
					30, 109, 32, 109, 30, 109
				};
				xCoords[i][0] = abyte44;
				yCoords[i][0] = abyte233;
				return;

			case 78: // 'N'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte45[] = {
					1, 1, 50, 50, 50
				};
				byte abyte234[] = {
					30, 108, 32, 110, 32
				};
				xCoords[i][0] = abyte45;
				yCoords[i][0] = abyte234;
				return;

			case 79: // 'O'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte46[] = {
					1, 1, 10, 40, 50, 50, 40, 10, 1, 10
				};
				byte abyte235[] = {
					40, 100, 110, 110, 100, 40, 30, 30, 40, 30
				};
				xCoords[i][0] = abyte46;
				yCoords[i][0] = abyte235;
				return;

			case 80: // 'P'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte47[] = {
					1, 1, 40, 50, 50, 40, 1, 40
				};
				byte abyte236[] = {
					30, 110, 110, 100, 80, 70, 70, 70
				};
				xCoords[i][0] = abyte47;
				yCoords[i][0] = abyte236;
				return;

			case 81: // 'Q'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte48[] = {
					1, 1, 10, 40, 50, 50, 40, 10, 1, 10
				};
				byte abyte237[] = {
					40, 100, 110, 110, 100, 40, 30, 30, 40, 30
				};
				xCoords[i][0] = abyte48;
				yCoords[i][0] = abyte237;
				byte abyte401[] = {
					30, 35, 50, 35
				};
				byte abyte535[] = {
					30, 20, 11, 20
				};
				xCoords[i][1] = abyte401;
				yCoords[i][1] = abyte535;
				return;

			case 82: // 'R'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte49[] = {
					1, 1, 40, 50, 50, 40, 1, 40
				};
				byte abyte238[] = {
					30, 110, 110, 100, 80, 70, 70, 70
				};
				xCoords[i][0] = abyte49;
				yCoords[i][0] = abyte238;
				byte abyte402[] = {
					30, 50, 30
				};
				byte abyte536[] = {
					70, 30, 70
				};
				xCoords[i][1] = abyte402;
				yCoords[i][1] = abyte536;
				return;

			case 83: // 'S'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte50[] = {
					1, 10, 40, 50, 50, 40, 10, 1, 1, 10, 
					40, 50, 40
				};
				byte abyte239[] = {
					40, 30, 30, 40, 60, 70, 70, 80, 100, 110, 
					110, 100, 110
				};
				xCoords[i][0] = abyte50;
				yCoords[i][0] = abyte239;
				return;

			case 84: // 'T'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte51[] = {
					1, 50, 1
				};
				byte abyte240[] = {
					110, 110, 110
				};
				xCoords[i][0] = abyte51;
				yCoords[i][0] = abyte240;
				byte abyte403[] = {
					25, 25, 25
				};
				byte abyte537[] = {
					110, 30, 110
				};
				xCoords[i][1] = abyte403;
				yCoords[i][1] = abyte537;
				return;

			case 85: // 'U'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte52[] = {
					1, 1, 10, 40, 50, 50, 50
				};
				byte abyte241[] = {
					110, 40, 30, 30, 40, 110, 40
				};
				xCoords[i][0] = abyte52;
				yCoords[i][0] = abyte241;
				return;

			case 86: // 'V'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte53[] = {
					1, 25, 50, 25
				};
				byte abyte242[] = {
					110, 31, 110, 31
				};
				xCoords[i][0] = abyte53;
				yCoords[i][0] = abyte242;
				return;

			case 87: // 'W'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte54[] = {
					1, 15, 25, 35, 50, 35
				};
				byte abyte243[] = {
					110, 31, 100, 31, 110, 31
				};
				xCoords[i][0] = abyte54;
				yCoords[i][0] = abyte243;
				return;

			case 88: // 'X'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte55[] = {
					1, 50, 1
				};
				byte abyte244[] = {
					30, 110, 30
				};
				xCoords[i][0] = abyte55;
				yCoords[i][0] = abyte244;
				byte abyte404[] = {
					1, 50, 1
				};
				byte abyte538[] = {
					110, 30, 110
				};
				xCoords[i][1] = abyte404;
				yCoords[i][1] = abyte538;
				return;

			case 89: // 'Y'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte56[] = {
					1, 25, 49, 25
				};
				byte abyte245[] = {
					110, 70, 110, 70
				};
				xCoords[i][0] = abyte56;
				yCoords[i][0] = abyte245;
				byte abyte405[] = {
					25, 25, 25
				};
				byte abyte539[] = {
					70, 30, 70
				};
				xCoords[i][1] = abyte405;
				yCoords[i][1] = abyte539;
				return;

			case 90: // 'Z'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte57[] = {
					1, 49, 1, 49, 1
				};
				byte abyte246[] = {
					110, 110, 30, 30, 30
				};
				xCoords[i][0] = abyte57;
				yCoords[i][0] = abyte246;
				return;

			case 91: // '['
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte58[] = {
					21, 1, 1, 21, 1
				};
				byte abyte247[] = {
					110, 110, 30, 30, 30
				};
				xCoords[i][0] = abyte58;
				yCoords[i][0] = abyte247;
				return;

			case 92: // '\\'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte59[] = {
					1, 41, 1
				};
				byte abyte248[] = {
					110, 30, 110
				};
				xCoords[i][0] = abyte59;
				yCoords[i][0] = abyte248;
				return;

			case 93: // ']'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte60[] = {
					1, 21, 21, 1, 21
				};
				byte abyte249[] = {
					110, 110, 30, 30, 30
				};
				xCoords[i][0] = abyte60;
				yCoords[i][0] = abyte249;
				return;

			case 94: // '^'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte61[] = {
					1, 21, 41, 21
				};
				byte abyte250[] = {
					90, 110, 90, 110
				};
				xCoords[i][0] = abyte61;
				yCoords[i][0] = abyte250;
				return;

			case 95: // '_'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte62[] = {
					3, 50, 3
				};
				byte abyte251[] = {
					30, 30, 30
				};
				xCoords[i][0] = abyte62;
				yCoords[i][0] = abyte251;
				return;

			case 96: // '`'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte63[] = {
					10, 10, 20, 10
				};
				byte abyte252[] = {
					80, 95, 110, 95
				};
				xCoords[i][0] = abyte63;
				yCoords[i][0] = abyte252;
				byte abyte406[] = {
					13, 13, 9, 9, 13, 9
				};
				byte abyte540[] = {
					80, 85, 85, 80, 80, 80
				};
				xCoords[i][1] = abyte406;
				yCoords[i][1] = abyte540;
				return;

			case 97: // 'a'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte64[] = {
					30, 25, 6, 1, 1, 6, 30, 6
				};
				byte abyte253[] = {
					35, 30, 30, 35, 49, 54, 54, 54
				};
				xCoords[i][0] = abyte64;
				yCoords[i][0] = abyte253;
				byte abyte407[] = {
					35, 30, 30, 25, 6, 1, 6
				};
				byte abyte541[] = {
					30, 35, 65, 70, 70, 65, 70
				};
				xCoords[i][1] = abyte407;
				yCoords[i][1] = abyte541;
				return;

			case 98: // 'b'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte65[] = {
					2, 2, 2
				};
				byte abyte254[] = {
					100, 30, 100
				};
				xCoords[i][0] = abyte65;
				yCoords[i][0] = abyte254;
				byte abyte408[] = {
					2, 10, 29, 35, 35, 29, 10, 2, 10
				};
				byte abyte542[] = {
					65, 70, 70, 65, 35, 30, 30, 35, 30
				};
				xCoords[i][1] = abyte408;
				yCoords[i][1] = abyte542;
				return;

			case 99: // 'c'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte66[] = {
					30, 22, 9, 1, 1, 9, 22, 30, 22
				};
				byte abyte255[] = {
					65, 70, 70, 60, 40, 30, 30, 35, 30
				};
				xCoords[i][0] = abyte66;
				yCoords[i][0] = abyte255;
				return;

			case 100: // 'd'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte67[] = {
					30, 30, 30
				};
				byte abyte256[] = {
					30, 100, 30
				};
				xCoords[i][0] = abyte67;
				yCoords[i][0] = abyte256;
				byte abyte409[] = {
					30, 25, 6, 1, 1, 6, 25, 30, 25
				};
				byte abyte543[] = {
					65, 70, 70, 65, 35, 30, 30, 35, 30
				};
				xCoords[i][1] = abyte409;
				yCoords[i][1] = abyte543;
				return;

			case 101: // 'e'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte68[] = {
					1, 30, 30, 22, 9, 1, 1, 9, 22, 30, 
					22
				};
				byte abyte257[] = {
					52, 52, 64, 70, 70, 64, 35, 30, 30, 35, 
					30
				};
				xCoords[i][0] = abyte68;
				yCoords[i][0] = abyte257;
				return;

			case 102: // 'f'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte69[] = {
					11, 11, 15, 31, 15
				};
				byte abyte258[] = {
					30, 90, 95, 95, 95
				};
				xCoords[i][0] = abyte69;
				yCoords[i][0] = abyte258;
				byte abyte410[] = {
					1, 26, 1
				};
				byte abyte544[] = {
					70, 70, 70
				};
				xCoords[i][1] = abyte410;
				yCoords[i][1] = abyte544;
				return;

			case 103: // 'g'
				xCoords[i] = new byte[3][];
				yCoords[i] = new byte[3][];
				byte abyte70[] = {
					30, 25, 6, 1, 1, 6, 25, 30, 25
				};
				byte abyte259[] = {
					65, 70, 70, 65, 35, 30, 30, 35, 30
				};
				xCoords[i][0] = abyte70;
				yCoords[i][0] = abyte259;
				byte abyte411[] = {
					30, 30, 30, 30
				};
				byte abyte545[] = {
					15, 50, 65, 50
				};
				xCoords[i][1] = abyte411;
				yCoords[i][1] = abyte545;
				byte abyte651[] = {
					1, 6, 25, 30, 25
				};
				byte abyte704[] = {
					9, 5, 5, 15, 5
				};
				xCoords[i][2] = abyte651;
				yCoords[i][2] = abyte704;
				return;

			case 104: // 'h'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte71[] = {
					3, 3, 3
				};
				byte abyte260[] = {
					110, 30, 110
				};
				xCoords[i][0] = abyte71;
				yCoords[i][0] = abyte260;
				byte abyte412[] = {
					3, 10, 30, 35, 35, 35
				};
				byte abyte546[] = {
					65, 70, 70, 65, 30, 65
				};
				xCoords[i][1] = abyte412;
				yCoords[i][1] = abyte546;
				return;

			case 105: // 'i'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte72[] = {
					23, 23, 27, 27, 27
				};
				byte abyte261[] = {
					80, 85, 85, 80, 85
				};
				xCoords[i][0] = abyte72;
				yCoords[i][0] = abyte261;
				byte abyte413[] = {
					25, 25, 19, 25
				};
				byte abyte547[] = {
					30, 70, 70, 70
				};
				xCoords[i][1] = abyte413;
				yCoords[i][1] = abyte547;
				return;

			case 106: // 'j'
				xCoords[i] = new byte[3][];
				yCoords[i] = new byte[3][];
				byte abyte73[] = {
					23, 23, 27, 27, 27
				};
				byte abyte262[] = {
					80, 85, 85, 80, 85
				};
				xCoords[i][0] = abyte73;
				yCoords[i][0] = abyte262;
				byte abyte414[] = {
					25, 25, 19, 25
				};
				byte abyte548[] = {
					30, 70, 70, 70
				};
				xCoords[i][1] = abyte414;
				yCoords[i][1] = abyte548;
				byte abyte652[] = {
					19, 25, 25, 20, 15, 10, 15
				};
				byte abyte705[] = {
					70, 70, 20, 15, 15, 20, 15
				};
				xCoords[i][2] = abyte652;
				yCoords[i][2] = abyte705;
				return;

			case 107: // 'k'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte74[] = {
					2, 2, 2
				};
				byte abyte263[] = {
					100, 30, 100
				};
				xCoords[i][0] = abyte74;
				yCoords[i][0] = abyte263;
				byte abyte415[] = {
					20, 2, 20, 2
				};
				byte abyte549[] = {
					67, 50, 30, 50
				};
				xCoords[i][1] = abyte415;
				yCoords[i][1] = abyte549;
				return;

			case 108: // 'l'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte75[] = {
					1, 12, 12, 12
				};
				byte abyte264[] = {
					100, 100, 30, 100
				};
				xCoords[i][0] = abyte75;
				yCoords[i][0] = abyte264;
				return;

			case 109: // 'm'
				xCoords[i] = new byte[3][];
				yCoords[i] = new byte[3][];
				byte abyte76[] = {
					1, 1, 7, 12, 17, 17, 17
				};
				byte abyte265[] = {
					30, 61, 70, 70, 65, 30, 65
				};
				xCoords[i][0] = abyte76;
				yCoords[i][0] = abyte265;
				byte abyte416[] = {
					1, 1, 1
				};
				byte abyte550[] = {
					47, 70, 47
				};
				xCoords[i][1] = abyte416;
				yCoords[i][1] = abyte550;
				byte abyte653[] = {
					17, 17, 23, 28, 33, 33, 33
				};
				byte abyte706[] = {
					30, 61, 70, 70, 65, 30, 65
				};
				xCoords[i][2] = abyte653;
				yCoords[i][2] = abyte706;
				return;

			case 110: // 'n'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte77[] = {
					1, 1, 7, 23, 30, 30, 30
				};
				byte abyte266[] = {
					30, 60, 70, 70, 65, 30, 65
				};
				xCoords[i][0] = abyte77;
				yCoords[i][0] = abyte266;
				byte abyte417[] = {
					1, 1, 1
				};
				byte abyte551[] = {
					57, 70, 57
				};
				xCoords[i][1] = abyte417;
				yCoords[i][1] = abyte551;
				return;

			case 111: // 'o'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte78[] = {
					6, 1, 1, 6, 25, 30, 30, 25, 6, 25
				};
				byte abyte267[] = {
					30, 34, 65, 70, 70, 65, 34, 30, 30, 30
				};
				xCoords[i][0] = abyte78;
				yCoords[i][0] = abyte267;
				return;

			case 112: // 'p'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte79[] = {
					1, 1, 1
				};
				byte abyte268[] = {
					70, 2, 70
				};
				xCoords[i][0] = abyte79;
				yCoords[i][0] = abyte268;
				byte abyte418[] = {
					1, 6, 25, 30, 30, 25, 6, 1, 6
				};
				byte abyte552[] = {
					33, 30, 30, 34, 65, 70, 70, 65, 70
				};
				xCoords[i][1] = abyte418;
				yCoords[i][1] = abyte552;
				return;

			case 113: // 'q'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte80[] = {
					30, 30, 30
				};
				byte abyte269[] = {
					70, 2, 70
				};
				xCoords[i][0] = abyte80;
				yCoords[i][0] = abyte269;
				byte abyte419[] = {
					1, 6, 25, 30, 30, 25, 6, 1, 1, 1
				};
				byte abyte553[] = {
					33, 30, 30, 34, 65, 70, 70, 65, 33, 65
				};
				xCoords[i][1] = abyte419;
				yCoords[i][1] = abyte553;
				return;

			case 114: // 'r'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte81[] = {
					1, 1, 1
				};
				byte abyte270[] = {
					30, 70, 30
				};
				xCoords[i][0] = abyte81;
				yCoords[i][0] = abyte270;
				byte abyte420[] = {
					1, 12, 22, 30, 22
				};
				byte abyte554[] = {
					62, 70, 70, 66, 70
				};
				xCoords[i][1] = abyte420;
				yCoords[i][1] = abyte554;
				return;

			case 115: // 's'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte82[] = {
					1, 10, 20, 30, 30, 20, 10, 1, 1, 10, 
					20, 30, 20
				};
				byte abyte271[] = {
					35, 30, 30, 35, 45, 50, 50, 55, 65, 70, 
					70, 65, 70
				};
				xCoords[i][0] = abyte82;
				yCoords[i][0] = abyte271;
				return;

			case 116: // 't'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte83[] = {
					11, 11, 16, 22, 26, 22
				};
				byte abyte272[] = {
					96, 35, 30, 30, 35, 30
				};
				xCoords[i][0] = abyte83;
				yCoords[i][0] = abyte272;
				byte abyte421[] = {
					1, 21, 1
				};
				byte abyte555[] = {
					70, 70, 70
				};
				xCoords[i][1] = abyte421;
				yCoords[i][1] = abyte555;
				return;

			case 117: // 'u'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte84[] = {
					30, 30, 30
				};
				byte abyte273[] = {
					39, 30, 39
				};
				xCoords[i][0] = abyte84;
				yCoords[i][0] = abyte273;
				byte abyte422[] = {
					30, 30, 25, 6, 1, 1, 1
				};
				byte abyte556[] = {
					70, 39, 30, 30, 36, 70, 36
				};
				xCoords[i][1] = abyte422;
				yCoords[i][1] = abyte556;
				return;

			case 118: // 'v'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte85[] = {
					1, 16, 30, 16
				};
				byte abyte274[] = {
					70, 31, 70, 31
				};
				xCoords[i][0] = abyte85;
				yCoords[i][0] = abyte274;
				return;

			case 119: // 'w'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte86[] = {
					1, 10, 19, 10
				};
				byte abyte275[] = {
					70, 31, 70, 31
				};
				xCoords[i][0] = abyte86;
				yCoords[i][0] = abyte275;
				byte abyte423[] = {
					37, 28, 19, 28
				};
				byte abyte557[] = {
					70, 31, 70, 31
				};
				xCoords[i][1] = abyte423;
				yCoords[i][1] = abyte557;
				return;

			case 120: // 'x'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte87[] = {
					1, 35, 1
				};
				byte abyte276[] = {
					70, 30, 70
				};
				xCoords[i][0] = abyte87;
				yCoords[i][0] = abyte276;
				byte abyte424[] = {
					35, 1, 35
				};
				byte abyte558[] = {
					70, 30, 70
				};
				xCoords[i][1] = abyte424;
				yCoords[i][1] = abyte558;
				return;

			case 121: // 'y'
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte88[] = {
					30, 15, 1, 15
				};
				byte abyte277[] = {
					70, 30, 10, 30
				};
				xCoords[i][0] = abyte88;
				yCoords[i][0] = abyte277;
				byte abyte425[] = {
					1, 15, 1
				};
				byte abyte559[] = {
					70, 30, 70
				};
				xCoords[i][1] = abyte425;
				yCoords[i][1] = abyte559;
				return;

			case 122: // 'z'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte89[] = {
					0, 29, 1, 29, 1
				};
				byte abyte278[] = {
					70, 70, 30, 30, 30
				};
				xCoords[i][0] = abyte89;
				yCoords[i][0] = abyte278;
				return;

			case 123: // '{'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte90[] = {
					14, 9, 5, 5, 1, 5, 5, 9, 14, 9
				};
				byte abyte279[] = {
					111, 111, 106, 74, 70, 66, 35, 29, 29, 29
				};
				xCoords[i][0] = abyte90;
				yCoords[i][0] = abyte279;
				return;

			case 124: // '|'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte91[] = {
					25, 25, 25
				};
				byte abyte280[] = {
					110, 30, 110
				};
				xCoords[i][0] = abyte91;
				yCoords[i][0] = abyte280;
				return;

			case 125: // '}'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte92[] = {
					1, 6, 10, 10, 14, 10, 10, 6, 1, 6
				};
				byte abyte281[] = {
					111, 111, 106, 74, 70, 66, 35, 29, 29, 29
				};
				xCoords[i][0] = abyte92;
				yCoords[i][0] = abyte281;
				return;

			case 126: // '~'
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte93[] = {
					5, 15, 25, 35, 25
				};
				byte abyte282[] = {
					81, 87, 81, 87, 81
				};
				xCoords[i][0] = abyte93;
				yCoords[i][0] = abyte282;
				return;

			case 127: // '\177'
			default:
				return;
			}
		if (i < 192)
			switch (i)
			{
			case 161: 
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte94[] = {
					25, 25, 25
				};
				byte abyte283[] = {
					30, 90, 30
				};
				xCoords[i][0] = abyte94;
				yCoords[i][0] = abyte283;
				byte abyte426[] = {
					25, 25, 25
				};
				byte abyte560[] = {
					100, 110, 100
				};
				xCoords[i][1] = abyte426;
				yCoords[i][1] = abyte560;
				return;

			case 162: 
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte95[] = {
					31, 23, 10, 2, 2, 10, 23, 31, 23
				};
				byte abyte284[] = {
					65, 70, 70, 60, 40, 30, 30, 35, 30
				};
				xCoords[i][0] = abyte95;
				yCoords[i][0] = abyte284;
				byte abyte427[] = {
					17, 17, 17
				};
				byte abyte561[] = {
					74, 26, 74
				};
				xCoords[i][1] = abyte427;
				yCoords[i][1] = abyte561;
				return;

			case 163: 
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte96[] = {
					35, 30, 18, 13, 13, 9, 1, 8, 35, 8
				};
				byte abyte285[] = {
					105, 110, 110, 105, 40, 34, 30, 30, 30, 30
				};
				xCoords[i][0] = abyte96;
				yCoords[i][0] = abyte285;
				byte abyte428[] = {
					22, 3, 22
				};
				byte abyte562[] = {
					70, 70, 70
				};
				xCoords[i][1] = abyte428;
				yCoords[i][1] = abyte562;
				return;

			case 164: 
				xCoords[i] = new byte[5][];
				yCoords[i] = new byte[5][];
				byte abyte97[] = {
					10, 5, 5, 10, 20, 25, 25, 20, 10, 20
				};
				byte abyte286[] = {
					80, 74, 65, 60, 60, 65, 74, 80, 80, 80
				};
				xCoords[i][0] = abyte97;
				yCoords[i][0] = abyte286;
				byte abyte429[] = {
					7, 2, 7
				};
				byte abyte563[] = {
					77, 82, 77
				};
				xCoords[i][1] = abyte429;
				yCoords[i][1] = abyte563;
				byte abyte654[] = {
					7, 2, 7
				};
				byte abyte707[] = {
					63, 58, 63
				};
				xCoords[i][2] = abyte654;
				yCoords[i][2] = abyte707;
				byte abyte753[] = {
					23, 28, 23
				};
				byte abyte769[] = {
					63, 58, 63
				};
				xCoords[i][3] = abyte753;
				yCoords[i][3] = abyte769;
				byte abyte784[] = {
					23, 28, 23
				};
				byte abyte787[] = {
					77, 82, 77
				};
				xCoords[i][4] = abyte784;
				yCoords[i][4] = abyte787;
				return;

			case 165: 
				xCoords[i] = new byte[4][];
				yCoords[i] = new byte[4][];
				byte abyte98[] = {
					1, 25, 49, 25
				};
				byte abyte287[] = {
					110, 70, 110, 70
				};
				xCoords[i][0] = abyte98;
				yCoords[i][0] = abyte287;
				byte abyte430[] = {
					25, 25, 25
				};
				byte abyte564[] = {
					70, 30, 70
				};
				xCoords[i][1] = abyte430;
				yCoords[i][1] = abyte564;
				byte abyte655[] = {
					10, 40, 10
				};
				byte abyte708[] = {
					70, 70, 70
				};
				xCoords[i][2] = abyte655;
				yCoords[i][2] = abyte708;
				byte abyte754[] = {
					10, 40, 10
				};
				byte abyte770[] = {
					60, 60, 60
				};
				xCoords[i][3] = abyte754;
				yCoords[i][3] = abyte770;
				return;

			case 166: 
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte99[] = {
					25, 25, 25
				};
				byte abyte288[] = {
					110, 80, 110
				};
				xCoords[i][0] = abyte99;
				yCoords[i][0] = abyte288;
				byte abyte431[] = {
					25, 25, 25
				};
				byte abyte565[] = {
					30, 60, 30
				};
				xCoords[i][1] = abyte431;
				yCoords[i][1] = abyte565;
				return;

			case 167: 
				xCoords[i] = new byte[3][];
				yCoords[i] = new byte[3][];
				byte abyte100[] = {
					36, 26, 11, 1, 1, 36, 36, 36
				};
				byte abyte289[] = {
					104, 109, 109, 104, 94, 69, 59, 69
				};
				xCoords[i][0] = abyte100;
				yCoords[i][0] = abyte289;
				byte abyte432[] = {
					1, 11, 26, 36, 36, 1, 1, 11, 1
				};
				byte abyte566[] = {
					36, 31, 31, 36, 47, 71, 81, 86, 81
				};
				xCoords[i][1] = abyte432;
				yCoords[i][1] = abyte566;
				byte abyte656[] = {
					36, 26, 36
				};
				byte abyte709[] = {
					59, 54, 59
				};
				xCoords[i][2] = abyte656;
				yCoords[i][2] = abyte709;
				return;

			case 168: 
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte101[] = {
					9, 9, 5, 5, 9, 5
				};
				byte abyte290[] = {
					100, 110, 110, 100, 100, 100
				};
				xCoords[i][0] = abyte101;
				yCoords[i][0] = abyte290;
				byte abyte433[] = {
					34, 34, 30, 30, 34, 30
				};
				byte abyte567[] = {
					100, 110, 110, 100, 100, 100
				};
				xCoords[i][1] = abyte433;
				yCoords[i][1] = abyte567;
				return;

			case 169: 
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte102[] = {
					30, 25, 20, 15, 15, 20, 25, 30, 25
				};
				byte abyte291[] = {
					65, 60, 60, 65, 70, 75, 75, 70, 75
				};
				xCoords[i][0] = abyte102;
				yCoords[i][0] = abyte291;
				byte abyte434[] = {
					35, 35, 30, 15, 10, 10, 10, 10, 15, 30, 
					35, 30
				};
				byte abyte568[] = {
					55, 80, 86, 86, 80, 70, 60, 55, 50, 50, 
					55, 50
				};
				xCoords[i][1] = abyte434;
				yCoords[i][1] = abyte568;
				return;

			case 170: 
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte103[] = {
					2, 7, 17, 22, 22, 27, 22
				};
				byte abyte292[] = {
					105, 110, 110, 105, 90, 85, 90
				};
				xCoords[i][0] = abyte103;
				yCoords[i][0] = abyte292;
				byte abyte435[] = {
					22, 7, 2, 2, 7, 17, 22, 17
				};
				byte abyte569[] = {
					100, 100, 95, 90, 85, 85, 90, 85
				};
				xCoords[i][1] = abyte435;
				yCoords[i][1] = abyte569;
				return;

			case 171: 
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte104[] = {
					15, 1, 15, 1
				};
				byte abyte293[] = {
					85, 70, 55, 70
				};
				xCoords[i][0] = abyte104;
				yCoords[i][0] = abyte293;
				byte abyte436[] = {
					30, 16, 30, 16
				};
				byte abyte570[] = {
					85, 70, 55, 70
				};
				xCoords[i][1] = abyte436;
				yCoords[i][1] = abyte570;
				return;

			case 172: 
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte105[] = {
					1, 40, 40, 40
				};
				byte abyte294[] = {
					70, 70, 60, 70
				};
				xCoords[i][0] = abyte105;
				yCoords[i][0] = abyte294;
				return;

			case 173: 
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte106[] = {
					10, 40, 10
				};
				byte abyte295[] = {
					70, 70, 70
				};
				xCoords[i][0] = abyte106;
				yCoords[i][0] = abyte295;
				return;

			case 174: 
				xCoords[i] = new byte[3][];
				yCoords[i] = new byte[3][];
				byte abyte107[] = {
					36, 36, 31, 16, 11, 11, 11, 11, 16, 31, 
					36, 31
				};
				byte abyte296[] = {
					94, 104, 110, 110, 104, 98, 97, 93, 87, 87, 
					93, 87
				};
				xCoords[i][0] = abyte107;
				yCoords[i][0] = abyte296;
				byte abyte437[] = {
					19, 19, 26, 28, 28, 26, 19, 26
				};
				byte abyte571[] = {
					90, 107, 107, 105, 100, 98, 98, 98
				};
				xCoords[i][1] = abyte437;
				yCoords[i][1] = abyte571;
				byte abyte657[] = {
					25, 29, 25
				};
				byte abyte710[] = {
					98, 90, 98
				};
				xCoords[i][2] = abyte657;
				yCoords[i][2] = abyte710;
				return;

			case 175: 
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte108[] = {
					15, 35, 15
				};
				byte abyte297[] = {
					110, 110, 110
				};
				xCoords[i][0] = abyte108;
				yCoords[i][0] = abyte297;
				return;

			case 176: 
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte109[] = {
					22, 17, 17, 22, 27, 32, 32, 27, 22, 27
				};
				byte abyte298[] = {
					100, 105, 111, 116, 116, 111, 105, 100, 100, 100
				};
				xCoords[i][0] = abyte109;
				yCoords[i][0] = abyte298;
				return;

			case 177: 
				xCoords[i] = new byte[3][];
				yCoords[i] = new byte[3][];
				byte abyte110[] = {
					25, 25, 25
				};
				byte abyte299[] = {
					80, 50, 80
				};
				xCoords[i][0] = abyte110;
				yCoords[i][0] = abyte299;
				byte abyte438[] = {
					49, 1, 49
				};
				byte abyte572[] = {
					65, 65, 65
				};
				xCoords[i][1] = abyte438;
				yCoords[i][1] = abyte572;
				byte abyte658[] = {
					49, 1, 49
				};
				byte abyte711[] = {
					40, 40, 40
				};
				xCoords[i][2] = abyte658;
				yCoords[i][2] = abyte711;
				return;

			case 178: 
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte111[] = {
					1, 6, 12, 16, 16, 1, 1, 17, 1
				};
				byte abyte300[] = {
					110, 115, 115, 110, 105, 95, 90, 90, 90
				};
				xCoords[i][0] = abyte111;
				yCoords[i][0] = abyte300;
				return;

			case 179: 
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte112[] = {
					1, 6, 12, 16, 16, 12, 16, 16, 12, 6, 
					1, 6
				};
				byte abyte301[] = {
					110, 113, 113, 110, 105, 102, 99, 94, 91, 91, 
					94, 91
				};
				xCoords[i][0] = abyte112;
				yCoords[i][0] = abyte301;
				byte abyte439[] = {
					12, 6, 12
				};
				byte abyte573[] = {
					102, 102, 102
				};
				xCoords[i][1] = abyte439;
				yCoords[i][1] = abyte573;
				return;

			case 180: 
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte113[] = {
					30, 30, 36, 36, 30, 36
				};
				byte abyte302[] = {
					105, 110, 110, 105, 105, 105
				};
				xCoords[i][0] = abyte113;
				yCoords[i][0] = abyte302;
				byte abyte440[] = {
					29, 29, 19, 29
				};
				byte abyte574[] = {
					110, 95, 80, 95
				};
				xCoords[i][1] = abyte440;
				yCoords[i][1] = abyte574;
				return;

			case 181: 
				xCoords[i] = new byte[3][];
				yCoords[i] = new byte[3][];
				byte abyte114[] = {
					1, 1, 1
				};
				byte abyte303[] = {
					36, 20, 36
				};
				xCoords[i][0] = abyte114;
				yCoords[i][0] = abyte303;
				byte abyte441[] = {
					30, 30, 30
				};
				byte abyte575[] = {
					39, 30, 39
				};
				xCoords[i][1] = abyte441;
				yCoords[i][1] = abyte575;
				byte abyte659[] = {
					30, 30, 25, 6, 1, 1, 1
				};
				byte abyte712[] = {
					70, 39, 30, 30, 36, 70, 36
				};
				xCoords[i][2] = abyte659;
				yCoords[i][2] = abyte712;
				return;

			case 182: 
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte115[] = {
					35, 35, 30, 30, 30
				};
				byte abyte304[] = {
					30, 110, 110, 30, 110
				};
				xCoords[i][0] = abyte115;
				yCoords[i][0] = abyte304;
				byte abyte442[] = {
					30, 20, 9, 9, 20, 30, 20
				};
				byte abyte576[] = {
					110, 110, 100, 85, 75, 75, 75
				};
				xCoords[i][1] = abyte442;
				yCoords[i][1] = abyte576;
				return;

			case 183: 
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte116[] = {
					27, 27, 23, 23, 27, 23
				};
				byte abyte305[] = {
					68, 73, 73, 68, 68, 68
				};
				xCoords[i][0] = abyte116;
				yCoords[i][0] = abyte305;
				return;

			case 184: 
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte117[] = {
					18, 29, 19, 29
				};
				byte abyte306[] = {
					30, 20, 17, 20
				};
				xCoords[i][0] = abyte117;
				yCoords[i][0] = abyte306;
				return;

			case 185: 
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte118[] = {
					15, 3, 15
				};
				byte abyte307[] = {
					90, 90, 90
				};
				xCoords[i][0] = abyte118;
				yCoords[i][0] = abyte307;
				byte abyte443[] = {
					4, 9, 9, 9
				};
				byte abyte577[] = {
					109, 113, 90, 113
				};
				xCoords[i][1] = abyte443;
				yCoords[i][1] = abyte577;
				return;

			case 186: 
				xCoords[i] = new byte[1][];
				yCoords[i] = new byte[1][];
				byte abyte119[] = {
					18, 13, 13, 18, 33, 38, 38, 33, 18, 33
				};
				byte abyte308[] = {
					80, 85, 111, 116, 116, 111, 85, 80, 80, 80
				};
				xCoords[i][0] = abyte119;
				yCoords[i][0] = abyte308;
				return;

			case 187: 
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte120[] = {
					16, 30, 16, 30
				};
				byte abyte309[] = {
					85, 70, 55, 70
				};
				xCoords[i][0] = abyte120;
				yCoords[i][0] = abyte309;
				byte abyte444[] = {
					1, 15, 1, 15
				};
				byte abyte578[] = {
					85, 70, 55, 70
				};
				xCoords[i][1] = abyte444;
				yCoords[i][1] = abyte578;
				return;

			case 188: 
				xCoords[i] = new byte[4][];
				yCoords[i] = new byte[4][];
				byte abyte121[] = {
					4, 40, 4
				};
				byte abyte310[] = {
					50, 104, 50
				};
				xCoords[i][0] = abyte121;
				yCoords[i][0] = abyte310;
				byte abyte445[] = {
					16, 4, 16
				};
				byte abyte579[] = {
					80, 80, 80
				};
				xCoords[i][1] = abyte445;
				yCoords[i][1] = abyte579;
				byte abyte660[] = {
					5, 10, 10, 10
				};
				byte abyte713[] = {
					99, 103, 80, 103
				};
				xCoords[i][2] = abyte660;
				yCoords[i][2] = abyte713;
				byte abyte755[] = {
					37, 37, 27, 40, 27
				};
				byte abyte771[] = {
					50, 74, 60, 60, 60
				};
				xCoords[i][3] = abyte755;
				yCoords[i][3] = abyte771;
				return;

			case 189: 
				xCoords[i] = new byte[4][];
				yCoords[i] = new byte[4][];
				byte abyte122[] = {
					5, 40, 5
				};
				byte abyte311[] = {
					50, 104, 50
				};
				xCoords[i][0] = abyte122;
				yCoords[i][0] = abyte311;
				byte abyte446[] = {
					17, 5, 17
				};
				byte abyte580[] = {
					80, 80, 80
				};
				xCoords[i][1] = abyte446;
				yCoords[i][1] = abyte580;
				byte abyte661[] = {
					6, 11, 11, 11
				};
				byte abyte714[] = {
					99, 103, 80, 103
				};
				xCoords[i][2] = abyte661;
				yCoords[i][2] = abyte714;
				byte abyte756[] = {
					25, 30, 36, 40, 40, 25, 25, 41, 25
				};
				byte abyte772[] = {
					70, 75, 75, 70, 65, 55, 50, 50, 50
				};
				xCoords[i][3] = abyte756;
				yCoords[i][3] = abyte772;
				return;

			case 190: 
				xCoords[i] = new byte[4][];
				yCoords[i] = new byte[4][];
				byte abyte123[] = {
					5, 40, 5
				};
				byte abyte312[] = {
					50, 102, 50
				};
				xCoords[i][0] = abyte123;
				yCoords[i][0] = abyte312;
				byte abyte447[] = {
					5, 10, 16, 20, 20, 16, 20, 20, 16, 10, 
					5, 10
				};
				byte abyte581[] = {
					99, 102, 102, 99, 94, 91, 88, 83, 80, 80, 
					83, 80
				};
				xCoords[i][1] = abyte447;
				yCoords[i][1] = abyte581;
				byte abyte662[] = {
					16, 10, 16
				};
				byte abyte715[] = {
					91, 91, 91
				};
				xCoords[i][2] = abyte662;
				yCoords[i][2] = abyte715;
				byte abyte757[] = {
					36, 36, 26, 39, 26
				};
				byte abyte773[] = {
					50, 74, 60, 60, 60
				};
				xCoords[i][3] = abyte757;
				yCoords[i][3] = abyte773;
				return;

			case 191: 
				xCoords[i] = new byte[2][];
				yCoords[i] = new byte[2][];
				byte abyte124[] = {
					41, 31, 11, 1, 1, 21, 21, 21
				};
				byte abyte313[] = {
					40, 30, 30, 40, 60, 70, 92, 70
				};
				xCoords[i][0] = abyte124;
				yCoords[i][0] = abyte313;
				byte abyte448[] = {
					21, 21, 21
				};
				byte abyte582[] = {
					101, 110, 101
				};
				xCoords[i][1] = abyte448;
				yCoords[i][1] = abyte582;
				return;

			case 128: 
			case 129: 
			case 130: 
			case 131: 
			case 132: 
			case 133: 
			case 134: 
			case 135: 
			case 136: 
			case 137: 
			case 138: 
			case 139: 
			case 140: 
			case 141: 
			case 142: 
			case 143: 
			case 144: 
			case 145: 
			case 146: 
			case 147: 
			case 148: 
			case 149: 
			case 150: 
			case 151: 
			case 152: 
			case 153: 
			case 154: 
			case 155: 
			case 156: 
			case 157: 
			case 158: 
			case 159: 
			case 160: 
			default:
				return;
			}
		switch (i)
		{
		case 192: 
			xCoords[i] = new byte[3][];
			yCoords[i] = new byte[3][];
			byte abyte125[] = {
				18, 33, 18
			};
			byte abyte314[] = {
				118, 105, 118
			};
			xCoords[i][0] = abyte125;
			yCoords[i][0] = abyte314;
			byte abyte449[] = {
				10, 40, 10
			};
			byte abyte583[] = {
				54, 54, 54
			};
			xCoords[i][1] = abyte449;
			yCoords[i][1] = abyte583;
			byte abyte663[] = {
				0, 25, 50, 25
			};
			byte abyte716[] = {
				30, 89, 30, 89
			};
			xCoords[i][2] = abyte663;
			yCoords[i][2] = abyte716;
			return;

		case 193: 
			xCoords[i] = new byte[3][];
			yCoords[i] = new byte[3][];
			byte abyte126[] = {
				0, 25, 50, 25
			};
			byte abyte315[] = {
				30, 89, 30, 89
			};
			xCoords[i][0] = abyte126;
			yCoords[i][0] = abyte315;
			byte abyte450[] = {
				10, 40, 10
			};
			byte abyte584[] = {
				54, 54, 54
			};
			xCoords[i][1] = abyte450;
			yCoords[i][1] = abyte584;
			byte abyte664[] = {
				33, 18, 33
			};
			byte abyte717[] = {
				118, 105, 118
			};
			xCoords[i][2] = abyte664;
			yCoords[i][2] = abyte717;
			return;

		case 194: 
			xCoords[i] = new byte[3][];
			yCoords[i] = new byte[3][];
			byte abyte127[] = {
				0, 25, 50, 25
			};
			byte abyte316[] = {
				30, 89, 30, 89
			};
			xCoords[i][0] = abyte127;
			yCoords[i][0] = abyte316;
			byte abyte451[] = {
				10, 40, 10
			};
			byte abyte585[] = {
				54, 54, 54
			};
			xCoords[i][1] = abyte451;
			yCoords[i][1] = abyte585;
			byte abyte665[] = {
				16, 25, 34, 25
			};
			byte abyte718[] = {
				105, 118, 105, 118
			};
			xCoords[i][2] = abyte665;
			yCoords[i][2] = abyte718;
			return;

		case 195: 
			xCoords[i] = new byte[3][];
			yCoords[i] = new byte[3][];
			byte abyte128[] = {
				0, 25, 50, 25
			};
			byte abyte317[] = {
				30, 89, 30, 89
			};
			xCoords[i][0] = abyte128;
			yCoords[i][0] = abyte317;
			byte abyte452[] = {
				10, 40, 10
			};
			byte abyte586[] = {
				54, 54, 54
			};
			xCoords[i][1] = abyte452;
			yCoords[i][1] = abyte586;
			byte abyte666[] = {
				10, 20, 30, 40, 30
			};
			byte abyte719[] = {
				110, 116, 110, 116, 110
			};
			xCoords[i][2] = abyte666;
			yCoords[i][2] = abyte719;
			return;

		case 196: 
			xCoords[i] = new byte[4][];
			yCoords[i] = new byte[4][];
			byte abyte129[] = {
				0, 25, 50, 25
			};
			byte abyte318[] = {
				30, 89, 30, 89
			};
			xCoords[i][0] = abyte129;
			yCoords[i][0] = abyte318;
			byte abyte453[] = {
				10, 40, 10
			};
			byte abyte587[] = {
				54, 54, 54
			};
			xCoords[i][1] = abyte453;
			yCoords[i][1] = abyte587;
			byte abyte667[] = {
				11, 11, 15, 15, 11, 15
			};
			byte abyte720[] = {
				105, 115, 115, 105, 105, 105
			};
			xCoords[i][2] = abyte667;
			yCoords[i][2] = abyte720;
			byte abyte758[] = {
				36, 36, 40, 40, 36, 40
			};
			byte abyte774[] = {
				105, 115, 115, 105, 105, 105
			};
			xCoords[i][3] = abyte758;
			yCoords[i][3] = abyte774;
			return;

		case 197: 
			xCoords[i] = new byte[3][];
			yCoords[i] = new byte[3][];
			byte abyte130[] = {
				0, 25, 50, 25
			};
			byte abyte319[] = {
				30, 89, 30, 89
			};
			xCoords[i][0] = abyte130;
			yCoords[i][0] = abyte319;
			byte abyte454[] = {
				10, 40, 10
			};
			byte abyte588[] = {
				54, 54, 54
			};
			xCoords[i][1] = abyte454;
			yCoords[i][1] = abyte588;
			byte abyte668[] = {
				22, 17, 17, 22, 27, 32, 32, 27, 22, 27
			};
			byte abyte721[] = {
				100, 105, 111, 116, 116, 111, 105, 100, 100, 100
			};
			xCoords[i][2] = abyte668;
			yCoords[i][2] = abyte721;
			return;

		case 198: 
			xCoords[i] = new byte[5][];
			yCoords[i] = new byte[5][];
			byte abyte131[] = {
				1, 30, 30, 30
			};
			byte abyte320[] = {
				30, 100, 30, 100
			};
			xCoords[i][0] = abyte131;
			yCoords[i][0] = abyte320;
			byte abyte455[] = {
				13, 30, 13
			};
			byte abyte589[] = {
				60, 60, 60
			};
			xCoords[i][1] = abyte455;
			yCoords[i][1] = abyte589;
			byte abyte669[] = {
				30, 50, 30
			};
			byte abyte722[] = {
				100, 100, 100
			};
			xCoords[i][2] = abyte669;
			yCoords[i][2] = abyte722;
			byte abyte759[] = {
				30, 50, 30
			};
			byte abyte775[] = {
				30, 30, 30
			};
			xCoords[i][3] = abyte759;
			yCoords[i][3] = abyte775;
			byte abyte785[] = {
				30, 40, 30
			};
			byte abyte788[] = {
				64, 64, 64
			};
			xCoords[i][4] = abyte785;
			yCoords[i][4] = abyte788;
			return;

		case 199: 
			xCoords[i] = new byte[2][];
			yCoords[i] = new byte[2][];
			byte abyte132[] = {
				41, 31, 11, 1, 1, 11, 31, 41, 31
			};
			byte abyte321[] = {
				90, 100, 100, 90, 40, 30, 30, 40, 30
			};
			xCoords[i][0] = abyte132;
			yCoords[i][0] = abyte321;
			byte abyte456[] = {
				18, 29, 19, 29
			};
			byte abyte590[] = {
				30, 20, 17, 20
			};
			xCoords[i][1] = abyte456;
			yCoords[i][1] = abyte590;
			return;

		case 200: 
			xCoords[i] = new byte[3][];
			yCoords[i] = new byte[3][];
			byte abyte133[] = {
				1, 41, 1
			};
			byte abyte322[] = {
				60, 60, 60
			};
			xCoords[i][0] = abyte133;
			yCoords[i][0] = abyte322;
			byte abyte457[] = {
				19, 34, 19
			};
			byte abyte591[] = {
				118, 105, 118
			};
			xCoords[i][1] = abyte457;
			yCoords[i][1] = abyte591;
			byte abyte670[] = {
				50, 1, 1, 50, 1
			};
			byte abyte723[] = {
				30, 30, 90, 90, 90
			};
			xCoords[i][2] = abyte670;
			yCoords[i][2] = abyte723;
			return;

		case 201: 
			xCoords[i] = new byte[3][];
			yCoords[i] = new byte[3][];
			byte abyte134[] = {
				1, 40, 1
			};
			byte abyte323[] = {
				60, 60, 60
			};
			xCoords[i][0] = abyte134;
			yCoords[i][0] = abyte323;
			byte abyte458[] = {
				33, 18, 33
			};
			byte abyte592[] = {
				118, 105, 118
			};
			xCoords[i][1] = abyte458;
			yCoords[i][1] = abyte592;
			byte abyte671[] = {
				50, 1, 1, 50, 1
			};
			byte abyte724[] = {
				30, 30, 90, 90, 90
			};
			xCoords[i][2] = abyte671;
			yCoords[i][2] = abyte724;
			return;

		case 202: 
			xCoords[i] = new byte[3][];
			yCoords[i] = new byte[3][];
			byte abyte135[] = {
				1, 40, 1
			};
			byte abyte324[] = {
				60, 60, 60
			};
			xCoords[i][0] = abyte135;
			yCoords[i][0] = abyte324;
			byte abyte459[] = {
				17, 26, 35, 26
			};
			byte abyte593[] = {
				105, 118, 105, 118
			};
			xCoords[i][1] = abyte459;
			yCoords[i][1] = abyte593;
			byte abyte672[] = {
				50, 1, 1, 50, 1
			};
			byte abyte725[] = {
				30, 30, 90, 90, 90
			};
			xCoords[i][2] = abyte672;
			yCoords[i][2] = abyte725;
			return;

		case 203: 
			xCoords[i] = new byte[4][];
			yCoords[i] = new byte[4][];
			byte abyte136[] = {
				1, 40, 1
			};
			byte abyte325[] = {
				60, 60, 60
			};
			xCoords[i][0] = abyte136;
			yCoords[i][0] = abyte325;
			byte abyte460[] = {
				50, 1, 1, 50, 1
			};
			byte abyte594[] = {
				30, 30, 90, 90, 90
			};
			xCoords[i][1] = abyte460;
			yCoords[i][1] = abyte594;
			byte abyte673[] = {
				11, 11, 15, 15, 11, 15
			};
			byte abyte726[] = {
				105, 115, 115, 105, 105, 105
			};
			xCoords[i][2] = abyte673;
			yCoords[i][2] = abyte726;
			byte abyte760[] = {
				36, 36, 40, 40, 36, 40
			};
			byte abyte776[] = {
				105, 115, 115, 105, 105, 105
			};
			xCoords[i][3] = abyte760;
			yCoords[i][3] = abyte776;
			return;

		case 204: 
			xCoords[i] = new byte[4][];
			yCoords[i] = new byte[4][];
			byte abyte137[] = {
				19, 34, 19
			};
			byte abyte326[] = {
				118, 105, 118
			};
			xCoords[i][0] = abyte137;
			yCoords[i][0] = abyte326;
			byte abyte461[] = {
				27, 27, 27
			};
			byte abyte595[] = {
				90, 30, 90
			};
			xCoords[i][1] = abyte461;
			yCoords[i][1] = abyte595;
			byte abyte674[] = {
				10, 44, 10
			};
			byte abyte727[] = {
				30, 30, 30
			};
			xCoords[i][2] = abyte674;
			yCoords[i][2] = abyte727;
			byte abyte761[] = {
				10, 44, 10
			};
			byte abyte777[] = {
				90, 90, 90
			};
			xCoords[i][3] = abyte761;
			yCoords[i][3] = abyte777;
			return;

		case 205: 
			xCoords[i] = new byte[4][];
			yCoords[i] = new byte[4][];
			byte abyte138[] = {
				33, 18, 33
			};
			byte abyte327[] = {
				118, 105, 118
			};
			xCoords[i][0] = abyte138;
			yCoords[i][0] = abyte327;
			byte abyte462[] = {
				26, 26, 26
			};
			byte abyte596[] = {
				90, 30, 90
			};
			xCoords[i][1] = abyte462;
			yCoords[i][1] = abyte596;
			byte abyte675[] = {
				42, 10, 42
			};
			byte abyte728[] = {
				90, 90, 90
			};
			xCoords[i][2] = abyte675;
			yCoords[i][2] = abyte728;
			byte abyte762[] = {
				42, 10, 42
			};
			byte abyte778[] = {
				30, 30, 30
			};
			xCoords[i][3] = abyte762;
			yCoords[i][3] = abyte778;
			return;

		case 206: 
			xCoords[i] = new byte[4][];
			yCoords[i] = new byte[4][];
			byte abyte139[] = {
				17, 26, 35, 26
			};
			byte abyte328[] = {
				105, 118, 105, 118
			};
			xCoords[i][0] = abyte139;
			yCoords[i][0] = abyte328;
			byte abyte463[] = {
				26, 26, 26
			};
			byte abyte597[] = {
				90, 30, 90
			};
			xCoords[i][1] = abyte463;
			yCoords[i][1] = abyte597;
			byte abyte676[] = {
				42, 10, 42
			};
			byte abyte729[] = {
				90, 90, 90
			};
			xCoords[i][2] = abyte676;
			yCoords[i][2] = abyte729;
			byte abyte763[] = {
				42, 10, 42
			};
			byte abyte779[] = {
				30, 30, 30
			};
			xCoords[i][3] = abyte763;
			yCoords[i][3] = abyte779;
			return;

		case 207: 
			xCoords[i] = new byte[5][];
			yCoords[i] = new byte[5][];
			byte abyte140[] = {
				26, 26, 26
			};
			byte abyte329[] = {
				90, 30, 90
			};
			xCoords[i][0] = abyte140;
			yCoords[i][0] = abyte329;
			byte abyte464[] = {
				42, 10, 42
			};
			byte abyte598[] = {
				90, 90, 90
			};
			xCoords[i][1] = abyte464;
			yCoords[i][1] = abyte598;
			byte abyte677[] = {
				42, 10, 42
			};
			byte abyte730[] = {
				30, 30, 30
			};
			xCoords[i][2] = abyte677;
			yCoords[i][2] = abyte730;
			byte abyte764[] = {
				11, 11, 15, 15, 11, 15
			};
			byte abyte780[] = {
				105, 115, 115, 105, 105, 105
			};
			xCoords[i][3] = abyte764;
			yCoords[i][3] = abyte780;
			byte abyte786[] = {
				36, 36, 40, 40, 36, 40
			};
			byte abyte789[] = {
				105, 115, 115, 105, 105, 105
			};
			xCoords[i][4] = abyte786;
			yCoords[i][4] = abyte789;
			return;

		case 208: 
			xCoords[i] = new byte[2][];
			yCoords[i] = new byte[2][];
			byte abyte141[] = {
				10, 10, 40, 50, 50, 50, 50, 50, 40, 10, 
				40
			};
			byte abyte330[] = {
				30, 110, 110, 100, 80, 80, 60, 40, 30, 30, 
				30
			};
			xCoords[i][0] = abyte141;
			yCoords[i][0] = abyte330;
			byte abyte465[] = {
				20, 1, 20
			};
			byte abyte599[] = {
				70, 70, 70
			};
			xCoords[i][1] = abyte465;
			yCoords[i][1] = abyte599;
			return;

		case 209: 
			xCoords[i] = new byte[2][];
			yCoords[i] = new byte[2][];
			byte abyte142[] = {
				5, 15, 25, 35, 25
			};
			byte abyte331[] = {
				110, 116, 110, 116, 110
			};
			xCoords[i][0] = abyte142;
			yCoords[i][0] = abyte331;
			byte abyte466[] = {
				1, 1, 40, 40, 40
			};
			byte abyte600[] = {
				30, 90, 30, 90, 30
			};
			xCoords[i][1] = abyte466;
			yCoords[i][1] = abyte600;
			return;

		case 210: 
			xCoords[i] = new byte[2][];
			yCoords[i] = new byte[2][];
			byte abyte143[] = {
				14, 29, 14
			};
			byte abyte332[] = {
				118, 105, 118
			};
			xCoords[i][0] = abyte143;
			yCoords[i][0] = abyte332;
			byte abyte467[] = {
				11, 1, 1, 11, 31, 41, 41, 31, 11, 31
			};
			byte abyte601[] = {
				91, 81, 41, 31, 31, 41, 81, 91, 91, 91
			};
			xCoords[i][1] = abyte467;
			yCoords[i][1] = abyte601;
			return;

		case 211: 
			xCoords[i] = new byte[2][];
			yCoords[i] = new byte[2][];
			byte abyte144[] = {
				28, 13, 28
			};
			byte abyte333[] = {
				118, 105, 118
			};
			xCoords[i][0] = abyte144;
			yCoords[i][0] = abyte333;
			byte abyte468[] = {
				11, 1, 1, 11, 31, 41, 41, 31, 11, 31
			};
			byte abyte602[] = {
				91, 81, 41, 31, 31, 41, 81, 91, 91, 91
			};
			xCoords[i][1] = abyte468;
			yCoords[i][1] = abyte602;
			return;

		case 212: 
			xCoords[i] = new byte[2][];
			yCoords[i] = new byte[2][];
			byte abyte145[] = {
				12, 21, 30, 21
			};
			byte abyte334[] = {
				105, 118, 105, 118
			};
			xCoords[i][0] = abyte145;
			yCoords[i][0] = abyte334;
			byte abyte469[] = {
				11, 1, 1, 11, 31, 41, 41, 31, 11, 31
			};
			byte abyte603[] = {
				91, 81, 41, 31, 31, 41, 81, 91, 91, 91
			};
			xCoords[i][1] = abyte469;
			yCoords[i][1] = abyte603;
			return;

		case 213: 
			xCoords[i] = new byte[2][];
			yCoords[i] = new byte[2][];
			byte abyte146[] = {
				6, 16, 26, 36, 26
			};
			byte abyte335[] = {
				110, 116, 110, 116, 110
			};
			xCoords[i][0] = abyte146;
			yCoords[i][0] = abyte335;
			byte abyte470[] = {
				11, 1, 1, 11, 31, 41, 41, 31, 11, 31
			};
			byte abyte604[] = {
				91, 81, 41, 31, 31, 41, 81, 91, 91, 91
			};
			xCoords[i][1] = abyte470;
			yCoords[i][1] = abyte604;
			return;

		case 214: 
			xCoords[i] = new byte[3][];
			yCoords[i] = new byte[3][];
			byte abyte147[] = {
				11, 1, 1, 11, 31, 41, 41, 31, 11, 31
			};
			byte abyte336[] = {
				91, 81, 41, 31, 31, 41, 81, 91, 91, 91
			};
			xCoords[i][0] = abyte147;
			yCoords[i][0] = abyte336;
			byte abyte471[] = {
				11, 11, 15, 15, 11, 15
			};
			byte abyte605[] = {
				105, 115, 115, 105, 105, 105
			};
			xCoords[i][1] = abyte471;
			yCoords[i][1] = abyte605;
			byte abyte678[] = {
				36, 36, 40, 40, 36, 40
			};
			byte abyte731[] = {
				105, 115, 115, 105, 105, 105
			};
			xCoords[i][2] = abyte678;
			yCoords[i][2] = abyte731;
			return;

		case 215: 
			xCoords[i] = new byte[2][];
			yCoords[i] = new byte[2][];
			byte abyte148[] = {
				1, 35, 1
			};
			byte abyte337[] = {
				70, 30, 70
			};
			xCoords[i][0] = abyte148;
			yCoords[i][0] = abyte337;
			byte abyte472[] = {
				35, 1, 35
			};
			byte abyte606[] = {
				70, 30, 70
			};
			xCoords[i][1] = abyte472;
			yCoords[i][1] = abyte606;
			return;

		case 216: 
			xCoords[i] = new byte[2][];
			yCoords[i] = new byte[2][];
			byte abyte149[] = {
				11, 1, 1, 11, 31, 41, 41, 31, 11, 31
			};
			byte abyte338[] = {
				91, 81, 41, 31, 31, 41, 81, 91, 91, 91
			};
			xCoords[i][0] = abyte149;
			yCoords[i][0] = abyte338;
			byte abyte473[] = {
				42, 0, 42
			};
			byte abyte607[] = {
				92, 30, 92
			};
			xCoords[i][1] = abyte473;
			yCoords[i][1] = abyte607;
			return;

		case 217: 
			xCoords[i] = new byte[2][];
			yCoords[i] = new byte[2][];
			byte abyte150[] = {
				13, 28, 13
			};
			byte abyte339[] = {
				118, 105, 118
			};
			xCoords[i][0] = abyte150;
			yCoords[i][0] = abyte339;
			byte abyte474[] = {
				1, 1, 11, 31, 41, 41, 41
			};
			byte abyte608[] = {
				90, 40, 30, 30, 40, 90, 40
			};
			xCoords[i][1] = abyte474;
			yCoords[i][1] = abyte608;
			return;

		case 218: 
			xCoords[i] = new byte[2][];
			yCoords[i] = new byte[2][];
			byte abyte151[] = {
				27, 12, 27
			};
			byte abyte340[] = {
				118, 105, 118
			};
			xCoords[i][0] = abyte151;
			yCoords[i][0] = abyte340;
			byte abyte475[] = {
				1, 1, 11, 31, 41, 41, 41
			};
			byte abyte609[] = {
				90, 40, 30, 30, 40, 90, 40
			};
			xCoords[i][1] = abyte475;
			yCoords[i][1] = abyte609;
			return;

		case 219: 
			xCoords[i] = new byte[2][];
			yCoords[i] = new byte[2][];
			byte abyte152[] = {
				11, 20, 29, 20
			};
			byte abyte341[] = {
				105, 118, 105, 118
			};
			xCoords[i][0] = abyte152;
			yCoords[i][0] = abyte341;
			byte abyte476[] = {
				1, 1, 11, 31, 41, 41, 41
			};
			byte abyte610[] = {
				90, 40, 30, 30, 40, 90, 40
			};
			xCoords[i][1] = abyte476;
			yCoords[i][1] = abyte610;
			return;

		case 220: 
			xCoords[i] = new byte[3][];
			yCoords[i] = new byte[3][];
			byte abyte153[] = {
				1, 1, 11, 31, 41, 41, 41
			};
			byte abyte342[] = {
				90, 40, 30, 30, 40, 90, 40
			};
			xCoords[i][0] = abyte153;
			yCoords[i][0] = abyte342;
			byte abyte477[] = {
				11, 11, 15, 15, 11, 15
			};
			byte abyte611[] = {
				105, 115, 115, 105, 105, 105
			};
			xCoords[i][1] = abyte477;
			yCoords[i][1] = abyte611;
			byte abyte679[] = {
				36, 36, 40, 40, 36, 40
			};
			byte abyte732[] = {
				105, 115, 115, 105, 105, 105
			};
			xCoords[i][2] = abyte679;
			yCoords[i][2] = abyte732;
			return;

		case 221: 
			xCoords[i] = new byte[3][];
			yCoords[i] = new byte[3][];
			byte abyte154[] = {
				26, 11, 26
			};
			byte abyte343[] = {
				118, 105, 118
			};
			xCoords[i][0] = abyte154;
			yCoords[i][0] = abyte343;
			byte abyte478[] = {
				0, 19, 40, 19
			};
			byte abyte612[] = {
				90, 60, 90, 60
			};
			xCoords[i][1] = abyte478;
			yCoords[i][1] = abyte612;
			byte abyte680[] = {
				19, 19, 19
			};
			byte abyte733[] = {
				61, 30, 61
			};
			xCoords[i][2] = abyte680;
			yCoords[i][2] = abyte733;
			return;

		case 222: 
			xCoords[i] = new byte[2][];
			yCoords[i] = new byte[2][];
			byte abyte155[] = {
				1, 1, 1
			};
			byte abyte344[] = {
				100, 30, 100
			};
			xCoords[i][0] = abyte155;
			yCoords[i][0] = abyte344;
			byte abyte479[] = {
				1, 24, 34, 34, 24, 1, 24
			};
			byte abyte613[] = {
				60, 60, 70, 80, 90, 90, 90
			};
			xCoords[i][1] = abyte479;
			yCoords[i][1] = abyte613;
			return;

		case 223: 
			xCoords[i] = new byte[1][];
			yCoords[i] = new byte[1][];
			byte abyte156[] = {
				1, 1, 9, 20, 30, 30, 20, 34, 34, 24, 
				14, 24
			};
			byte abyte345[] = {
				30, 80, 90, 90, 80, 70, 60, 50, 40, 30, 
				30, 30
			};
			xCoords[i][0] = abyte156;
			yCoords[i][0] = abyte345;
			return;

		case 224: 
			xCoords[i] = new byte[3][];
			yCoords[i] = new byte[3][];
			byte abyte157[] = {
				7, 22, 7
			};
			byte abyte346[] = {
				93, 80, 93
			};
			xCoords[i][0] = abyte157;
			yCoords[i][0] = abyte346;
			byte abyte480[] = {
				30, 25, 6, 1, 1, 6, 30, 6
			};
			byte abyte614[] = {
				35, 30, 30, 35, 49, 54, 54, 54
			};
			xCoords[i][1] = abyte480;
			yCoords[i][1] = abyte614;
			byte abyte681[] = {
				35, 30, 30, 25, 6, 1, 6
			};
			byte abyte734[] = {
				30, 35, 65, 70, 70, 65, 70
			};
			xCoords[i][2] = abyte681;
			yCoords[i][2] = abyte734;
			return;

		case 225: 
			xCoords[i] = new byte[3][];
			yCoords[i] = new byte[3][];
			byte abyte158[] = {
				23, 8, 23
			};
			byte abyte347[] = {
				93, 80, 93
			};
			xCoords[i][0] = abyte158;
			yCoords[i][0] = abyte347;
			byte abyte481[] = {
				30, 25, 6, 1, 1, 6, 30, 6
			};
			byte abyte615[] = {
				35, 30, 30, 35, 49, 54, 54, 54
			};
			xCoords[i][1] = abyte481;
			yCoords[i][1] = abyte615;
			byte abyte682[] = {
				35, 30, 30, 25, 6, 1, 6
			};
			byte abyte735[] = {
				30, 35, 65, 70, 70, 65, 70
			};
			xCoords[i][2] = abyte682;
			yCoords[i][2] = abyte735;
			return;

		case 226: 
			xCoords[i] = new byte[3][];
			yCoords[i] = new byte[3][];
			byte abyte159[] = {
				6, 15, 24, 15
			};
			byte abyte348[] = {
				80, 93, 80, 93
			};
			xCoords[i][0] = abyte159;
			yCoords[i][0] = abyte348;
			byte abyte482[] = {
				30, 25, 6, 1, 1, 6, 30, 6
			};
			byte abyte616[] = {
				35, 30, 30, 35, 49, 54, 54, 54
			};
			xCoords[i][1] = abyte482;
			yCoords[i][1] = abyte616;
			byte abyte683[] = {
				35, 30, 30, 25, 6, 1, 6
			};
			byte abyte736[] = {
				30, 35, 65, 70, 70, 65, 70
			};
			xCoords[i][2] = abyte683;
			yCoords[i][2] = abyte736;
			return;

		case 227: 
			xCoords[i] = new byte[3][];
			yCoords[i] = new byte[3][];
			byte abyte160[] = {
				1, 11, 21, 31, 21
			};
			byte abyte349[] = {
				81, 87, 81, 87, 81
			};
			xCoords[i][0] = abyte160;
			yCoords[i][0] = abyte349;
			byte abyte483[] = {
				30, 25, 6, 1, 1, 6, 30, 6
			};
			byte abyte617[] = {
				35, 30, 30, 35, 49, 54, 54, 54
			};
			xCoords[i][1] = abyte483;
			yCoords[i][1] = abyte617;
			byte abyte684[] = {
				35, 30, 30, 25, 6, 1, 6
			};
			byte abyte737[] = {
				30, 35, 65, 70, 70, 65, 70
			};
			xCoords[i][2] = abyte684;
			yCoords[i][2] = abyte737;
			return;

		case 228: 
			xCoords[i] = new byte[4][];
			yCoords[i] = new byte[4][];
			byte abyte161[] = {
				30, 25, 6, 1, 1, 6, 30, 6
			};
			byte abyte350[] = {
				35, 30, 30, 35, 49, 54, 54, 54
			};
			xCoords[i][0] = abyte161;
			yCoords[i][0] = abyte350;
			byte abyte484[] = {
				35, 30, 30, 25, 6, 1, 6
			};
			byte abyte618[] = {
				30, 35, 65, 70, 70, 65, 70
			};
			xCoords[i][1] = abyte484;
			yCoords[i][1] = abyte618;
			byte abyte685[] = {
				5, 5, 9, 9, 5, 9
			};
			byte abyte738[] = {
				105, 115, 115, 105, 105, 105
			};
			xCoords[i][2] = abyte685;
			yCoords[i][2] = abyte738;
			byte abyte765[] = {
				22, 22, 26, 26, 22, 26
			};
			byte abyte781[] = {
				105, 115, 115, 105, 105, 105
			};
			xCoords[i][3] = abyte765;
			yCoords[i][3] = abyte781;
			return;

		case 229: 
			xCoords[i] = new byte[3][];
			yCoords[i] = new byte[3][];
			byte abyte162[] = {
				14, 9, 9, 14, 19, 24, 24, 19, 14, 19
			};
			byte abyte351[] = {
				81, 86, 91, 96, 96, 91, 86, 81, 81, 81
			};
			xCoords[i][0] = abyte162;
			yCoords[i][0] = abyte351;
			byte abyte485[] = {
				30, 25, 6, 1, 1, 6, 30, 6
			};
			byte abyte619[] = {
				35, 30, 30, 35, 49, 54, 54, 54
			};
			xCoords[i][1] = abyte485;
			yCoords[i][1] = abyte619;
			byte abyte686[] = {
				35, 30, 30, 25, 6, 1, 6
			};
			byte abyte739[] = {
				30, 35, 65, 70, 70, 65, 70
			};
			xCoords[i][2] = abyte686;
			yCoords[i][2] = abyte739;
			return;

		case 230: 
			xCoords[i] = new byte[3][];
			yCoords[i] = new byte[3][];
			byte abyte163[] = {
				20, 15, 6, 1, 1, 6, 20, 6
			};
			byte abyte352[] = {
				35, 30, 30, 35, 49, 54, 54, 54
			};
			xCoords[i][0] = abyte163;
			yCoords[i][0] = abyte352;
			byte abyte486[] = {
				25, 20, 20, 15, 5, 0, 5
			};
			byte abyte620[] = {
				30, 35, 65, 70, 70, 65, 70
			};
			xCoords[i][1] = abyte486;
			yCoords[i][1] = abyte620;
			byte abyte687[] = {
				20, 40, 40, 35, 25, 20, 20, 25, 35, 40, 
				35
			};
			byte abyte740[] = {
				54, 54, 64, 70, 70, 65, 35, 30, 30, 35, 
				30
			};
			xCoords[i][2] = abyte687;
			yCoords[i][2] = abyte740;
			return;

		case 231: 
			xCoords[i] = new byte[2][];
			yCoords[i] = new byte[2][];
			byte abyte164[] = {
				30, 22, 9, 1, 1, 9, 22, 30, 22
			};
			byte abyte353[] = {
				65, 70, 70, 60, 40, 30, 30, 35, 30
			};
			xCoords[i][0] = abyte164;
			yCoords[i][0] = abyte353;
			byte abyte487[] = {
				10, 21, 11, 21
			};
			byte abyte621[] = {
				30, 20, 17, 20
			};
			xCoords[i][1] = abyte487;
			yCoords[i][1] = abyte621;
			return;

		case 232: 
			xCoords[i] = new byte[2][];
			yCoords[i] = new byte[2][];
			byte abyte165[] = {
				8, 23, 8
			};
			byte abyte354[] = {
				94, 81, 94
			};
			xCoords[i][0] = abyte165;
			yCoords[i][0] = abyte354;
			byte abyte488[] = {
				1, 30, 30, 22, 9, 1, 1, 9, 22, 30, 
				22
			};
			byte abyte622[] = {
				52, 52, 64, 70, 70, 64, 35, 30, 30, 35, 
				30
			};
			xCoords[i][1] = abyte488;
			yCoords[i][1] = abyte622;
			return;

		case 233: 
			xCoords[i] = new byte[2][];
			yCoords[i] = new byte[2][];
			byte abyte166[] = {
				23, 8, 23
			};
			byte abyte355[] = {
				93, 80, 93
			};
			xCoords[i][0] = abyte166;
			yCoords[i][0] = abyte355;
			byte abyte489[] = {
				1, 30, 30, 22, 9, 1, 1, 9, 22, 30, 
				22
			};
			byte abyte623[] = {
				52, 52, 64, 70, 70, 64, 35, 30, 30, 35, 
				30
			};
			xCoords[i][1] = abyte489;
			yCoords[i][1] = abyte623;
			return;

		case 234: 
			xCoords[i] = new byte[2][];
			yCoords[i] = new byte[2][];
			byte abyte167[] = {
				7, 16, 25, 16
			};
			byte abyte356[] = {
				80, 93, 80, 93
			};
			xCoords[i][0] = abyte167;
			yCoords[i][0] = abyte356;
			byte abyte490[] = {
				1, 30, 30, 22, 9, 1, 1, 9, 22, 30, 
				22
			};
			byte abyte624[] = {
				52, 52, 64, 70, 70, 64, 35, 30, 30, 35, 
				30
			};
			xCoords[i][1] = abyte490;
			yCoords[i][1] = abyte624;
			return;

		case 235: 
			xCoords[i] = new byte[3][];
			yCoords[i] = new byte[3][];
			byte abyte168[] = {
				1, 30, 30, 22, 9, 1, 1, 9, 22, 30, 
				22
			};
			byte abyte357[] = {
				52, 52, 64, 70, 70, 64, 35, 30, 30, 35, 
				30
			};
			xCoords[i][0] = abyte168;
			yCoords[i][0] = abyte357;
			byte abyte491[] = {
				5, 5, 9, 9, 5, 9
			};
			byte abyte625[] = {
				105, 115, 115, 105, 105, 105
			};
			xCoords[i][1] = abyte491;
			yCoords[i][1] = abyte625;
			byte abyte688[] = {
				22, 22, 26, 26, 22, 26
			};
			byte abyte741[] = {
				105, 115, 115, 105, 105, 105
			};
			xCoords[i][2] = abyte688;
			yCoords[i][2] = abyte741;
			return;

		case 236: 
			xCoords[i] = new byte[2][];
			yCoords[i] = new byte[2][];
			byte abyte169[] = {
				19, 34, 19
			};
			byte abyte358[] = {
				94, 81, 94
			};
			xCoords[i][0] = abyte169;
			yCoords[i][0] = abyte358;
			byte abyte492[] = {
				20, 28, 28, 28
			};
			byte abyte626[] = {
				70, 70, 30, 70
			};
			xCoords[i][1] = abyte492;
			yCoords[i][1] = abyte626;
			return;

		case 237: 
			xCoords[i] = new byte[2][];
			yCoords[i] = new byte[2][];
			byte abyte170[] = {
				33, 18, 33
			};
			byte abyte359[] = {
				93, 80, 93
			};
			xCoords[i][0] = abyte170;
			yCoords[i][0] = abyte359;
			byte abyte493[] = {
				20, 28, 28, 28
			};
			byte abyte627[] = {
				70, 70, 30, 70
			};
			xCoords[i][1] = abyte493;
			yCoords[i][1] = abyte627;
			return;

		case 238: 
			xCoords[i] = new byte[2][];
			yCoords[i] = new byte[2][];
			byte abyte171[] = {
				17, 26, 35, 26
			};
			byte abyte360[] = {
				80, 93, 80, 93
			};
			xCoords[i][0] = abyte171;
			yCoords[i][0] = abyte360;
			byte abyte494[] = {
				20, 28, 28, 28
			};
			byte abyte628[] = {
				70, 70, 30, 70
			};
			xCoords[i][1] = abyte494;
			yCoords[i][1] = abyte628;
			return;

		case 239: 
			xCoords[i] = new byte[3][];
			yCoords[i] = new byte[3][];
			byte abyte172[] = {
				20, 28, 28, 28
			};
			byte abyte361[] = {
				70, 70, 30, 70
			};
			xCoords[i][0] = abyte172;
			yCoords[i][0] = abyte361;
			byte abyte495[] = {
				11, 11, 15, 15, 11, 15
			};
			byte abyte629[] = {
				105, 115, 115, 105, 105, 105
			};
			xCoords[i][1] = abyte495;
			yCoords[i][1] = abyte629;
			byte abyte689[] = {
				36, 36, 40, 40, 36, 40
			};
			byte abyte742[] = {
				105, 115, 115, 105, 105, 105
			};
			xCoords[i][2] = abyte689;
			yCoords[i][2] = abyte742;
			return;

		case 240: 
			xCoords[i] = new byte[2][];
			yCoords[i] = new byte[2][];
			byte abyte173[] = {
				1, 25, 41, 41, 31, 11, 1, 1, 11, 31, 
				41, 31
			};
			byte abyte362[] = {
				100, 89, 66, 40, 30, 30, 40, 60, 70, 70, 
				60, 70
			};
			xCoords[i][0] = abyte173;
			yCoords[i][0] = abyte362;
			byte abyte496[] = {
				28, 8, 28
			};
			byte abyte630[] = {
				100, 86, 100
			};
			xCoords[i][1] = abyte496;
			yCoords[i][1] = abyte630;
			return;

		case 241: 
			xCoords[i] = new byte[3][];
			yCoords[i] = new byte[3][];
			byte abyte174[] = {
				1, 11, 21, 31, 21
			};
			byte abyte363[] = {
				81, 87, 81, 87, 81
			};
			xCoords[i][0] = abyte174;
			yCoords[i][0] = abyte363;
			byte abyte497[] = {
				1, 1, 7, 23, 30, 30, 30
			};
			byte abyte631[] = {
				30, 60, 70, 70, 65, 30, 65
			};
			xCoords[i][1] = abyte497;
			yCoords[i][1] = abyte631;
			byte abyte690[] = {
				1, 1, 1
			};
			byte abyte743[] = {
				57, 70, 57
			};
			xCoords[i][2] = abyte690;
			yCoords[i][2] = abyte743;
			return;

		case 242: 
			xCoords[i] = new byte[2][];
			yCoords[i] = new byte[2][];
			byte abyte175[] = {
				8, 23, 8
			};
			byte abyte364[] = {
				94, 81, 94
			};
			xCoords[i][0] = abyte175;
			yCoords[i][0] = abyte364;
			byte abyte498[] = {
				6, 1, 1, 6, 25, 30, 30, 25, 6, 25
			};
			byte abyte632[] = {
				30, 34, 65, 70, 70, 65, 34, 30, 30, 30
			};
			xCoords[i][1] = abyte498;
			yCoords[i][1] = abyte632;
			return;

		case 243: 
			xCoords[i] = new byte[2][];
			yCoords[i] = new byte[2][];
			byte abyte176[] = {
				24, 9, 24
			};
			byte abyte365[] = {
				93, 80, 93
			};
			xCoords[i][0] = abyte176;
			yCoords[i][0] = abyte365;
			byte abyte499[] = {
				6, 1, 1, 6, 25, 30, 30, 25, 6, 25
			};
			byte abyte633[] = {
				30, 34, 65, 70, 70, 65, 34, 30, 30, 30
			};
			xCoords[i][1] = abyte499;
			yCoords[i][1] = abyte633;
			return;

		case 244: 
			xCoords[i] = new byte[2][];
			yCoords[i] = new byte[2][];
			byte abyte177[] = {
				7, 16, 25, 16
			};
			byte abyte366[] = {
				80, 93, 80, 93
			};
			xCoords[i][0] = abyte177;
			yCoords[i][0] = abyte366;
			byte abyte500[] = {
				6, 1, 1, 6, 25, 30, 30, 25, 6, 25
			};
			byte abyte634[] = {
				30, 34, 65, 70, 70, 65, 34, 30, 30, 30
			};
			xCoords[i][1] = abyte500;
			yCoords[i][1] = abyte634;
			return;

		case 245: 
			xCoords[i] = new byte[2][];
			yCoords[i] = new byte[2][];
			byte abyte178[] = {
				1, 11, 21, 31, 21
			};
			byte abyte367[] = {
				81, 87, 81, 87, 81
			};
			xCoords[i][0] = abyte178;
			yCoords[i][0] = abyte367;
			byte abyte501[] = {
				6, 1, 1, 6, 25, 30, 30, 25, 6, 25
			};
			byte abyte635[] = {
				30, 34, 65, 70, 70, 65, 34, 30, 30, 30
			};
			xCoords[i][1] = abyte501;
			yCoords[i][1] = abyte635;
			return;

		case 246: 
			xCoords[i] = new byte[3][];
			yCoords[i] = new byte[3][];
			byte abyte179[] = {
				6, 1, 1, 6, 25, 30, 30, 25, 6, 25
			};
			byte abyte368[] = {
				30, 34, 65, 70, 70, 65, 34, 30, 30, 30
			};
			xCoords[i][0] = abyte179;
			yCoords[i][0] = abyte368;
			byte abyte502[] = {
				5, 5, 9, 9, 5, 9
			};
			byte abyte636[] = {
				105, 115, 115, 105, 105, 105
			};
			xCoords[i][1] = abyte502;
			yCoords[i][1] = abyte636;
			byte abyte691[] = {
				22, 22, 26, 26, 22, 26
			};
			byte abyte744[] = {
				105, 115, 115, 105, 105, 105
			};
			xCoords[i][2] = abyte691;
			yCoords[i][2] = abyte744;
			return;

		case 247: 
			xCoords[i] = new byte[3][];
			yCoords[i] = new byte[3][];
			byte abyte180[] = {
				1, 31, 1
			};
			byte abyte369[] = {
				60, 60, 60
			};
			xCoords[i][0] = abyte180;
			yCoords[i][0] = abyte369;
			byte abyte503[] = {
				14, 14, 18, 18, 14, 18
			};
			byte abyte637[] = {
				70, 75, 75, 70, 70, 70
			};
			xCoords[i][1] = abyte503;
			yCoords[i][1] = abyte637;
			byte abyte692[] = {
				14, 14, 18, 18, 14, 18
			};
			byte abyte745[] = {
				45, 50, 50, 45, 45, 45
			};
			xCoords[i][2] = abyte692;
			yCoords[i][2] = abyte745;
			return;

		case 248: 
			xCoords[i] = new byte[2][];
			yCoords[i] = new byte[2][];
			byte abyte181[] = {
				0, 30, 0
			};
			byte abyte370[] = {
				30, 70, 30
			};
			xCoords[i][0] = abyte181;
			yCoords[i][0] = abyte370;
			byte abyte504[] = {
				6, 1, 1, 6, 25, 30, 30, 25, 6, 25
			};
			byte abyte638[] = {
				30, 34, 65, 70, 70, 65, 34, 30, 30, 30
			};
			xCoords[i][1] = abyte504;
			yCoords[i][1] = abyte638;
			return;

		case 249: 
			xCoords[i] = new byte[3][];
			yCoords[i] = new byte[3][];
			byte abyte182[] = {
				8, 23, 8
			};
			byte abyte371[] = {
				94, 81, 94
			};
			xCoords[i][0] = abyte182;
			yCoords[i][0] = abyte371;
			byte abyte505[] = {
				30, 30, 30
			};
			byte abyte639[] = {
				39, 30, 39
			};
			xCoords[i][1] = abyte505;
			yCoords[i][1] = abyte639;
			byte abyte693[] = {
				30, 30, 25, 6, 1, 1, 1
			};
			byte abyte746[] = {
				70, 39, 30, 30, 36, 70, 36
			};
			xCoords[i][2] = abyte693;
			yCoords[i][2] = abyte746;
			return;

		case 250: 
			xCoords[i] = new byte[3][];
			yCoords[i] = new byte[3][];
			byte abyte183[] = {
				23, 8, 23
			};
			byte abyte372[] = {
				93, 80, 93
			};
			xCoords[i][0] = abyte183;
			yCoords[i][0] = abyte372;
			byte abyte506[] = {
				30, 30, 30
			};
			byte abyte640[] = {
				39, 30, 39
			};
			xCoords[i][1] = abyte506;
			yCoords[i][1] = abyte640;
			byte abyte694[] = {
				30, 30, 25, 6, 1, 1, 1
			};
			byte abyte747[] = {
				70, 39, 30, 30, 36, 70, 36
			};
			xCoords[i][2] = abyte694;
			yCoords[i][2] = abyte747;
			return;

		case 251: 
			xCoords[i] = new byte[3][];
			yCoords[i] = new byte[3][];
			byte abyte184[] = {
				7, 16, 25, 16
			};
			byte abyte373[] = {
				80, 93, 80, 93
			};
			xCoords[i][0] = abyte184;
			yCoords[i][0] = abyte373;
			byte abyte507[] = {
				30, 30, 30
			};
			byte abyte641[] = {
				39, 30, 39
			};
			xCoords[i][1] = abyte507;
			yCoords[i][1] = abyte641;
			byte abyte695[] = {
				30, 30, 25, 6, 1, 1, 1
			};
			byte abyte748[] = {
				70, 39, 30, 30, 36, 70, 36
			};
			xCoords[i][2] = abyte695;
			yCoords[i][2] = abyte748;
			return;

		case 252: 
			xCoords[i] = new byte[4][];
			yCoords[i] = new byte[4][];
			byte abyte185[] = {
				30, 30, 30
			};
			byte abyte374[] = {
				39, 30, 39
			};
			xCoords[i][0] = abyte185;
			yCoords[i][0] = abyte374;
			byte abyte508[] = {
				30, 30, 25, 6, 1, 1, 1
			};
			byte abyte642[] = {
				70, 39, 30, 30, 36, 70, 36
			};
			xCoords[i][1] = abyte508;
			yCoords[i][1] = abyte642;
			byte abyte696[] = {
				5, 5, 9, 9, 5, 9
			};
			byte abyte749[] = {
				105, 115, 115, 105, 105, 105
			};
			xCoords[i][2] = abyte696;
			yCoords[i][2] = abyte749;
			byte abyte766[] = {
				22, 22, 26, 26, 22, 26
			};
			byte abyte782[] = {
				105, 115, 115, 105, 105, 105
			};
			xCoords[i][3] = abyte766;
			yCoords[i][3] = abyte782;
			return;

		case 253: 
			xCoords[i] = new byte[3][];
			yCoords[i] = new byte[3][];
			byte abyte186[] = {
				20, 5, 20
			};
			byte abyte375[] = {
				93, 80, 93
			};
			xCoords[i][0] = abyte186;
			yCoords[i][0] = abyte375;
			byte abyte509[] = {
				1, 15, 1
			};
			byte abyte643[] = {
				70, 30, 70
			};
			xCoords[i][1] = abyte509;
			yCoords[i][1] = abyte643;
			byte abyte697[] = {
				30, 15, 1, 15
			};
			byte abyte750[] = {
				70, 30, 10, 30
			};
			xCoords[i][2] = abyte697;
			yCoords[i][2] = abyte750;
			return;

		case 254: 
			xCoords[i] = new byte[2][];
			yCoords[i] = new byte[2][];
			byte abyte187[] = {
				1, 1, 1
			};
			byte abyte376[] = {
				70, 10, 70
			};
			xCoords[i][0] = abyte187;
			yCoords[i][0] = abyte376;
			byte abyte510[] = {
				1, 8, 24, 30, 30, 23, 8, 1, 8
			};
			byte abyte644[] = {
				35, 30, 30, 38, 53, 60, 60, 55, 60
			};
			xCoords[i][1] = abyte510;
			yCoords[i][1] = abyte644;
			return;

		case 255: 
			xCoords[i] = new byte[4][];
			yCoords[i] = new byte[4][];
			byte abyte188[] = {
				30, 15, 1, 15
			};
			byte abyte377[] = {
				70, 30, 10, 30
			};
			xCoords[i][0] = abyte188;
			yCoords[i][0] = abyte377;
			byte abyte511[] = {
				1, 15, 1
			};
			byte abyte645[] = {
				70, 30, 70
			};
			xCoords[i][1] = abyte511;
			yCoords[i][1] = abyte645;
			byte abyte698[] = {
				5, 5, 9, 9, 5, 9
			};
			byte abyte751[] = {
				105, 115, 115, 105, 105, 105
			};
			xCoords[i][2] = abyte698;
			yCoords[i][2] = abyte751;
			byte abyte767[] = {
				22, 22, 26, 26, 22, 26
			};
			byte abyte783[] = {
				105, 115, 115, 105, 105, 105
			};
			xCoords[i][3] = abyte767;
			yCoords[i][3] = abyte783;
			return;
		}
	}
}
