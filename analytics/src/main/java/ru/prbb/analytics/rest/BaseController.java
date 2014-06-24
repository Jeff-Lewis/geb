package ru.prbb.analytics.rest;

import java.sql.SQLException;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.domain.ResultError;

public abstract class BaseController {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	@ExceptionHandler
	@ResponseBody
	private Result handleException(Exception ex) {
		String message = ex.getMessage();

		if (ex instanceof PersistenceException) {
			try {
				Throwable cause = ex.getCause();
				SQLException sqlException = (SQLException) cause.getCause();
				message = sqlException.getMessage();
			} catch (Exception e) {
				log.error("catch PersistenceException", e);
			}
		}

		return new ResultError(message);
	}

}
