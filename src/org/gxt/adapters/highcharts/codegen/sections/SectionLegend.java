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
 * Filename: SectionLegend.java
 ****************************************************************************
 * @author <a href="mailto:daniele.strollo@gmail.com">Daniele Strollo</a>
 ***************************************************************************/
package org.gxt.adapters.highcharts.codegen.sections;

import org.gxt.adapters.highcharts.codegen.types.HighChartJS;

/**
 * @author Daniele Strollo
 *
 */
public final class SectionLegend extends Section {

	public SectionLegend(final HighChartJS parent, final boolean insertDefault) {
		super(parent, insertDefault);
	}

	public SectionLegend(final HighChartJS parent) {
		super(parent);
	}

	@Override
	public SectionKeys getSectionKey() {
		return Section.SectionKeys.SEC_LEGEND;
	}
}
