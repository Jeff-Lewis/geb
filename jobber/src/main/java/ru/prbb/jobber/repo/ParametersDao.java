package ru.prbb.jobber.repo;


public interface ParametersDao {

	String getValue(String name);

	int setValue(String name, String value);

	int delValue(String task);

}
