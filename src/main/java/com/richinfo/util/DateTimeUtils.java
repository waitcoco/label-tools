package com.richinfo.util;



import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class DateTimeUtils {
	

    public static int compare_date(String date1, String date2, String aFmtDate) {
        
        
        try {
        	if(date1 == null || "".equals(date1.trim()) || date2 == null || "".equals(date2.trim())){
        		return 3;
        	}
            Date dt1 = strToDate(date1,aFmtDate);
            Date dt2 = strToDate(date2,aFmtDate);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception e) {
        	e.printStackTrace();
            return 3;
        }
    }
    

    public static int compare_nowdate(String date1, String aFmtDate) {
        
        try {
        	if(date1 == null || "".equals(date1.trim()) ){
        		return 3;
        	}
            Date dt1 = strToDate(date1,aFmtDate);
            Date dt2 = new Date();
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception e) {
        	e.printStackTrace();
            return 3;
        }
    }

	public static String dateToStr(Date aDteValue, String aFmtDate) {
		String strRtn = null;
		if (aFmtDate.length() == 0) {
			aFmtDate = "yyyyMMddHHmmss";
		}
		Format fmtDate = new SimpleDateFormat(aFmtDate);
		try {
			strRtn = fmtDate.format(aDteValue);
		} catch (Exception e) {
		}
		return strRtn;
	}

	public static String getDate(String format) {
		Date a = new Date();
		return dateToStr(a, format);
	}

	public static String getDateLongStr() {
		Date a = new Date();
		return String.valueOf(a.getTime());
	}

	public static String getDate() {
		Date a = new Date();
		return dateToStr(a, "yyyy-MM-dd HH:mm:ss");
	}

	public static String getDate(String format, int addday) {
		Date a = new Date();
		return dateToStr(addday(a, addday), format);
	}

	public static Date strToDate(String aStrValue, String aFmtDate)
			throws ParseException {
		Date aDateRtn = new Date();
		if (aFmtDate.trim().length() == 0) {
			aFmtDate = "yyyy-MM-dd";
		}
		SimpleDateFormat fmtDate = new SimpleDateFormat(aFmtDate);
		try {
			aDateRtn.setTime(fmtDate.parse(aStrValue).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			throw e;
		}
		return aDateRtn;
	}
	public static Date str2Date(String aStrValue, String aFmtDate)
			{
		Date aDateRtn = new Date();
		if (aFmtDate.trim().length() == 0) {
			aFmtDate = "yyyy-MM-dd";
		}
		SimpleDateFormat fmtDate = new SimpleDateFormat(aFmtDate);
		try {
			aDateRtn.setTime(fmtDate.parse(aStrValue).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return aDateRtn;
	}

	public static Date addSecond(Date strdate, int second) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(strdate);
		cal.add(Calendar.SECOND, second);
		return cal.getTime();
	}
	public static String addSecond(int second) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.SECOND, second);
		return dateToStr(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
	}
	public static Date addMinute(Date strdate, int minute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(strdate);
		cal.add(Calendar.MINUTE, minute);
		return cal.getTime();
	}
	public static int getNowHour(){
		return Integer.valueOf(getDate("HH"));
	}
	public static int getNowMinute(){
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MINUTE);
	}
	public static Date addHour(Date strdate, int hour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(strdate);
		cal.add(Calendar.HOUR, hour);
		return cal.getTime();
	}

	public static Date addday(Date strdate, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(strdate);
		cal.add(Calendar.DATE, day);
		return cal.getTime();
	}
	public static String addday(String strdate, int day,String dateformate) {
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(strToDate(strdate, dateformate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.add(Calendar.DATE, day);
		return dateToStr(cal.getTime(),dateformate);
	}
	public static String addHour(String strdate, int hour,String dateformate) {
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(strToDate(strdate, dateformate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.add(Calendar.HOUR, hour);
		return dateToStr(cal.getTime(),dateformate);
	}
	public static Date addMonth(Date strdate, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(strdate);
		cal.add(Calendar.MONTH, num);
		return cal.getTime();

	}
	public static Date addYear(Date strdate, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(strdate);
		cal.add(Calendar.YEAR, num);
		return cal.getTime();
	}

	public static String getMinDayOfCurrentWeek(String fmate) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK,
				calendar.getActualMinimum(Calendar.DAY_OF_WEEK));
		SimpleDateFormat format = new SimpleDateFormat(fmate);
		return format.format(calendar.getTime());
	}

	public static String getMaxDayOfCurrentWeek(String fmate) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK,
				calendar.getActualMaximum(Calendar.DAY_OF_WEEK));
		SimpleDateFormat format = new SimpleDateFormat(fmate);
		return format.format(calendar.getTime());
	}

	public static String getMinDayOfCurrentMonth(String fmate) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		SimpleDateFormat format = new SimpleDateFormat(fmate);
		return format.format(calendar.getTime());
	}

	public static String getMaxDayOfCurrentMonth(String fmate) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		SimpleDateFormat format = new SimpleDateFormat(fmate);
		return format.format(calendar.getTime());
	}

	/**
	 * 
	 * Description: 获取前七天的时间
	 * 
	 * @Version1.0 2010-8-27 下午04:45:13 by 凡红恩（fanhongen@emay.cn）创建
	 * @param strdate
	 * @param day
	 * @return
	 */
	public static String getDateOfPre(int day) {
		Date date = addday(new Date(), day);
		return dateToStr(date, "yyyy-MM-dd");
	}

	/**
	 * 获得两个日期间的差
	 * 
	 * @param sdate
	 *            起始时间
	 * @param edate
	 *            终止时间
	 * @param bettype
	 *            Calendar.DAY_OF_YEAR 求查的单位
	 * @return
	 */
	public static long numBetweenTwoDate(Date sdate, Date edate, int bettype) {
		if (bettype == Calendar.MONTH) {
			Calendar aCalendar = Calendar.getInstance();
			aCalendar.setTime(sdate);
			int day1 = aCalendar.get(bettype);
			aCalendar.setTime(edate);
			int day2 = aCalendar.get(bettype);
			return day2 - day1;
		} else if (bettype == Calendar.HOUR || bettype == Calendar.HOUR_OF_DAY) {
			// String ssdate=DateTimeUtils.dateToStr(sdate, "yyyyMMddHH");
			// String sedate=DateTimeUtils.dateToStr(edate, "yyyyMMddHH");
			// Integer ssmonth=Integer.parseInt(ssdate.substring(6,8));
			// Integer semonth=Integer.parseInt(sedate.substring(6,8));
			// if(ssmonth==semonth){
			// Integer sshour=Integer.parseInt(ssdate.substring(8,10));
			// Integer sehour=Integer.parseInt(sedate.substring(8,10));
			// return sehour-sshour;
			// }
			long seconds = (edate.getTime() - sdate.getTime()) / 1000;
			return (int) seconds / (60 * 60);// 相差的小时数
		} else if (bettype == Calendar.SECOND ) {
			long seconds = (edate.getTime() - sdate.getTime()) / 1000;
			return seconds;
		}else if (bettype == Calendar.DAY_OF_YEAR) {
			long seconds = (edate.getTime() - sdate.getTime()) / 1000;
			return (int) seconds / (24 * 60 * 60); // 相差的天数
		} else {
			return 0;
		}
	}

	/**
	 * 
	 * Description:
	 * 
	 * @Version1.0 2011-2-28 下午03:47:55 by 凡红恩（fanhongen@emay.cn）创建
	 * @param strDateStart
	 * @param strDateEnd
	 * @return
	 * @throws ParseException
	 */
	public static long getDateofpoor(String strDateStart, String strDateEnd)
			throws ParseException {
		long quot = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date_start = sdf.parse(strDateStart);
			Date date_end = sdf.parse(strDateEnd);
			quot = date_end.getTime() - date_start.getTime();
			quot /= 1000 * 60 * 60 * 24;
		} catch (ParseException e) {
			e.printStackTrace();
			throw e;
		}
		return quot;
	}

	public static String betweendate(String statetime, String endtime)
			throws ParseException {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date begin = dfs.parse(statetime);
		Date end = dfs.parse(endtime);
		long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
		long day1 = between / (24 * 3600);
		long hour1 = between % (24 * 3600) / 3600;
		long minute1 = between % 3600 / 60;
		long second1 = between % 60;
		return "" + day1 + "天" + hour1 + "小时" + minute1 + "分" + second1 + "秒";
	}

	public static long betweendateday(String statetime, String endtime)
			throws ParseException {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");
		Date begin = dfs.parse(statetime);
		Date end = dfs.parse(endtime);
		long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
		long day1 = between / (24 * 3600);
		return day1;
	}

	/**
	 * 两个时间差值（单位为秒）
	 * 
	 * @param statetime
	 *            开始时间
	 * @param endtime
	 *            结束时间
	 * @param format
	 *            格式（yyyy-MM-dd HH:mm:ss 或 yyyyMMddHHmmssSSS）
	 * @return
	 * @throws ParseException
	 */
	public static long betweensecond(String statetime, String endtime,
			String format) {
		SimpleDateFormat dfs = new SimpleDateFormat(format);
		long between =0l;
		try {
			Date begin = dfs.parse(statetime);
			Date end = dfs.parse(endtime);
			between =(end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
		}catch (ParseException ex){
			ex.printStackTrace();
		}
		return between;
	}

	public static long str2long(String time,String format){
		if(time==null || time.isEmpty()){
			return 0l;
		}
		SimpleDateFormat dfs = new SimpleDateFormat(format);
		Date datetime = null;
		try {
			datetime = dfs.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return datetime.getTime()/1000;
	}
	/**
	 * 获取淘宝增量时间段
	 * 
	 * @param minute
	 *            间隔时间单位分钟,最小为10分钟
	 * @return
	 */
	public static Date[] getTaoBaoSandE(int minute) {
		if (minute < 10) {
			minute = 10;
		}
		Date end = new Date();
		String e = getDate("yyyy-MM-dd");
		Date start = addMinute(end, -1 * minute);
		String s = dateToStr(start, "yyyy-MM-dd");
		if (s.equals(e)) {
			return new Date[] { start, end };
		} else {
			Date st = null;
			try {
				e += " 00:00:00";
				st = strToDate(e, "yyyy-MM-dd HH:mm:ss");
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			Date end2 = addday(new Date(), -1);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(end2);
			calendar.set(Calendar.HOUR, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			end2 = calendar.getTime();
			Date st2 = addMinute(end2, -1 * minute);
			return new Date[] { st, end, st2, end2 };
		}
	}

	/**
	 * 将yyyy-MM-dd时间字符串格式化成yyyy年MM月dd日格式字符串。
	 * 
	 * @param s
	 * @return
	 */
	public static String formateCNDate(String s) {
		StringBuilder result = new StringBuilder();
		result.append(s.substring(0, 4)).append("年").append(s.substring(5, 7))
				.append("月").append(s.substring(8, 10)).append("日");
		return result.toString();
	}

	public static void main(String[] args) throws ParseException {
//		String endtime = "2017-05-01 10:10:10";
//		System.out.println(endtime.compareTo(DateTimeUtils.getDate()));	
//		Date acttime = DateTimeUtils.str2Date("2017年05月14日 星期日 10:23", "yyyy年MM月dd日 E HH:mm");
//		String endtime ="2017-06-10 15:45:00";
//		System.out.println(endtime.compareTo(DateTimeUtils.getDate()));
		String url="http://kskdfksdjf?uuid=lsldlf&wxname=可是打开方式可&now=2017年06月25日";
		String nickname="可是到付款";
		url = url.substring(0,url.indexOf("&wxname"))+"&wxname="+nickname+"&now="+DateTimeUtils.getDate("yyyy年MM月dd日");
		System.out.println(url);
	}

	public static String addDay(String format, int addday) {
		Date a = new Date();
		return dateToStr(addday(a, addday), format);
	}

	public static String addMinute(int addMinute) {
		Date a = new Date();
		return dateToStr(addMinute(a, addMinute), "yyyy-MM-dd HH:mm:ss");
	}

	// 活动是否已到期
	public static boolean compareDate(String src) {
		if (!"".equals(src) && null != src) {
			if(src.length()==10){
				src += " 23:59:59";
			}
		} else {
			return false;
		}
		Date endtime = null;
		Date now = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			endtime = sdf.parse(src);
			now = new Date();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now.compareTo(endtime) > 0;
	}

	/**
	 * 获取上月的的第一天和最后一天
	 * 
	 * @return
	 */
	public static String[] getLastMonth() {
		// 取得系统当前时间
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		// 取得系统当前时间所在月第一天时间对象
		cal.set(Calendar.DAY_OF_MONTH, 1);
		// 日期减一,取得上月最后一天时间对象
		cal.add(Calendar.DAY_OF_MONTH, -1);
		// 输出上月最后一天日期
		int day = cal.get(Calendar.DAY_OF_MONTH);

		String months = "";
		String days = "";

		if (month > 1) {
			month--;
		} else {
			year--;
			month = 12;
		}
		if (!(String.valueOf(month).length() > 1)) {
			months = "0" + month;
		} else {
			months = String.valueOf(month);
		}
		if (!(String.valueOf(day).length() > 1)) {
			days = "0" + day;
		} else {
			days = String.valueOf(day);
		}
		String firstDay = "" + year + "-" + months + "-01";
		String lastDay = "" + year + "-" + months + "-" + days;

		String[] lastMonth = new String[2];
		lastMonth[0] = firstDay;
		lastMonth[1] = lastDay;
		return lastMonth;
	}

	public static String nowDateToStr() {
		// SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmssSSS");
		Date date = new Date();
		return String.valueOf(date.getTime()); // sdf.format(date);
	}



	public static int getWeek4StrDate(String date) {
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(DateTimeUtils.strToDate(date, "yyyy-MM-dd"));
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
		int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if(week_index==0)
			week_index=7;
		return week_index;
	}

	public static String getMonday4Date() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		// 当前时间，貌似多余，其实是为了所有可能的系统一致  
		cal.setTimeInMillis(System.currentTimeMillis());
		if (cal.get(Calendar.DAY_OF_WEEK) - 1 == 0) {
			cal.add(Calendar.DATE, -7);
		}
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return sf.format(cal.getTime());
	}

	public static String getSunday4Date() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		// 当前时间，貌似多余，其实是为了所有可能的系统一致  
		cal.setTimeInMillis(System.currentTimeMillis());
		if (cal.get(Calendar.DAY_OF_WEEK) - 1 == 0) {
			cal.add(Calendar.DATE, -7);
		}
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.add(Calendar.DATE, 6);
		return sf.format(cal.getTime());
	}
	
	/*
	 * 两个时间段内所有时间
	 * 返回值<'yyyy-MM-dd','yyyy年MM月dd日'>
	 */
	public static Map<String,String> getComparedate(String date1,String date2){
		Map<String,String> slist = new LinkedHashMap<String,String>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日"); 
			Date   begin=sdf.parse(date1);      
			Date   end=sdf.parse(date2);      
			double   between=(end.getTime()-begin.getTime())/1000;//除以1000是为了转换成秒      
			double  day=between/(24*3600);
			for(int i = 0;i<day;i++){
				Calendar cd = Calendar.getInstance();   
			    cd.setTime(sdf.parse(date1));   
			    cd.add(Calendar.DATE, i);//增加一天   
			    slist.put(sdf.format(cd.getTime()), sdf2.format(cd.getTime()));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return slist;
	}
	/*
	 * 获取当天0点的时间
	 */
	public static String getStartTime(){  
        Calendar todayStart = Calendar.getInstance();  
        todayStart.set(Calendar.HOUR, 0);  
        todayStart.set(Calendar.MINUTE, 0);  
        todayStart.set(Calendar.SECOND, 0);  
        todayStart.set(Calendar.MILLISECOND, 0);  
        return todayStart.getTime().getTime()+"";  
    }  
	
	public static List<String> getyear(String year){
		List<String> years = new ArrayList<String>();
		String nowyear = getDate("yyyy");
		for(int i = Integer.parseInt(nowyear);i>=Integer.parseInt(year);i--){
			years.add(i+"");
		}
		return years;
	}
	
 }
