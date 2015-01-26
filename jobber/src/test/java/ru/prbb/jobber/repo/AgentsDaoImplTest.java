/**
 * 
 */
package ru.prbb.jobber.repo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ru.prbb.jobber.domain.AgentItem;

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
	 * @throws UnknownHostException 
	 */
	@Test
	public void testAdd() throws UnknownHostException {
		dao.add(InetAddress.getByName("192.168.1.1"));
		Assert.assertEquals(1 + 1, dao.list().size());

		dao.add(InetAddress.getByName("192.168.1.1"));
		Assert.assertEquals(1 + 1, dao.list().size());

		dao.add(InetAddress.getByName("192.168.1.2"));
		Assert.assertEquals(2 + 1, dao.list().size());
	}

	/**
	 * Test method for
	 * {@link ru.prbb.analytics.repo.AgentsDaoImpl#remove(java.lang.String)}.
	 * @throws UnknownHostException 
	 */
	@Test
	public void testRemove() throws UnknownHostException {
		dao.add(InetAddress.getByName("192.168.1.1"));
		dao.add(InetAddress.getByName("192.168.1.2"));
		Assert.assertEquals(2 + 1, dao.list().size());

		dao.remove(InetAddress.getByName("192.168.1.1"));
		Assert.assertEquals(1 + 1, dao.list().size());

		dao.remove(InetAddress.getByName("192.168.1.2"));
		Assert.assertEquals(0 + 1, dao.list().size());

		dao.remove(InetAddress.getByName("192.168.1.1"));
		Assert.assertEquals(0 + 1, dao.list().size());
	}

	/**
	 * Test method for {@link ru.prbb.analytics.repo.AgentsDaoImpl#list()}.
	 * @throws UnknownHostException 
	 */
	@Test
	public void testList() throws UnknownHostException {
		String[] addrs = { "192.168.1.1", "192.168.1.2" };
		for (String host : addrs) {
			dao.add(InetAddress.getByName(host));
		}

		Collection<AgentItem> list = dao.list();
		Assert.assertEquals(2 + 1, list.size());
	}

}
