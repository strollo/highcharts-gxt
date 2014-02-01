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
 * Filename: SectionXAxis.java
 ****************************************************************************
 * @author <a href="mailto:daniele.strollo@gmail.com">Daniele Strollo</a>
 ***************************************************************************/

package org.gxt.adapters.highcharts.codegen.sections;

import org.gxt.adapters.highcharts.codegen.types.HighChartJS;

/**
 * @author Daniele Strollo
 *
 */
public class SectionXAxis extends Section {

	public SectionXAxis(final HighChartJS parent, final boolean insertDefault) {
		super(parent, insertDefault);
	}

	public SectionXAxis(final HighChartJS parent) {
		super(parent);
	}

	/* (non-Javadoc)
	 * @see org.gxt.adapters.highcharts.sections.Section#getSectionKey()
	 */
	@Override
	public final SectionKeys getSectionKey() {
		return Section.SectionKeys.SEC_XAXIS;
	}

	public final void setTitle(final String title) {
		if (title != null && title.trim().length() > 0) {
			this.addRawOption("title: {rotation: 0, text: '" + title.trim() + "', align: 'middle', enabled: 'middle' }");
		}
	}

}
