package rithm.ltl;

import rithm.core.RiTHMSpecification;

public class DefaultRiTHMSpecification implements RiTHMSpecification{
	protected String specText;
	public DefaultRiTHMSpecification(String specText)
	{
		this.specText = specText;
	}
	public String getTextDescription() {
		// TODO Auto-generated method stub
		return specText;
	}
}
