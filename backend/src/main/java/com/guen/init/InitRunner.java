package com.guen.init;

import com.guen.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InitRunner implements ApplicationRunner {
    @Autowired
    ErrorCode.ErrorCodeInitializer errorCodeInitializer;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("==================================\n property 초기화 \n =========================");
        errorCodeInitializer.init();
    }
}
