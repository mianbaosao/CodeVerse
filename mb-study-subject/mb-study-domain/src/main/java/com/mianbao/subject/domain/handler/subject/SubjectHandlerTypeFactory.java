package com.mianbao.subject.domain.handler.subject;



import com.mianbao.subject.common.enums.SubjectInfoTypeEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 题目类型工厂
 * @Author:bread
 * @Date: 2024-08-26 17:37
 * 实现了 InitializingBean 接口，在 Spring 完成依赖注入后会自动调用 afterPropertiesSet 方法进行初始化。
 */
@Component
public class SubjectHandlerTypeFactory implements InitializingBean {

    @Resource
    private List<SubjectTypeHandler>subjectTypeHandlerList;


    private Map<SubjectInfoTypeEnum,SubjectTypeHandler> handlerMap=new HashMap<>();

    public SubjectTypeHandler getHandler(int subjectType) {
        SubjectInfoTypeEnum subjectInfoTypeEnum = SubjectInfoTypeEnum.getByCode(subjectType);
        return handlerMap.get(subjectInfoTypeEnum);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        for(SubjectTypeHandler subjectTypeHandler:subjectTypeHandlerList){
            handlerMap.put(subjectTypeHandler.getHandlerType(),subjectTypeHandler);
        }
    }
}
