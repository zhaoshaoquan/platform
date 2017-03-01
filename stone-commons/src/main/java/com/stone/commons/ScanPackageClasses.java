package com.stone.commons;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * 根据指定的包扫描该包下面所有添加指定注解的类
 * @author 赵少泉
 * @date 2015年10月28日 下午2:45:40
 */
public class ScanPackageClasses{ 
	protected final Logger log = LoggerFactory.getLogger(ScanPackageClasses.class);
	public static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";
	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	private MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);
	private final List<TypeFilter> includeFilters = new LinkedList<TypeFilter>();
	
	@SafeVarargs
	public ScanPackageClasses(Class<? extends Annotation>...annotationTypes){
		Assert.notEmpty(annotationTypes, "At least one annotation class must be specified");
		for(Class<? extends Annotation> annotation : annotationTypes){
			this.includeFilters.add(new AnnotationTypeFilter(annotation, false));
		}
	}

	public Set<Class<?>> doScan(String...basePackages){
		Assert.notEmpty(basePackages, "At least one base package must be specified");
		Set<Class<?>> beanClass = new LinkedHashSet<Class<?>>();
		for(String basePackage : basePackages){
			try{
				String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX	+ ClassUtils.convertClassNameToResourcePath(basePackage) + "/" + DEFAULT_RESOURCE_PATTERN;
				Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);
				for(Resource resource : resources){
					if(resource.isReadable()){
						MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
						if(isCandidateClass(metadataReader)){
							String className = metadataReader.getClassMetadata().getClassName();
							try{
								beanClass.add(Class.forName(className));
							}catch(ClassNotFoundException e){
								log.error(String.format("Class Not Found : %s", className), e);
							}
							
						}
					}
				}
			}catch(IOException e){
				log.error("I/O failure during classpath scanning", e);
			}
		}
		return beanClass;
	}

	protected boolean isCandidateClass(MetadataReader metadataReader) throws IOException{
		for(TypeFilter tf : this.includeFilters){
			if(tf.match(metadataReader, this.metadataReaderFactory)){
				return true;
			}
		}
		return false;
	}

}
