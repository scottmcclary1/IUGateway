package org.scigap.iucig.filemanager;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.scigap.iucig.filemanager.util.JaaSConfiguration;
import org.scigap.iucig.filemanager.util.LoginConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class KerberosConnector {
    private static final Logger log = LoggerFactory.getLogger(KerberosConnector.class);
    public static final String KERB_PROPERTIES = "kerb.properties";
    public static final String KERB_HOST = "kerb.host";
    public static final String  KERB_CONF_LOCATION = "kerb.conf.location";
    public static final String KERB_LOGIN_LOCATION = "kerb.login.location";
    public static final String JAVA_SECURITY_KRB5_CONF = "java.security.krb5.conf";
    public static final String JAVA_SECURITY_AUTH_LOGIN_CONFIG = "java.security.auth.login.config";
    public static final String JAVAX_SECURITY_AUTH_USE_SUBJECT_CREDS_ONLY = "javax.security.auth.useSubjectCredsOnly";
    public static final String SUN_SECURITY_KRB5_DEBUG = "sun.security.krb5.debug";
    public static final String LOGIN_FILE_LOCATION = "kerb.conffile.location";
    public static final String LOGIN_FILE_NAME = "login2.conf";
    private static Properties properties = new Properties();
    private String loginFile;
    private String host;

    public KerberosConnector() throws Exception{
        loginFile = readProperty(LOGIN_FILE_LOCATION)+LOGIN_FILE_NAME;
        host = readProperty(KERB_HOST);
        System.out.println("HOST : " + host);
        String krbConf = readProperty(KERB_CONF_LOCATION);

        System.setProperty(JAVA_SECURITY_KRB5_CONF, krbConf);
//        System.setProperty(JAVA_SECURITY_AUTH_LOGIN_CONFIG, loginFile);
//        System.out.println("LOGIN FILE: "+loginFile);
        System.setProperty(JAVAX_SECURITY_AUTH_USE_SUBJECT_CREDS_ONLY, "false");
        System.setProperty(SUN_SECURITY_KRB5_DEBUG, "true");
    }

    public Session getSession(String remoteUser) throws Exception{

        String krbLogin = readProperty(KERB_LOGIN_LOCATION);
        LoginConfigUtil loginConfigUtil = new LoginConfigUtil();
        String ticketCache = loginConfigUtil.searchTicket(remoteUser);
        javax.security.auth.login.Configuration.setConfiguration(new JaaSConfiguration(ticketCache));

        JSch jsch = new JSch();
        JSch.setLogger(new MyLogger());

        Session session = null;
        try {
            session = jsch.getSession(remoteUser, host, 22);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            config.put("PreferredAuthentications",
                    "gssapi-with-mic");
            config.put("MaxAuthTries", "5");
            session.setConfig(config);
            session.connect(5000);
        } catch (JSchException e) {
            log.error("Authentication fails.." , e);
            throw new Exception("Authentication fails..", e);
        }
        return session;
    }

    public String readProperty (String propertyName) throws Exception{
        try {
            URL resource = KerberosConnector.class.getClassLoader().getResource(KERB_PROPERTIES);
            if (resource != null){
                properties.load(resource.openStream());
                return properties.getProperty(propertyName);
            }
        } catch (IOException e) {
            log.error("Unable to read properties..", e);
            throw new Exception("Unable to read properties.." , e) ;
        }
        return null;
    }


    public static class MyLogger implements com.jcraft.jsch.Logger {
        static java.util.Hashtable name=new java.util.Hashtable();
        static{
            name.put(new Integer(DEBUG), "DEBUG: ");
            name.put(new Integer(INFO), "INFO: ");
            name.put(new Integer(WARN), "WARN: ");
            name.put(new Integer(ERROR), "ERROR: ");
            name.put(new Integer(FATAL), "FATAL: ");
        }
        public boolean isEnabled(int level){
            return true;
        }
        public void log(int level, String message){
            System.err.print(name.get(new Integer(level)));
            System.err.println(message);
        }
    }
}
