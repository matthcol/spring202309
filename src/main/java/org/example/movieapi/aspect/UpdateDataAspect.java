package org.example.movieapi.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class UpdateDataAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateDataAspect.class);

    @Pointcut("execution(* org.example.movieapi.service.impl.*.add*(..))")
    public void add(){}

    @Pointcut("execution(* org.example.movieapi.service.impl.*.update*(..))" +
            " || execution(* org.example.movieapi.service.impl.*.set*(..))")
    public void update(){}

    @Pointcut("execution(* org.example.movieapi.service.impl.*.delete*(..))")
    public void delete(){}

    @AfterReturning(
            pointcut = "add() || update() || delete()",
            returning = "data"
    )
    public void afterReturningData(Object data){
        LOGGER.info("Data updated: {}", data);
    }
}
