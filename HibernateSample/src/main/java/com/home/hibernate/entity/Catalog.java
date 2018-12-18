package com.home.hibernate.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

import com.home.hibernate.ItemCollectionFactory;
import com.home.hibernate.Relation;

@Entity
public class Catalog extends AbstractCatalog {
	private static final long serialVersionUID = 1L;

}
