package com.zzx.sharding.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhouzhixiang
 * @desc：teammessage
 * @date：2019-12-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamMessage implements Serializable {

    private static final long serialVersionUID = -860585142674992046L;
    /**
     * TeamMessage ID 主键, 自增
     */
    private Long id;


    /**
     * 群消息id（服务端）
     */
    private Long teamMsgId;

    /**
     * 群消息id（客户端）
     */
    private String teamClientMsgId;

    /**
     * 消息类型：0-文本，1-图片，2-音频，3-视频，4-文件，5-地理位置，6-自定义，7-提醒，8-AI机器人，9-群通知
     */
    private String msgType;

    /**
     * 提醒类型：0-普通消息，1-客服进入，2-客户进入，3-客服进入欢迎提醒，4-敏感词命中提醒消息
     */
    private int remindType;

    private String convType;

    /**
     * 场景：0-team，1-p2p，2-superTeam
     */
    private int scene;

    /**
     * 内容
     */
    private String text;

    /**
     * 群通知类型：默认null（普通消息），0-更新群，1-拉人入群，2-踢人出群，3-接受入群邀请，4-通过入群邀请，5-添加群管理员，6-移除群管理员，7-主动退群，8-解散群，9-转让群，10-更新群成员禁言状态
     */
    private int teamAttachType;

    /**
     * 发送人类型：0-用户，1-客服，2-药师，3-医生
     */
    private int sendType;

    /**
     * 发送端ip
     */
    private String sendClientIp;

    /**
     * 发送端端口
     */
    private String sendClientPort;

    /**
     * 发送端类型：WEB、MAC、Android、IOS
     */
    private String sendClientType;

    /**
     * 发送端设备编号
     */
    private String sendDeviceId;

    /**
     * 发送人昵称
     */
    private String sendNick;

    /**
     * 群组id（对应群组的accid）
     */
    private Long teamId;

    /**
     * 发送人id（对应客服表和用户表的accid）
     */
    private String sendId;

    /**
     * 发送时间
     */
    private long sendAt;

    /**
     * 已读回执时间戳，如果有此字段, 说明此时间戳之前的所有消息对方均已读
     */
    private long msgReceiptTime;

    /**
     * 是否撤销：0-否，1-是
     */
    private int isRevoke;

    /**
     * 撤销时间
     */
    private long revokeAt;

    /**
     * 消息发送状态：0-发送成功，1-发送中，2-发送失败
     */
    private int msgStatus;

    /**
     * 状态：有效 0：无效
     */
    private int status;

    /**
     * 事件类型
     */
    private int eventType;

    /**
     * 附加消息
     */
    private String attach;

    /**
     * 自定义系统通知消息推送文本。仅在convType为CUSTOM_PERSON或CUSTOM_TEAM时含此字段
     */
    private String customApnsText;

    /**
     * 消息扩展字段
     */
    private String ext;

    /**
     * 标识是否被反垃圾(‘’，true，false)
     */
    private String antispam;

    /**
     * 易盾反垃圾的原始处理细节
     */
    private String yidunRes;

    public Serializable pkVal() {
        return this.id;
    }

}