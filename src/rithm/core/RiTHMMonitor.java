package rithm.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public interface RiTHMMonitor<Formula, T1 extends Collection<Formula>, T2 extends Map<?,?>> {
	public boolean SetFormulas(T1 Specs);
	public boolean SynthesizeMonitors(T1 Specs);
	public boolean SynthesizeMonitors(ArrayList<String> Filenames);
	public T2 runMonitor();
	public boolean SetTraceFile(String FileName);
	public boolean FillBuffer(ProgState ps);
	public void SetMonitoringEventListener(MonitoringEventListener<?,?> mel);
	public void SetMonitorValuation(MonValuation<?> val);
}
