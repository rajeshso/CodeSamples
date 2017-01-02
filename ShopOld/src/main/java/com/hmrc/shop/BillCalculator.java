/**
 * 
 */
package com.hmrc.shop;

/**
 * @author Rajesh
 * 
 * The BillCalculator is responsible to provide a bill for the given list of materials
 */
@FunctionalInterface
public interface BillCalculator {
	public double generate(String[] materials); 
}