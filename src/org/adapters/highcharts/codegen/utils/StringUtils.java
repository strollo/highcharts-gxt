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
 * Filename: StringUtils.java
 ****************************************************************************
 * @author <a href="mailto:daniele.strollo@gmail.com">Daniele Strollo</a>
 ***************************************************************************/

package org.adapters.highcharts.codegen.utils;

import java.util.Iterator;

/**
 * General purpose utils on strings.
 * @author Daniele Strollo
 */
public class StringUtils {
	public static final String NEW_LINE = "\n";
	public static final String TAB_CHAR = "\t";
	/// The character separating parameters
	public static final String PARAM_SEPT = ",";
	/// The character for separating blocks
	public static final String BLOCK_END = "}";

	public static final String join(final Iterable< ? extends Object > params, final String delimiter) {
		return join(params, delimiter, true);
	}

	public static final String join(final Iterable< ? extends Object > params, final String delimiter, final boolean avoidNull)
	{
		if (params == null || params.iterator() == null) {
			return null;
		}
		Iterator< ? extends Object > oIter = params.iterator();
		if (params == null || (!oIter.hasNext())) {
			return "";
		}
		StringBuilder oBuilder = new StringBuilder();
		while (oIter.hasNext()) {
			Object elem = oIter.next();
			if (elem != null || !avoidNull) {
				// first elem
				if (oBuilder.length() == 0) {
					oBuilder.append(elem);
				} else {
					// others
					oBuilder.append(delimiter).append(elem);
				}
			}
		}
		return oBuilder.toString();
	}
	
	/**
	 * Removes redundant separators.
	 * @return
	 */
	public static synchronized final String getCleanJS(final String js) {
		final StringBuilder retval = new StringBuilder(js);

		try {
			int from = -1;
			int to = 0;
			while (true) {
				from = retval.indexOf(StringUtils.PARAM_SEPT, from + 1);
				to = retval.indexOf(StringUtils.BLOCK_END, from);

				if (from < 0 || to < 0) {
					break;
				}

				String tmp = retval.substring(from + 1, to);
				if (tmp != null && tmp.trim().length() == 0) {
					retval.replace(from, to, "");
				}
			}
		} catch (Exception e) {
			ClientConsole.err("cannot format JS", e);
			return js;
		}

		return retval.toString();
	}
}
