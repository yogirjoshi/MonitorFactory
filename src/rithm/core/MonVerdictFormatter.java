/**
 * 
 */
package rithm.core;

/**
 * @author y2joshi
 *
 */
public interface MonVerdictFormatter<Spectype, MonStateType> {
	
	public void bufferVerdict(Spectype specId,MonStateType monState);
	public void writeVerdict();
	public void writeAll();
}
