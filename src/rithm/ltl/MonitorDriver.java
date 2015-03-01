package rithm.ltl;

import java.util.ArrayList;
import java.util.HashMap;

import rithm.core.ProgState;
import rithm.core.RiTHMMonitor;
import rithm.core.RiTHMSpecification;
import rithm.core.RiTHMTruthValue;
import rithm.datatools.XMLDataFactory;

public class MonitorDriver {
	public static void main(String[] args) {
		if(args.length < 3)
		{
			System.err.println("Usage: MonitorDriver <SpecificationFile> <DataFile> <HtmlOutput>");
			return;
		}
		XMLDataFactory xdFactory = new XMLDataFactory(args[1]);
//		XMLDataFactory xdFactory = new XMLDataFactory("/home/y2joshi/Input1.xml");
//		XMLDataFactory xdFactory = new XMLDataFactory("/home/y2joshi/TestDataTools.xml");
//		XMLDataFactory xdFactory = new XMLDataFactory("/home/y2joshi/TraceQnxThread.xml");
		RiTHMMonitor l;
		l = new LTLMonitor();
		l.SetMonitorValuation(new LTL3MonValuation());
		l.SetPredicateEvaluator(new DefaultPredicateEvaluator());
		
//		l.SynthesizeMonitors("/home/y2joshi/specqnx");
//		l.setOutFile("/home/y2joshi/out.html");
		
		l.SynthesizeMonitors(args[0]);
		l.setOutFile(args[2]);
		
		ProgState pState = xdFactory.GetNextProgState();
		while( pState != null)
		{
			l.FillBuffer(pState);
			pState = xdFactory.GetNextProgState();
		}
		l.runMonitor();
	}
}
