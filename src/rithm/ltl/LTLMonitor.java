package rithm.ltl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rithm.core.MonState;
import rithm.core.MonValuation;
import rithm.core.MonitoringEventListener;
import rithm.core.PredicateEvaluator;
import rithm.core.PredicateState;
import rithm.core.ProgState;
import rithm.core.RiTHMMonitor;

public class LTLMonitor implements RiTHMMonitor<String, ArrayList<String>, HashMap<String,String>>
{

	protected HashMap<String, String> CurrSpecStatus; 
	protected MonValuation<String> valuation;
	protected ArrayList<PredicateState> Buffer;
	protected PredicateEvaluator pe;
	protected HashMap<String, MonState> InitialStates;
	protected HashMap<String, ArrayList<MonState>> MonStates;
	public void LTLMonitor()
	{
		Buffer = new ArrayList<PredicateState>();
		CurrSpecStatus = new HashMap<String, String>();
		InitialStates = new HashMap<String, MonState>();
		MonStates = new HashMap<String, ArrayList<MonState>>();
	}
	public boolean FillBuffer(ProgState ps) {
		// TODO Auto-generated method stub
		Buffer.add(pe.EvaluatePredicates());
		return false;
	}
	public void SetMonitorValuation(MonValuation<?> val) {
		// TODO Auto-generated method stub
		MonValuation<String> val2 = (MonValuation<String>)val;
		this.valuation = val2;
	}
	protected ArrayList<MonitoringEventListener<String, String>> mlist;
	
	public void SetMonitoringEventListener(MonitoringEventListener<String, String> mel) {
		// TODO Auto-generated method stub
		mlist.add(mel);
	}
	
	public boolean SetFormulas(ArrayList<String> Specs) {
		// TODO Auto-generated method stub
		for(String each_spec: Specs)
		{
			CurrSpecStatus.put(each_spec, this.valuation.GetDefaultValuation());
		}
		return false;
	}
	public boolean SynthesizeMonitors(ArrayList<String> Filenames) {
		// TODO Auto-generated method stub
		ArrayList<DefaultMonState> states = new ArrayList<DefaultMonState>();
		String Pattern1 = "(\"\\([-]*[0-9]+,[ ]+[-]*[0-9]+\\)\")[ ]+->[ ]+(\"\\([-]*[0-9]+,[ ]+[-]*[0-9]+\\)\")[ ]+\\[label[ ]+=[ ]+\"\\(([a-z]+)(&&([a-z])+)*\\)\"\\]";
		String Pattern2 = "(\"\\([-]*[0-9]+,[ ]+[-]*[0-9]+\\)\")[ ]+->[ ]+(\"\\([-]*[0-9]+,[ ]+[-]*[0-9]+\\)\")[ ]+\\[label[ ]+=[ ]+\"(\\(<empty>\\))\"\\]";
		String Pattern3 = "(\"\\([-]*[0-9]+,[ ]+[-]*[0-9]+\\)\")[ ]+\\[label=\"\\([-]*[0-9]+,[ ]+[-]*[0-9]+\\)\",[ ]+style=[a-z]+,[ ]+color=([a-z]+)\\]";
		int spec_count = 0;
		String line;
		BufferedReader br;
		try
		{
			Pattern regex1 = Pattern.compile(Pattern1);
			Pattern regex2 = Pattern.compile(Pattern2);
			Pattern regex3 = Pattern.compile(Pattern3);
			for(String Filename: Filenames)
			{
				states.clear();
				br = new BufferedReader(new FileReader(Filename));
				String spec = br.readLine();
				while((line  = br.readLine()) != null)
				{
	
					Matcher m1 = regex1.matcher(line);
					Matcher m2 = regex2.matcher(line);
					Matcher m3 = regex3.matcher(line);
					if(m1.find())
					{
						String State1 = m1.group(1);
						DefaultMonState ds1 = new DefaultMonState(State1, "");
						if(!states.contains(ds1))
							states.add(ds1);
						String State2 = m1.group(2);
						DefaultMonState ds2 = new DefaultMonState(State2, "");
						if(!states.contains(ds2))
							states.add(ds2);
						DefaultPredicateState dp1 = new DefaultPredicateState();
						for(int i = 3; i < m1.groupCount(); i++)
							if(m1.group(i) != null)
								dp1.SetValue(m1.group(i), true);
						ds1.SetTransition(dp1, ds2);
					}
					if(m2.find())
					{
						String State1 = m2.group(1);
						DefaultMonState ds1 = new DefaultMonState(State1, "");
						if(!states.contains(ds1))
							states.add(ds1);
						String State2 = m2.group(2);
						DefaultMonState ds2 = new DefaultMonState(State2, "");
						if(!states.contains(ds2))
							states.add(ds2);
						ds1.SetTransition(new DefaultPredicateState(),ds2);
					}
					if(m3.find())
					{
						int id = states.indexOf(new DefaultMonState(m3.group(1), ""));
						DefaultMonState state = states.get(id);
						state.Valuation = this.valuation.GetSemanticDescription(m3.group(2));
						if(state.State.equals("(0, 0)"))
							this.InitialStates.put(Integer.toString(spec_count), state);
					}
				}
				br.close();
				spec_count++;
			}
			
		}
		catch(Exception fe)
		{
			System.err.println("Exception: " + fe.getMessage());
			return false;
		}

		return false;
	}

	
	public HashMap<String, String> runMonitor() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean SetTraceFile(String FileName) {
		// TODO Auto-generated method stub
		return false;
	}
	public void SetPredicateEvaluator(PredicateEvaluator pe)
	{
		this.pe = pe;
	}
}
