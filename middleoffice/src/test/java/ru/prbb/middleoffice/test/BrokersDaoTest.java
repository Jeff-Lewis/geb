package ru.prbb.middleoffice.test;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.prbb.middleoffice.repo.dictionary.BrokersDao;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {
//		"classpath:test-context.xml",
//		"classpath:/META-INF/spring/applicationContext.xml" })
//@Transactional
//@TransactionConfiguration(defaultRollback = true)
@Ignore("Не реализовано")
public class BrokersDaoTest
{
	@Autowired
	private BrokersDao dao;

	@Test
	public void testFindAll()
	{
		dao.findAll();
	}

	@Test
	public void testFindById()
	{
		//        Member member = memberDao.findById(0l);
		//
		//        Assert.assertEquals("John Smith", member.getName());
		//        Assert.assertEquals("john.smith@mailinator.com", member.getEmail());
		//        Assert.assertEquals("2125551212", member.getPhoneNumber());
		//        return;
	}

	@Test
	public void testFindByEmail()
	{
		//        Member member = memberDao.findByEmail("john.smith@mailinator.com");
		//
		//        Assert.assertEquals("John Smith", member.getName());
		//        Assert.assertEquals("john.smith@mailinator.com", member.getEmail());
		//        Assert.assertEquals("2125551212", member.getPhoneNumber());
		//        return;
	}

	@Test
	public void testRegister()
	{
		//        Member member = new Member();
		//        member.setEmail("jane.doe@mailinator.com");
		//        member.setName("Jane Doe");
		//        member.setPhoneNumber("2125552121");
		//
		//        memberDao.register(member);
		//        Long id = member.getId();
		//        Assert.assertNotNull(id);
		//        
		//        Assert.assertEquals(2, memberDao.findAllOrderedByName().size());
		//        Member newMember = memberDao.findById(id);
		//
		//        Assert.assertEquals("Jane Doe", newMember.getName());
		//        Assert.assertEquals("jane.doe@mailinator.com", newMember.getEmail());
		//        Assert.assertEquals("2125552121", newMember.getPhoneNumber());
		//        return;
	}

	@Test
	public void testFindAllOrderedByName()
	{
		//        Member member = new Member();
		//        member.setEmail("jane.doe@mailinator.com");
		//        member.setName("Jane Doe");
		//        member.setPhoneNumber("2125552121");
		//        memberDao.register(member);
		//
		//        List<Member> members = memberDao.findAllOrderedByName();
		//        Assert.assertEquals(2, members.size());
		//        Member newMember = members.get(0);
		//
		//        Assert.assertEquals("Jane Doe", newMember.getName());
		//        Assert.assertEquals("jane.doe@mailinator.com", newMember.getEmail());
		//        Assert.assertEquals("2125552121", newMember.getPhoneNumber());
		//        return;
	}
}
