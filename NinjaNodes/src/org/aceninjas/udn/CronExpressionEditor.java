/**
 *    Copyright (C) 2024 ACE Ninjas <aceninjas.org@gmail.com>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.aceninjas.udn;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import com.ibm.etools.mft.api.AbstractPropertyEditor;
import com.ibm.etools.mft.api.IPropertyEditor;

public class CronExpressionEditor extends AbstractPropertyEditor implements
		IPropertyEditor {

	protected Color blackColor = new Color(Display.getCurrent(), 0, 0, 0);
	protected Color redColor = new Color(Display.getCurrent(), 255, 0, 0);

	private String cronExpression;

	private Text secondText;
	private Text minuteText;
	private Text hourText;
	private Text dateText;
	private Text monthText;
	private Text dayofWeekText;
	private Text yearText;

	private Label validationLabel;

	public void createControls(Composite parent) {

		Label propertyNameLabel = new Label(parent, SWT.NONE | SWT.FILL);
		propertyNameLabel.setText("cron expression");

		Composite editorArea = new Composite(parent, SWT.NONE | SWT.FILL);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		editorArea.setLayoutData(gridData);

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 7;

		editorArea.setLayout(gridLayout);

		Label secondLabel = new Label(editorArea, SWT.NONE | SWT.FILL);
		secondLabel.setText("Seconds");
		Label minuteLabel = new Label(editorArea, SWT.NONE | SWT.FILL);
		minuteLabel.setText("Minutes");
		Label hourLabel = new Label(editorArea, SWT.NONE | SWT.FILL);
		hourLabel.setText("Hours");
		Label dateLabel = new Label(editorArea, SWT.NONE | SWT.FILL);
		dateLabel.setText("Day of month");
		Label monthLabel = new Label(editorArea, SWT.NONE | SWT.FILL);
		monthLabel.setText("Month");
		Label dayofWeekLabel = new Label(editorArea, SWT.NONE | SWT.FILL);
		dayofWeekLabel.setText("Day of week");
		Label yearLabel = new Label(editorArea, SWT.NONE | SWT.FILL);
		yearLabel.setText("Year");

		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;

		secondText = new Text(editorArea, SWT.SINGLE | SWT.BORDER);
		secondText.setLayoutData(gridData);

		minuteText = new Text(editorArea, SWT.SINGLE | SWT.BORDER);
		minuteText.setLayoutData(gridData);

		hourText = new Text(editorArea, SWT.SINGLE | SWT.BORDER);
		hourText.setLayoutData(gridData);

		dateText = new Text(editorArea, SWT.SINGLE | SWT.BORDER);
		dateText.setLayoutData(gridData);

		monthText = new Text(editorArea, SWT.SINGLE | SWT.BORDER);
		monthText.setLayoutData(gridData);

		dayofWeekText = new Text(editorArea, SWT.SINGLE | SWT.BORDER);
		dayofWeekText.setLayoutData(gridData);

		yearText = new Text(editorArea, SWT.SINGLE | SWT.BORDER);
		yearText.setLayoutData(gridData);
		if (cronExpression != null) {
			try {
				String[] tks = cronExpression.split(" ");
				secondText.setText(tks[0]);
				minuteText.setText(tks[1]);
				hourText.setText(tks[2]);
				dateText.setText(tks[3]);
				monthText.setText(tks[4]);
				dayofWeekText.setText(tks[5]);
				yearText.setText(tks[6]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		validationLabel = new Label(editorArea, SWT.NONE | SWT.FILL);
		gridData = new GridData();
		gridData.horizontalSpan = 7;
		gridData.grabExcessHorizontalSpace = true;
		gridData.minimumWidth = 300;
		validationLabel.setLayoutData(gridData);
		isValid();

		ModifyListener modifyListener = new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {

				setChanged();
				notifyObservers();
			}
		};
		secondText.addModifyListener(modifyListener);
		minuteText.addModifyListener(modifyListener);
		hourText.addModifyListener(modifyListener);
		dateText.addModifyListener(modifyListener);
		monthText.addModifyListener(modifyListener);
		dayofWeekText.addModifyListener(modifyListener);
		yearText.addModifyListener(modifyListener);

	}

	public Object getValue() {
		return (StringUtils.isBlank(secondText.getText()) ? "*" : secondText
				.getText())
				+ " "
				+ (StringUtils.isBlank(minuteText.getText()) ? "*" : minuteText
						.getText())
				+ " "
				+ (StringUtils.isBlank(hourText.getText()) ? "*" : hourText
						.getText())
				+ " "
				+ (StringUtils.isBlank(dateText.getText()) ? "*" : dateText
						.getText())
				+ " "
				+ (StringUtils.isBlank(monthText.getText()) ? "*" : monthText
						.getText())
				+ " "
				+ (StringUtils.isBlank(dayofWeekText.getText()) ? "*"
						: dayofWeekText.getText())
				+ " "
				+ (StringUtils.isBlank(yearText.getText()) ? "*" : yearText
						.getText());
	}

	public String isValid() {
		try {
			Object o = getValue();
			if (o != null && StringUtils.isNotEmpty(o.toString())) {
				cronExpression = (String) getValue();
			}
			CronDefinition cronDefinition = CronDefinitionBuilder
					.instanceDefinitionFor(CronType.QUARTZ);
			CronParser parser = new CronParser(cronDefinition);
			Cron quartzCron = parser.parse(cronExpression);
			CronDescriptor descriptor = CronDescriptor.instance(Locale.US);
			String desc = descriptor.describe(quartzCron);
			validationLabel.setForeground(blackColor);
			validationLabel.setText("Runs " + desc);
			return null;
		} catch (Exception e) {
			validationLabel.setForeground(redColor);
			validationLabel.setText("Invalid cron expression");
			return "Invalid cron expression";
		}
	}

	public void setCurrentValue(Object value) {

		if (value != null && value.toString().trim().length() > 0) {
			cronExpression = value.toString();
		} else {
			cronExpression = "1/5 * * * * * *";
		}
	}

	public void setDefaultValue(Object value) {
		cronExpression = value.toString();
	}

}
