package com.example.demo1.controller;

import com.example.demo1.service.TestService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("test")
public class TestController {
    private Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestService testService;

    @GetMapping("start")
    public String startActiviti() {
        log.info(">>>start...");
        testService.deployProcess();

        Map<String, Object> paramsMap = new HashMap<>();
        ProcessInstance pi = testService.startProcess("myProcess", paramsMap);
        log.info(">>>ProcessInstance id is:" + pi.getId());

        Task tk = testService.queryTaskByProcessId(pi.getId());
        if (tk != null) {
            log.info(">>>Task is:" + tk.getName());
        }
//        testService.processForm("myProcess");

//        testService.endProcess("20005");
        return "success";
    }
}
