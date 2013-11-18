package ru.prbb.middleoffice.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class IndexController
{
	@RequestMapping(method = RequestMethod.GET)
	public String displayIndex(Model model)
	{
		model.addAttribute("username", "CURRENT_USER");
		return "index";
	}
}
