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
 * Filename: SectionOption.java
 ****************************************************************************
 * @author <a href="mailto:daniele.strollo@gmail.com">Daniele Strollo</a>
 ***************************************************************************/

package org.gxt.adapters.highcharts.codegen.sections.options;

import java.util.List;
import java.util.Vector;
import org.gxt.adapters.highcharts.codegen.utils.StringUtils;

/**
 * The options that can be expressed in the charts are
 * represented by this class.
 * Inside {@link AvailableSectionOptions} you will find
 * the list of all available options that can be passed to
 * the different sections of the chart.
 * @author Daniele Strollo
 */
public final class SectionOption {
	private String name = null;
	private Class<?> type = null;
	private Object value = null;

	SectionOption(
			final String name,
			final Class<?> type,
			final Object value) {
		this.setName(name);
		this.type = type;
		this.value = value;
	}

	public void setName(final String name) {
		if (name != null && name.trim().length() > 0) {
			this.name = name;
		}
	}

	public void setOptClass(final Class<?> type) {
		this.type = type;
	}

	public Object getValue() {
		return this.value;
	}

	public void setValue(final Object value) throws Exception {
		if (value != null) {
			this.value = value;
		} else {
			throw new Exception(
					"Name: [" + this.name + "] " +
					"Type: [" + this.type + "]. " +
					"The passed value must be of type: " + type.getName() + " and cannot be null");
		}
	}

	/**
	 * The representation of the value in js.
	 * @return
	 */
	private String getValueRepr() {
		if (this.value == null) {
			return "";
		}
		if (this.value instanceof Number || this.value instanceof Boolean) {
			return this.value.toString();
		}
		if (this.value instanceof List) {
			StringBuffer retval = new StringBuffer();
			retval.append("[");
			List<String> elems = new Vector<String>();
			for (Object elem : (List<?>) this.value) {
				elems.add("'" + elem.toString() + "'");
			}
			retval.append(StringUtils.join(elems, ", "));
			retval.append("]");
			return retval.toString();
		}
		return "'" + this.value + "'";
	}

	/**
	 * Each option has a corresponding javascript code that will
	 * be replaced in the chart template at rendering phase.
	 * @return the corresponding javascript entry for the option.
	 */
	public String getJS() {
		if (this.value == null) {
			return "";
		}
		return this.name + ": " + getValueRepr();
	}
}
