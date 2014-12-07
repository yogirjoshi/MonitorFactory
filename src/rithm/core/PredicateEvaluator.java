package rithm.core;

import java.util.Collection;

public abstract class PredicateEvaluator{

	protected ProgState pState;
	protected PredicateState prState; 
	public void PredicateEvaluator()
	{
		
	}
	public void SetProgStateObj(ProgState pState)
	{
		this.pState = pState;
	}
	abstract public void SetProgStateJson(String pState);
	abstract public PredicateState EvaluatePredicates();
}
