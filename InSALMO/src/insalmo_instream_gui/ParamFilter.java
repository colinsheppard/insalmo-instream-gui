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

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

public class ParamFilter extends FileFilter {

	public ParamFilter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}
		String extension = getExtension(f);
		if (extension != null) {
			if (extension.equals("Params")){
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	@Override
	public String getDescription() {
		return "Parameter File (.Params)";
	}

	private String getExtension(File f){
		String[] broke = f.getName().split("\\.");
		return broke[broke.length-1];
	}
}

