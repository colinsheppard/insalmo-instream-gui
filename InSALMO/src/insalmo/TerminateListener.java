package insalmo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TerminateListener implements ActionListener{
	private final InSALMOView parent;
	
	public TerminateListener (InSALMOView parent){
		this.parent = parent;
	}
	public void actionPerformed(ActionEvent e) {
		parent.terminateExperiment(e);
	}

}
