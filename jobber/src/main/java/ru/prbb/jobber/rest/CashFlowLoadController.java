package ru.prbb.jobber.rest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.jobber.domain.CashFlowData;
import ru.prbb.jobber.domain.CashFlowResultItem;
import ru.prbb.jobber.repo.BloombergServices;

/**
 * Загрузка дат погашений
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/CashFlowLoad")
public class CashFlowLoadController
{
	@Autowired
	private BloombergServices bs;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<CashFlowResultItem> execute(
			@RequestParam String[] securities) {
		List<CashFlowData> datas = new ArrayList<>();

		for (String s : securities) {
			final int p = s.indexOf(':');
			final int p1 = s.indexOf(':', p + 1);

			final Long id = new Long(s.substring(0, p));
			final Date date = Utils.parseDate(s.substring(p + 1, p1));
			final String name = s.substring(p1 + 1);

			datas.add(new CashFlowData(id, name, date));
		}

		return bs.executeCashFlowLoad(datas);
	}
}
