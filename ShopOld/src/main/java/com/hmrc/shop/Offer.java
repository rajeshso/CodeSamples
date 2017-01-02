/**
 * 
 */
package com.hmrc.shop;

/**
 * Representation of the Offer.
 * The given item would be updated based on the busines logic of the offer.
 * @author Rajesh
 *
 */
@FunctionalInterface
public interface Offer {
	public abstract void apply(Item i);
}
