package it.sineo.android.changelog;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.xmlpull.v1.XmlPullParserException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChangelogFactory {

	// private final static String CHANGELOG = "changelog";
	private final static String RELEASE = "release";
	// private final static String CHANGE = "change";

	protected transient static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public static Changelog buildFromResource(Context ctx, int resourceId) {
		Changelog changelog = new Changelog();
		try {
			XmlResourceParser parser = ctx.getResources().getXml(resourceId);
			Release release = null;
			Change change = null;
			parser.next();
			int eventType = parser.getEventType();
			while (eventType != XmlResourceParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlResourceParser.START_TAG: {
					if (RELEASE.equals(parser.getName())) {
						String d = parser.getAttributeValue(null, "date");
						String v = parser.getAttributeValue(null, "version");
						release = new Release();
						release.setVersion(v);
						release.setDate(sdf.parse(d));
						changelog.getReleases().add(release);
					}
					break;
				}
				case XmlResourceParser.TEXT: {
					change = new Change(parser.getText());
					release.getChanges().add(change);
				}
				}
				eventType = parser.next();
			}
		} catch (XmlPullParserException xppex) {
			xppex.printStackTrace();
		} catch (IOException ioex) {
			ioex.printStackTrace();
		} catch (ParseException pex) {
			pex.printStackTrace();
		}
		return changelog;
	}

	public static View inflate(Context ctx, int resourceId) {
		return inflate(ctx, buildFromResource(ctx, resourceId));
	}

	public static View inflate(Context ctx, Changelog changelog) {
		LayoutInflater li = LayoutInflater.from(ctx);
		View view = li.inflate(R.layout.changelog_view, null, false);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		LinearLayout changelogContainer = (LinearLayout) view.findViewById(R.id.changelog_container);
		for (Release release : changelog.getReleases()) {

			TextView tvRelease = (TextView) li.inflate(R.layout.changelog_release, null, false);
			SpannableString ss = new SpannableString(sdf.format(release.getDate()) + " - " + release.getVersion());
			ss.setSpan(new StyleSpan(Typeface.BOLD), 0, ss.length(), 0);
			tvRelease.setText(ss);
			changelogContainer.addView(tvRelease);
			for (Change change : release.getChanges()) {
				TextView tvChange = (TextView) li.inflate(R.layout.changelog_change, null, false);
				tvChange.setText("- " + change.getText());
				changelogContainer.addView(tvChange);
			}
		}
		return view;
	}

	public static AlertDialog.Builder buildAlertDialog(Context ctx, int xmlResourceId, int iconId) {
		Changelog changelog = ChangelogFactory.buildFromResource(ctx, xmlResourceId);

		View view = ChangelogFactory.inflate(ctx, changelog);

		AlertDialog.Builder bldr = new AlertDialog.Builder(ctx).setTitle(R.string.title).setIcon(iconId).setView(view)
				.setNegativeButton(R.string.close, null);
		return bldr;
	}
}
