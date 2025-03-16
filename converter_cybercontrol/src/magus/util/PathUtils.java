package magus.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathUtils {

	 public static String replaceHashWithNumber(String input) {
	        // 定义正则表达式模式
	        Pattern pattern = Pattern.compile("#(\\d+)");
	        Matcher matcher = pattern.matcher(input);

	        // 使用 StringBuffer 来构建替换后的字符串
	        StringBuffer sb = new StringBuffer();

	        // 查找匹配项并进行替换
	        while (matcher.find()) {
	            String number = matcher.group(1);
	            matcher.appendReplacement(sb, number + "号");
	        }
	        matcher.appendTail(sb);

	        return sb.toString();
	    }
	 public static void main(String[] args) {
		String str = "D:\\CyberControl\\HMI\\project\\一期辅控网\\graph\\图形资源_out\\辅控网-一单元炉内\\#1采样架列表显示.ser";
		String string = replaceHashWithNumber(str);
		System.out.println(string);
	}
}
