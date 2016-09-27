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
 * Filename: RawStringType.java
 ****************************************************************************
 * @author <a href="mailto:daniele.strollo@gmail.com">Daniele Strollo</a>
 ***************************************************************************/

package org.gxt.adapters.highcharts.codegen.sections.options.types;

/**
 * A raw string is used for options which string representation
 * must not be quoted.
 * For example it is useful for expressing functions to append
 * as handlers (e.g. formatter).
 * @author Daniele Strollo
 *
 */
public class RawStringType {
	private String value = null;

	public RawStringType(final String value) {
		if (value != null && value.length() > 0) {
			this.value = value.trim();
		}
	}

	@Override
	public String toString() {
		return value;
	}
}
