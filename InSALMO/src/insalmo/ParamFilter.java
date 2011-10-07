package insalmo;

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

