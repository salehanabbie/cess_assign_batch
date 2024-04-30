package com.bsva.utils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

public class DateUtil implements Serializable
{

	public static Logger log = Logger.getLogger(DateUtil.class);
	public DateUtil()
	{

	}

	public boolean validateDate(String creationDate, String dateFormat)
	{
		if (creationDate == null)
		{
			return false;
		}
		else
		{
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			try
			{
				Date date = sdf.parse(creationDate);
				return true;
			}
			catch (ParseException pe)
			{
				return false;
			}
		}
	}

	public boolean validateIfISFutureDate(Date dateToBeVal)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try
		{
			String formatDate = sdf.format(dateToBeVal);
			Date formatteDateToBeVal = sdf.parse(formatDate);
			System.out.println("dateToBeVal=============================="+formatDate);
			String formatCurrDate = sdf.format(new Date());
			Date currentDate = sdf.parse(formatCurrDate);
			log.info("validDate: "+ formatDate);
			if (currentDate.before(formatteDateToBeVal))
				return true;
			else
				return false;
		}
		catch (ParseException pe)
		{
			return false;
		}
	}

	public boolean validateFirstCollDateIsGreaterThan3Days(Date firstCollDate, Date loadDate)
	{
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(loadDate);
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 3);
		java.util.Date threeDaysFromLoad = cal.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try
		{
			String firstcdate = sdf.format(firstCollDate);
			Date formattedFirstCollDate = sdf.parse(firstcdate);

			String threedate = sdf.format(threeDaysFromLoad);
			Date formatted3LoadedDate = sdf.parse(threedate);

			log.info("First Coll Date: "+ firstcdate);
			log.info("Load Date: "+ loadDate);
			log.info("threedate: "+ threedate);

			if (formattedFirstCollDate.before(formatted3LoadedDate))
				return false;
			else
				return true;
		}
		catch (ParseException pe)
		{
			return false;
		}
	}

	public boolean validateFirstDateIsBeforeFromDate(Date firstDate, Date fromDate)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try
		{
			String strFirstDate = sdf.format(firstDate);
			Date formatFirstDate = sdf.parse(strFirstDate);

			String strFromDate = sdf.format(fromDate);
			Date formatFromDate = sdf.parse(strFromDate);
			log.debug("formatFirstDate: "+formatFirstDate);
			log.debug("formatFromDate: "+formatFromDate);

			if (formatFirstDate.equals(formatFromDate))
			{
				log.debug("equals....returning true");
				return true;
			}
			else
			{
				if(formatFirstDate.after(formatFromDate))
				{
					log.debug("date is after....returning true");
					return true;
				}
				else
				{
					log.debug("date is NOT EQUAL OR AFTER....returning FALSE");
					return false;
				}
			}
		}
		catch (ParseException pe)
		{
			log.error("ParseException: "+pe.getMessage());
			return false;
		}
	}


	public boolean validateFinalCollDateBetweenFromAndToDate(Date finalDate, Date fromDate, Date toDate)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try
		{
			String strFinalDate = sdf.format(finalDate);
			Date formatFinalDate = sdf.parse(strFinalDate);

			String strFromDate = sdf.format(fromDate);
			Date formatFromDate = sdf.parse(strFromDate);

			String strToDate = sdf.format(toDate);
			Date formatToDate = sdf.parse(strToDate);

			if((finalDate.equals(toDate) || finalDate.before(toDate)) &&(finalDate.equals(formatFromDate) || finalDate.after(formatFromDate)))
				return true;
			else
				return false;
		}
		catch (ParseException pe)
		{
			return false;
		}
	}
	/**
	 *
	 * @param date
	 * @param dateFormat
	 * @return
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String date, String dateFormat )throws ParseException{
		SimpleDateFormat dateFormatter = new SimpleDateFormat( dateFormat );
		return  dateFormatter.parse(date);
	}
	/**
	 *
	 * @param date
	 *
	 *
	 * @param dateFormat
	 * @return
	 */
	public static String formatStringDate( String date, String dateFormat ){
		SimpleDateFormat dateFormatter = new SimpleDateFormat( dateFormat );
		return dateFormatter.format( date );
	}
	/**
	 *
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static String convertDateToString( Date date, String dateFormat ){
		SimpleDateFormat dateFormatter = new SimpleDateFormat( dateFormat );
		return dateFormatter.format( date.getTime() );
	}

	public static Date parseCurDate(String dateFormat) throws Exception{
		SimpleDateFormat dateFormatter = new SimpleDateFormat( dateFormat );
		String stringDate = dateFormatter.format(new Date());
		try {
			return  dateFormatter.parse(stringDate);
		} catch (ParseException e) {
			throw new Exception(e.getMessage());
		}catch(Exception ex){
			throw new Exception(ex.getMessage());
		}

	}

	public static Date passDateWithFormat(Date date, String dateFormat) throws ParseException{
		SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
		String dateString = dateFormatter.format(date);
		return dateFormatter.parse(dateString);
	}

	public static XMLGregorianCalendar toXMLGregorianCalendar(Date date){
		GregorianCalendar gCalendar = new GregorianCalendar();
		gCalendar.setTime(date);
		XMLGregorianCalendar xmlCalendar = null;
		try {
			xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
		} catch (Exception ex) {
			//Logger.getLogger(StringReplace.class.getName()).log(Level.SEVERE, null, ex);
			ex.printStackTrace();
		}
		return xmlCalendar;
	}

	public static XMLGregorianCalendar toXMLGregorianCalendarWithFormat(Date date, String dateFormat){
		SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
		// GregorianCalendar gCalendar = new GregorianCalendar();
		String convertedDate = dateFormatter.format(date);
		//gCalendar.setTime(date);
		XMLGregorianCalendar xmlCalendar = null;
		try {
			xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(convertedDate);
		} catch (Exception ex) {
			//Logger.getLogger(StringReplace.class.getName()).log(Level.SEVERE, null, ex);
			ex.printStackTrace();
		}
		return xmlCalendar;
	}

	public static Date toDate(XMLGregorianCalendar calendar){
		if(calendar == null) {
			return null;
		}
		return calendar.toGregorianCalendar().getTime();
	}

	public static Date getDate(){
		return Calendar.getInstance().getTime();
	}
	/**
	 *
	 * @param month
	 * @return
	 */
	public static String getMonthFullname( int month ){
		String monthFullname = null;
		//not use zero index
//        month 				+= 1;
		month 	= (month==0)?month+1:month;

		switch (month) {
			case 1: monthFullname  = "January"; break;
			case 2: monthFullname  = "February"; break;
			case 3: monthFullname  = "March"; break;
			case 4: monthFullname  = "April"; break;
			case 5: monthFullname  = "May"; break;
			case 6: monthFullname  = "June"; break;
			case 7: monthFullname  = "July"; break;
			case 8: monthFullname  = "August"; break;
			case 9: monthFullname  = "September"; break;
			case 10: monthFullname = "October"; break;
			case 11: monthFullname = "November"; break;
			case 12: monthFullname = "December"; break;
			default: break;
		}
		return monthFullname;
	}
	/**
	 *
	 * @param month
	 * @return
	 */
	public static String getMonthFullname(int month, boolean actualValue){
		String monthFullname = null;

		if(!actualValue)
		{
			//not use zero index
			month 				+= 1;
		}

		switch (month) {
			case 1: monthFullname  = "January"; break;
			case 2: monthFullname  = "February"; break;
			case 3: monthFullname  = "March"; break;
			case 4: monthFullname  = "April"; break;
			case 5: monthFullname  = "May"; break;
			case 6: monthFullname  = "June"; break;
			case 7: monthFullname  = "July"; break;
			case 8: monthFullname  = "August"; break;
			case 9: monthFullname  = "September"; break;
			case 10: monthFullname = "October"; break;
			case 11: monthFullname = "November"; break;
			case 12: monthFullname = "December"; break;
			default: break;
		}
		return monthFullname;
	}
	/**
	 *
	 * @param day
	 * @return
	 */
	public static String getDayAbbrevaitedName( int day ){
		String dayAbbrevatedName = "";
		//not use zero index
		day 				= (day==0)?day+1:day;

		switch (day) {
			case 1:  dayAbbrevatedName = "SUN"; break;
			case 2:  dayAbbrevatedName = "MON"; break;
			case 3:  dayAbbrevatedName = "TUE"; break;
			case 4:  dayAbbrevatedName = "WED"; break;
			case 5:  dayAbbrevatedName = "THU"; break;
			case 6:  dayAbbrevatedName = "FRI"; break;
			case 7:  dayAbbrevatedName = "SAT"; break;
			default: dayAbbrevatedName = "Invalid day.";break;
		}
		return dayAbbrevatedName;
	}
	/**
	 *
	 * @param day
	 * @return
	 */
	public static String getDayFullname( int day ){
		String dayAbbrevatedName = "";
		//not use zero index
		day 				= (day==0)?day+1:day;

		switch (day) {
			case 1:  dayAbbrevatedName = "SUNDAY"; 		break;
			case 2:  dayAbbrevatedName = "MONDAY"; 		break;
			case 3:  dayAbbrevatedName = "TUESDAY"; 	break;
			case 4:  dayAbbrevatedName = "WEDNESDAY"; 	break;
			case 5:  dayAbbrevatedName = "THURSDAY"; 	break;
			case 6:  dayAbbrevatedName = "FRIDAT"; 		break;
			case 7:  dayAbbrevatedName = "SATURDAY"; 	break;
			default: dayAbbrevatedName = "Invalid day.";break;
		}
		return dayAbbrevatedName;
	}

	public static int getDay( Date date ){
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);

		return calender.get( Calendar.DATE );
	}

	public static int getMonth( Date date ){
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);

		return calender.get( Calendar.MONTH );
	}

	public static int getYear( Date date ){
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);

		return calender.get( Calendar.YEAR );
	}

	public static Long milisecondsToHours( Long miliseconds ){
		return (miliseconds !=null)? miliseconds / (1000*3600): miliseconds;
	}

	public static Long milisecondsToMinutes( Long miliseconds ){

		return (miliseconds !=null)? miliseconds / (1000*60): miliseconds;
	}



}