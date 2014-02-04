package ru.prbb.rest;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.domain.BloombergResultItem;
import ru.prbb.service.BloombergServices;

/**
 * Загрузка котировок
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/QuotesLoad")
public class QuotesLoadController
{
	@Autowired
	private BloombergServices bs;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<BloombergResultItem> execute(
			@RequestParam String[] securities,
			@RequestParam String begin,
			@RequestParam(required = false) String end) {

		if (Utils.isEmpty(end)) {
			final Calendar c = Calendar.getInstance(Utils.LOCALE);
			c.add(Calendar.DAY_OF_MONTH, -1);
			end = Utils.createDateFormatYMD().format(c.getTime());
		}

		return bs.executeQuotesLoad(Utils.parseDate(begin), Utils.parseDate(end), securities);
	}
}
