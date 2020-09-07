package util;

public class OSHelper {

	private static  String OS = System.getProperty("os.name").toLowerCase();

	public static OSType getOSType() throws Exception
	{

		if (isWindows()) {
			return OSType.WINDOWS;
		} else if (isMac()) {
			return OSType.MAC;
		} else if (isUnix()) {
			return OSType.UNIX;
		}
		else {
			System.out.println("Your OS is not support!!");
		}

		throw new Exception("OS Not Found");
	}

	public static boolean isWindows() {

		return (OS.contains("win"));

	}

	public static boolean isMac() {

		return (OS.contains("mac"));

	}

	public static boolean isUnix() {

		return (OS.contains("nix") || OS.contains("nux") || OS.indexOf("aix") > 0 );

	}

	public enum OSType
	{
		WINDOWS, UNIX, MAC;
	}

}
