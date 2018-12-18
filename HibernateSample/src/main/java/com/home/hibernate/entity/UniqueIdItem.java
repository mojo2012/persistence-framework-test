package com.home.hibernate.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
public abstract class UniqueIdItem {
	@Id
	private long id = UUID.randomUUID().getMostSignificantBits();

	@UpdateTimestamp
	// the index is needed for ORDER BY in combination with FETCH JOINS and pagination!
//	@Property(indexed = true)
	@Index(name = "idx_Item_lastModifiedAt")
	protected Date lastModifiedAt;

	protected Date createdAt;

	@Column
	Long uniquenessHash;
	
	@PrePersist
	public void prePersist() {
		if (this.createdAt == null) {
			this.createdAt = new Date();
		}
		
		uniquenessHash = UUID.randomUUID().getMostSignificantBits();
	}

	@PreUpdate
	public void preUpdate() {
		lastModifiedAt = new Date();
		uniquenessHash = UUID.randomUUID().getMostSignificantBits();
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	@Version
	private long version = -1;

	@Column
	private String uid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public long getId() {
		return id;
	}

	public long getVersion() {
		return version;
	}

	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

}
