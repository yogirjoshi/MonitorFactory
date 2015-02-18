package rithm.ltl;

import rithm.core.RiTHMTruthValue;

public class DefaultRiTHMTruthValue implements RiTHMTruthValue{
	String truthValue;
	public DefaultRiTHMTruthValue(String truthValue)
	{
		this.truthValue = truthValue;
	}
	public String getTruthValueDescription() {
		// TODO Auto-generated method stub
		return truthValue;
	}

}
