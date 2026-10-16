package utils;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

public class Utility {

	public final static String BUNDLE_FILE_NAME = "resources.Bundle";
	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

	public static void addMessageToFacesContext(String key) {

	}

	public static void addSuccessMessage(String key) {
		try {
			key = getMessage(key, BUNDLE_FILE_NAME);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			FacesMessage msg = new FacesMessage(key, "success");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public static String getMessage(String key, String bundle) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(bundle);
		MessageFormat msgFormat = new MessageFormat(resourceBundle.getString(key));
		return msgFormat.format(null);
	}

	public static void addErrorMessage(String key) {
		try {
			key = getMessage(key, BUNDLE_FILE_NAME);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			FacesMessage msg = new FacesMessage(key, "error");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public static void addWarningMessage(String key) {
		try {
			key = getMessage(key, BUNDLE_FILE_NAME);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			FacesMessage msg = new FacesMessage(key, "warn");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public static void addMessage(String msg) {
		FacesMessage facesMessage = new FacesMessage(msg, "warn");
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
	
	public static Date startDate(Date date) {
		if (date == null)
			return null;
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MILLISECOND, 0);
		return c1.getTime();
	}
}
