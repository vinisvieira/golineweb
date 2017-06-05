package br.com.goline2.util;

/**
 * Classe para armazenamento de constantes úteis
 * 
 */
public class Constants {

	public static String APPLICATION_NAME = "GoLine_2.0";

	public static String PERSISTENCE_UNIT_NAME = "br.com.goline2";
	public static String LOGIN_PATH_NAME = "/login";

	public static String SESSION_USER_LOGED = "userLoged";
	public static String SESSION_USER_LOGED_TYPE = "userLogedType";

	public static final String ROBO_MAIL = "appcontato@goline.com.br";
	public static final String ROBO_MAIL_PASS = "";

	public static final String RECOVERY_KEY = "332ec4e3e4722932f5a93c16c3dacb389892783d";

	// Entity Status
	public static final boolean ACTIVE_ENTITY = true;
	public static final boolean INACTIVE_ENTITY = false;

	// User Status
	public static final boolean ACTIVE_USER = true;
	public static final boolean INACTIVE_USER = false;

	// Artist Status
	public static final int INACTIVE = 0;
	public static final int PENDING = 1;
	public static final int ACTIVE = 2;

	// Media Type
	public static final String MEDIA_JPG = ".jpg";

	// Header's
	public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
	public static final String ACCESS_CONTROL_ALLOW_ORIGIN_EXTRA = "*";

	// File's URL
	public static final String CATALINA_BASE = "catalina.base";
	public static final String UPLOAD_PATH = "/webapps/data/goline2/";

}
