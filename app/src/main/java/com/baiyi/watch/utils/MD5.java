package com.baiyi.watch.utils;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.security.MessageDigest;

/**
 * 
 * <p>
 * <b>MD5</b> MD5工具类
 * </p>
 * 
 * @author <a href="mailto:hewentian1989@qq.com">hewentian</a>
 * @since 2015年11月2日
 * 
 */
public class MD5 {
	/**
	 * 产生MD5信息摘要
	 * 
	 * @author <a href="mailto:hewentian1989@qq.com">hewentian</a>
	 * @since 2015年10月21日
	 * 
	 * @param bytes
	 * @return
	 */
	public static byte[] genByte(byte[] bytes) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(bytes);
			byte[] digests = md.digest();

			return digests;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 产生MD5信息摘要，默认将以UTF-8编码传入参数
	 * 
	 * @author <a href="mailto:hewentian1989@qq.com">hewentian</a>
	 * @since 2015年10月21日
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] genByte(String str) {
		try {
			return genByte(str.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 产生MD5信息摘要
	 * 
	 * @author <a href="mailto:hewentian1989@qq.com">hewentian</a>
	 * @since 2015年10月21日
	 * 
	 * @param bytes
	 *            不能为空
	 * @return
	 */
	public static String genStr(byte[] bytes) {
		try {
			byte[] digests = genByte(bytes);

			return byte2hex(digests);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 产生MD5信息摘要，默认将以UTF-8编码传入参数
	 * 
	 * @author <a href="mailto:hewentian1989@qq.com">hewentian</a>
	 * @since 2015年10月21日
	 * 
	 * @param str
	 *            不能为空
	 * @return
	 */
	public static String genStr(String str) {
		try {
			return genStr(str.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String byte2hex(byte[] b) {
		StringBuilder sb = new StringBuilder();
		String stmp = "";
		for (int i = 0; i < b.length; i++) {
			stmp = (Integer.toHexString(b[i] & 0xFF));
			if (stmp.length() == 1) {
				sb.append("0").append(stmp);
			} else {
				sb.append(stmp);
			}
		}

		return sb.toString().toLowerCase();
	}
}