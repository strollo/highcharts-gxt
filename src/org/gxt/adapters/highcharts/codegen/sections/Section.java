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
 * Filename: Section.java
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
 * A section corresponds to blocks inside the chart.
 * To each block a set of options can be assigned.
 * Options can be assigned in two ways:
 * <p>
 * <b>Either by using a factory approach:</b>
 * </p>
 * <pre>
 * hc.getSection(SectionKeys.SEC_TITLE).addOption(AvailableSectionOptions.SEC_TITLE_TEXT, "My first chart");
 * </pre>
 * <b>or by setting raw options</b>
 * <pre>
 * hc.getSection(SectionKeys.SEC_TITLE).addRawOption("text: 'My first chart'");
 * </pre>
 * The instructions above have the same effect. In the first case the addOption method
 * will constraint the right application of the option to the right section.
 * <br/>
 * For example an <b>exception</b> will be thrown if using the following line:
 * <pre>
 * <i>// option *not* compatible with the chart section</i>
 * hc.getSection(SectionKeys.<i>SEC_CHART</i>).addOption(AvailableSectionOptions.SEC_TITLE_TEXT, "My first chart");
 * </pre>
 * that is because the option SEC_TITLE_TEXT is intended to be used only inside
 * section title (i.e. and not chart).
 *
 * <p>
 * The usage of raw options is instead not checked and is demanded to the
 * developer the responsibility to check it is rightly used.
 * But they can be used for options not mapped in the AvailableSectionOptions list
 * (e.g. for options of section plotOptions).
 * The raw options are intended as the raw string to be passed to the JS controller.
 * So they have both the attribute name and the string parameters are quoted.
 * </p>
 *
 * This section is abstract since the JS transformation can be differently expressed
 * for the implementing sections (e.g. the plotOptions will only accept rawOptions,
 * while chartSection will add code for resize handling of the chart container).
 *
 * Sections must not be instantiated, to manage them it is enough to use the method
 * {@link org.gxt.adapters.highcharts.widgets.HighChart#getSection(SectionKeys)}.
 * @author Daniele Strollo
 */
public abstract class Section {

	/**
	 * Keys for the sections factory.
	 * The key string is used at generation phase as tag name of
	 * the section block of the chart while the sectionClass is used to:
	 * <ul>
	 * <li>instantiate automatically the sections inside {@link HighChartJS}
	 * constructor;
	 * <li>check that the options added to a section are compatible with that section
	 * (see {@link Section#addOption(AvailableSectionOptions, Object)}.
	 * <ul>
	 * @author Daniele Strollo
	 *
	 */
	public static enum SectionKeys {
		SEC_CHART("chart", SectionChart.class),
		SEC_CREDITS("credits", SectionCredits.class),
		SEC_LEGEND("legend", SectionLegend.class),
		SEC_LOADING("loading", SectionLoading.class),
		SEC_SUBTITLE("subtitle", SectionSubTitle.class),
		SEC_TITLE("title", SectionTitle.class),
		SEC_TOOLTIP("tooltip", SectionTooltip.class),
		SEC_XAXIS("xAxis", SectionXAxis.class),
		SEC_YAXIS("yAxis", SectionYAxis.class),
		// accepts only raw options
		SEC_PLOT_OPTIONS("plotOptions", SectionPlotOptions.class);

		private String key = null;
		private Class<? extends Section> theClass = null;
		private SectionKeys(final String key, final Class<? extends Section> theClass) {
			this.key = key;
			this.theClass = theClass;
		}
		public Class<? extends Section> getSectionClass() {
			return this.theClass;
		}
		@Override
		public String toString() {
			return this.key;
		}
	}

	private List<AvailableSectionOptions> options = new Vector<AvailableSectionOptions>();
	private List<String> rawOptions = new Vector<String>();
	private HighChartJS parent = null;

	/**
	 * The parameter parent is the root of the overall js to be generated.
	 * It is needed for example inside the {@link SectionChart} for getting
	 * the assigned id of the chart in order to generate the registration
	 * for resize events of the window.
	 * @param parent the root chart that will contain this section.
	 */
	protected Section(final HighChartJS parent) {
		this(parent, false);
	}

	protected Section(final HighChartJS parent, final boolean insertDefault) {
		this.parent = parent;
		if (insertDefault) {
			// Put the default values
			for (AvailableSectionOptions option : AvailableSectionOptions.values()) {
				if (option.getSectionClass() == this.getClass() && option.getOption().getValue() != null) {
					try {
						this.addOption(option, option.getOption().getValue());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	protected final List<AvailableSectionOptions> getOptions() {
		return this.options;
	}

	protected final List<String> getRawOptions() {
		return this.rawOptions;
	}

	protected final HighChartJS getHighChart() {
		return this.parent;
	}

	/**
	 * Each option has a corresponding javascript code that will
	 * be replaced in the chart template at rendering phase.
	 * @return the corresponding javascript entry for the option.
	 */
	public String getJS() {
		if (this.options.size() == 0 && this.rawOptions.size() == 0) {
			return null;
		}

		StringBuilder retval = new StringBuilder();
		retval.append(this.getSectionName() + ": {");

		// The options
		List<String> entries = null;
		if (options.size() > 0) {
			entries = new Vector<String>();
			for (AvailableSectionOptions option : options) {
				entries.add(option.getOption().getJS());
			}
			retval.append(StringUtils.join(entries, ", "));
		}

		// the Raw options
		if (this.rawOptions.size() > 0) {
			if (options.size() > 0) { retval.append(","); }
			entries = new Vector<String>();
			for (String option : rawOptions) {
				entries.add(option);
			}
			retval.append(StringUtils.join(entries, ", "));
		}

		retval.append("}");
		return retval.toString();
	}


	public abstract SectionKeys getSectionKey();

	public final String getSectionName() {
		return this.getSectionKey().toString();
	}

	/**
	 * Allows to manually add an option to a section.
	 * This will allow the support of future (or actually not mapped) options.
	 * <p><b>Example:</b></p>
	 * <pre>
	 *  HighChart hc = ...;
	 *  hc.getSection(SectionKeys.SEC_CHART).addRawOption("title: 'thetitle'");
	 *  hc.getSection(SectionKeys.SEC_CHART).addRawOption("inverted: false);
	 * </pre>
	 * <p>
	 * <b>Notice:</b> these options will be appended at JS generation phase
	 * and will not be checked. So use them carefully.
	 * </p>
	 * @param optionLine
	 */
	public final void addRawOption(final String optionLine) {
		if (optionLine != null && optionLine.trim().length() > 0) {
			this.rawOptions.add(optionLine.trim());
		}
	}

	public final void addOption(final AvailableSectionOptions option, final Object value) throws Exception {
		if (option != null && value != null) {
			if (option.getSectionClass() != this.getClass()) {
				throw new Exception("The option parameter " + option.name() + " is not assignable to section: " + this.getSectionName());
			}
			option.getOption().setValue(value);
			this.options.add(option);
		}
	}
}
