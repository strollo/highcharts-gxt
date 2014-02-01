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
 * Filename: SectionChart.java
 ****************************************************************************
 * @author <a href="mailto:daniele.strollo@gmail.com">Daniele Strollo</a>
 ***************************************************************************/

package org.gxt.adapters.highcharts.codegen.sections;

import java.util.List;
import java.util.Vector;

import org.gxt.adapters.highcharts.codegen.sections.options.AvailableSectionOptions;
import org.gxt.adapters.highcharts.codegen.types.HighChartJS;
import org.gxt.adapters.highcharts.codegen.utils.StringUtils;

/**
 * @author Daniele Strollo
 *
 */
public final class SectionChart extends Section {
	public SectionChart(final HighChartJS parent) {
		super(parent);
	}

	public SectionChart(final HighChartJS parent, final boolean insertDefault) {
		super(parent, insertDefault);
	}

	@Override
	public SectionKeys getSectionKey() {
		return Section.SectionKeys.SEC_CHART;
	}

	@Override
	public String getJS() {
		List<AvailableSectionOptions> options = this.getOptions();

		StringBuilder retval = new StringBuilder();
		retval.append(this.getSectionName() + ": {");

		// Inserts the events for registering the chart
		// in order to handle resize events.
		retval.append(
				"renderTo: '" + this.getHighChart().getDivId() + "', " +
				"events: { " +
					"load: function registerMe() { " +
					"this.id = '" + this.getHighChart().getJSChartName() + "'; " +
					"$wnd.registerChart(this, true); " +
				"} " +
		"}");

		if (options.size() == 0) {
			// end of section chart block
			retval.append("}");
			return retval.toString();
		}

		if (options != null && options.size() > 0) {
			retval.append(",");
			// The options
			List<String> entries = new Vector<String>();
			for (AvailableSectionOptions option : options) {
				entries.add(option.getOption().getJS());
			}
			retval.append(StringUtils.join(entries, ", "));
		}

		// the Raw options
		List<String> rawOptions = this.getRawOptions();
		if (rawOptions.size() > 0) {
			retval.append(",");
			List<String> entries = new Vector<String>();
			for (String option : rawOptions) {
				entries.add(option);
			}
			retval.append(StringUtils.join(entries, ", "));
		}

		retval.append("}");
		return retval.toString();
	}

}
