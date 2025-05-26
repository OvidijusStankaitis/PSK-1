package com.psk.autoproject.extensibility;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import java.util.logging.Logger;

@Interceptor
@GreetingBinding
public class GreetingInterceptor {
    private static final Logger log = Logger.getLogger(GreetingInterceptor.class.getName());

    @AroundInvoke
    public Object logInvoke(InvocationContext ctx) throws Exception {
        log.info(">> Entering: " + ctx.getMethod());
        Object result = ctx.proceed();
        log.info("<< Exiting: " + ctx.getMethod() + " returned " + result);
        return result;
    }
}
