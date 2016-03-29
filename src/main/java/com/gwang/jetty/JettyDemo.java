package com.gwang.jetty;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.session.HashSessionIdManager;
import org.eclipse.jetty.server.session.HashSessionManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettyDemo {
    private static final Logger log = LoggerFactory.getLogger(JettyDemo.class);
	
    protected int port = 8844;
    protected String contextPath = "/";
    protected AtomicBoolean isJettyRunning = new AtomicBoolean(false);
    private AtomicReference<Exception> launchException = new AtomicReference<Exception>(null);
	
    public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public AtomicBoolean getIsJettyRunning() {
		return isJettyRunning;
	}

	public void setIsJettyRunning(AtomicBoolean isJettyRunning) {
		this.isJettyRunning = isJettyRunning;
	}

	public AtomicReference<Exception> getLaunchException() {
		return launchException;
	}

	public void setLaunchException(AtomicReference<Exception> launchException) {
		this.launchException = launchException;
	}

	public JettyDemo(int port, String contextPath) {
        this.port = port;
        this.contextPath = contextPath;
    }

    public void startJetty() {
        final Thread thread = new Thread("HTTP_SERVER_DAEMON"){
            @Override public void run() {
                launchJetty(port);
            }
        };
        thread.setDaemon(true);
        thread.start();
    }

    public boolean launchJetty(int port) {
        log.info("Start http server at {}, context path is {}", port, contextPath);
        try {
            Server server = new Server(port);
            ServletContextHandler context = getServletContextHandler();
            
            // Specify the Session ID Manager
            HashSessionIdManager idmanager = new HashSessionIdManager();
            server.setSessionIdManager(idmanager);
            
            // Create the SessionHandler (wrapper) to handle the sessions
            HashSessionManager manager = new HashSessionManager();
            SessionHandler sessions = new SessionHandler(manager);
            
            context.setHandler(sessions);
            server.setHandler(context);
            server.start();
            isJettyRunning.set(true);
            server.join();
            return true;
        }catch (Exception ex) {
            isJettyRunning.set(false);
            log.error("LAUNCH_HTTP_SERVER_FAIL", ex);
            launchException.set(ex);
            return false;
        }
    }

    protected ServletContextHandler getServletContextHandler() {
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setClassLoader(Thread.currentThread().getContextClassLoader());
        contextHandler.setErrorHandler(null);
        contextHandler.setContextPath(contextPath);
        Map<String, ServletHolder> holders = createHolders();
        for(Map.Entry<String, ServletHolder> en : holders.entrySet()) {
            contextHandler.addServlet(en.getValue(), en.getKey());
        }
        return contextHandler;
    }

    private Map<String, ServletHolder> createHolders() {
        Map<String, ServletHolder> map = new HashMap<String, ServletHolder>();
        map.put("/hello/*", new ServletHolder(new ServletDemo()));
        map.put("/ok", new ServletHolder(new ServletDemo("ok")));
        return map;
    }

    public static void main(String[] args) {
    	int port = 8080;
    	String contextPath = "/";
    	JettyDemo jettyDemo = new JettyDemo(port, contextPath);
    	jettyDemo.startJetty();
        int times = 100;
        while(!jettyDemo.getIsJettyRunning().get() && times-- > 0) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                log.error("WAIT_FOR_HTTP_SERVER_ERROR", ex);
            }
        }
        if(jettyDemo.getIsJettyRunning().get()) {
        	log.info("LAUNCH_HTTP_SERVER_SUCC:{},{},{}", port, contextPath, times);
        }else {
            throw new RuntimeException("Launch http server fail", jettyDemo.getLaunchException().get());
        }
	}
}
