package ru.prbb.middleoffice.rest.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.domain.SendingItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.services.SendingDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Рассылка E-mail и SMS
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/Sending")
public class SendingController
		extends BaseController
{

	@Autowired
	private SendingDao dao;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<SendingItem> postSend(
			@RequestParam String text,
			@RequestParam(value = "recp", required = false) String recPhones,
			@RequestParam(value = "recm", required = false) String recMails)
	{
		log.info("POST Sending: recp=({}), recm=({})", recPhones, recMails);
		log.info("POST Sending: text=" + text);
		List<SendingItem> res = new ArrayList<>();

		if (Utils.isNotEmpty(recMails)) {
			String mails[] = recMails.split(",");
			for (String mail : mails) {
				if (Utils.isNotEmpty(mail)) {
					if (mail.indexOf('@') > 0) {
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
					if (phone.charAt(0) == '+') {
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
	@ResponseBody
	public ResultData getText(
			@PathVariable("id") Long id)
	{
		log.info("GET Sending: id={}", id);
		String text;
		switch (id.intValue()) {
		case 0:
			text = dao.getAnalitic();
			break;
		case 1:
			text = dao.getTrader();
			break;
		case 2:
			text = "Добрый день!\n" +
					"\n" +
					"Ридинг за " + new SimpleDateFormat("dd.MM.yyyy").format(new Date()) + " доступен по ссылке:\n" +
					"\n" +
					"\n" +
					"\n" +
					"Количество страниц: \n" +
					"\n" +
					"Спасибо!";
			break;

		default:
			text = "Текст не задан.";
			break;
		}
		return new ResultData(text);
	}

	@RequestMapping(value = "/Phone", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboPhone(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Sending: Phone='{}'", query);
		return dao.findComboPhone(query);
	}

	@RequestMapping(value = "/Mail", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboMail(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Sending: Mail='{}'", query);
		return dao.findComboMail(query);
	}
}
