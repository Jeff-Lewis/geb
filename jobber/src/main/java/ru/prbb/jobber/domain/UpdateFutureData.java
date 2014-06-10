/**
 * 
 */
package ru.prbb.jobber.domain;

import java.sql.Date;

public class UpdateFutureData {
	public final long id_sec;
	public final String security_name;
	public final String name;
	public final String short_name;
	public final long type_id = 2;
	public final Date first_tradeable_date;
	public final Date last_tradeable_date;
 
	public UpdateFutureData(Long securityCode, String secName, String name, String shortName,
			Date first_tradeable_date, Date last_tradeable_date) {
		this.id_sec = securityCode;
		this.security_name = secName;
		this.name = name;
		this.short_name = shortName;
		this.first_tradeable_date = first_tradeable_date;
		this.last_tradeable_date = last_tradeable_date;
	}
}
