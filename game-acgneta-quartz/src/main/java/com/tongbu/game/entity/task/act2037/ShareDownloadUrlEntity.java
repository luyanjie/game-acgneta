package com.tongbu.game.entity.task.act2037;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jokin
 * @date 2018/11/19 18:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShareDownloadUrlEntity implements Serializable {
    private static final long serialVersionUID = -6349480112088300310L;
    private int id;
    private String sku;
    private String version;
    private String url;
    private String realSku;
    private String regisndesc;
}
