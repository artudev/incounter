package pl.vivifiedbits.incounter.customview;

/**
 * Created by Artur Kasprzak on 25.04.2018.
 */
public interface DoubleText {

	String getSmallText();

	String getLargeText();

	void setSmallText(String text);

	void setLargeText(String text);

	void setText(String small, String large);
}
