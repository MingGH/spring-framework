package run.runnable.service.impl;

import org.springframework.stereotype.Service;
import run.runnable.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {


	@Override
	public void add() {
		System.out.println("添加一个对象");
	}

	@Override
	public void delete(Integer id) {
		System.out.println("删除一个对象");
	}

	@Override
	public void update(Integer id) {
		System.out.println("更新一个对象");
	}

	@Override
	public void query(Integer id) {
		System.out.println("查询一个对象");
	}
}
