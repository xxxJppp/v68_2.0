package com.dmg.niuniuserver.manager;

import com.dmg.niuniuserver.manager.init.ServerInitAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;


@Service
public class ServiceManager {

	@Autowired(required = false)
	private List<ServerInitAction> list;

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		// 初始化服务
		for (ServerInitAction serverInitAction : list) {
			serverInitAction.initServerAction();
		}
	}
}