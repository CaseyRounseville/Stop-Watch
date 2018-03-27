package log;

import java.io.PrintStream;
import java.io.File;
import java.io.FileNotFoundException;

public class Log {
	public static PrintStream out = null;
	public static PrintStream err = null;
	
	static {
		try {
			out = new PrintStream(new File("logs/out.txt"));
			err = new PrintStream(new File("logs/err.txt"));
		} catch (FileNotFoundException e) {
			System.err.println("Log: COULD NOT FIND LOG FILES");
			System.err.println(e);
		}
	}
	
	public static void close() {
		out.close();
		err.close();
	}
}