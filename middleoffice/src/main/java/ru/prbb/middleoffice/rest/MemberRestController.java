package ru.prbb.middleoffice.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.Member;
import ru.prbb.middleoffice.repo.MemberDao;

@Controller
@RequestMapping("/rest/members")
public class MemberRestController
{
    @Autowired
    private MemberDao dao;

    @RequestMapping(method=RequestMethod.GET, produces="application/json")
    public @ResponseBody List<Member> listAllMembers()
    {
        return dao.findAllOrderedByName();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody Member lookupMemberById(@PathVariable("id") Long id)
    {
        return dao.findById(id);
    }
}
