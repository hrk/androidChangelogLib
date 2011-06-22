package it.sineo.android.changelog;

import java.io.Serializable;

public class Change implements Serializable {
	private final static long serialVersionUID = 1L;

	private String text;

	public Change(String text) {
		if (text != null) {
			this.text = text.replaceAll("[\\s]+", " ");
		} else {
			this.text = null;
		}
	}

	public String getText() {
		return text;
	}
}
