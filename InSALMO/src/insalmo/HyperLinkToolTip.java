package insalmo;

import java.awt.BorderLayout;

import javax.swing.JEditorPane;
import javax.swing.JToolTip;
import javax.swing.LookAndFeel;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.plaf.ToolTipUI;

public class HyperLinkToolTip extends JToolTip {
	private JEditorPane theEditorPane;
 
	public HyperLinkToolTip() {
		setLayout(new BorderLayout());
		LookAndFeel.installBorder(this, "ToolTip.border");
		LookAndFeel.installColors(this, "ToolTip.background", "ToolTip.foreground");
		theEditorPane = new JEditorPane();
		theEditorPane.setContentType("text/html");
		theEditorPane.setEditable(false);
		theEditorPane.addHyperlinkListener(new HyperlinkListener() {
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					// do whatever you want with the url
					System.out.println("clicked on link : " + e.getURL());
				}
			}
		});
		add(theEditorPane);
	}
	public void setTipText(String tipText) {
		theEditorPane.setText(tipText);
	}
	public void updateUI() {
		setUI(new ToolTipUI() {});
	}
}
