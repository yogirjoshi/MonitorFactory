package rithm.ltl;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.Gson;

import rithm.core.PredicateEvaluator;
import rithm.core.PredicateState;
import rithm.datatools.DefaultProgramState;

public class DefaultPredicateEvaluator extends PredicateEvaluator{

	@Override
	public void SetProgStateJson(String pState) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		DefaultProgramState dpState = gson.fromJson(pState, DefaultProgramState.class );
		this.pState = dpState;
	}
	public void SetProgState(DefaultProgramState pState) {
		// TODO Auto-generated method stub
		this.pState = pState;
	}
	@Override
	public PredicateState EvaluatePredicates() {
		// TODO Auto-generated method stub
		this.prState = new DefaultPredicateState();
		DefaultProgramState dpstate   = (DefaultProgramState)pState;
		DefaultPredicateState dprstate = (DefaultPredicateState) prState;
		if(dpstate.GetVal("trying").equals(Integer.toString(1)))
			dprstate.SetValue("trying", true);
		if(dpstate.GetVal("granted").equals(Integer.toString(1)))
			dprstate.SetValue("granted", true);
		return this.prState;
	}

}
