package jt.beans;

import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class ThemeSwitcherBean {

	private TreeMap<String, String> themes;

	private String theme;

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	/**
	 * Initialisiert die Liste der <code>themes</code> mit einer Liste von
	 * Themes. Das aktuelle <code>theme</code> wird mit dem Wert "aristo"
	 * initialisiert.
	 */
	@PostConstruct
	public void init() {
		themes = new TreeMap<String, String>();
		themes.put("Aristo", "aristo");
		themes.put("Black-Tie", "black-tie");
		themes.put("Blitzer", "blitzer");
		themes.put("Bluesky", "bluesky");
		themes.put("Casablanca", "casablanca");
		themes.put("Cupertino", "cupertino");
		themes.put("Dark-Hive", "dark-hive");
		themes.put("Dot-Luv", "dot-luv");
		themes.put("Eggplant", "eggplant");
		themes.put("Excite-Bike", "excite-bike");
		themes.put("Flick", "flick");
		themes.put("Glass-X", "glass-x");
		themes.put("Home", "home");
		themes.put("Hot-Sneaks", "hot-sneaks");
		themes.put("Humanity", "humanity");
		themes.put("Le-Frog", "le-frog");
		themes.put("Midnight", "midnight");
		themes.put("Mint-Choc", "mint-choc");
		themes.put("Overcast", "overcast");
		themes.put("Pepper-Grinder", "pepper-grinder");
		themes.put("Redmond", "redmond");
		themes.put("Rocket", "rocket");
		themes.put("Sam", "sam");
		themes.put("Smoothness", "smoothness");
		themes.put("South-Street", "south-street");
		themes.put("Start", "start");
		themes.put("Sunny", "sunny");
		themes.put("Swanky-Purse", "swanky-purse");
		themes.put("Trontastic", "trontastic");
		themes.put("UI-Darkness", "ui-darkness");
		themes.put("UI-Lightness", "ui-lightness");
		themes.put("Vader", "vader");
		theme = "aristo";
	}

	public TreeMap<String, String> getThemes() {
		return themes;
	}

	public void setThemes(TreeMap<String, String> themes) {
		this.themes = themes;
	}
}
