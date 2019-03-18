package org.vebqa.vebtal.tdrestserver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vebqa.vebtal.AbstractTestAdaptionResource;
import org.vebqa.vebtal.TestAdaptionResource;
import org.vebqa.vebtal.model.Command;
import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.td.IBANStore;

public class TdResource extends AbstractTestAdaptionResource implements TestAdaptionResource {
	
	private static final Logger logger = LoggerFactory.getLogger(TdResource.class);
	
	public TdResource() {
	
	}
	
	public Response execute(Command cmd) {

		// disable user actions
		TDTestAdaptionPlugin.setDisableUserActions(true);

		Response tResponse = new Response();

		Response result = null;
		try {
			Class<?> cmdClass = Class.forName("org.vebqa.vebtal.td.commands." + getCommandClassName(cmd));
			Constructor<?> cons = cmdClass.getConstructor(String.class, String.class, String.class);
			Object cmdObj = cons.newInstance(cmd.getCommand(), cmd.getTarget(), cmd.getValue());

			// get type
			Method mType = cmdClass.getMethod("getType");
			CommandType cmdType = (CommandType) mType.invoke(cmdObj);
			TDTestAdaptionPlugin.addCommandToList(cmd, cmdType);

			// execute
			Method m = cmdClass.getDeclaredMethod("executeImpl", Object.class);

			setStart();
			result = (Response) m.invoke(cmdObj, IBANStore.getStore().getDriver());
			setFinished();

		} catch (ClassNotFoundException e) {
			logger.error("Keyword class {} not found.", e, getCommandClassName(cmd));
		} catch (NoSuchMethodException e) {
			logger.error("Execute method in keyword class not found.", e);
		} catch (SecurityException e) {
			logger.error("Cannot invoke keyword class.", e);
		} catch (InstantiationException e) {
			logger.error("Cannot instantiate keyword class.", e);
		} catch (IllegalAccessException e) {
			logger.error("Illegal access for keyword class.", e);
		} catch (IllegalArgumentException e) {
			logger.error("Illegal argument while invoking keyword.", e);
		} catch (InvocationTargetException e) {
			logger.error("Cannot invoke keyword class.", e);
		}

		if (result == null) {
			tResponse.setCode(Response.FAILED);
			tResponse.setMessage("Cannot resolve command.");
			return tResponse;
		}
		if (result.getCode() != Response.PASSED) {
			TDTestAdaptionPlugin.setLatestResult(false, result.getMessage());
		} else {
			TDTestAdaptionPlugin.setLatestResult(true, "ok: " + result.getMessage());
		}

		// enable user actions
		TDTestAdaptionPlugin.setDisableUserActions(false);

		return result;
	}	

}
