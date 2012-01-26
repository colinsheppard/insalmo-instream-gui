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
