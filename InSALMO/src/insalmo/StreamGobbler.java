package insalmo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.swing.JTextArea;

public class StreamGobbler implements Runnable{
	String newline;
	String name;
	InputStream is;
	Thread thread;
	JTextArea textArea;
	FileOutputStream fstream;
	DataOutputStream out;
	BufferedWriter bw;
	String outFilename;

	public StreamGobbler (String name, InputStream is, JTextArea textArea,String outFilename,String newline) {
		this.name = name;
		this.is = is;
		this.textArea = textArea;
		this.outFilename = outFilename;
		this.newline = newline;
	}

	public void start () {
		thread = new Thread (this);
		thread.start ();
	}

	public void run () {
		try {
			InputStreamReader isr = new InputStreamReader (is);
			BufferedReader br = new BufferedReader (isr);
			fstream = new FileOutputStream(outFilename);
			out = new DataOutputStream(fstream);
			bw = new BufferedWriter(new OutputStreamWriter(out));

			while (true) {
				String s = br.readLine ();
				if (s == null) break;
				//System.out.println ("[" + name + "] " + s);
				this.textArea.append(("[" + name + "] " + s + newline));
				this.bw.append(("[" + name + "] " + s + newline));
				this.bw.flush();
			}
			br.close();
			is.close ();
			bw.close();
			out.close();
			fstream.close();
		} catch (Exception ex) {
			System.out.println ("Problem reading stream " + name + "... :" + ex);
			ex.printStackTrace ();
		}
	}

}
