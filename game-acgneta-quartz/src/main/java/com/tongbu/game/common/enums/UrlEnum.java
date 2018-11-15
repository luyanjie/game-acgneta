package com.tongbu.game.common.enums;

import com.tongbu.game.service.task.act10107.AcfunImpl;
import com.tongbu.game.service.task.act10107.BilibiliImpl;
import com.tongbu.game.service.task.act10107.UrlService;

/**
 * @author jokin
 * @date 2018/11/15 10:35
 */
public enum UrlEnum {
    /**
     * B站
     * */
    BILIBILI(new BilibiliImpl()),
    /**
     * A站
     * */
    ACFUN(new AcfunImpl());

    private UrlService urlService;

    UrlEnum(UrlService urlService)
    {
        this.urlService = urlService;
    }

    public UrlService getUrlService() {
        return urlService;
    }
}
