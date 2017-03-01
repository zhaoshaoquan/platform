package com.stone.jetty;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.annotations.AnnotationParser;
import org.eclipse.jetty.annotations.AnnotationParser.Handler;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.statistic.CounterStatistic;
import org.eclipse.jetty.webapp.WebAppContext;

public class PlatformAnnotationConfiguration extends AnnotationConfiguration {
	
	private static final Logger LOG = Log.getLogger(PlatformAnnotationConfiguration.class);
	
	private List<Resource> getResources(ClassLoader aLoader) throws IOException{
		if(aLoader instanceof URLClassLoader){
			List<Resource> _result = new ArrayList<Resource>();
			URL[] _urls = ((URLClassLoader)aLoader).getURLs();
			for(URL _url : _urls){
				_result.add(Resource.newResource(_url));
			}
			return _result;
		}
		return Collections.emptyList();
	}

	public void parseContainerPath(final WebAppContext context, final AnnotationParser parser)throws Exception{
        final Set<Handler> handlers = new HashSet<Handler>();
        handlers.addAll(_discoverableAnnotationHandlers);
        handlers.addAll(_containerInitializerAnnotationHandlers);
        if(_classInheritanceHandler != null){
        	handlers.add(_classInheritanceHandler);
        }
        _containerPathStats = new CounterStatistic();
        List<Resource> _resources = getResources(getClass().getClassLoader());
        for(Resource r : _resources){
            if(_parserTasks != null){
                ParserTask task = new ParserTask(parser, handlers, r, _containerClassNameResolver);
                _parserTasks.add(task);  
                _containerPathStats.increment();
                if(LOG.isDebugEnabled()){
                	task.setStatistic(new TimeStatistic());
                }
            }
        } 
    }
}
