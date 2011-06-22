package it.sineo.android.changelog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Changelog implements Serializable {
	private final static long serialVersionUID = 1L;

	private List<Release> releases = new ArrayList<Release>();

	public List<Release> getReleases() {
		return releases;
	}
}
