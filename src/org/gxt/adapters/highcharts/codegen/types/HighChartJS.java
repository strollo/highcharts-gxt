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
 * Filename: HighChartJS.java
 ****************************************************************************
 * @author <a href="mailto:daniele.strollo@gmail.com">Daniele Strollo</a>
 ***************************************************************************/

package org.gxt.adapters.highcharts.codegen.types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.gxt.adapters.highcharts.codegen.sections.Section;
import org.gxt.adapters.highcharts.codegen.sections.Section.SectionKeys;
import org.gxt.adapters.highcharts.codegen.sections.SectionChart;
import org.gxt.adapters.highcharts.codegen.sections.SectionCredits;
import org.gxt.adapters.highcharts.codegen.sections.SectionLegend;
import org.gxt.adapters.highcharts.codegen.sections.SectionLoading;
import org.gxt.adapters.highcharts.codegen.sections.SectionPlotOptions;
import org.gxt.adapters.highcharts.codegen.sections.SectionSubTitle;
import org.gxt.adapters.highcharts.codegen.sections.SectionTitle;
import org.gxt.adapters.highcharts.codegen.sections.SectionTooltip;
import org.gxt.adapters.highcharts.codegen.sections.SectionXAxis;
import org.gxt.adapters.highcharts.codegen.sections.SectionYAxis;
import org.gxt.adapters.highcharts.codegen.utils.StringUtils;

/**
 * Artifact for wrapping highcharts javascript objects inside java objects.
 * Through this class it is possible to generate the javascript code that
 * will represent the rendered highchart.
 * @author Daniele Strollo
 */
public class HighChartJS {
	private Map<SectionKeys, Section> sections = new HashMap<SectionKeys, Section>();
	private String id = null;
	private List<SeriesType> seriesList = new Vector<SeriesType>();
	private String currDivId = null;
	private int widthOffset = -1;
	private int heightOffset = -1;

	public HighChartJS(final String id) {
		if (id != null) {
			this.id = id.trim();
		}

		this.initSections();
	}

	private void initSections() {
		// init the sections
		/* not usable in GWT since getConstructor is not mapped.
		for (SectionKeys section : SectionKeys.values()) {
			try {
				Section toAdd = section.getSectionClass().getConstructor(HighChartJS.class).newInstance(this);
				sections.put(toAdd.getSectionKey(), toAdd);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		 */

		Section section = new SectionChart(this);
		sections.put(section.getSectionKey(), section);

		section = new SectionCredits(this);
		sections.put(section.getSectionKey(), section);

		section = new SectionLegend(this);
		sections.put(section.getSectionKey(), section);

		section = new SectionLoading(this);
		sections.put(section.getSectionKey(), section);

		section = new SectionSubTitle(this);
		sections.put(section.getSectionKey(), section);

		section = new SectionTitle(this);
		sections.put(section.getSectionKey(), section);

		section = new SectionTooltip(this);
		sections.put(section.getSectionKey(), section);

		section = new SectionXAxis(this);
		sections.put(section.getSectionKey(), section);

		section = new SectionYAxis(this);
		sections.put(section.getSectionKey(), section);

		section = new SectionPlotOptions(this);
		sections.put(section.getSectionKey(), section);
	}

	public final Section getSection(final SectionKeys key) {
		if (this.sections.containsKey(key)) {
			// FIXME concurrency not handled
			return this.sections.get(key);
		}

		return null;
	}

	public final void addSeries(final SeriesType series) {
		if (series != null) {
			this.seriesList.add(series);
		}
	}

	public final String getJS() {
		StringBuilder retval = new StringBuilder();
		retval.append("<script type=\"text/javascript\">" + StringUtils.NEW_LINE);
		retval.append("var " + this.getJSChartName() + " = new $wnd.Highcharts.Chart({" + StringUtils.NEW_LINE);

		// The sections
		List<String> entries = new Vector<String>();
		for (Section section : sections.values()) {
			entries.add(section.getJS());
		}
		retval.append(StringUtils.join(entries, ", " + StringUtils.NEW_LINE));
		// End of block
		retval.append("," + StringUtils.NEW_LINE);

		if (this.seriesList != null && this.seriesList.size() > 0) {
			// The series entries
			entries = new Vector<String>();
			for (SeriesType series : this.seriesList) {
				entries.add(series.getJS());
			}
			retval.append("series: [" + StringUtils.NEW_LINE +
					StringUtils.join(entries, ", " + StringUtils.NEW_LINE) +
					StringUtils.NEW_LINE + "],");
			retval.append(StringUtils.NEW_LINE);
		}

		retval.append("});");
		retval.append(StringUtils.NEW_LINE + "</script>");
		return retval.toString();
	}

	public final String getId() {
		return this.id;
	}

	public final String getJSChartName() {
		return "chart" + this.id;
	}

	public final void setDivId(final String containerDiv) {
		this.currDivId = containerDiv;
	}

	public final String getDivId() {
		return this.currDivId;
	}

	public final void doRender() {
		this.injectJS(this.currDivId, this.getJS());
		this.doResize();
	}

	public final void doResize() {
		this.resize(this.getJSChartName(), this.widthOffset, this.heightOffset);
	}

	public final void setUseParent(final boolean useParent) {
		this.setUseParent(this.getJSChartName(), useParent);
	}

	/**
	 * The id of the container responsible to handle the resize of this
	 * component.
	 * @param chartId
	 * @param widthOffset
	 * @param heightOffset
	 */
	private native void setUseParent(final String chartId, final boolean useParent) /*-{
		$wnd.setUseParent(chartId, useParent);
	}-*/;

	private native void resize(final String chartId, final int widthOffset, final int heightOffset) /*-{
		$wnd.resizeChart(chartId, widthOffset, heightOffset);
	}-*/;

	public final void setWidthOffset(final int widthOffset) {
		if (widthOffset >= 0) {
			this.widthOffset = widthOffset;
		}
	}

	public final void setHeightOffset(final int heightOffset) {
		if (heightOffset >= 0) {
			this.heightOffset = heightOffset;
		}
	}

	/*
	 * Used internally on render phase.
	 */
	private native void injectJS(final String divID, final String jsCode) /*-{
		var theContainer = $doc.getElementById(divID);
		if (theContainer != null && jsCode != null) {
			theContainer.innerHTML = jsCode;

			var x = theContainer.getElementsByTagName("script");
			for(var i=0; i < x.length; i++) {
				eval(x[i].text);
			}
		} else {
			alert('div: ' + divID + ' not found ');
		}
	}-*/;
}
