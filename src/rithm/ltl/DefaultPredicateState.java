package rithm.ltl;

import java.rmi.NoSuchObjectException;
import java.util.HashMap;

import rithm.core.PredicateState;

public class DefaultPredicateState implements PredicateState{
	protected HashMap<String, Boolean> PredValues;
	public DefaultPredicateState()
	{
		PredValues = new HashMap<String, Boolean>();
	}
	public boolean SetValue(String Name, boolean Value) {
		// TODO Auto-generated method stub
		if(!PredValues.containsKey(Name))
		{
			PredValues.put(Name, Value);
			return true;
		}
		return false;
	} 

	public boolean GetValue(String Name) throws RuntimeException{
		// TODO Auto-generated method stub
		if(PredValues.containsKey(Name))
			return PredValues.get(Name);
		else
			throw new RuntimeException("No Predicate Value set for " + Name);
	}
	
	public boolean equals(Object obj)
	{
		if(!(obj instanceof DefaultPredicateState))
			return false;
		DefaultPredicateState dps2 = (DefaultPredicateState) obj;
		if(this.PredValues.size() != dps2.PredValues.size())
			return false;
		return true;
	}
	public int hashCode(){
		int hashVal=0;
		for(String key: PredValues.keySet())
		{
			hashVal+=key.hashCode();
		}
		return hashVal;
	}
}
