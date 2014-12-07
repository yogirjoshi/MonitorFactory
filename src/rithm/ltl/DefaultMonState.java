package rithm.ltl;

import java.util.HashMap;

import rithm.core.MonState;
import rithm.core.PredicateState;

public class DefaultMonState implements MonState{
	protected String State;
	protected String Valuation;
	protected HashMap<PredicateState, MonState> Transitions;
	public DefaultMonState(String state, String valuation)
	{
		this.State = state;
		this.Valuation = valuation;
		Transitions = new HashMap<PredicateState, MonState>();
	}
	public void SetValuation(String Valuation)
	{
		this.Valuation = Valuation;
	}
	public MonState GetNextMonState(PredicateState Pred) {
		// TODO Auto-generated method stub
		DefaultPredicateState dpState;
		if(Pred instanceof DefaultPredicateState)
		{
			dpState = (DefaultPredicateState)Pred;
			return Transitions.get(dpState);
		}
		return null;
	}
	public boolean SetTransition(PredicateState ps, MonState ms)
	{
		Transitions.put(ps, ms);
		return true;
	}
	public boolean equals(Object obj)
	{
		if(!(obj instanceof DefaultMonState))
			return false;
		DefaultMonState state2 = (DefaultMonState) obj;
		if(state2.State.equals(this.State))
			return true;
		else
			return false;
	}
	public int hashCode()
	{
		return this.State.hashCode();
	}
}
