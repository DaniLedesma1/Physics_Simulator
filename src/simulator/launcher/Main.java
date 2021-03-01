package simulator.launcher;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

/*
 * Examples of command-line parameters:
 * 
 *  -h
 *  -i resources/examples/ex4.4body.txt -s 100
 *  -i resources/examples/ex4.4body.txt -o resources/examples/ex4.4body.out -s 100
 *  -i resources/examples/ex4.4body.txt -o resources/examples/ex4.4body.out -s 100 -gl ftcg
 *  -i resources/examples/ex4.4body.txt -o resources/examples/ex4.4body.out -s 100 -gl nlug
 *
 */

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.json.JSONObject;

import simulator.Control.Controller;
import simulator.factories.BasicBodyBuilder;
import simulator.factories.Builder;
import simulator.factories.BuilderBasedFactory;
import simulator.factories.Factory;
import simulator.factories.FallingToCenterGravityBuilder;
import simulator.factories.MassLossingBodyBuilder;
import simulator.factories.NewtonUniversalGravitationBuilder;
import simulator.factories.NoGravityBuilder;
import simulator.model.Body;
import simulator.model.GravityLaws;
import simulator.model.PhysicsSimulator;
import simulator.view.MainWindow;

public class Main {

	// default values for some parameters
	private final static Double _dtimeDefaultValue = 2500.0;

	// some attributes to stores values corresponding to command-line parameters
	private static Double _dtime = null;
	private static String _inFile = null;
	private static String _outFile = null;
	private static int step = 150; 
	private static JSONObject _gravityLawsInfo = null;
	private static boolean graphMode = false;
	
	// factories
	private static Factory<Body> _bodyFactory;
	private static Factory<GravityLaws> _gravityLawsFactory;

	private static void init() {
		//gravityLawsFactory
		ArrayList<Builder<GravityLaws>> gravityLawsBuilders = new ArrayList<>();
		gravityLawsBuilders.add(new NewtonUniversalGravitationBuilder());
		gravityLawsBuilders.add(new FallingToCenterGravityBuilder());
		gravityLawsBuilders.add(new NoGravityBuilder());
		_gravityLawsFactory = new BuilderBasedFactory<GravityLaws>(gravityLawsBuilders);
		
		//BodyFactory
		ArrayList<Builder<Body>> bodyBuilders = new ArrayList<>();
		bodyBuilders.add(new BasicBodyBuilder());
		bodyBuilders.add(new MassLossingBodyBuilder());
		_bodyFactory = new BuilderBasedFactory<Body>(bodyBuilders);
	}

	private static void parseArgs(String[] args) {


		Options cmdLineOptions = buildOptions(); 		// define the valid command line options
		CommandLineParser parser = new DefaultParser();	// parse the command line as provided in args
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseHelpOption(line, cmdLineOptions);
			parseGraphModeOption(line);
			parseInFileOption(line);
			parseOutFileOption(line);
			parseStepOption(line);
			parseDeltaTimeOption(line);
			parseGravityLawsOption(line);		


			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		// help
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message.").build());
		
		//GraphicMode
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Select graphic mode").build());

		// input file
		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Bodies JSON input file.").build());

		//output file
		cmdLineOptions.addOption(Option.builder("o").longOpt("output").hasArg().desc("Output file, where output is "
				+ "written. Default value: the standard output.").build());
		
		//steps
		cmdLineOptions.addOption(Option.builder("s").longOpt("steps").hasArg().desc("An integer representing the number of\n" + 
				"simulation steps. Default value: 150.").build());
		
		// delta-time
		cmdLineOptions.addOption(Option.builder("dt").longOpt("delta-time").hasArg()
				.desc("A double representing actual time, in seconds, per simulation step. Default value: "
						+ _dtimeDefaultValue + ".")
				.build());

		// gravity laws -- there is a workaround to make it work even when
		// _gravityLawsFactory is null. 
		String gravityLawsValues = "N/A";
		String defaultGravityLawsValue = "N/A";
		if (_gravityLawsFactory != null) {
			gravityLawsValues = "";
			for (JSONObject fe : _gravityLawsFactory.getInfo()) {
				if (gravityLawsValues.length() > 0) {
					gravityLawsValues = gravityLawsValues + ", ";
				}
				gravityLawsValues = gravityLawsValues + "'" + fe.getString("type") + "' (" + fe.getString("desc") + ")";
			}
			defaultGravityLawsValue = _gravityLawsFactory.getInfo().get(0).getString("type");
		}
		cmdLineOptions.addOption(Option.builder("gl").longOpt("gravity-laws").hasArg()
				.desc("Gravity laws to be used in the simulator. Possible values: " + gravityLawsValues
						+ ". Default value: '" + defaultGravityLawsValue + "'.")
				.build());

		return cmdLineOptions;
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}
	
