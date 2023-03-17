package com.javatiaocao.myblog.controller;

import com.javatiaocao.myblog.service.FeedbackService;
import com.javatiaocao.myblog.service.PrivateWordService;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class PrivateWordController {

    @Autowired
    PrivateWordService privateWordService;

    @PostMapping("/getAllPrivateWord")
    public String getAllPrivateWord() {

        try {

            DataMap data = privateWordService.getAllPrivateWord();
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("FeedbackController_getArticleManagement Exception", e);

        }

        return JsonResult.fail(com.tiaozaowang.constant.CodeType.SERVER_EXCEPTION).toJSON();

    }

}


