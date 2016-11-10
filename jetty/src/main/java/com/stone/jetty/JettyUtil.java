package com.stone.jetty;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebXmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;

public class JettyUtil {
	private static final Logger logger = LoggerFactory.getLogger(JettyUtil.class);
	private Server server;
    private boolean isHttps = false;
	private String webdefault = "webdefault.xml";
	private String webAppPath = "src\\main\\webapp";

	public static JettyUtil newJettyUtil(){
		return new JettyUtil();
	}

    public JettyUtil isHttps(final boolean isHttps){
        this.isHttps = isHttps;
        return this;
    }

    public JettyUtil setWebAppPath(final String webAppPath){
        this.webAppPath = webAppPath;
        return this;
    }

	public JettyUtil createServer(String contextPath, int port){
		server = new Server();
		WebAppContext webContext = new WebAppContext();
		webContext.setMaxFormContentSize(-1);
		try{
			webContext.setContextPath(contextPath);
			File projectFile = new File(System.getProperty("user.dir"));
			File webapp = new File(projectFile, webAppPath);
			if(!webapp.exists())webapp.mkdirs();

			ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resourcePatternResolver.getResources("classpath*:META-INF/resources");

			MultiResource baseResource = new MultiResource(webapp);
			for(Resource resource : resources){
				baseResource.addResource(resource.getURI());
			}
			webContext.setBaseResource(baseResource);
			webContext.setDefaultsDescriptor(webdefault);
			webContext.setConfigurations(new Configuration[] {new WebXmlConfiguration(), new PlatformAnnotationConfiguration()});

			ServerConnector connector;
            if(!isHttps){
                connector = new ServerConnector(server);
            }else{
                //https相关配置
                HttpConfiguration config = new HttpConfiguration();
                config.setSecureScheme("https");
                config.addCustomizer(new SecureRequestCustomizer());
                SslContextFactory sslContextFactory = new SslContextFactory();
                sslContextFactory.setKeyStorePath("keystore");
                sslContextFactory.setKeyStorePassword("OBF:1ini1k1j1jg620zj1jd41jyf1ikw");
                sslContextFactory.setKeyManagerPassword("OBF:1ini1k1j1jg620zj1jd41jyf1ikw");
                connector = new ServerConnector(server, new SslConnectionFactory(sslContextFactory, "http/1.1"), new HttpConnectionFactory(config));
            }

			QueuedThreadPool threadPool = new QueuedThreadPool(50);
			connector.setPort(port);
			server.addConnector(connector);
			server.addBean(threadPool);
			server.setHandler(webContext);
			server.setAttribute("org.eclipse.jetty.server.Request.maxFormContentSize", -1);
			server.setAttribute("org.mortbay.util.URI.charset", "utf-8");
			server.setStopAtShutdown(true);
		}catch (Exception e){
			logger.error(e.getMessage(), e);
		}
		return this;
	}

	public JettyUtil createServer(String warPath, String contextPath, int port) {
		server = new Server();
		WebAppContext webContext = new WebAppContext();
		try{
			webContext.setContextPath(contextPath);
			webContext.setWar(warPath);
			QueuedThreadPool threadPool = new QueuedThreadPool(50);
			ServerConnector connector = new ServerConnector(server);
			connector.setPort(port);
			server.setConnectors(new Connector[] {connector});
			server.addBean(threadPool);
			server.setAttribute("org.eclipse.jetty.server.Request.maxFormContentSize", -1);
			server.setAttribute("org.mortbay.util.URI.charset", "utf-8");
			server.setHandler(webContext);
			server.setStopAtShutdown(true);
		}catch (Exception e){
			logger.error(e.getMessage(), e);
		}
		return this;
	}

	public JettyUtil start(){
		try{
			this.server.start();
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return this;
	}
}
