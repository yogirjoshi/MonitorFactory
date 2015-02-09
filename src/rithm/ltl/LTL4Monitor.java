package rithm.ltl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Properties;

import rithm.core.MonState;
import rithm.core.MonValuation;
import rithm.core.MonitoringEventListener;
import rithm.core.PredicateEvaluator;
import rithm.core.PredicateState;
import rithm.core.ProgState;
import rithm.core.RiTHMMonitor;
import rithm.parsertools.LTLParser;

public class LTL4Monitor implements RiTHMMonitor<String, ArrayList<String>, HashMap<String,String>>
{


	public boolean SynthesizeMonitors(ArrayList<String> Specs) {
		// TODO Auto-generated method stub
		return false;
	}
	protected HashMap<String, String> CurrSpecStatus; 
	protected MonValuation<String> valuation;
	protected ArrayList<PredicateState> Buffer;
	protected PredicateEvaluator pe;
	
	protected HashMap<String, MonState> InitialStates;
	protected HashMap<String, MonState> CurrentStates;
	protected ArrayList<MonitoringEventListener<String, String>> mlist;
	
	protected Properties propSet;
	protected String ltltoolsDirname;
	protected String ltlMonOutDirname;
	
	protected LTLParser ltlParser;
	protected ArrayList<String> specList;
	
	protected String outFileName;
	public LTL4Monitor()
	{
		Buffer = new ArrayList<PredicateState>();
		CurrSpecStatus = new HashMap<String, String>();
		InitialStates = new HashMap<String, MonState>();
		CurrentStates = new HashMap<String, MonState>();
		mlist = new ArrayList<MonitoringEventListener<String,String>>();
		propSet = new Properties();
		try
		{
//			propSet.load(this.getClass().getResourceAsStream("ltl3tools.properties"));
			propSet.load(new FileInputStream("ltl3tools.properties"));
			ltltoolsDirname = (String)propSet.getProperty("ltl3toolsDirectory");
			ltlMonOutDirname = (String)propSet.getProperty("ltl3MonOutputDirectory");
		}
		catch(IOException ioException)
		{
			System.err.println(ioException.getMessage());
		}
		ltlParser = new LTLParser("LTL");
		specList = new ArrayList<String>();
	}
	public void setOutFile(String outFile)
	{
		this.outFileName = outFile;
	}
	public boolean FillBuffer(ProgState ps) {
		// TODO Auto-generated method stub
		pe.SetProgStateObj(ps);
		assert Buffer != null;
		assert pe != null;
		Buffer.add((PredicateState)pe.EvaluatePredicates());
		return false;
	}
	public void SetMonitorValuation(MonValuation<?> val) {
		// TODO Auto-generated method stub
		MonValuation<String> val2 = (MonValuation<String>)val;
		this.valuation = val2;
	}

	
	public void SetMonitoringEventListener(MonitoringEventListener<?, ?> mel) {
		// TODO Auto-generated method stub
		MonitoringEventListener<String, String> mel1 = ((MonitoringEventListener<String, String>)mel);
		mlist.add(mel1);
	}
	
