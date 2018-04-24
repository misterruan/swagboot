/*
 * Copyright 2012-2014 Wanda.cn All right reserved. This software is the
 * confidential and proprietary information of Wanda.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Wanda.cn.
 */
package com.rock.common.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Description:时间操作工具 Date: 2015/11/3 9:19 Author: zhaozhiwei
 */
public class DateUtils {

	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String DEFAULT_FORMAT_14 = "yyyyMMddHHmmss";

	public static final String dtLong = "yyyyMMddHHmmssSSSS";

	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	public static final String DATE_PATTERN_YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";

	public static final String DATE_PATTERN_SEQUENCE = "yyMMddHHmmssSSS";

	public static final String DATE_FORMAT_8 = "yyyyMMdd";

	public static final String TIME_FORMAT_6 = "HHmmss";

	public static final DateTimeFormatter DATE_FORMATTER_8 = DateTimeFormatter.ofPattern(DATE_FORMAT_8);
	public static final DateTimeFormatter DATE_TIME_FORMATTER_14 = DateTimeFormatter.ofPattern(DEFAULT_FORMAT_14);
	public static final DateTimeFormatter DATE_TIME_FORMATTER_DEFAULT = DateTimeFormatter.ofPattern(DEFAULT_FORMAT);

	/**
	 * 将字符串转换成date
	 *
	 * @param str
	 *            格式yyyy-MM-dd hh:mm:ss
	 * @return
	 */
	public static Date toDate(String str) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
			Date date = sdf.parse(str);
			return date;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将字符串转换成date
	 *
	 * @param str
	 * @param format
	 *            格式
	 * @return
	 */
	public static Date toDate(String str, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date date = sdf.parse(str);
			return date;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 时间转成字符串,格式yyyy-MM-dd hh:mm:ss
	 *
	 * @param date
	 *            时间
	 * @return
	 */
	public static String toString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
		return sdf.format(date);
	}

	/**
	 * 时间转成字符串
	 *
	 * @param date
	 *            时间
	 * @param format
	 *            格式
	 * @return
	 */
	public static String toString(Date date, String format) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 当前时间加秒
	 *
	 * @param seconds
	 *            秒数
	 * @return
	 */
	public static Date addSeconds(int seconds) {
		return org.apache.commons.lang3.time.DateUtils.addSeconds(new Date(), seconds);
	}

	/**
	 * 当前时间加分钟
	 *
	 * @param minutes
	 *            分钟数
	 * @return
	 */
	public static Date addMinutes(int minutes) {
		return org.apache.commons.lang3.time.DateUtils.addMinutes(new Date(), minutes);
	}

	/**
	 * 当前时间加小时
	 *
	 * @param hours
	 * @return
	 */
	public static Date addHours(int hours) {
		return org.apache.commons.lang3.time.DateUtils.addHours(new Date(), hours);
	}

	/**
	 * 当前时间加天数
	 *
	 * @param day
	 * @return
	 */
	public static Date addDay(int day) {
		return org.apache.commons.lang3.time.DateUtils.addDays(new Date(), day);
	}

	/**
	 * 当前时间加天年
	 *
	 * @param year
	 * @return
	 */
	public static Date addYear(int year) {
		return org.apache.commons.lang3.time.DateUtils.addYears(new Date(), year);
	}

	/**
	 * 昨天开始时间 格式 :yyyy-MM-dd 01:00:00
	 *
	 * @return
	 */
	public static Date yesterday() {
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DATE, -1);

		calendar.set(Calendar.HOUR_OF_DAY, 1);

		calendar.set(Calendar.MINUTE, 0);

		calendar.set(Calendar.SECOND, 0);

		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	/**
	 * 十天前
	 *
	 * @return
	 */
	public static Date tenDaysAgo() {

		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DATE, -10);

		calendar.set(Calendar.HOUR_OF_DAY, 1);

		calendar.set(Calendar.MINUTE, 0);

		calendar.set(Calendar.SECOND, 0);

		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	/**
	 * 三天前
	 *
	 * @return
	 */
	public static Date threeDaysAgo() {

		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DATE, -3);

		calendar.set(Calendar.HOUR_OF_DAY, 1);

		calendar.set(Calendar.MINUTE, 0);

		calendar.set(Calendar.SECOND, 0);

		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	/**
	 * @return
	 */
	public static Date nextHour() {
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.HOUR, 1);

