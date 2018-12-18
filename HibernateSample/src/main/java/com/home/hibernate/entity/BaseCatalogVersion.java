package com.home.hibernate.entity;

import javax.persistence.CascadeType;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;

import com.home.hibernate.Relation;

@Entity
public class BaseCatalogVersion extends UniqueIdItem {
	private static final long serialVersionUID = 1L;

	protected String name;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@Cascade({ org.hibernate.annotations.CascadeType.DETACH, org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK,
			org.hibernate.annotations.CascadeType.REPLICATE, org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST,
			org.hibernate.annotations.CascadeType.REFRESH })
	@JoinColumn(name = "synchronizationTarget_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
//	@OnDelete(action = OnDeleteAction.CASCADE)
	protected BaseCatalogVersion synchronizationTarget;

	@NotNull
	@Relation(mappedTo = "versions", referencedType = Catalog.class)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@Cascade({ org.hibernate.annotations.CascadeType.DETACH, org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK,
			org.hibernate.annotations.CascadeType.REPLICATE, org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST,
			org.hibernate.annotations.CascadeType.REFRESH })
	@JoinColumn(nullable = false, name = "catalog_id")
	public Catalog catalog;

	public Catalog getCatalog() {
		return this.catalog;
	}

	public void setSynchronizationTarget(BaseCatalogVersion synchronizationTarget) {
		this.synchronizationTarget = synchronizationTarget;
	}

	public String getName() {
		return this.name;
	}

	public BaseCatalogVersion getSynchronizationTarget() {
		return this.synchronizationTarget;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
		catalog.getVersions().add(this);
	}
}
