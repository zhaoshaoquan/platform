package com.stone.config;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by 赵少泉 on 2016-06-13.
 */
public class FreeMarkerView extends org.springframework.web.servlet.view.freemarker.FreeMarkerView {
    @Override
    protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
        model.put("basePath", String.format("%s://%s:%s%s/", request.getScheme(), request.getServerName(), request.getServerPort(), request.getContextPath()));
        super.exposeHelpers(model, request);
    }
}
