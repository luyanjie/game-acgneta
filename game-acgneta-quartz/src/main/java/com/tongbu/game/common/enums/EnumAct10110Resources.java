package com.tongbu.game.common.enums;

import com.tongbu.game.service.task.Act10110NewsResources;
import com.tongbu.game.service.task.act10110.AcgDogeImpl;
import com.tongbu.game.service.task.act10110.GameHomeImpl;
import com.tongbu.game.service.task.act10110.GamerSkyImpl;
import com.tongbu.game.service.task.act10110.SupplementImpl;

/**
 * @author jokin
 * @date 2019/1/15 15:51
 */
public enum EnumAct10110Resources {

    /**
     * 对未成功获取外站详细内容的新闻进行重新获取
     */
    SUPPLEMENT(new SupplementImpl()),

    /**
     * 动漫星空
     * */
    GAMESKY(new GamerSkyImpl()),
    /**
     * 动漫之家
     * */
    GAMEHOME(new GameHomeImpl()),

    /**
     * ACG狗屋
     * */
    ACGDOGE(new AcgDogeImpl())        ;

    private Act10110NewsResources act10110NewsResources;

    private EnumAct10110Resources(Act10110NewsResources act10110NewsResources){
        this.act10110NewsResources = act10110NewsResources;
    }

    public Act10110NewsResources getAct10110NewsResources(){
        return this.act10110NewsResources;
    }
}
