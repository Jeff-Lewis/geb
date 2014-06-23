package ru.prbb.middleoffice.rest;

import java.sql.SQLException;

import javax.persistence.PersistenceException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.ResultError;

public abstract class BaseController {

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
				// TODO: handle exception
			}
		}

		return new ResultError(message);
	}

}
