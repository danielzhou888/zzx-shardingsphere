/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.89.53
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 192.168.89.53:3306
 Source Schema         : ddky_im_bis

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 08/03/2020 13:18:39
*/

DROP SCHEMA IF EXISTS im_bis;
CREATE SCHEMA IF NOT EXISTS im_bis;
use im_bis

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for im_team_message
-- ----------------------------
DROP TABLE IF EXISTS `im_team_message0`;
CREATE TABLE `im_team_message0`
(
    `id`                 bigint(20) NOT NULL COMMENT '主键id',
    `team_msg_id`        bigint(20)    DEFAULT NULL COMMENT '群消息id(服务端生成消息id)',
    `team_client_msg_id` varchar(32)   DEFAULT '' COMMENT '群消息id（客户端生成）',
    `team_id`            bigint(20)    DEFAULT NULL COMMENT '群组id（对应群组的accid）',
    `msg_type`           varchar(15)   DEFAULT 'TEXT' COMMENT '消息类型：TEXT-文本，PICTURE-图片，AUDIO-音频，VIDEO-视频，FILE-文件，GEO-地理位置，CUSTOM-自定义，TIP-提醒，ROBOT-AI机器人，NOTICATION-群通知，TEAM_INVITE-邀请入群，TEAM_INVITE_REJECT-拒绝邀请，CUSTOM_TEAM_MSG -群组自定义系统通知',
    `event_type`         tinyint(4)    DEFAULT '1' COMMENT '事件类型：1-会话类型消息（p2p消息、群聊消息、自定义系统通知、云信内置系统通知），2-登录事件，3-登出/离线事件，4-聊天室聊天消息，5-音视频时长、白板时长消息，6-音视频白板大小、下载地址消息，7-单聊消息撤回，8-群聊消息撤回，9-汇报主播、管理员进出聊天室事件消息，10-汇报专线电话通话结束回调抄送的消息，11-汇报短信回执抄送的消息，12-汇报短信上行消息，13-汇报用户进出音视频/白板房间的消息，14-汇报聊天室队列操作的事件消息，20-易盾异步反垃圾结果信息',
    `remind_type`        tinyint(4)    DEFAULT '0' COMMENT '提醒类型：0-普通消息，1-客服进入，2-客户进入，3-客服进入欢迎提醒，4-敏感词命中提醒消息',
    `conv_type`          varchar(20)   DEFAULT '' COMMENT '场景类型：TEAM',
    `scene`              tinyint(4)    DEFAULT '0' COMMENT '场景：0-team，1-p2p，2-superTeam',
    `text`               varchar(5000) DEFAULT '' COMMENT '内容',
    `attach`             varchar(5000) DEFAULT '' COMMENT '附加消息',
    `team_attach_type`   tinyint(4)    DEFAULT NULL COMMENT '群通知类型：默认null（普通消息），0-更新群，1-拉人入群，2-踢人出群，3-接受入群邀请，4-通过入群邀请，5-添加群管理员，6-移除群管理员，7-主动退群，8-解散群，9-转让群，10-更新群成员禁言状态',
    `send_type`          tinyint(4)    DEFAULT '0' COMMENT '发送人类型：0-用户，1-客服，2-药师，3-医生',
    `send_client_ip`     varchar(20)   DEFAULT '' COMMENT '发送端ip',
    `send_client_port`   varchar(5)    DEFAULT '' COMMENT '发送端端口',
    `send_client_type`   varchar(10)   DEFAULT '' COMMENT '发送客户端类型： AOS、IOS、PC、WINPHONE、WEB、REST',
    `send_device_id`     varchar(32)   DEFAULT '' COMMENT '发送端设备编号',
    `send_nick`          varchar(50)   DEFAULT '' COMMENT '发送人昵称',
    `send_id`            varchar(32)   DEFAULT '' COMMENT '发送人id（对应客服表和用户表的accid）',
    `send_at`            bigint(11)    DEFAULT NULL COMMENT '发送时间',
    `msg_receipt_time`   bigint(11)    DEFAULT NULL COMMENT '已读回执时间戳，如果有此字段, 说明此时间戳之前的所有消息对方均已读',
    `is_revoke`          tinyint(4)    DEFAULT '0' COMMENT '是否撤销：0-否，1-是',
    `revoke_at`          bigint(11)    DEFAULT '0' COMMENT '撤销时间',
    `custom_apns_text`   varchar(200)  DEFAULT '' COMMENT '自定义系统通知消息推送文本。仅在convType为CUSTOM_PERSON或CUSTOM_TEAM时含此字段',
    `ext`                varchar(64)   DEFAULT '' COMMENT '消息扩展字段',
    `antispam`           varchar(12)   DEFAULT '' COMMENT '标识是否被反垃圾(‘’，true，false)',
    `yidun_res`          varchar(500)  DEFAULT '' COMMENT '易盾反垃圾的原始处理细节',
    `msg_status`         tinyint(4)    DEFAULT '0' COMMENT '消息发送状态：0-发送成功，1-发送中，2-发送失败',
    `status`             tinyint(4)    DEFAULT '0' COMMENT '状态：有效 0：无效',
    PRIMARY KEY (`id`),
    UNIQUE KEY `index_tt` (`id`, `team_msg_id`, `team_client_msg_id`) USING BTREE,
    KEY `index_team_id_on_im_team_message` (`team_id`),
    KEY `index_send_at_on_im_team_message` (`send_at`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='IM消息表';

SET FOREIGN_KEY_CHECKS = 1;


/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.89.53
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 192.168.89.53:3306
 Source Schema         : ddky_im_bis

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 08/03/2020 13:18:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for im_team_message
-- ----------------------------
DROP TABLE IF EXISTS `im_team_message1`;
CREATE TABLE `im_team_message1`
(
    `id`                 bigint(20) NOT NULL COMMENT '主键id',
    `team_msg_id`        bigint(20)    DEFAULT NULL COMMENT '群消息id(服务端生成消息id)',
    `team_client_msg_id` varchar(32)   DEFAULT '' COMMENT '群消息id（客户端生成）',
    `team_id`            bigint(20)    DEFAULT NULL COMMENT '群组id（对应群组的accid）',
    `msg_type`           varchar(15)   DEFAULT 'TEXT' COMMENT '消息类型：TEXT-文本，PICTURE-图片，AUDIO-音频，VIDEO-视频，FILE-文件，GEO-地理位置，CUSTOM-自定义，TIP-提醒，ROBOT-AI机器人，NOTICATION-群通知，TEAM_INVITE-邀请入群，TEAM_INVITE_REJECT-拒绝邀请，CUSTOM_TEAM_MSG -群组自定义系统通知',
    `event_type`         tinyint(4)    DEFAULT '1' COMMENT '事件类型：1-会话类型消息（p2p消息、群聊消息、自定义系统通知、云信内置系统通知），2-登录事件，3-登出/离线事件，4-聊天室聊天消息，5-音视频时长、白板时长消息，6-音视频白板大小、下载地址消息，7-单聊消息撤回，8-群聊消息撤回，9-汇报主播、管理员进出聊天室事件消息，10-汇报专线电话通话结束回调抄送的消息，11-汇报短信回执抄送的消息，12-汇报短信上行消息，13-汇报用户进出音视频/白板房间的消息，14-汇报聊天室队列操作的事件消息，20-易盾异步反垃圾结果信息',
    `remind_type`        tinyint(4)    DEFAULT '0' COMMENT '提醒类型：0-普通消息，1-客服进入，2-客户进入，3-客服进入欢迎提醒，4-敏感词命中提醒消息',
    `conv_type`          varchar(20)   DEFAULT '' COMMENT '场景类型：TEAM',
    `scene`              tinyint(4)    DEFAULT '0' COMMENT '场景：0-team，1-p2p，2-superTeam',
    `text`               varchar(5000) DEFAULT '' COMMENT '内容',
    `attach`             varchar(5000) DEFAULT '' COMMENT '附加消息',
    `team_attach_type`   tinyint(4)    DEFAULT NULL COMMENT '群通知类型：默认null（普通消息），0-更新群，1-拉人入群，2-踢人出群，3-接受入群邀请，4-通过入群邀请，5-添加群管理员，6-移除群管理员，7-主动退群，8-解散群，9-转让群，10-更新群成员禁言状态',
    `send_type`          tinyint(4)    DEFAULT '0' COMMENT '发送人类型：0-用户，1-客服，2-药师，3-医生',
    `send_client_ip`     varchar(20)   DEFAULT '' COMMENT '发送端ip',
    `send_client_port`   varchar(5)    DEFAULT '' COMMENT '发送端端口',
    `send_client_type`   varchar(10)   DEFAULT '' COMMENT '发送客户端类型： AOS、IOS、PC、WINPHONE、WEB、REST',
    `send_device_id`     varchar(32)   DEFAULT '' COMMENT '发送端设备编号',
    `send_nick`          varchar(50)   DEFAULT '' COMMENT '发送人昵称',
    `send_id`            varchar(32)   DEFAULT '' COMMENT '发送人id（对应客服表和用户表的accid）',
    `send_at`            bigint(11)    DEFAULT NULL COMMENT '发送时间',
    `msg_receipt_time`   bigint(11)    DEFAULT NULL COMMENT '已读回执时间戳，如果有此字段, 说明此时间戳之前的所有消息对方均已读',
    `is_revoke`          tinyint(4)    DEFAULT '0' COMMENT '是否撤销：0-否，1-是',
    `revoke_at`          bigint(11)    DEFAULT '0' COMMENT '撤销时间',
    `custom_apns_text`   varchar(200)  DEFAULT '' COMMENT '自定义系统通知消息推送文本。仅在convType为CUSTOM_PERSON或CUSTOM_TEAM时含此字段',
    `ext`                varchar(64)   DEFAULT '' COMMENT '消息扩展字段',
    `antispam`           varchar(12)   DEFAULT '' COMMENT '标识是否被反垃圾(‘’，true，false)',
    `yidun_res`          varchar(500)  DEFAULT '' COMMENT '易盾反垃圾的原始处理细节',
    `msg_status`         tinyint(4)    DEFAULT '0' COMMENT '消息发送状态：0-发送成功，1-发送中，2-发送失败',
    `status`             tinyint(4)    DEFAULT '0' COMMENT '状态：有效 0：无效',
    PRIMARY KEY (`id`),
    UNIQUE KEY `index_tt` (`id`, `team_msg_id`, `team_client_msg_id`) USING BTREE,
    KEY `index_team_id_on_im_team_message` (`team_id`),
    KEY `index_send_at_on_im_team_message` (`send_at`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='IM消息表';

SET FOREIGN_KEY_CHECKS = 1;

/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.89.53
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 192.168.89.53:3306
 Source Schema         : ddky_im_bis

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 08/03/2020 13:18:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for im_team_message
-- ----------------------------
DROP TABLE IF EXISTS `im_team_message2`;
CREATE TABLE `im_team_message2`
(
    `id`                 bigint(20) NOT NULL COMMENT '主键id',
    `team_msg_id`        bigint(20)    DEFAULT NULL COMMENT '群消息id(服务端生成消息id)',
    `team_client_msg_id` varchar(32)   DEFAULT '' COMMENT '群消息id（客户端生成）',
    `team_id`            bigint(20)    DEFAULT NULL COMMENT '群组id（对应群组的accid）',
    `msg_type`           varchar(15)   DEFAULT 'TEXT' COMMENT '消息类型：TEXT-文本，PICTURE-图片，AUDIO-音频，VIDEO-视频，FILE-文件，GEO-地理位置，CUSTOM-自定义，TIP-提醒，ROBOT-AI机器人，NOTICATION-群通知，TEAM_INVITE-邀请入群，TEAM_INVITE_REJECT-拒绝邀请，CUSTOM_TEAM_MSG -群组自定义系统通知',
    `event_type`         tinyint(4)    DEFAULT '1' COMMENT '事件类型：1-会话类型消息（p2p消息、群聊消息、自定义系统通知、云信内置系统通知），2-登录事件，3-登出/离线事件，4-聊天室聊天消息，5-音视频时长、白板时长消息，6-音视频白板大小、下载地址消息，7-单聊消息撤回，8-群聊消息撤回，9-汇报主播、管理员进出聊天室事件消息，10-汇报专线电话通话结束回调抄送的消息，11-汇报短信回执抄送的消息，12-汇报短信上行消息，13-汇报用户进出音视频/白板房间的消息，14-汇报聊天室队列操作的事件消息，20-易盾异步反垃圾结果信息',
    `remind_type`        tinyint(4)    DEFAULT '0' COMMENT '提醒类型：0-普通消息，1-客服进入，2-客户进入，3-客服进入欢迎提醒，4-敏感词命中提醒消息',
    `conv_type`          varchar(20)   DEFAULT '' COMMENT '场景类型：TEAM',
    `scene`              tinyint(4)    DEFAULT '0' COMMENT '场景：0-team，1-p2p，2-superTeam',
    `text`               varchar(5000) DEFAULT '' COMMENT '内容',
    `attach`             varchar(5000) DEFAULT '' COMMENT '附加消息',
    `team_attach_type`   tinyint(4)    DEFAULT NULL COMMENT '群通知类型：默认null（普通消息），0-更新群，1-拉人入群，2-踢人出群，3-接受入群邀请，4-通过入群邀请，5-添加群管理员，6-移除群管理员，7-主动退群，8-解散群，9-转让群，10-更新群成员禁言状态',
    `send_type`          tinyint(4)    DEFAULT '0' COMMENT '发送人类型：0-用户，1-客服，2-药师，3-医生',
    `send_client_ip`     varchar(20)   DEFAULT '' COMMENT '发送端ip',
    `send_client_port`   varchar(5)    DEFAULT '' COMMENT '发送端端口',
    `send_client_type`   varchar(10)   DEFAULT '' COMMENT '发送客户端类型： AOS、IOS、PC、WINPHONE、WEB、REST',
    `send_device_id`     varchar(32)   DEFAULT '' COMMENT '发送端设备编号',
    `send_nick`          varchar(50)   DEFAULT '' COMMENT '发送人昵称',
    `send_id`            varchar(32)   DEFAULT '' COMMENT '发送人id（对应客服表和用户表的accid）',
    `send_at`            bigint(11)    DEFAULT NULL COMMENT '发送时间',
    `msg_receipt_time`   bigint(11)    DEFAULT NULL COMMENT '已读回执时间戳，如果有此字段, 说明此时间戳之前的所有消息对方均已读',
    `is_revoke`          tinyint(4)    DEFAULT '0' COMMENT '是否撤销：0-否，1-是',
    `revoke_at`          bigint(11)    DEFAULT '0' COMMENT '撤销时间',
    `custom_apns_text`   varchar(200)  DEFAULT '' COMMENT '自定义系统通知消息推送文本。仅在convType为CUSTOM_PERSON或CUSTOM_TEAM时含此字段',
    `ext`                varchar(64)   DEFAULT '' COMMENT '消息扩展字段',
    `antispam`           varchar(12)   DEFAULT '' COMMENT '标识是否被反垃圾(‘’，true，false)',
    `yidun_res`          varchar(500)  DEFAULT '' COMMENT '易盾反垃圾的原始处理细节',
    `msg_status`         tinyint(4)    DEFAULT '0' COMMENT '消息发送状态：0-发送成功，1-发送中，2-发送失败',
    `status`             tinyint(4)    DEFAULT '0' COMMENT '状态：有效 0：无效',
    PRIMARY KEY (`id`),
    UNIQUE KEY `index_tt` (`id`, `team_msg_id`, `team_client_msg_id`) USING BTREE,
    KEY `index_team_id_on_im_team_message` (`team_id`),
    KEY `index_send_at_on_im_team_message` (`send_at`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='IM消息表';

SET FOREIGN_KEY_CHECKS = 1;


