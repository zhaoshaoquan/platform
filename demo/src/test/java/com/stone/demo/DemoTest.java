package com.stone.demo;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.stone.commons.page.Page;
import com.stone.demo.domain.Demo;
import com.stone.demo.service.DemoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:META-INF/spring/module-*.xml")
public class DemoTest{
	@Resource
	private DemoService demoService;
	
	@Test
	public void testSave(){
		Demo demo = new Demo();
		demo.setName("aa");
		try{
			demoService.save(demo);
			System.out.println(demo.getId());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdate(){
		Demo demo = new Demo();
		demo.setId(1);
		demo.setName("55555555555");
		try{
			demoService.update(demo);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFind(){
		try{
			Demo demo = demoService.findById(1);
			System.out.println(demo.getName());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDelete(){
		Demo demo = new Demo();
		demo.setId(2);
		demo.setName("aa");
		try{
			demoService.delete(demo);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSelectAll(){
		try{
			List<Demo> list = demoService.selectAll();
			for(Demo demo : list){
				System.out.println(demo.getId() +"======"+ demo.getName());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSelectByName(){
		try{
			List<Demo> list = demoService.selectByName("a");
			for(Demo demo : list){
				System.out.println(demo.getId() +"======"+ demo.getName());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSelectByPage(){
		try{
			Page<Demo> page = new Page<>(1, 20);
			page.addParams("name", "aa");
			demoService.selectByPage(page);
			for(Demo demo : page.getResult()){
				System.out.println(demo.getId() +"======"+ demo.getName());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
