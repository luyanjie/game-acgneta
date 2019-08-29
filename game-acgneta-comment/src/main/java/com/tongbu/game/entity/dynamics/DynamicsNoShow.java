package com.tongbu.game.entity.dynamics;

import com.tongbu.game.common.enums.UserActionEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * 需要展示的动态
 */
@Data
@Document(collection = "user_dynamics_no_show")
@CompoundIndexes({
        @CompoundIndex(name = "idx_uid",def = "{'uid': 1, '_id': -1}")
})
@AllArgsConstructor
public class DynamicsNoShow implements Serializable {

    private static final long serialVersionUID = -2706229931361856252L;

    /**
     * MongoDB默认会为每个document生成一个 _id 属性，作为默认主键，且默认值为ObjectId,可以更改 _id 的值(可为空字符串)，但每个document必须拥有 _id 属性。
     当然，也可以自己设置@Id主键，不过官方建议使用MongoDB自动生成。
     * */
    @Id
    @ApiModelProperty(hidden = true) // swagger 页面不显示
    private String _id;

    /**
     * 用户行为
     */
    private UserActionEnum userActionEnum;
    /**
     * 用户id
     * 声明该字段需要加索引，加索引后以该字段为条件检索将大大提高速度。
     * 唯一索引的话是@Indexed(unique = true)。
     * 也可以对数组进行索引，如果被索引的列是数组时，mongodb会索引这个数组中的每一个元素。
     * */
    //@Indexed
    @Field("uid") // 代表一个字段，可以不加，不加的话默认以参数名为列名。
    private int uid;
    /**
     * 动画id
     * 当行为为 动画、问答、捏报相关时的id
     */
    // @Transient 被该注解标注的，将不会被录入到数据库中。只作为普通的javaBean属性。
    private int typeId;
    /**
     * 评论id
     */
    private int commentId;
    /**
     * 描述
     */
    private String description;

    /**
     * 状态，1 有效 0：无效（被删除）
     */
    @ApiModelProperty(hidden = true)
    private int status = 1;

    //@Transient // 一般情况不序列化
    //@JsonIgnore // jackson不序列化
    //@JSONField(serialize = false) // fast不序列化
    @ApiModelProperty(hidden = true) // swagger 页面不显示 ，如果不是实体类，是参数使用 @ApiIgnore
    private long timestamp;
}
