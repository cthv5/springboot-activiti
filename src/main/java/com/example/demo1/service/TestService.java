package com.example.demo1.service;

import org.activiti.engine.*;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class TestService {
    private Logger log = LoggerFactory.getLogger(TestService.class);

    // 部署服务
    @Autowired
    private RepositoryService repositoryService;

    // 运行服务
    @Autowired
    private RuntimeService runtimeService;

    // 任务服务
    @Autowired
    private TaskService taskService;

    // 历史数据服务
    @Autowired
    private HistoryService historyService;

    // 表单服务
    @Autowired
    private FormService formService;

    // 部署流程
    public void deployProcess() {
        Deployment deployment = repositoryService
                .createDeployment()
                .name("test流程")
                .addClasspathResource("processes/process-1.bpmn")
                .deploy();
    }

    // 启动流程
    public ProcessInstance startProcess(String processDefinitionKey, Map<String, Object> paramsMap) {
        return runtimeService.startProcessInstanceByKey(processDefinitionKey, paramsMap);
    }

    // 结束流程
    public void endProcess(String processId) {
        runtimeService.deleteProcessInstance(processId, "结束");
        log.info(">>>结束流程:" + processId);
    }

    // 根据流程id查找任务
    public Task queryTaskByProcessId(String processInstanceId) {
        return taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    }

    // 表单数据
    public void processForm(String procDefId) {
        StartFormData startFormData = formService.getStartFormData(procDefId);
        List<FormProperty> formProperties = startFormData.getFormProperties();
        for (FormProperty formProperty : formProperties) {
            log.info(formProperty.getType().getName());
        }
    }
}
