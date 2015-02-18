package rithm.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public interface RiTHMMonitor <T1 extends Collection<RiTHMSpecification>, T2 extends Map<RiTHMSpecification,RiTHMTruthValue>> {
	public boolean SetFormulas(T1 Specs);
	public boolean SynthesizeMonitors(T1 Specs);
	public boolean SynthesizeMonitors(String Filename);
	public T2 runMonitor();
	public boolean SetTraceFile(String FileName);
	public boolean FillBuffer(ProgState ps);
	public void SetMonitoringEventListener(MonitoringEventListener mel);
	public void SetMonitorValuation(MonValuation val);
}
