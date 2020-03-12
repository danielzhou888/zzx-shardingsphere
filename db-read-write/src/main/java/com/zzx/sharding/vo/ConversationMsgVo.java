package com.zzx.sharding.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

/**
 * @description: 会话类型事件消息VO
 * @author: zhouzhixiang
 * @date: 2019-12-13
 * @company: 叮当快药科技集团有限公司
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class ConversationMsgVo implements Serializable {

    private static final long serialVersionUID = -8072958081588316597L;

    /** 消息内容 */
    private String body;
    /** 附加消息 */
    private JSONObject attach;
    /** 会话类型 */
    private String eventType;
    /** 消息发送者的用户账号 */
    private String fromAccount;
    /** 发送客户端类型： AOS、IOS、PC、WINPHONE、WEB、REST */
    private String fromClientType;
    /** 发送设备id */
    private String fromDeviceId;
    private String fromNick;
    /** 消息发送时间 */
    private String msgTimestamp;
    private String msgType;
    /** 会话具体类型 */
    private String convType;
    /** 消息id */
    private String msgidClient;
    /** 服务端生成的消息id(可转为Long型数据) */
    private String msgidServer;
    /** 若convType为TEAM或CUSTOM_TEAM，则to为tid，即群id */
    private String to;
    private String resendFlag;
    /** 消息发送方的客户端IP地址 */
    private String ip;
    /** 消息发送方的客户端端口号 */
    private String port;
    /** 当前群成员accid列表。仅在群成员不超过200人，且convType为TEAM或CUSTOM_TEAM时包含此字段 */
    private String tMembers;
    /** 自定义系统通知消息是否存离线:0：不存，1：存。*/
    /** 仅在convType为CUSTOM_PERSON或CUSTOM_TEAM时含此字段，可转为Integer类型数据 */
    private String customSafeFlag;
    /** 自定义系统通知消息推送文本。仅在convType为CUSTOM_PERSON或CUSTOM_TEAM时含此字段 */
    private String customApnsText;
    /** 消息扩展字段 */
    private String ext;
    /** 标识是否被反垃圾，仅在被反垃圾时才有此字段，可转为Boolean类型数据 */
    private String antispam;
    /** 易盾反垃圾的原始处理细节 */
    private String yidunRes;
    /** 点对点消息是否黑名单，仅在消息发送方被拉黑时才有此字段，可转为Boolean类型数据 */
    private String blacklist;

    public String toJson() {
        return JSONObject.toJSONString(this);
    }

    public String getAttachJson() {
        if (attach != null) {
            try {
                return new String(JSONObject.toJSONString(attach).getBytes(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.error("getAttachJson error = {}", e);
            }
        }
        return "";
    }
}
