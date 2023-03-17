package com.javatiaocao.myblog.service.Impl;

import com.javatiaocao.myblog.mapper.ArticleMapper;
import com.javatiaocao.myblog.mapper.PrivateWordMapper;
import com.javatiaocao.myblog.model.Article;
import com.javatiaocao.myblog.model.PrivateWord;
import com.javatiaocao.myblog.service.ArticleService;
import com.javatiaocao.myblog.service.PrivateWordService;
import com.javatiaocao.myblog.utils.DataMap;
import com.tiaozaowang.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivateWordServiceImpl implements PrivateWordService {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    PrivateWordMapper privateWordMapper;

    @Override
    public DataMap getAllPrivateWord() {

        List<PrivateWord> allPrivateWord = privateWordMapper.getAllPrivateWord();

        JSONObject returnJson = new JSONObject();
        //JSONObject content = new JSONObject();
        JSONArray privateWordArr = new JSONArray();

        for (PrivateWord privateWord : allPrivateWord) {
            JSONObject content = new JSONObject();
            //JSONObject json = new JSONObject();
            content.put("id", privateWord.getId());
            content.put("publisherDate", privateWord.getPublisherDate());
            content.put("privateWord", privateWord.getPrivateWord());
            if (privateWord.getReplyContent() == null) {
                content.put("replyContent", StringUtil.BLANK);
            } else {
                content.put("replyContent", privateWord.getReplyContent());
            }

            JSONObject json = new JSONObject();
            json.put("publisher", articleMapper.getUsernameById(Integer.parseInt(privateWord.getPublisherId())));
            json.put("content", content);


            privateWordArr.add(json);
        }

        returnJson.put("result", privateWordArr);


        return DataMap.success().setData(returnJson);
    }
}
