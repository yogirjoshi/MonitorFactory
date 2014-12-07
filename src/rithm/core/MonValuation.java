package rithm.core;

import java.util.Collection;
import java.util.HashMap;

public interface MonValuation <T1> {
	public void SetValues(Collection<T1> TruthValues);
	public void SetSemanticDescription(HashMap<T1, String> Desc);
	public String GetSemanticDescription(T1 Semantic);
	public T1 GetDefaultValuation();
}
