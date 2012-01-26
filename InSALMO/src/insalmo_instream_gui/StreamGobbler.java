/*
 * InSALMO/InSTREAM Graphic User Interface, last revised October 2011.
 * Developed and maintained by Steve Railsback, Lang, Railsback & Associates,
 * Steve@LangRailsback.com and Colin Sheppard, critter@stanfordalumni.org.
 * Development sponsored by US Bureau of Reclamation under the
 * Central Valley Project Improvement Act, EPRI, USEPA, USFWS,
 * USDA Forest Service, and others.
 * Copyright (C) 2011 Lang, Railsback & Associates.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program (see file LICENSE); if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 * */
package insalmo_instream_gui;

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
