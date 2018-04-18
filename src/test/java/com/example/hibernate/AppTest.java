package com.example.hibernate;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AppTest {

	private static TransactionTokenMapper mapper = new TransactionTokenMapper();

	@Before
	public void startTransaction() {
		mapper.startTransaction();
	}

	@After
	public void commitTransaction() {
		mapper.commitTransaction();
	}

	private TransactionToken tokenFactory(String tokenPrefix, String transactionPrefix)
	{
		TransactionToken t = new TransactionToken();
		t.setToken(tokenPrefix + System.currentTimeMillis());
		t.setTransaction(transactionPrefix + System.currentTimeMillis());
		return t;
	}

	@Test
	public void testInsert() {
		TransactionToken t = tokenFactory("alpha", "beta");
		mapper.insert(t);
		assertTrue(t.getId() > -1);

		long count = mapper.count();

		TransactionToken t2 = tokenFactory("cappa", "delta");
		mapper.insert(t2);
		assertTrue(t2.getId() > -1);

		assertEquals(count + 1, mapper.count());
	}

	@Test
	public void testUpdate() {
		TransactionToken t = tokenFactory("faraday", "gamma");
		mapper.insert(t);

		TransactionToken t2 = mapper.getById(t.getId());
		assertEquals(t.getToken(), t2.getToken());
		assertEquals(t.getTransaction(), t2.getTransaction());

		t2.setToken("bingo" + System.currentTimeMillis());
		t2.setTransaction("funky" + System.currentTimeMillis());
		mapper.update(t2);

		TransactionToken t3 = mapper.getById(t.getId());
		assertEquals(t2.getToken(), t3.getToken());
		assertEquals(t2.getTransaction(), t3.getTransaction());
	}

	@Test
	public void testDeleteById() {
		long count = mapper.count();

		TransactionToken t = tokenFactory("indigo", "jakarta");
		mapper.insert(t);
		assertEquals(count + 1, mapper.count());

		mapper.delete(t);
		assertEquals(count,  mapper.count());
	}

	@Test
	public void testDeleteByTransaction() {
		long count = mapper.count();

		TransactionToken t2 = tokenFactory("kava", "lambda");
		mapper.insert(t2);
		assertEquals(count + 1, mapper.count());

		mapper.deleteByTransaction(t2);
		assertEquals(count, mapper.count());
	}

	@Test
	public void testFindByTransaction() {
		TransactionToken t = tokenFactory("manual", "nova");
		mapper.insert(t);
		assertTrue(t.getId() >= 0);

		TransactionToken t2 = mapper.selectByTransaction(t.getTransaction());
		assertEquals(t.getToken(), t2.getToken());
		assertEquals(t.getTransaction(), t2.getTransaction());
	}

	@Test
	public void testRollback() {
		long count = mapper.count();

		TransactionToken t = tokenFactory("omega", "passport");
		mapper.insert(t);
		assertEquals(count + 1, mapper.count());

		mapper.rollbackTransaction();
		assertEquals(count, mapper.count());

		startTransaction();
		TransactionToken t3 = tokenFactory("quark", "star");
		mapper.insert(t3);
		assertEquals(count + 1, mapper.count());
	}

	@Test
	public void testAddRemoveReferenceId() {
		TransactionToken t = tokenFactory("omega", "passport");
		mapper.insert(t);
		mapper.clearPersistenceContext();
		t = mapper.getById(t.getId());
		assertEquals(0, t.getReferenceIds().size());
		t.addReferenceId("referenceId1");
		t.addReferenceId("referenceId2");
		mapper.update(t);
		mapper.clearPersistenceContext();
		t = mapper.getById(t.getId());
		assertEquals(2, t.getReferenceIds().size());
		t.removeReferenceId("referenceId2");
		mapper.update(t);
		mapper.clearPersistenceContext();
		t = mapper.getById(t.getId());
		assertEquals(1, t.getReferenceIds().size());
	}




}
