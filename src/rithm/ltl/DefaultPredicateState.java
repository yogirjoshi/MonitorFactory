package rithm.ltl;

import java.util.HashMap;

import rithm.core.PredicateState;

public class DefaultPredicateState implements PredicateState{
	protected HashMap<String, Boolean> PredValues;
	String timeStamp;
	public DefaultPredicateState()
	{
		PredValues = new HashMap<String, Boolean>();
		timeStamp="0";
	}
	public DefaultPredicateState(DefaultPredicateState dpS)
	{
		this.PredValues = new HashMap<String, Boolean>();
		for(String key: dpS.PredValues.keySet())
		{
			this.PredValues.put(key, dpS.PredValues.get(key));
		}
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
//		if(this.PredValues.size() > dps2.PredValues.size())
//		{
//			for(String key1 : this.PredValues.keySet())
//			{
//				if(dps2.PredValues.containsKey(key1))
//					if(dps2.PredValues.get(key1) != this.PredValues.get(key1))
//						return false;
//			}
//		}
//		else
//		{
//			for(String key1 : dps2.PredValues.keySet())
//			{
//				if(this.PredValues.containsKey(key1))
//					if(dps2.PredValues.get(key1) != this.PredValues.get(key1))
//						return false;
//			}
//		}
		return true;
	}
	public String toString()
	{
		String retVal="";
		for(String key : this.PredValues.keySet())
		{
			retVal+=key + " " + this.PredValues.get(key).toString() + "\n";
		}
		return retVal;
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
