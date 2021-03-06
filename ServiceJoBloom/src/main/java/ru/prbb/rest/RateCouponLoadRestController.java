package ru.prbb.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.domain.BloombergResultItem;
import ru.prbb.service.BloombergServices;

/**
 * Загрузка ставки по купонам
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/RateCouponLoad")
public class RateCouponLoadRestController
{
	@Autowired
	private BloombergServices bs;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<BloombergResultItem> execute(
			@RequestParam String[] securities) {

		Map<String, Long> items = new HashMap<>();

		for (String s : securities) {
			final int p = s.indexOf(':');

			final Long id = new Long(s.substring(0, p));
			final String name = s.substring(p + 1);

			items.put(name, id);
		}

		return bs.executeRateCouponLoad(items);
	}
}
