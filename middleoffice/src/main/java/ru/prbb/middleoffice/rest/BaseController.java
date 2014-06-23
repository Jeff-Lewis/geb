package ru.prbb.middleoffice.rest;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

public abstract class BaseController {

	@ExceptionHandler
	@ResponseBody
	private Map<String, Object> handleException(Exception ex) {
		String message = ex.getMessage();

		if (ex instanceof PersistenceException) {
			try {
				Throwable cause = ex.getCause();
				SQLException sqlException = (SQLException) cause.getCause();
				message = sqlException.getMessage();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		Map<String, Object> res = new HashMap<String, Object>(4);
		res.put("success", Boolean.FALSE);
		res.put("error", message);
		return res;
	}

}