	private static void parseGraphModeOption(CommandLine line) throws ParseException {
		String mode = line.getOptionValue("m");
		
		if(mode != null) {
			if (mode.equals("gui")) { graphMode = true; }
			else if (mode.equals("batch")) { graphMode = false; }
			else throw new ParseException("Not valid graphical option");
		}
		//si null se queda en batch por defecto

	}


	private static void parseInFileOption(CommandLine line) throws ParseException {
		_inFile = line.getOptionValue("i");
		if (_inFile == null && !graphMode) {
			throw new ParseException("An input file of bodies is required");
		}
	}

	private static void parseOutFileOption(CommandLine line) throws ParseException {
		_outFile = line.getOptionValue("o");
	}
	
	private static void parseStepOption(CommandLine line) throws ParseException {
		String steep = line.getOptionValue("s");
		if(steep != null) {
			step = Integer.parseInt(steep);
		}
		else step = 150;
	}
	
	private static void parseDeltaTimeOption(CommandLine line) throws ParseException {
		String dt = line.getOptionValue("dt", _dtimeDefaultValue.toString());
		try {
			_dtime = Double.parseDouble(dt);
			assert (_dtime > 0);
		} catch (Exception e) {
			throw new ParseException("No delta-time valid value. Default value will be used: 2500");
		}
	}

	private static void parseGravityLawsOption(CommandLine line) throws ParseException {

		// this line is just a work around to make it work even when _gravityLawsFactory
		// is null, you can remove it when've defined _gravityLawsFactory
		if (_gravityLawsFactory == null)
			return;

		String gl = line.getOptionValue("gl");
		if (gl != null) {
			for (JSONObject fe : _gravityLawsFactory.getInfo()) { 
				if (gl.equals(fe.getString("type"))) {
					_gravityLawsInfo = fe;
	
					break;
				}
			}
			if (_gravityLawsInfo == null) {
				throw new ParseException("Invalid gravity laws: " + gl);
			}
		} else {
			_gravityLawsInfo = _gravityLawsFactory.getInfo().get(0);
		}
	}

	private static void startBatchMode() throws Exception {
		// create and connect components, then start the simulator
		
		GravityLaws gravityLaws = _gravityLawsFactory.createInstance(_gravityLawsInfo);
		PhysicsSimulator sim = new PhysicsSimulator(gravityLaws,_dtime);
		Controller ctrl = new Controller(sim,_bodyFactory, _gravityLawsFactory);
		
		FileInputStream is = null;
		FileOutputStream os = null;
	
		is = new FileInputStream(_inFile);
		if(_outFile == null) os = null;
		else os = new FileOutputStream(_outFile);

		ctrl.loadBodies(is);
		ctrl.run(step, os);
		
	}
	
	private static void startGUIMode() throws Exception {
		
		GravityLaws gravityLaws = _gravityLawsFactory.createInstance(_gravityLawsInfo);
		PhysicsSimulator sim = new PhysicsSimulator(gravityLaws,_dtime);
		Controller ctrl = new Controller(sim,_bodyFactory, _gravityLawsFactory);
	
		FileInputStream is = null;
		if (_inFile != null) {
			is = new FileInputStream(_inFile);
			ctrl.loadBodies(is);
		}
		
		SwingUtilities.invokeAndWait(
			new Runnable() {
					@Override 
					public void run() { new MainWindow(ctrl);}
				}
			);
	}

	private static void start(String[] args) throws Exception {
		parseArgs(args);
		
		if(!graphMode) startBatchMode();
		else startGUIMode();
	}

	public static void main(String[] args) {
		try {
			init();
			start(args);
		} catch (Exception e) {

			System.err.println("Something went wrong ...");
			System.err.println();
			e.printStackTrace();
		}
	}
}
