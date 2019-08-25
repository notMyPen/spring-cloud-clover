package rrx.cnuo.cncommon.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class DateUtil {

	public static final String DATE_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DATE_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_MMDD = "MMdd";
    public static final String TIME_YMD_HMS = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_YMDHMS = "yyyyMMddHHmmss";
    public static final String TIME_HHMMSS = "HHmmss";
    
	/**
	 * 将Date类型转换为字符串
	 *
	 * @param date 日期类型
	 * @return 日期字符串
	 */
	public static String format(Object date) throws Exception {
		return format(date, TIME_YMD_HMS);
	}

	/**
	 * 将Date类型转换为字符串
	 *
	 * @param date    日期类型
	 * @param pattern 字符串格式
	 * @return 日期字符串
	 */
	public static String format(Object date, String pattern) throws Exception {
		if (date == null) {
			return "null";
		}
		if (pattern == null || pattern.equals("") || pattern.equals("null")) {
			pattern = TIME_YMD_HMS;
		}
		return new java.text.SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 将字符串转换为Date类型
	 *
	 * @param date 字符串类型
	 * @return 日期类型
	 */
	public static Date format(String date) throws Exception {
		return format(date, null);
	}

	/**
	 * 将字符串转换为Date类型
	 *
	 * @param date    字符串类型
	 * @param pattern 格式
	 * @return 日期类型
	 */
	public static Date format(String date, String pattern) throws Exception {
		if (pattern == null || pattern.equals("") || pattern.equals("null")) {
			pattern = TIME_YMD_HMS;
		}
		if (date == null || date.equals("") || date.equals("null")) {
			return new Date();
		}
		Date d = null;
		try {
			d = new java.text.SimpleDateFormat(pattern).parse(date);
		} catch (ParseException pe) {
		}
		return d;
	}

	/**
	 * 时间转换成日期
	 */
	public static Date getRoundDate(Date d) throws Exception {
		return format(format(d, DATE_YYYY_MM_DD), DATE_YYYY_MM_DD);
	}

	/**
	 * 得到几分钟后或几分钟前的时间
	 */
	public static Date getMinute(Date d, int minute) throws Exception {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.add(Calendar.MINUTE, minute);
		return now.getTime();
	}

	/**
	 * 得到几天后或几天前的时间
	 */
	public static Date getHour(Date d, int hour) throws Exception {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.add(Calendar.HOUR, hour);
		return now.getTime();
	}

	/**
	 * 得到几天后或几天前的时间
	 */
	public static Date getDate(Date d, int day) throws Exception {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.add(Calendar.DATE, day);
		return now.getTime();
	}

	/**
	 * 得到几个月前或几月后的时间
	 */
	public static Date getMonth(Date d, int month) throws Exception {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.add(Calendar.MONTH, month);
//		now.set(Calendar.MONTH, now.get(Calendar.MONTH) - month);
		return now.getTime();
	}

	/**
	 * 得到几年前或几年后的时间
	 */
	public static Date getYear(Date d, int year) throws Exception {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.add(Calendar.YEAR, year);
		return now.getTime();
	}

	/**
	 * 获取n天后的日期
	 *
	 * @param rptdate
	 * @param n
	 * @return
	 */
	public static Date getTheNextDate(Date rptdate, int n) throws Exception {
		Calendar cd = Calendar.getInstance();
		cd.setTime(rptdate);
		cd.add(Calendar.DATE, n);
		return cd.getTime();
	}

	/**
	 * 将毫秒转为天
	 *
	 * @param time
	 * @return
	 */
	public static int millisToDay(Long time) throws Exception {
		if (time == null) {
			return 0;
		}
		return (int) (time / (1000 * 3600 * 24));
	}

	/**
	 * 毫秒转为秒
	 *
	 * @param timeMillis
	 * @return
	 */
	public static long timeMillisToSeconds(long timeMillis) throws Exception {
		return timeMillis / 1000;
	}

	/**
	 * 比较两个时间的天数之差
	 *
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @author xuhongyu
	 */
	public static int DateBetweenDay(Date beginDate, Date endDate) throws Exception {
		Date beginDate1 = getRoundDate(beginDate);
		Date endDate1 = getRoundDate(endDate);
		long beginTime = beginDate1.getTime();
		long endTime = endDate1.getTime();
		return (int) ((endTime - beginTime) / (1000 * 60 * 60 * 24));
	}

	/**
	 * 获取今天还剩下多少秒
	 *
	 * @return
	 */
	public static int getMiao() throws Exception {
		Calendar curDate = Calendar.getInstance();
		Calendar tommorowDate = new GregorianCalendar(curDate.get(Calendar.YEAR), curDate.get(Calendar.MONTH),
				curDate.get(Calendar.DATE) + 1, 0, 0, 0);
		return (int) (tommorowDate.getTimeInMillis() - curDate.getTimeInMillis()) / 1000;
	}

	/**
	 * 获取小时内还剩下多少秒
	 *
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getMiaoLeftHour() throws Exception {
		Date now = new Date();
		Date dt = getHour(now, 1);
		Date dt1 = new Date(dt.getYear(), dt.getMonth(), dt.getDate(), dt.getHours(), 0);
		return (int) ((dt1.getTime() - now.getTime()) / 1000);
	}

	/**
	 * 获取精确到秒的时间戳
	 *
	 * @return
	 */
	public static int getSecondTimestamp(Date date) throws Exception {
		if (null == date) {
			return 0;
		}
		String timestamp = String.valueOf(date.getTime());
		int length = timestamp.length();
		if (length > 3) {
			return Integer.valueOf(timestamp.substring(0, length - 3));
		} else {
			return 0;
		}
	}

	/**
	 * @param n    前进/后退 月数
	 * @param date 日期
	 * @return
	 * @function 获取上N月 第1号
	 */
	public static String getLastMonthFirstDay(Date date, int n) throws Exception {
		Calendar cal_1 = Calendar.getInstance();
		cal_1.setTime(date);
		cal_1.add(Calendar.MONTH, n);
		cal_1.set(Calendar.DAY_OF_MONTH, 1);
		return format(cal_1.getTime(), DATE_YYYY_MM_DD);
	}

	/**
	 * @return
	 * @function 获取时间差
	 */
	public static long getDatePoor(Date endDate, Date startDate) throws Exception {
		long nd = 1000 * 24 * 60 * 60;
		// 获得两个时间的毫秒时间差异
		long diff = endDate.getTime() - startDate.getTime();
		// 计算差多少天
		long day = diff / nd;
		return day;
	}

	/**
	 * 时间转整型值
	 *
	 * @param date
	 * @return
	 */
	public static int getSecond(Date date) throws Exception {
		if (null == date) {
			return 0;
		}
		return (int) (date.getTime() / 1000);
	}

	/**
	 * 整型秒数转时间
	 *
	 * @param second
	 * @return
	 */
	public static Date getDate(Integer second) throws Exception {
		if (second == null || second <= 0) {
			return null;
		}
		return new Date(((long) second) * 1000);
	}

	/**
	 * 时间戳转换成日期格式字符串
	 *
	 * @param formatStr
	 * @return
	 */

	public static Date timeStampToDate(Integer seconds, String format) throws Exception {

		if (seconds == null) {

			return null;

		}

		if (format == null || format.isEmpty())
			format = TIME_YMD_HMS;

		SimpleDateFormat sdf = new SimpleDateFormat(format);

		long times = (long) seconds * 1000;

		String dateStr = sdf.format(new Date(times));

		return format(dateStr, format);

	}

	/**
	 * date2比date1多的天数
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int differentDays(Date date1, Date date2) throws Exception {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int day1 = cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if (year1 != year2) // 同一年
		{
			int timeDistance = 0;
			for (int i = year1; i < year2; i++) {
				if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) // 闰年
				{
					timeDistance += 366;
				} else // 不是闰年
				{
					timeDistance += 365;
				}
			}

			return timeDistance + (day2 - day1);
		} else // 不同年
		{
			return day2 - day1;
		}
	}

	// 获取指定时间戳（秒)与当天的间隔天数
	public static int getSecondToNowDay(Integer seconds) throws Exception {
		int day = 0;
		if (seconds != null) {
			String time = DateUtil.format(new Date(), DATE_YYYY_MM_DD);
			Date sDate = DateUtil.format(time, DATE_YYYY_MM_DD);
			Date eDate = DateUtil.timeStampToDate(seconds, DATE_YYYY_MM_DD);
			if (sDate.getTime() > eDate.getTime()) {
				day = DateUtil.differentDays(eDate, sDate) * -1;
			} else {
				day = DateUtil.differentDays(sDate, eDate);
			}
		}
		return day;
	}

	public static int getBetweenYear(Date beginDate, Date endDate) throws Exception {
		int yearCount = 0;
		if (beginDate != null && endDate != null) {
			Calendar beginCalendar = Calendar.getInstance();
			Calendar endCalendar = Calendar.getInstance();
			beginCalendar.setTime(beginDate);
			endCalendar.setTime(endDate);
			yearCount = endCalendar.get(Calendar.YEAR) - beginCalendar.get(Calendar.YEAR);
		}

		return yearCount;
	}

	public static int getBetweenMonth(Date beginDate, Date endDate) throws Exception {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		// 将String日期转换成date
		Date date1 = getRoundDate(beginDate);
		Date date2 = getRoundDate(endDate);
		c1.setTime(date1);
		c2.setTime(date2);
		// 判断两个日期的大小
		if (c2.getTimeInMillis() < c1.getTimeInMillis())
			return 0;
		int year1 = c1.get(Calendar.YEAR);
		int year2 = c2.get(Calendar.YEAR);
		int month1 = c1.get(Calendar.MONTH);
		int month2 = c2.get(Calendar.MONTH);
		int day1 = c1.get(Calendar.DAY_OF_MONTH);
		int day2 = c2.get(Calendar.DAY_OF_MONTH);
		// 获取年的差值 假设 d1 = 2015-9-30 d2 = 2015-12-16
		int yearInterval = year2 - year1;
		// 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
		if (month2 < month1 || month1 == month2 && day2 < day1)
			yearInterval--;
		// 获取月数差值
		int monthInterval = (month2 + 12) - month1;
		if (day2 > day1)
			monthInterval++;
		monthInterval %= 12;
		return yearInterval * 12 + monthInterval;
	}

	public static int getBetweenDay(Date beginDate, Date endDate) throws Exception {
		int day = 0;
		if (beginDate != null && endDate != null) {
			if (beginDate.getTime() > endDate.getTime()) {
				day = DateUtil.differentDays(endDate, beginDate) * -1;
			} else {
				day = DateUtil.differentDays(beginDate, endDate);
			}
		}
		return day;
	}

	/**
	 * 验证当前时间距指定时间的小时差
	 * 
	 * @return
	 */
	public static int getLeftHours(Date time) throws Exception {
		int hour = 0;
		if (time != null) {
			Calendar now = Calendar.getInstance();
			Calendar date = Calendar.getInstance();
			date.setTime(time);
			int nHour = now.get(Calendar.HOUR_OF_DAY);
			int dHour = date.get(Calendar.HOUR_OF_DAY);
			hour = dHour - nHour;
		}

		return hour;
	}

	/**
	 * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
	 * 
	 * @param nowTime   当前时间
	 * @param startTime 开始时间
	 * @param endTime   结束时间
	 * @return
	 */
	public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) throws Exception {

		if (nowTime.getTime() == startTime.getTime() || nowTime.getTime() == endTime.getTime()) {
			return true;
		}
		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);
		Calendar begin = Calendar.getInstance();
		begin.setTime(startTime);
		Calendar end = Calendar.getInstance();
		end.setTime(endTime);
		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 是否指定整点
	 *
	 * @param formatStr
	 * @return
	 */

	public static boolean isAppointHour(Date date, String timeStr) throws Exception {

		boolean flag = false;

		if (date != null && StringUtils.isNotBlank(timeStr)) {

			String dateStr = DateUtil.format(date, "HH:mm");

			if (dateStr.equals(timeStr)) {
				flag = true;
			}
		}

		return flag;

	}

	/**
	 * 是否是指定的间隔小时数
	 */
	public static boolean isHourByCnt(Date time, Integer cnt) throws Exception {
		boolean flag = false;
		if (time != null) {
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < 24; i++) {
				if (i % cnt == 0) {
					String s = String.valueOf(i) + ":00:00";
					if (i < 10) {
						s = "0" + String.valueOf(i) + ":00:00";
					}
					list.add(s);
				}
			}
			String timeStr = DateUtil.format(time, "HH:mm:ss");
			if (StringUtils.isNotBlank(timeStr) && list.contains(timeStr)) {
				flag = true;
			}
		}

		return flag;
	}

	/**
	 * 是否是指定的间隔分钟数
	 */
	public static boolean isMinuteByCnt(Date time, Integer cnt) throws Exception {
		boolean flag = false;
		if (time != null && cnt != null) {
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < 60; i++) {
				if (i % cnt == 0) {
					String s = String.valueOf(i) + ":00";
					if (i < 10) {
						s = "0" + String.valueOf(i) + ":00";
					}
					list.add(s);
				}
			}
			String timeStr = DateUtil.format(time, "mm:ss");
			if (StringUtils.isNotBlank(timeStr) && list.contains(timeStr)) {
				flag = true;
			}
		}

		return flag;
	}

	/**
	 * 
	 * 得到几天前的时间
	 * 
	 * @param d
	 * 
	 * @param day
	 * 
	 * @return
	 * 
	 */

	public static Date getDateBefore(Date d, int day) throws Exception {

		Calendar now = Calendar.getInstance();

		now.setTime(d);

		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);

		return now.getTime();

	}

	/**
	 * 
	 * 得到几天后的时间
	 * 
	 * @param d
	 * 
	 * @param day
	 * 
	 * @return
	 * 
	 */

	public static Date getDateAfter(Date d, int day) throws Exception {

		Calendar now = Calendar.getInstance();

		now.setTime(d);

		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);

		return now.getTime();

	}

	/**
	 * 获取秒数对应的时分秒(HH小时mm分ss秒)时间字符串
	 * @author xuhongyu
	 * @param leftTimeSec 秒
	 * @return
	 */
	public static String getTimeStrFromSecond(Long leftTimeSec) {
		int hour = (int) (leftTimeSec/3600);
		int minute = (int) ((leftTimeSec%3600)/60);
		int second = (int) ((leftTimeSec%3600)%60);
		StringBuffer sb = new StringBuffer();
		sb.append(hour).append("小时").append(minute).append("分").append(second).append("秒");
		return sb.toString();
	}
	
	public static void main(String[] args){
		List<Long> forwarderUids = new ArrayList<>();
		forwarderUids.add(1124L);
		forwarderUids.add(632L);
		forwarderUids.add(1456L);
		forwarderUids.add(9773L);
		forwarderUids.add(6L);
		
		List<Long> forwarderOpenFailUids = new ArrayList<>();
		forwarderOpenFailUids.add(1456L);
		forwarderOpenFailUids.add(9773L);
		
		/*Iterator<Long> iter = forwarderUids.iterator();
		Long forwarderUid = null;
		while(iter.hasNext()){
			forwarderUid = iter.next();
			if(forwarderOpenFailUids.contains(forwarderUid)){
				iter.remove();
			}
		}*/
		
		/*for(Long forwarderOpenFailUid : forwarderOpenFailUids){
			forwarderUids.remove(forwarderOpenFailUid);
		}*/
		
		forwarderUids.removeAll(forwarderOpenFailUids);
		
		System.out.println(forwarderUids);
	}
}