	public boolean SetFormulas(ArrayList<String> Specs) {
		// TODO Auto-generated method stub
		for(String each_spec: Specs)
		{
			CurrSpecStatus.put(each_spec, this.valuation.GetDefaultValuation());
		}
		return false;
	}
	protected void createMonsfromTools(String line, ArrayList<String> Filenames, int specCount) throws IOException, InterruptedException
	{
		System.out.println(line);
    	ProcessBuilder p = new ProcessBuilder();
    	p.directory(new File(ltltoolsDirname));
    	p.command("/bin/bash", "./ltl2monLTL3", "\""+ line +"\"");
    	p.redirectOutput(new File(ltlMonOutDirname + Integer.toString(specCount) + ".txt"));
    	Filenames.add(ltlMonOutDirname + Integer.toString(specCount) + ".txt");
    	specList.add(specCount, line);
    	Process ps = p.start();
    	ps.waitFor();
	}
	protected void createLookupTable(ArrayList<DefaultMonState> states, ArrayList<String> Filenames) throws IOException {
		String Pattern1 = "(\"\\([-]*[0-9]+,[ ]+[-]*[0-9]+\\)\")[ ]+->[ ]+(\"\\([-]*[0-9]+,[ ]+[-]*[0-9]+\\)\")[ ]+\\[label[ ]+=[ ]+\"\\(([a-z]+(&&[a-z]+)*)\\)\"\\]";
		String Pattern2 = "(\"\\([-]*[0-9]+,[ ]+[-]*[0-9]+\\)\")[ ]+->[ ]+(\"\\([-]*[0-9]+,[ ]+[-]*[0-9]+\\)\")[ ]+\\[label[ ]+=[ ]+\"(\\(<empty>\\))\"\\]";
		String Pattern3 = "(\"\\([-]*[0-9]+,[ ]+[-]*[0-9]+\\)\")[ ]+\\[label=\"\\([-]*[0-9]+,[ ]+[-]*[0-9]+\\)\",[ ]+style=[a-z]+,[ ]+color=([a-z]+)\\]";
		int spec_count = 0;
		String line;
		BufferedReader br;
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
				System.out.println("---------------------------------------------");
				System.out.println(line);
				Matcher m1 = regex1.matcher(line);
				Matcher m2 = regex2.matcher(line);
				Matcher m3 = regex3.matcher(line);
				if(m1.find())
				{
					String State1 = m1.group(1);
					DefaultMonState ds1 = new DefaultMonState(State1, "");
					if(!states.contains(ds1))
						states.add(ds1);
					else
						ds1 = states.get(states.indexOf(ds1));
					String State2 = m1.group(2);
					DefaultMonState ds2 = new DefaultMonState(State2, "");
					if(!states.contains(ds2))
						states.add(ds2);
					else
						ds2 = states.get(states.indexOf(ds2));

					DefaultPredicateState dp1 = new DefaultPredicateState();
					for (String retval: m1.group(3).split("&&")){
						dp1.SetValue(retval, true);
						System.out.println("Predicate ->" + retval );
					}
					ds1.SetTransition(dp1, ds2);
					System.out.println(ds1.State + " to " + ds2.State );
				}
				if(m2.find())
				{
					String State1 = m2.group(1);
					DefaultMonState ds1 = new DefaultMonState(State1, "");
					if(!states.contains(ds1))
						states.add(ds1);
					else
						ds1 = states.get(states.indexOf(ds1));
					String State2 = m2.group(2);
					DefaultMonState ds2 = new DefaultMonState(State2, "");
					if(!states.contains(ds2))
						states.add(ds2);
					else
						ds2 = states.get(states.indexOf(ds2));

					ds1.SetTransition(new DefaultPredicateState(),ds2);
					System.out.println(ds1.State + " to " + ds2.State );
				}
				if(m3.find())
				{
					int id = states.indexOf(new DefaultMonState(m3.group(1), ""));
					DefaultMonState state = states.get(id);
					state.Valuation = this.valuation.GetSemanticDescription(m3.group(2));
					System.out.println(state.State + " valuation ->" + state.Valuation);
					if(state.State.contains("(0, 0)"))
					{
						this.InitialStates.put(Integer.toString(spec_count), state);
						this.CurrentStates.put(Integer.toString(spec_count), state);
						CurrSpecStatus.put(Integer.toString(spec_count), this.valuation.GetDefaultValuation());
						//							System.out.println(state.State + " set initial value " + state.Valuation);
					}
				}
			}
			br.close();
			spec_count++;
		}
			
	}
	public boolean SynthesizeMonitors(String filename) {
		// TODO Auto-generated method stub
		ArrayList<String> Filenames = new ArrayList<String>();
		int specCount = 0;
		System.out.println(filename);
		BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filename));
            String line = null;
            while ((line = reader.readLine()) != null) {
            	ltlParser.AppendSpec(line);
            	createMonsfromTools(line, Filenames, specCount);
            	specCount++;
            }
        } catch (IOException e) {
        	System.err.println(e.getMessage());
        	return false;
        } 
        catch(InterruptedException e){
        	System.err.println(e.getMessage());
        	return false;
        }
        finally {
            try {
                reader.close();
            } catch (Exception e) {
            	System.err.println(e.getMessage());
            	return false;
            }
        }
		ArrayList<DefaultMonState> states = new ArrayList<DefaultMonState>();
		try
		{
			createLookupTable(states,Filenames);
		}
		catch(IOException e)
		{
			System.err.println(e.getMessage());
			return false;
		}
		return true;
	}

	public void setPredState(DefaultPredicateState dpState,ArrayList<String> predsNeeded)
	{
		Iterator it = dpState.PredValues.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry<String, Boolean> pairs = (Map.Entry<String, Boolean>)it.next();
			if(!predsNeeded.contains(pairs.getKey()))
				it.remove();
		}
	}
	public HashMap<String, String> runMonitor() {
		// TODO Auto-generated method stub
		BufferedWriter outWriter;
		try
		{
			outWriter = new BufferedWriter(new FileWriter(new File(outFileName)));
			outWriter.write("<html>");
			outWriter.write("<body>");
			for(int i =0; i < Buffer.size();i++)
			{
//				System.out.println("__________________________________________________________________");
//				System.out.println("Event " + Integer.toString(i));
				
				DefaultPredicateState topState = (DefaultPredicateState)Buffer.get(i);
				outWriter.write("Event:" + Integer.toString(i) +" Timestamp:" + topState.timeStamp);
				for(int j = 0; j < CurrentStates.size();j++)
				{
					DefaultPredicateState dpPredState = new DefaultPredicateState((DefaultPredicateState)Buffer.get(i));
					ArrayList<String> predsForthisSpec = ltlParser.getPredsForSpec(specList.get(j));
					
					setPredState(dpPredState, predsForthisSpec);
					
					DefaultMonState nextState = (DefaultMonState)CurrentStates.get(Integer.toString(j)).GetNextMonState(dpPredState);
					
					if(nextState != null)
					{
						CurrentStates.put(Integer.toString(j),nextState);
						DefaultMonState ms1 = (DefaultMonState)CurrentStates.get(Integer.toString(j));
//						System.out.println("Specification: " + specList.get(j) + " => " + ms1.Valuation);
	//					System.out.println("State " + Integer.toString(i) + " " + dpPredState.toString());
						outWriter.write("<div style=\"background: #B0B0B0 \">");
						
						if(ms1.Valuation.equals("Satisfied"))
						{
//							outWriter.write("<div style=\"background: LightGreen\">");
							outWriter.write("Specification: " + specList.get(j) + " => " + "<font color=\"Lime\">" + ms1.Valuation + "</font>");
//							outWriter.write("</div>");
						}
						if(ms1.Valuation.equals("Violated"))
						{
//							outWriter.write("<div style=\"background: #FF9900\">");
							outWriter.write("Specification: " + specList.get(j) + " => " + "<font color=\"Red\">" + ms1.Valuation + "</font>");
//							outWriter.write("</div>");
						}
						if(ms1.Valuation.equals("Validation status Unknown"))
						{
//							outWriter.write("<div style=\"background: yellow\">");
							outWriter.write("Specification: " + specList.get(j) + " => " + "<font color=\"Yellow\">" + ms1.Valuation + "</font>");
//							outWriter.write("</div>");
						}
						outWriter.write("</div>");
						CurrSpecStatus.put(Integer.toString(j),ms1.Valuation);
						
						
						for(MonitoringEventListener<String, String> ml: mlist)
						{
							ml.MonValuationChanged(CurrSpecStatus.get(Integer.toString(j)), ms1.Valuation);
						}
					}
					else
					{
						System.err.println("State is null");
					}
				}
			}
			outWriter.write("</body>");
			outWriter.write("</html>");
			outWriter.close();
		}catch(IOException io)
		{
			System.out.println(io.getMessage());
		}
		return CurrSpecStatus;
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
