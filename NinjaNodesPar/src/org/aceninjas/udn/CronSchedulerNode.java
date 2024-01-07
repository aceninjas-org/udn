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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbInputNode;
import com.ibm.broker.plugin.MbInputNodeInterface;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbUserException;

public class CronSchedulerNode extends MbInputNode implements
		MbInputNodeInterface {

	private String cronExpression;
	private Trigger trigger;
	private String message;
	private boolean enabled = true;
	private Date nextFireDatetime;

	public CronSchedulerNode() throws MbException {
		createOutputTerminal("out");
		createOutputTerminal("failure");
	}

	public int run(MbMessageAssembly messageAssembly) throws MbException {

		// getMessageFlow().getApplicationName()
		if (!enabled)
			return exit(MbInputNode.SUCCESS_RETURN);

		if (trigger == null)
			setupCronTrigger();

		if (message == null)
			message = "<Hello></Hello>";

		MbMessage msg = createMessage(message.getBytes());
		msg.finalizeMessage(MbMessage.FINALIZE_VALIDATE);
		MbMessageAssembly newAssembly = new MbMessageAssembly(messageAssembly,
				msg);

		if (!shouldFire())
			return exit(MbInputNode.SUCCESS_CONTINUE);

		String logFileName = (String) (String) getUserDefinedAttribute(getName()
				+ "_logFile");
		if (logFileName != null) {
			Path logFile = Paths.get(logFileName);
			try {
				if (!logFile.toFile().exists())
					Files.createFile(logFile);
				Files.write(logFile, (getNodeName() + ".cronExpression="
						+ cronExpression + "\n").getBytes(),
						StandardOpenOption.APPEND);
			} catch (Exception ex) {

			}
		}

		int retCode = MbInputNode.SUCCESS_CONTINUE;
		nextFireDatetime = trigger.getFireTimeAfter(nextFireDatetime);
		try {
			getOutputTerminal("out").propagate(newAssembly);
			retCode = MbInputNode.SUCCESS_CONTINUE; // ?????????
		} catch (Exception e) {

			createExceptionTree(newAssembly, e);

			if (getOutputTerminal("failure").isAttached()) {
				getOutputTerminal("failure").propagate(newAssembly);
				retCode = MbInputNode.SUCCESS_CONTINUE;
			} else {
				retCode = MbInputNode.FAILURE_CONTINUE;
			}
		}

		return exit(retCode);

	}

	private int exit(int retCode) {
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
		}

		return retCode;
	}

	private void createExceptionTree(MbMessageAssembly newAssembly, Exception e) {
		try {
			if (e instanceof MbException) {
				UDNHelper.createExceptionTree(newAssembly.getExceptionList()
						.getRootElement(), (MbException) e);
			} else {
				UDNHelper.createExceptionTree(newAssembly.getExceptionList()
						.getRootElement(), new MbUserException(this, "run",
						"mynodes", "3",
						UDNHelper.getExceptionFullStackTrace(e), null));
			}
		} catch (Exception ex) {
		}
	}

	private void setupCronTrigger() {

		if (cronExpression == null) {
			cronExpression = (String) getUserDefinedAttribute(getName()
					+ "_cronExpression");
		}
		trigger = TriggerBuilder.newTrigger()
				.withIdentity("CronSchedulerNodeTrigger", "group")
				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
				.build();
		nextFireDatetime = trigger.getFireTimeAfter(new Date());
	}

	public void onDelete() {
		trigger = null;

	}

	private boolean shouldFire() {
		return System.currentTimeMillis() - nextFireDatetime.getTime() >= 0;
	}

	public static String getNodeName() {
		return "CronSchedulerNode";
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String isEnabled() {
		return enabled + "";
	}

	public void setEnabled(String enabled) {
		this.enabled = Boolean.getBoolean(enabled);
	}

	public static void main(String[] args) {

		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("CronSchedulerNodeTrigger", "group")
				.withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
				.build();
		Date nextFireDatetime = null;
		for (int i = 0; i < 5; i++) {
			if (nextFireDatetime == null) {
				nextFireDatetime = trigger.getFireTimeAfter(new Date());
			} else {
				nextFireDatetime = trigger.getFireTimeAfter(nextFireDatetime);
			}
//			System.out.println(nextFireDatetime.toLocaleString());
		}
	}

}
