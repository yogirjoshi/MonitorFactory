/**
 * 
 */
package rithm.core;

import java.rmi.NoSuchObjectException;

/**
 * @author y2joshi
 *
 */
public interface PredicateState {
	public boolean SetValue(String Name, boolean Value);
	public boolean GetValue(String Name) throws NoSuchObjectException;
}
