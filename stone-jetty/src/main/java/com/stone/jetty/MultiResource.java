package com.stone.jetty;

import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.resource.FileResource;
import org.eclipse.jetty.util.resource.Resource;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 赵少泉 on 2016-09-01.
 */
public class MultiResource extends FileResource {
    private final List<File> files = new ArrayList<>(5);
    private final List<String> uris = new ArrayList<>(5);

    public MultiResource()throws URISyntaxException{
        this(null);
    }

    public MultiResource(File file)throws URISyntaxException{
        super(file==null ? systemTempUri() : new URI(normalizeURI(file, file.toURI())));
        if(file != null){
            this.files.add(file);
            this.uris.add(normalizeURI(file, file.toURI()));
        }
    }

    public MultiResource addResource(URL url){
        File file;
        try{
            file = new File(url.toURI());
        }catch(URISyntaxException e){
            return this;
        }catch(Exception e){
            if(!url.toString().startsWith("file:"))throw new IllegalArgumentException("!file:");
            try{
                String furl = "file:" + URIUtil.encodePath(url.toString().substring(5));
                URI uri = new URI(furl);
                file = uri.getAuthority()==null ? new File(uri) : new File("//" + uri.getAuthority() + URIUtil.decodePath(url.getFile()));
            }catch(Exception e2){
                try{
                    URLConnection connection = url.openConnection();
                    Permission perm = connection.getPermission();
                    file = new File(perm == null ? url.getFile() : perm.getName());
                }catch(IOException e3){
                    return this;
                }
            }
        }
        this.files.add(file);
        this.uris.add(normalizeURI(file, file.toURI()));
        return this;
    }

    public MultiResource addResource(URI uri){
        File file = new File(uri);
        this.files.add(file);
        this.uris.add(normalizeURI(file, file.toURI()));
        return this;
    }

    public MultiResource addResource(File file){
        this.files.add(file);
        this.uris.add(normalizeURI(file, file.toURI()));
        return this;
    }

    @Override
    public Resource addPath(String path) throws IOException{
        path = URIUtil.canonicalPath(path);
        if(path == null)throw new MalformedURLException();
        if("/".equals(path)){
            try{
                return new FileResource(new URI(uris.get(0)));
            }catch(URISyntaxException e){
                throw new MalformedURLException(path);
            }
        }
        path = URIUtil.encodePath(path);
        URI uri = null;
        try{
            for(int i=0;i<files.size();i++){
                if(files.get(i).isDirectory()){
                    uri = new URI(URIUtil.addPaths(uris.get(i), path));
                }else{
                    uri = new URI(String.format("%s%s", uris.get(i), path));
                }
                FileResource fileResource = new FileResource(uri);
                if(fileResource.exists())return fileResource;
            }
        }catch(final URISyntaxException e){
            throw new MalformedURLException(){{initCause(e);}};
        }
        return new FileResource(uri);
    }

    private static String normalizeURI(File file, URI uri){
        String u = uri.toASCIIString();
        if(file.isDirectory()){
            if(!u.endsWith("/"))
                u += "/";
        }else if(file.exists() && u.endsWith("/"))
            u = u.substring(0, u.length() - 1);
        return u;
    }

    private static URI systemTempUri()throws URISyntaxException{
        File file = new File(System.getProperty("java.io.tmpdir"));
        return new URI(normalizeURI(file, file.toURI()));
    }
}
