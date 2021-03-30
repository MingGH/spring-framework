package run.runnable.test;

import run.runnable.config.Appconfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import run.runnable.service.PersonService;
import run.runnable.service.impl.PersonServiceImpl;

public class Test {
	public static void main(String[] args) {
		System.out.println("===================你好=====================");
		AnnotationConfigApplicationContext ac  = new AnnotationConfigApplicationContext(Appconfig.class);
		PersonService personService = ac.getBean(PersonServiceImpl.class);
		personService.add();
		System.out.println(personService);

	}
}
