package rithm.ltl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import rithm.core.MonValuation;

public class LTL3MonValuation implements MonValuation<String>{


	protected String CurrValuation;
	protected HashMap<String, String> Semantics;
	protected ArrayList<String> TruthValues;
	public LTL3MonValuation()
	{
		Semantics = new HashMap<String, String>();
		TruthValues = new ArrayList<String>();
		TruthValues.add("yellow");
		TruthValues.add("green");
		TruthValues.add("red");
		Semantics.put("yellow", "Unknown");
		Semantics.put("green", "Specification is Satisfied");
		Semantics.put("red", "Specification is Violated");
	}
	public String GetDefaultValuation() {
		// TODO Auto-generated method stub
		return this.TruthValues.get(0);
	}
	public void SetValues(Collection<String> TruthValues) {
		// TODO Auto-generated method stub
		if(TruthValues instanceof ArrayList<?>)
		{
			for(String each_truth_value: TruthValues)
				this.TruthValues.add(each_truth_value);
		}
	}

	public void SetSemanticDescription(HashMap<String, String> Desc) {
		// TODO Auto-generated method stub
		for(String each_key : Desc.keySet())
		{
			this.Semantics.put(each_key, Desc.get(each_key));
		}
		
	}

	public String GetSemanticDescription(String Semantic) {
		// TODO Auto-generated method stub
		return this.Semantics.get(Semantic);
	}

}
