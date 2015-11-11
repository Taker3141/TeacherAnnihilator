package toolbox;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogStream extends PrintStream
{
	private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private Calendar c = Calendar.getInstance();
	
	public LogStream()
	{
		super(System.out);
	}
	
	@Override
	public void println(String s)
	{
		StackTraceElement e = Thread.currentThread().getStackTrace()[2];
		String[] className = e.getClassName().split("\\.");
		super.println("[" + className[className.length - 1] + " at " + dateFormat.format(c.getTime()) + "] " + s);
	}
}
