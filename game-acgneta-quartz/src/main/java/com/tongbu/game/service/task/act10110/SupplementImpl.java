package com.tongbu.game.service.task.act10110;

import com.tongbu.game.common.enums.EnumAct10110Resources;
import com.tongbu.game.entity.task.act10110.GameNewEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author jokin
 * @date 2019-02-26
 * <p>
 * 对未成功获取外站详细内容的新闻进行重新获取
 */
@Slf4j
public class SupplementImpl extends AbstractService {

    @Override
    public void exec() {
        List<GameNewEntity> list = undoneList();
        list.forEach(x -> getDetails(x.getId(), x.getWebUrl(), x.getModuleId(),1));
    }

    @Override
    public void getDetails(int newId, String webUrl, int moduleId,int source) {
        try {
            switch (moduleId) {
                case 8:
                    EnumAct10110Resources.GAMESKY.getAct10110NewsResources().getDetails(newId, webUrl, moduleId, source);
                    break;
                case 9:
                    EnumAct10110Resources.GAMEHOME.getAct10110NewsResources().getDetails(newId, webUrl, moduleId, source);
                    break;
                case 10:
                    EnumAct10110Resources.ACGDOGE.getAct10110NewsResources().getDetails(newId, webUrl, moduleId, source);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            log.error(webUrl, e);
        }
    }
}
