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

	@Override
	public PredicateState EvaluatePredicates() {
		// TODO Auto-generated method stub
		this.prState = new DefaultPredicateState();
		DefaultProgramState dpState;
		return this.prState;
	}

}
