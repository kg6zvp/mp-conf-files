package es.eisig.mp_conf_files;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

/**
 * Provide string interpolation of mp-config variables
 * @author kg6zvp
 */
public class StringInterpolator {
	public static final Pattern VAR_PATTERN = Pattern.compile("(?<!\\\\)\\$\\{(.*?)\\}");
	public static final Pattern ESCAPED_PATTERN = Pattern.compile("\\\\\\$\\{(.*?)\\}");

	public static String interpolate(Config config, String value) {
		Matcher varMatcher = VAR_PATTERN.matcher(value);
		String outputString = value;
		String varName;
		while(varMatcher.find()) {
			varName = varMatcher.group(1);
			outputString = outputString.replaceAll(Pattern.quote("${" + varName + "}")
			                                       , config.getValue(varName, String.class));
		}
		outputString = outputString.replaceAll(ESCAPED_PATTERN.toString(), "\\${$1}");
		return outputString;
	}

	public static String interpolate(String value) {
		return interpolate(ConfigProvider.getConfig(), value);
	}
}
