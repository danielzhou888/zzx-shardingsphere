package com.zzx.sharding.service;

import com.zzx.sharding.entity.TeamMessage;
import com.zzx.sharding.vo.ConversationMsgVo;

import java.util.List;

/**
 * 群组消息服务
 *
 * @author zhouzhixiang
 */
public interface TeamMessageServiceApi {

    int insert(TeamMessage message);

    /**
     * 插入通用消息
     * @param message
     * @author zhouzhixiang
     * @return
     */
    int insertMsg(ConversationMsgVo message);

    int insertSample();

    TeamMessage selectById(long id);

    /**
     * 拉取群会话历史记录（对应云信公有云接口）
     * @param scene       场景
     * @param to          群id
     * @param beginTime   查询开始时间
     * @param endTime     查询结束时间
     * @param lastMsgId   查询起点消息
     * @param limit       本次查询限制数量
     * @param reverse     默认false，表示从endTime开始往前查找历史消息; true表示从beginTime开始往后查找历史消息
     * @param asc         表示对查询结果按照时间进行排序的方式： 如果asc和reverse设置相同(都为true，或者都为false)，查询结果按照时间戳从大到小；如果asc和reverse设置不同，查询结果按照时间戳从小到大。
     * @author zhouzhixiang
     * @return
     */
    List<TeamMessage> getHistoryMessages(String scene, String to, Long beginTime, Long endTime, long lastMsgId, int limit, boolean reverse, boolean asc) throws IllegalArgumentException;

    /**
     * 抄送消息，生产投递kafka失败时候，业务插入此条消息到DB中
     * @author zhouzhixiang
     * @param message
     */
    boolean insertMessageWhenSendKafkaError(String message);

    /**
     * 获取会话最后一条消息的时间戳
     * @param teamId
     * @author zhouzhixiang
     * @return
     */
    Long getLastMsgTimeByTeamId(long teamId);

    /**
     * 撤回群消息
     * @param msgServerId
     * @param deleteTime
     * @return
     */
    boolean teamWithdrawMsg(long msgServerId, long deleteTime);

    List<TeamMessage> selectAll();

    long deleteById(long id);
}
