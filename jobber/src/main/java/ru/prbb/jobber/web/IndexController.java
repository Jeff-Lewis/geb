package ru.prbb.jobber.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class IndexController
{
	@RequestMapping(method = RequestMethod.GET)
	public String display(Model model)
	{
		return "index";
	}
}
