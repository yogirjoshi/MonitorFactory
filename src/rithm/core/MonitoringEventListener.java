package rithm.core;

public abstract class MonitoringEventListener <Formula, TruthVal> {
	abstract public void MonValuationChanged(Formula Spec, TruthVal M);
}
