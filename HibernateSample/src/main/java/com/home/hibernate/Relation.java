package com.home.hibernate;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.home.hibernate.entity.UniqueIdItem;

@Target({ FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Relation {
	boolean DEFAULT_CASCADE_ON_DELETE = false;

	/**
	 * If {@link OneToManyRelation} is used, the {@link Relation#mappedTo()}
	 * property is a single property containing the item reference of the other
	 * end. {@link ManyToOneRelation} basically means the same, just that the
	 * referencing property is a collection.
	 */
	Class<? extends UniqueIdItem> referencedType();

	/**
	 * The name of the relation. Depending on the underlying persistence
	 * framework, this might be used for the relation table name. If not set,
	 * then it will be automatically generated.
	 * 
	 * @return the relation name or an empty string if not defined
	 */
	String relationName() default "";

	/**
	 * @return the name of the property of the referenced item.
	 */
	String mappedTo() default "";

	/**
	 * If this is "true", deleting a reference from a relation collection also
	 * deletes the real item.<br />
	 * Example: When deleting a user from a user group and this is set to
	 * "true", the referenced user is also deleted. If set to "false" the
	 * referenced user is kept, only the reference is removed on both sides of
	 * the relation. <br />
	 */
	// boolean casacadeOnDelete() default DEFAULT_CASCADE_ON_DELETE;
}
