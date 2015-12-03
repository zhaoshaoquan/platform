package com.stone.demo.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import com.stone.commons.annotation.FreemarkerTag;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@FreemarkerTag(name="upper")
public class UpperDirective implements TemplateDirectiveModel{

	@SuppressWarnings("rawtypes")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)throws TemplateException, IOException{
		body.render(new UpperCaseFilterWriter(env.getOut()));
	}

	private static class UpperCaseFilterWriter extends Writer{
		private final Writer out;

		UpperCaseFilterWriter(Writer out){
			this.out = out;
		}

		public void write(char[] cbuf, int off, int len) throws IOException{
			char[] transformedCbuf = new char[len];
			for(int i = 0; i < len; i++){
				transformedCbuf[i] = Character.toUpperCase(cbuf[i + off]);
			}
			out.write(transformedCbuf);
		}

		public void flush() throws IOException{
			out.flush();
		}

		public void close() throws IOException{
			out.close();
		}
	}
}
