/**
 * 
 */
package ru.prbb;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author RBr
 *
 */
public class UtilsTest extends Assert {

	/**
	 * Test method for {@link ru.prbb.Utils#isEmpty(java.lang.String)}.
	 */
	@Test
	public void testIsEmptyString() {
		assertEquals(true, Utils.isEmpty((String) null));
		assertEquals(true, Utils.isEmpty(""));
		assertEquals(true, Utils.isEmpty("   "));
		assertEquals(false, Utils.isEmpty(" x "));
	}

	/**
	 * Test method for {@link ru.prbb.Utils#isEmpty(java.lang.Number)}.
	 */
	@Test
	public void testIsEmptyNumber() {
		assertEquals(true, Utils.isEmpty((Number) null));
		assertEquals(true, Utils.isEmpty(1));
		assertEquals(true, Utils.isEmpty(12.3));
		assertEquals(false, Utils.isEmpty(0));
		assertEquals(false, Utils.isEmpty(0.0));
	}

	/**
	 * Test method for {@link ru.prbb.Utils#isNotEmpty(java.lang.String)}.
	 */
	@Test
	public void testIsNotEmpty() {
		assertEquals(false, Utils.isNotEmpty(null));
		assertEquals(false, Utils.isNotEmpty(""));
		assertEquals(false, Utils.isNotEmpty("   "));
		assertEquals(true, Utils.isNotEmpty(" x "));
	}

	/**
	 * Test method for {@link ru.prbb.Utils#parseString(java.lang.String)}.
	 */
	@Test
	public void testParseString() {
		assertEquals(null, Utils.parseString(null));
		assertEquals(null, Utils.parseString(""));
		assertEquals(null, Utils.parseString("   "));
		String value = " x ";
		assertEquals(value, Utils.parseString(value));
	}

	/**
	 * Test method for {@link ru.prbb.Utils#parseLong(java.lang.Long)}.
	 */
	@Test
	public void testParseLong() {
		assertEquals(null, Utils.parseLong(null));
		String value = "0";
		assertEquals(null, Utils.parseLong(new Long(value)));
		value = "1";
		assertEquals(new Long(value), Utils.parseLong(new Long(value)));
	}

	/**
	 * Test method for {@link ru.prbb.Utils#parseDate(java.lang.String)}.
	 */
	@Test
	public void testParseDate() {
		assertEquals(null, Utils.parseDate(null));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", new Locale("RU", "ru"));
		Date date = Calendar.getInstance().getTime();
		java.sql.Date expected = new java.sql.Date(date.getTime());
		java.sql.Date actual = Utils.parseDate(sdf.format(date));
		assertEquals(expected.toString(), actual.toString());
	}

	/**
	 * Test method for {@link ru.prbb.Utils#toString(java.lang.Object)}.
	 */
	@Test
	public void testToStringObject() {
		assertEquals(null, Utils.toString(null));
		String value = "10";
		assertEquals(value, Utils.toString(value));
	}

	/**
	 * Test method for {@link ru.prbb.Utils#toDouble(java.lang.Object)}.
	 */
	@Test
	public void testToDouble() {
		assertEquals(null, Utils.toDouble(null));
		String value = "10";
		assertEquals(new BigDecimal(value), Utils.toDouble(new BigDecimal(value)));
	}

	/**
	 * Test method for {@link ru.prbb.Utils#toLong(java.lang.Object)}.
	 */
	@Test
	public void testToLong() {
		assertEquals(null, Utils.toLong(null));
		String value = "1";
		assertEquals(new Long(value), Utils.toLong(new BigDecimal(1)));
	}

	/**
	 * Test method for {@link ru.prbb.Utils#toInteger(java.lang.Object)}.
	 */
	@Test
	public void testToInteger() {
		assertEquals(null, Utils.toInteger(null));
		String value = "1";
		assertEquals(new Integer(value), Utils.toInteger(new Integer(value)));
	}

	/**
	 * Test method for {@link ru.prbb.Utils#toByte(java.lang.Object)}.
	 */
	@Test
	public void testToByte() {
		assertEquals(null, Utils.toByte(null));
		String value = "1";
		assertEquals(new Byte(value), Utils.toByte(new Byte(value)));
	}

	/**
	 * Test method for {@link ru.prbb.Utils#toTimestamp(java.lang.Object)}.
	 */
	@Test
	public void testToTimestamp() {
		assertEquals(null, Utils.toTimestamp(null));
		String value = "1387266506083";
		assertEquals(value, Utils.toTimestamp(value));
	}

	/**
	 * Test method for {@link ru.prbb.Utils#toDate(java.lang.Object)}.
	 */
	@Test
	public void testToDate() {
		assertEquals(null, Utils.toDate(null));
		String value = "2012-12-12";
		assertEquals(value, Utils.toDate(value));
	}

	/**
	 * Test method for {@link ru.prbb.Utils#toTime(java.lang.Object)}.
	 */
	@Test
	public void testToTime() {
		assertEquals(null, Utils.toTime(null));
		String value = "12:12:12";
		assertEquals(value, Utils.toTime(value));
	}

}
