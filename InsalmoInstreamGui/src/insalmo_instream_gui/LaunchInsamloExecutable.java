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

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class LaunchInsamloExecutable implements Runnable{
	Thread thread;
	String launchDir;
	public Process process = null;
	InsalmoInstreamView parent;
	Boolean isLFT = null;
	JExpandableTextArea textArea = null;
	String runName = null;
	
	public LaunchInsamloExecutable(String launchDir,InsalmoInstreamView parent,boolean useGraphics, boolean isLFT,
			JExpandableTextArea textArea, String runName){
		this.runName = runName;
		this.textArea = textArea;
		this.isLFT = isLFT;
		this.launchDir = launchDir;
		this.parent = parent;
		this.thread = new Thread(this);
		this.thread.start();
		String useBat = (useGraphics) ? "" : "-b";
		String appPath = MetaProject.getInstance().getApplicationDir();
		try {
			ProcessBuilder pb = null;
			if(MetaProject.getInstance().isInsalmo()){
				if(MetaProject.getInstance().isInsalmoFA()){
					pb = new ProcessBuilder(appPath+"\\..\\Code\\inSALMO-FA\\.libs\\insalmo-fa.exe", useBat);
				}else{
					pb = new ProcessBuilder(appPath+"\\..\\Code\\inSALMO\\.libs\\insalmo.exe", useBat);
				}
			}else if(MetaProject.getInstance().isInstream()){
				pb = new ProcessBuilder(appPath+"\\..\\Code\\inSTREAM\\.libs\\instream.exe", useBat);
			}else{
				pb = new ProcessBuilder(appPath+"\\..\\Code\\inSTREAM-SD\\.libs\\instream6-0.exe", useBat);
			}
			Map<String, String> env = pb.environment();
			env.put("Path", appPath+"\\..\\Code\\Swarm\\bin;"+env.get("Path"));
			env.put("SWARMHOME",appPath+"\\..\\Code\\Swarm");
			pb.directory(new File(launchDir));
			this.process = pb.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void run() {
		try {
			while(this.process == null){
				this.thread.sleep(100);
			}
			this.process.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.parent.actionHandler.modelRunCompleted(this);
	}
	public Process getProcess(){
		return this.process;
	}
	public String getName(){
		return this.runName;
	}
}
