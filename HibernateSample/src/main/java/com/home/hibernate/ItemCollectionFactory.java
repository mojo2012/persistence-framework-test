package com.home.hibernate;

import java.lang.reflect.Field;
import java.util.Collection;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;

import com.home.hibernate.entity.UniqueIdItem;

/**
 * <p>ItemCollectionFactory class.</p>
 *
 * @author mojo2012
 * @version 1.0
 * @since 1.0
 */
public class ItemCollectionFactory {

	/**
	 * <p>wrap.</p>
	 *
	 * @param owner            a {@link io.spotnext.infrastructure.type.Item} object.
	 * @param propertyName     a {@link java.lang.String} object.
	 * @param collectionToWrap a {@link java.util.Collection} object.
	 * @param                  <I> a I object.
	 * @return a {@link io.spotnext.infrastructure.support.ProxyCollection} object.
	 */
	public static <I extends UniqueIdItem> ProxyCollection<I> wrap(UniqueIdItem owner, String propertyName,
			Collection<I> collectionToWrap, Class<? extends Collection> collectionType) {

		final Field properyField = ClassUtil.getFieldDefinition(owner.getClass(), propertyName, true);

		boolean updateOtherReference = true; // relAnnotation != null && RelationType.ManyToMany.equals(relAnnotation.type());

		final ProxyCollection<?> proxyCol = new ProxyCollection<I>(collectionToWrap, collectionType,
				updateOtherReference ? (e) -> updateOwnerReference(properyField, owner, e) : null,
				updateOtherReference ? (e) -> updateOwnerReference(properyField, null, e) : null);

		return (ProxyCollection<I>) proxyCol;
	}

	private static void updateOwnerReference(final Field collectionPropertyField, final UniqueIdItem owner,
			final UniqueIdItem ownedItem) {

		final Relation relation = ClassUtil.getAnnotation(collectionPropertyField, Relation.class);

		final String mappedTo = relation.mappedTo();
		ClassUtil.setField(ownedItem, mappedTo, owner);
	}
}
