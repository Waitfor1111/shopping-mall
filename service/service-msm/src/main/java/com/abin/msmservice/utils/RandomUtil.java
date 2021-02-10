package com.abin.msmservice.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * 获取随机数
 * 
 * @author qianyi
 *
 */
public class RandomUtil {

	private static final Random random = new Random();

	private static final DecimalFormat fourdf = new DecimalFormat("0000");

	private static final DecimalFormat sixdf = new DecimalFormat("000000");

	public static String getFourBitRandom() {

//		String randomCode = "";
//		// 用字符数组的方式随机
//		String model = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//		char[] m = model.toCharArray();
//		for (int j = 0; j < 6; j++) {
//			char c = m[(int) (Math.random() * 36)];
//			// 保证六位随机数之间没有重复的
//			if (randomCode.contains(String.valueOf(c))) {
//				j--;
//				continue;
//			}
//			randomCode = randomCode + c;
//
//		}
//		return randomCode;
		return fourdf.format(random.nextInt(10000));
	}

	public static String getSixBitRandom() {
		return sixdf.format(random.nextInt(1000000));
	}

	/**
	 * 给定数组，抽取n个数据
	 * @param list
	 * @param n
	 * @return
	 */
	public static ArrayList getRandom(List list, int n) {

		Random random = new Random();

		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();

		// 生成随机数字并存入HashMap
		for (int i = 0; i < list.size(); i++) {

			int number = random.nextInt(100) + 1;

			hashMap.put(number, i);
		}

		// 从HashMap导入数组
		Object[] robjs = hashMap.values().toArray();

		ArrayList r = new ArrayList();

		// 遍历数组并打印数据
		for (int i = 0; i < n; i++) {
			r.add(list.get((int) robjs[i]));
			System.out.print(list.get((int) robjs[i]) + "\t");
		}
		System.out.print("\n");
		return r;
	}
}