		return calendar.getTime();
	}

	/**
	 * 当前时间
	 *
	 * @return
	 */
	public static String now() {
		Date now = new Date();

		return toString(now);
	}

	/**
	 * 根据查询次数不同返回对应下次的执行时间 第一次 返回5 分钟, 第二次 10分钟 第三次 30分钟 第四次 60分钟 第五次 120分钟
	 *
	 * @param num
	 * @return
	 */
	public static Date getNextTime(int num) {
		switch (num) {
		case 0:
			return org.apache.commons.lang3.time.DateUtils.addMinutes(new Date(), 5);
		case 1:
			return org.apache.commons.lang3.time.DateUtils.addMinutes(new Date(), 10);
		case 2:
			return org.apache.commons.lang3.time.DateUtils.addMinutes(new Date(), 30);
		case 3:
			return org.apache.commons.lang3.time.DateUtils.addMinutes(new Date(), 60);
		case 4:
			return org.apache.commons.lang3.time.DateUtils.addMinutes(new Date(), 120);
		default:
			return org.apache.commons.lang3.time.DateUtils.addMinutes(new Date(), 5);
		}

	}

	/**
	 * 获取传入日期参数的后几天
	 *
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDay(Date date, int day) {
		return org.apache.commons.lang3.time.DateUtils.addDays(date, day);
	}

	/**
	 * 获取传入日期参数的下一天凌晨2点
	 *
	 * @param date
	 * @param date
	 * @return
	 */
	public static Date nextTime(Date date) {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		calendar.add(Calendar.DATE, 1);

		calendar.set(Calendar.HOUR_OF_DAY, 2);

		calendar.set(Calendar.MINUTE, 0);

		calendar.set(Calendar.SECOND, 0);

		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	/**
	 * 获取day天的凌晨时间
	 *
	 * @param day
	 * @return
	 */
	public static Date dayBreakTime(int day) {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(new Date());

		calendar.add(Calendar.DATE, day);

		calendar.set(Calendar.HOUR_OF_DAY, 0);

		calendar.set(Calendar.MINUTE, 0);

		calendar.set(Calendar.SECOND, 0);

		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}

	/**
	 * date1到date2相差几天
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long daysBetween(Date date1, Date date2) {
		Date temp1 = org.apache.commons.lang3.time.DateUtils.truncate(date1, Calendar.DATE);
		Date temp2 = org.apache.commons.lang3.time.DateUtils.truncate(date2, Calendar.DATE);
		return (temp1.getTime() - temp2.getTime()) / (1000 * 60 * 60 * 24);
	}

	/**
	 * 计算推送周期：间隔时间为：2分钟，10分钟，10分钟，1小时，2小时，6小时，15小时
	 * 
	 * @param syncTimes
	 *            同步次数
	 */
	public static LocalDateTime calcuateNextSyncTime(int syncTimes) {
		LocalDateTime time = LocalDateTime.now();
		LocalDateTime nextSyncTime = null;
		switch (syncTimes) {
		case 1:
			nextSyncTime = time.plusMinutes(2);
			break;
		case 2:
			nextSyncTime = time.plusMinutes(10);
			break;
		case 3:
			nextSyncTime = time.plusMinutes(10);
			break;
		case 4:
			nextSyncTime = time.plusHours(1);
			break;
		case 5:
			nextSyncTime = time.plusHours(2);
			break;
		case 6:
			nextSyncTime = time.plusHours(6);
			break;
		case 7:
			nextSyncTime = time.plusHours(15);
			break;
		default:
			nextSyncTime = time;
			break;
		}
		return nextSyncTime;
	}

	/**
	 * 计算推送周期：通知频率为15/15/30/180/1800/1800/1800/1800/3600，单位：秒
	 * 
	 * @param syncTimes
	 *            同步次数
	 * @param algorithm
	 *            算法
	 */
	public static LocalDateTime calcuateNextSyncTimeAlgorithm(int syncTimes) {
		LocalDateTime time = LocalDateTime.now();
		LocalDateTime nextSyncTime = null;
		switch (syncTimes) {
		case 1:
			nextSyncTime = time.plusSeconds(15);
			break;
		case 2:
			nextSyncTime = time.plusSeconds(15);
			break;
		case 3:
			nextSyncTime = time.plusSeconds(30);
			break;
		case 4:
			nextSyncTime = time.plusSeconds(180);
			break;
		case 5:
			nextSyncTime = time.plusSeconds(1800);
			break;
		case 6:
			nextSyncTime = time.plusSeconds(1800);
			break;
		case 7:
			nextSyncTime = time.plusSeconds(1800);
			break;
		case 8:
			nextSyncTime = time.plusSeconds(1800);
			break;
		case 9:
			nextSyncTime = time.plusSeconds(3600);
			break;
		default:
			nextSyncTime = time;
			break;
		}
		return nextSyncTime;
	}

	/**
	 * 计算推送周期：通知频率为15/15/30/180/1800/1800/1800/1800/3600，单位：秒
	 * 
	 * @param syncTimes
	 *            同步次数
	 */
	public static long getNextPubTime(int syncTimes) {
		switch (syncTimes) {
		case 1:
			return 15;
		case 2:
			return 15;
		case 3:
			return 30;
		case 4:
			return 180;
		case 5:
			return 1800;
		case 6:
			return 1800;
		case 7:
			return 1800;
		case 8:
			return 1800;
		case 9:
			return 3600;
		default:
			return 0;
		}
	}

	public static String getCurrDateStr() {
		return LocalDate.now().format(DATE_FORMATTER_8);
	}

	public static String getCurrDateTimeStr() {
		return LocalDateTime.now().format(DATE_TIME_FORMATTER_14);
	}

	public static LocalDate transforDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static LocalDateTime transforDateTime(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public static String format(Date date, DateTimeFormatter formatter) {
		return transforDateTime(date).format(formatter);
	}
}
