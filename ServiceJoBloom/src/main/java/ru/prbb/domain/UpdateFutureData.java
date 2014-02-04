/**
 * 
 */
package ru.prbb.domain;

public class UpdateFutureData {
	public final long id_sec;
	public final String security_name;
	public final String name;
	public final String short_name;
	public final long type_id = 2;

	public UpdateFutureData(Long securityCode, String secName, String name, String shortName) {
		this.id_sec = securityCode;
		this.security_name = secName;
		this.name = name;
		this.short_name = shortName;
	}
}