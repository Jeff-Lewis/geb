package ru.prbb.bloomberg;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class BloombergServicesImpl implements BloombergServices {

	@Override
	public Map<String, Object> executeFieldInfoRequest(String name, String code) {
//		final FieldInfoRequest r = new FieldInfoRequest(code);
//		r.execute(name);
//		return r.getAnswer();
		return new HashMap<>();
	}

	@Override
	public void executeBdhEpsRequest(String name, String dateStart, String dateEnd, String period,
			String calendar, Set<String> currencies, String[] securities, String[] params) {
		// security = crncy + '|' + code
//		final BdhEpsRequest r = new BdhEpsRequest(dateStart, dateEnd, period, calendar, currencies, securities, params);
//		r.setDbm(dbm);
//		r.execute(name);
	}

	@Override
	public void executeBdhRequest(String name, String dateStart, String dateEnd, String period,
			String calendar, Set<String> currencies, String[] securities, String[] params) {
		// security = crncy + '|' + code
//		final BdhRequest r = new BdhRequest(dateStart, dateEnd, period, calendar, currencies, securities, params);
//		r.setDbm(dbm);
//		r.execute(name);
	}

	@Override
	public void executeBdpRequest(String name, String[] securities, String[] params) {
//		final BdpRequest r = new BdpRequest(securities, params);
//		r.setDbm(dbm);
//		r.execute(name);
	}

	@Override
	public void executeBdpRequestOverride(String name, String[] securities, String[] params,
			String period, String over) {
//		final BdpRequestOverride r = new BdpRequestOverride(securities, params, period, over);
//		r.setDbm(dbm);
//		r.execute(name);
	}

	@Override
	public void executeBdpRequestOverrideQuarter(String name, String[] securities, String[] params,
			Set<String> currencies, String over) {
		// security = crncy + '|' + code
//		final BdpRequestOverrideQuarter r = new BdpRequestOverrideQuarter(securities, params, currencies, over);
//		r.setDbm(dbm);
//		r.execute(name);
	}

	@Override
	public void executeBdsRequest(String name, String[] securities, String[] params) {
//		final BdsRequest r = new BdsRequest(securities, params);
//		r.setDbm(dbm);
//		r.execute(name);
	}
}
