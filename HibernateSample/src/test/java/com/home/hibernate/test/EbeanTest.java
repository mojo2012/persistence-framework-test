package com.home.hibernate.test;

import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.home.hibernate.entity.BaseCatalogVersion;
import com.home.hibernate.entity.Catalog;
import com.home.hibernate.entity.CatalogVersion;

import io.ebean.Ebean;
import io.ebean.EbeanServer;

/**
 * @author preetham
 */
public class EbeanTest {
	private static EbeanServer ebeanServer = null;

	@BeforeClass
	public static void setUp() throws Exception {
		try {
			ebeanServer = Ebean.getDefaultServer();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@AfterClass
	public static void tearDown() throws Exception {
		Ebean.getDefaultServer().shutdown(true, true);
	}

	public Catalog mockCatalog() {
		final Catalog catalog = new Catalog();
		catalog.setUid("testCatalog-" + System.currentTimeMillis());

		final CatalogVersion catalogVersionOnline = new CatalogVersion();
		catalogVersionOnline.setCatalog(catalog);
		catalogVersionOnline.setUid("Online");

		final CatalogVersion catalogVersionStaged = new CatalogVersion();
		catalogVersionStaged.setCatalog(catalog);
		catalogVersionStaged.setUid("Staged");
		catalogVersionStaged.setSynchronizationTarget(catalogVersionOnline);

		Assert.assertEquals(2, catalog.getVersions().size());

		return catalog;
	}

	@Test(expected = EntityNotFoundException.class)
	public void testDeleteReferencedCatalog() {

		// creating catalogs
		Catalog catalog = mockCatalog();

		ebeanServer.beginTransaction();

		ebeanServer.save(catalog);
		ebeanServer.flush();
		ebeanServer.commitTransaction();

		Assert.assertEquals(2, catalog.getVersions().size());

		// removing catalog version
		ebeanServer.beginTransaction();
		BaseCatalogVersion cvToDelete = catalog.getVersions().stream().filter(cv -> "Online".equals(cv.getUid())).collect(Collectors.toList()).get(0);
		catalog.getVersions().remove(cvToDelete);

		ebeanServer.save(catalog);
		ebeanServer.flush();
		ebeanServer.commitTransaction();

		ebeanServer.beginTransaction();

		ebeanServer.refresh(catalog);
		Assert.assertEquals(1, catalog.getVersions().size());

		ebeanServer.refresh(cvToDelete);
		ebeanServer.commitTransaction();
	}

}
