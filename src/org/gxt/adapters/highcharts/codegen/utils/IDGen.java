/****************************************************************************
 *  This software is part of the HighchartsJS adapters for Ext GWT.
 ****************************************************************************
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this library.  If not, see <http://www.gnu.org/licenses/>.
 *
 ****************************************************************************
 * Filename: IDGen.java
 ****************************************************************************
 * @author <a href="mailto:daniele.strollo@gmail.com">Daniele Strollo</a>
 ***************************************************************************/

package org.gxt.adapters.highcharts.codegen.utils;

/**
 * @author Daniele Strollo
 *
 */
public class IDGen {
	public static final String generateID(final int size) {
		char[] s = new char[size];
		String itoh = "0123456789abcdef";

		// Make array of random hex digits. The UUID only has 32 digits in it, but we
		// allocate an extra items to make room for the '-'s we'll be inserting.
		for (int i = 0; i < size; i++) {
			s[i] = (char) Math.floor(Math.random() * 0x10);
		}

		// Conform to RFC-4122, section 4.4
		s[14 % size] = 4;  // Set 4 high bits of time_high field to version
		s[19 % size] = (char) ((s[19 % size] & 0x3) | 0x8);  // Specify 2 high bits of clock sequence

		// Convert to hex chars
		for (int i = 0; i <  size; i++) {
			s[i] = itoh.charAt((int) s[i]);
		}
		return new String(s);
	}

}
