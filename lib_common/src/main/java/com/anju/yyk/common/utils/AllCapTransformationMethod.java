package com.anju.yyk.common.utils;

import android.text.method.ReplacementTransformationMethod;

/**
 *
 * @author LeoWang
 *
 * @Package cn.com.drpeng.runman.common.utils
 *
 * @Description 小写转大写
 *
 * @Date 2019/5/30 17:22
 *
 * @modify:
 */
public class AllCapTransformationMethod extends ReplacementTransformationMethod {

	@Override
	protected char[] getOriginal() {
		return new char[]{ 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z' };
	}

	@Override
	protected char[] getReplacement() {
		return new char[]{ 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z' };
	}

}
