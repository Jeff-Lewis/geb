/**
 * 
 */
package ru.prbb.jobber.repo;

import java.util.Collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ru.prbb.jobber.repo.AgentsDaoImpl;

/**
 * @author ruslan
 *
 */
public class AgentsDaoImplTest {

	private AgentsDaoImpl dao;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		dao = new AgentsDaoImpl();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		dao = null;
	}

	/**
	 * Test method for
	 * {@link ru.prbb.analytics.repo.AgentsDaoImpl#add(java.lang.String)}.
	 */
	@Test
	public void testAdd() {
		dao.add("192.168.1.1");
		Assert.assertEquals(1, dao.list().size());

		dao.add("192.168.1.1");
		Assert.assertEquals(1, dao.list().size());

		dao.add("192.168.1.2");
		Assert.assertEquals(2, dao.list().size());
	}

	/**
	 * Test method for
	 * {@link ru.prbb.analytics.repo.AgentsDaoImpl#remove(java.lang.String)}.
	 */
	@Test
	public void testRemove() {
		dao.add("192.168.1.1");
		dao.add("192.168.1.2");
		Assert.assertEquals(2, dao.list().size());

		dao.remove("192.168.1.1");
		Assert.assertEquals(1, dao.list().size());

		dao.remove("192.168.1.2");
		Assert.assertEquals(0, dao.list().size());

		dao.remove("192.168.1.1");
		Assert.assertEquals(0, dao.list().size());
	}

	/**
	 * Test method for {@link ru.prbb.analytics.repo.AgentsDaoImpl#list()}.
	 */
	@Test
	public void testList() {
		String[] addrs = { "192.168.1.1", "192.168.1.2" };
		for (String host : addrs) {
			dao.add(host);
		}

		Collection<String> list = dao.list();
		Assert.assertEquals(2, list.size());
	}

}
