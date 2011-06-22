package it.sineo.android.changelog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Release implements Serializable {
	private final static long serialVersionUID = 1L;

	private String version;
	private Date date;
	private List<Change> changes = new ArrayList<Change>();

	public List<Change> getChanges() {
		return changes;
	}

	public String getVersion() {
		return version;
	}

	public Date getDate() {
		return date;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
