package com.home.hibernate.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

import com.home.hibernate.Relation;

@MappedSuperclass
public abstract class AbstractCatalog extends UniqueIdItem {
	private static final long serialVersionUID = 1L;

	protected String name;

	@Relation(mappedTo = "catalog", referencedType = CatalogVersion.class)
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH,
			CascadeType.REMOVE }, mappedBy = "catalog")
	@Cascade({ org.hibernate.annotations.CascadeType.DETACH, org.hibernate.annotations.CascadeType.SAVE_UPDATE,
			org.hibernate.annotations.CascadeType.LOCK, org.hibernate.annotations.CascadeType.REPLICATE, org.hibernate.annotations.CascadeType.MERGE,
			org.hibernate.annotations.CascadeType.PERSIST, org.hibernate.annotations.CascadeType.REFRESH, org.hibernate.annotations.CascadeType.REMOVE })
	public Set<BaseCatalogVersion> versions = new HashSet<>();

	public void setVersions(Set<BaseCatalogVersion> versions) {
//		this.getVersions().clear();
//		this.getVersions().addAll(versions);
//
//		for (CatalogVersion cv : versions) {
//			cv.setCatalog(this);
//		}

		this.versions = versions;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<BaseCatalogVersion> getVersions() {
//		return ItemCollectionFactory.wrap(this, "versions", versions, Set.class);
		return this.versions;
	}

	public String getName() {
		return this.name;
	}
}
