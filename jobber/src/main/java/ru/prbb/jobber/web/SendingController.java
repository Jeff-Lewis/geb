package ru.prbb.jobber.web;

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
import ru.prbb.jobber.domain.ResultData;
import ru.prbb.jobber.domain.SendingItem;
import ru.prbb.jobber.domain.SimpleItem;
import ru.prbb.jobber.repo.SendingDao;

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
			@RequestParam String subject,
			@RequestParam String text,
			@RequestParam(value = "recp") String recPhones,
			@RequestParam(value = "recm") String recMails,
			@RequestParam(required=false, defaultValue = "0") Integer service)
	{
		log.info("POST Sending: recp=({}), recm=({})", recPhones, recMails);
		log.info("POST Sending: subject={}", subject);
		log.info("POST Sending: text={}", text);
		List<SendingItem> res = new ArrayList<>();

		if (Utils.isNotEmpty(recMails)) {
			List<String> mails = new ArrayList<>();
			for (String mail : recMails.split(",")) {
				if (Utils.isNotEmpty(mail)) {
					if (mail.indexOf('@') > 0) {
						mails.add(mail);
					} else {
						List<String> groupMails = dao.getMailByGroup(mail);
						for (String groupMail : groupMails) {
							mails.add(groupMail);
						}
					}
				}
			}
			res.addAll(dao.sendMail(text, mails, (subject != null) ? subject : "info"));
		}

		if (Utils.isNotEmpty(recPhones)) {
			List<String> phones = new ArrayList<>();
			for (String phone : recPhones.split(",")) {
				if (Utils.isNotEmpty(phone)) {
					if (phone.charAt(0) == '+') {
						phones.add(phone);
					} else {
						List<String> groupPhones = dao.getPhoneByGroup(phone);
						for (String groupPhone : groupPhones) {
							phones.add(groupPhone);
						}
					}
				}
			}
			res.addAll(dao.sendSms(service, text, phones, 2));
		}

		return res;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getText(
			@PathVariable("id") Long id)
	{
		log.info("GET Sending/Text: id={}", id);
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
		case 3:
			text = "Fullermoney Audio доступен по ссылкам:\n" +
					"\n" +
					"Текущий выпуск:\n" +
					"\n" +
					"\n" +
					"Предыдущий выпуск:\n" +
					"\n" +
					"\n";
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
