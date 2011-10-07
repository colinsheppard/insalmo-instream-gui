package insalmo;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class LaunchInsamloExecutable implements Runnable{
	Thread thread;
	String launchDir;
	public Process process = null;
	InSALMOView parent;
	Boolean isLFT = null;
	JExpandableTextArea textArea = null;
	String runName = null;
	
	public LaunchInsamloExecutable(String launchDir,InSALMOView parent,boolean useGraphics, boolean isLFT,
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
			ProcessBuilder pb = new ProcessBuilder(appPath+"\\..\\Code\\inSALMO\\.libs\\insalmo.exe", useBat);
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
		this.parent.modelRunCompleted(this);
	}
	public Process getProcess(){
		return this.process;
	}
	public String getName(){
		return this.runName;
	}
}
