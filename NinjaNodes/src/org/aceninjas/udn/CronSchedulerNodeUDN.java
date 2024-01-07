package org.aceninjas.udn;

import com.ibm.broker.config.appdev.InputTerminal;
import com.ibm.broker.config.appdev.Node;
import com.ibm.broker.config.appdev.NodeProperty;
import com.ibm.broker.config.appdev.OutputTerminal;

/*** 
 * <p>  <I>CronSchedulerNodeUDN</I> instance</p>
 * <p></p>
 */
public class CronSchedulerNodeUDN extends Node {

	private static final long serialVersionUID = 1L;

	// Node constants
	protected final static String NODE_TYPE_NAME = "org/aceninjas/udn/CronSchedulerNode";
	protected final static String NODE_GRAPHIC_16 = "platform:/plugin/Ninjas/icons/full/obj16/org/aceninjas/udn/CronScheduler.gif";
	protected final static String NODE_GRAPHIC_32 = "platform:/plugin/Ninjas/icons/full/obj30/org/aceninjas/udn/CronScheduler.gif";

	protected final static String PROPERTY_CRONEXPRESSION = "cronExpression";
	protected final static String PROPERTY_MESSAGE = "message";

	protected NodeProperty[] getNodeProperties() {
		return new NodeProperty[] {
			new NodeProperty(CronSchedulerNodeUDN.PROPERTY_CRONEXPRESSION,		NodeProperty.Usage.MANDATORY,	false,	NodeProperty.Type.STRING, "0 40 03 * * ? *","",	"org.aceninjas.udn.CronExpressionEditor",	"org/aceninjas/udn/CronScheduler",	"Ninjas"),
			new NodeProperty(CronSchedulerNodeUDN.PROPERTY_MESSAGE,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.STRING, "cronschduler","","",	"org/aceninjas/udn/CronScheduler",	"Ninjas")
		};
	}

	public CronSchedulerNodeUDN() {
	}

	@Override
	public InputTerminal[] getInputTerminals() {
		return null;
	}

	public final OutputTerminal OUTPUT_TERMINAL_FAILURE = new OutputTerminal(this,"OutTerminal.failure");
	public final OutputTerminal OUTPUT_TERMINAL_OUT = new OutputTerminal(this,"OutTerminal.out");
	@Override
	public OutputTerminal[] getOutputTerminals() {
		return new OutputTerminal[] {
			OUTPUT_TERMINAL_FAILURE,
			OUTPUT_TERMINAL_OUT
		};
	}

	@Override
	public String getTypeName() {
		return NODE_TYPE_NAME;
	}

	protected String getGraphic16() {
		return NODE_GRAPHIC_16;
	}

	protected String getGraphic32() {
		return NODE_GRAPHIC_32;
	}

	/**
	 * Set the <I>CronSchedulerNodeUDN</I> "<I>Cron Expression</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>Cron Expression</I>"
	 */
	public CronSchedulerNodeUDN setCronExpression(String value) {
		setProperty(CronSchedulerNodeUDN.PROPERTY_CRONEXPRESSION, value);
		return this;
	}

	/**
	 * Get the <I>CronSchedulerNodeUDN</I> "<I>Cron Expression</I>" property
	 * 
	 * @return String; the value of the property "<I>Cron Expression</I>"
	 */
	public String getCronExpression() {
		return (String)getPropertyValue(CronSchedulerNodeUDN.PROPERTY_CRONEXPRESSION);
	}

	/**
	 * Set the <I>CronSchedulerNodeUDN</I> "<I>message</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>message</I>"
	 */
	public CronSchedulerNodeUDN setMessage(String value) {
		setProperty(CronSchedulerNodeUDN.PROPERTY_MESSAGE, value);
		return this;
	}

	/**
	 * Get the <I>CronSchedulerNodeUDN</I> "<I>message</I>" property
	 * 
	 * @return String; the value of the property "<I>message</I>"
	 */
	public String getMessage() {
		return (String)getPropertyValue(CronSchedulerNodeUDN.PROPERTY_MESSAGE);
	}

	public String getNodeName() {
		String retVal = super.getNodeName();
		if ((retVal==null) || retVal.equals(""))
			retVal = "CronScheduler";
		return retVal;
	};
}
