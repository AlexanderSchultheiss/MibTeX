/* MibTeX - Minimalistic tool to manage your references with BibTeX
 * 
 * Distributed under BSD 3-Clause License, available at Github
 * 
 * https://github.com/tthuem/MibTeX
 */
package de.mibtex.export.typo3;

import java.util.function.Function;

/**
 * This is a collection of default modifiers to use for the ExportTypo3Bibtex.
 * Each filter is a java.util.Function that takes a Typo3Entry and returns its modified version.
 * Most of the modifiers here are used for handling duplicate Typo3Entries (with respect to their title).
 * 
 * @author Paul Maximilian Bittner
 */
public class Modifiers {
	public static Function<Typo3Entry, Typo3Entry> MARK_IF_THOMAS_IS_EDITOR = Util.when(t -> t.editors.contains(Filters.THOMAS_THUEM), addTag("EditorialThomasThuem"));
	public static Function<Typo3Entry, Typo3Entry> MARK_IF_VENUE_IS_SE = Util.when(t -> "SE".equals(t.source.venue), appendToTitle("(SE)"));
	public static Function<Typo3Entry, Typo3Entry> MARK_IF_TO_APPEAR = Util.when(t -> t.note.toLowerCase().contains("to appear"), appendToBookTitle("(To Appear)"));
	public static Function<Typo3Entry, Typo3Entry> MARK_IF_TECHREPORT = Util.whenForced(Filters.IS_TECHREPORT, appendToTitle("(Technical Report)"), "Given entry is not a technical report! (Perhaps an illegal modifier?)");
	public static Function<Typo3Entry, Typo3Entry> MARK_AS_EXTENDED_ABSTRACT = appendToTitle("(Extended Abstract)");
	
	public static Function<Typo3Entry, Typo3Entry> appendToTitle(String suffix) {
		return t -> {
			t.title += " " + suffix;
			return t;
		};
	}
	
	public static Function<Typo3Entry, Typo3Entry> appendToBookTitle(String suffix) {
		return t -> {
			t.booktitle += " " + suffix;
			return t;
		};
	} 
	
	public static Function<Typo3Entry, Typo3Entry> addTag(String tag) {
		return t -> {
			t.tags.add(tag);
			return t;
		};
	}
	
	public static Function<Typo3Entry, Typo3Entry> whenKeyIs(String key, Function<Typo3Entry, Typo3Entry> f) {
		return Util.when(t -> t.key.equals(key), f);
	}
}