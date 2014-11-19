/**
 * 
 */
package ru.prbb.agent.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ruslan
 *
 */
@Controller
@RequestMapping(value = "/rest", produces = "application/json")
public class RestController {

	@RequestMapping("/a")
	@ResponseBody
	public String getIndex() {
		return "Bloomberg Agent";
	}
}
