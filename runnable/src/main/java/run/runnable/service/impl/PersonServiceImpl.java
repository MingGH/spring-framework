package run.runnable.service.impl;

import org.springframework.stereotype.Service;
import run.runnable.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {


	@Override
	public void add() {
		System.out.println("���һ������");
	}

	@Override
	public void delete(Integer id) {
		System.out.println("ɾ��һ������");
	}

	@Override
	public void update(Integer id) {
		System.out.println("����һ������");
	}

	@Override
	public void query(Integer id) {
		System.out.println("��ѯһ������");
	}
}
