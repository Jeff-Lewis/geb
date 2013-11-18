/**
 * 
 */
package ru.prbb.analytics.rest.params;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.domain.ViewParamsItem;
import ru.prbb.analytics.repo.params.ViewParamsDao;

/**
 * Справочник параметров
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/ViewParams")
public class ViewParamsController
{
	@Autowired
	private ViewParamsDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<ViewParamsItem> list()
	{
		// select param_id, blm_id, code, name from dbo.params
		return dao.show();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ResultData info(
			@PathVariable("id") String blm_id)
	{
		// select field_id,field_mnemonic,description,data_license_category,category,definition,comdty, equity,muni,pfd,m_mkt,govt,corp,indx,curncy,mtge,standard_width,  standard_decimal_places,field_type,back_office,extended_back_office,production_date, current_maximum_width,bval,bval_blocked,getfundamentals,gethistory,getcompany,old_mnemonic,data_license_category_2,psboopt from dbo.bloomberg_dl_fields where field_id=?
		return new ResultData(dao.getById(blm_id));
	}
}
