/**
 * Created Mar 10, 2008
 */
package com.crawljax.util;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crawljax.oraclecomparator.comparators.EditDistanceComparator;

/**
 * Test the edit distance algorithm.
 */
public class EditDistanceTest extends TestCase {

	private static final Logger LOG = LoggerFactory.getLogger(EditDistanceTest.class);
	private EditDistanceComparator editDistComparator = new EditDistanceComparator();

	/**
	 * Check if threshold calculation works.
	 */
	public void testGetThreshold() {
		String x = "<form>bl</form>";
		String y = "<form>blabla</form>";
		double p = 0.8;

		double expected = (2 * Math.max(x.length(), y.length()) * (1 - p));

		assertEquals(expected, editDistComparator.getThreshold(x, y, p));
	}

	/**
	 * Check if clone detection algorithm works correctly.
	 */
	public void testIsClone() {
		String x = "<form>BL</form>";
		String y = "<form>blabla</form>";

		LOG.debug(StringUtils.getLevenshteinDistance(x, y) + " Thesh: "
		        + editDistComparator.getThreshold(x, y, 0.7));
		assertTrue(editDistComparator.isClone(x, y, 0.0));
		assertTrue(editDistComparator.isClone(x, y, 0.5));
		assertTrue(editDistComparator.isClone(x, y, 0.7));
		assertTrue(editDistComparator.isClone(x, y, 0.75));
		assertTrue(editDistComparator.isClone(x, y, 0.84));
		assertFalse(editDistComparator.isClone(x, y, 0.89));
		assertFalse(editDistComparator.isClone(x, y, 0.893));
		assertFalse(editDistComparator.isClone(x, y, 0.9));
		assertFalse(editDistComparator.isClone(x, y, 1));

		boolean arg = false;

		try {
			editDistComparator.isClone(x, y, -2);
		} catch (IllegalArgumentException e) {
			arg = true;
		}

		assertTrue(arg);

		arg = false;

		try {
			editDistComparator.isClone(x, y, 2);
		} catch (IllegalArgumentException e) {
			arg = true;
		}

		assertTrue(arg);
	}
}
