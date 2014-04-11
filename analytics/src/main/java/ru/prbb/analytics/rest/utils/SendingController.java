package ru.prbb.analytics.rest.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.domain.SendingItem;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.utils.SendingDao;

/**
 * Рассылка E-mail и SMS
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/Sending")
public class SendingController
{
	@Autowired
	private SendingDao dao;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<SendingItem> show(
			@RequestParam String text,
			@RequestParam(value = "recp", required = false) String recPhones,
			@RequestParam(value = "recm", required = false) String recMails)
	{
		List<SendingItem> res = new ArrayList<>();

		if (Utils.isNotEmpty(recMails)) {
			String mails[] = recMails.split(",");
			for (String mail : mails) {
				if (Utils.isNotEmpty(mail)) {
					if (mail.contains("@")) {
						res.add(dao.sendMail(text, mail));
					} else {
						List<String> groupMails = dao.getMailByGroup(mail);
						for (String groupMail : groupMails) {
							res.add(dao.sendMail(text, groupMail));
						}
					}
				}
			}
		}

		if (Utils.isNotEmpty(recPhones)) {
			String phones[] = recPhones.split(",");
			for (String phone : phones) {
				if (Utils.isNotEmpty(phone)) {
					if (phone.startsWith("+")) {
						res.add(dao.sendSms(text, phone));
					} else {
						List<String> groupPhones = dao.getPhoneByGroup(phone);
						for (String groupPhone : groupPhones) {
							res.add(dao.sendSms(text, groupPhone));
						}
					}
				}
			}
		}

		return res;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ResultData getText(
			@PathVariable("id") Long id)
	{
		String text;
		switch (id.intValue()) {
		case 0:
			text = dao.getAnalitic();
			break;
		case 1:
			text = dao.getTrader();
			break;

		default:
			text = "Текст не задан.";
			break;
		}
		return new ResultData(text);
	}

	@RequestMapping(value = "/Phone", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboPhone(
			@RequestParam(required = false) String query)
	{
		return dao.findComboPhone(query);
	}

	@RequestMapping(value = "/Mail", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboMail(
			@RequestParam(required = false) String query)
	{
		return dao.findComboMail(query);
	}
}
