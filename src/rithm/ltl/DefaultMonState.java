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
//	public DefaultMonState(DefaultMonState ds)
//	{
//		this.State = ds.State;
//		this.Valuation = ds.Valuation;
//		this.Transitions = new HashMap<PredicateState, MonState>();
//		for(PredicateState ps: ds.Transitions.keySet())
//		{
//			this.Transitions.put(ps, ds.Transitions.get(ps));
//		}
//	}
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
			MonState m = Transitions.get(dpState);
			return m;
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
