package com.huahua.demo.listener

import love.forte.simboot.annotation.Filter
import love.forte.simboot.annotation.FilterValue
import love.forte.simboot.annotation.Listener
import love.forte.simboot.filter.MatchType
import love.forte.simbot.event.FriendMessageEvent
import love.forte.simbot.event.GroupMessageEvent
import love.forte.simbot.event.MessageEvent
import love.forte.simbot.logger.LoggerFactory
import love.forte.simbot.message.At
import love.forte.simbot.message.buildMessages
import love.forte.simbot.message.toText
import org.springframework.stereotype.Component

/**
 * ClassName: Listener
 * @description
 * @author 花云端
 * @date 2022-12-03 17:59
 */
@Component
class MyListener {

    /**
     * 获取日志
     */
    val logger = LoggerFactory.getLogger(MyListener::class)

    /***
     * 事件监听统一使用注解@Listener
     * 向指定事件类添加扩展函数达到对事件的监听
     * 常用的事件：
     *  1、GroupMessageEvent
     *  2、FriendMessageEvent
     *  3、MessageEvent
     */

    /**
     * 私聊事件监听
     * @receiver FriendMessageEvent 好友消息事件
     * @param name String   通过@Filter获取到的字符串
     *
     * 通过@Filter注解达到简单的消息过滤
     * 具体参数请参照文档
     */
    @Listener
    @Filter("你好,我是{{name}}", matchType = MatchType.REGEX_MATCHES)
    suspend fun FriendMessageEvent.friendListener(
        @FilterValue("name") name:String
    ){

        val msg = "${name}，你好"

        /**
         * 向事件中的好友发送消息
         */
        friend().send(msg)

        /**
         * 向事件中的好友回复消息,如果该消息支持回复的话
         */
        reply(msg)
    }

    /**
     * 简单的好友复读机
     * @receiver FriendMessageEvent
     */
    @Listener
    suspend fun FriendMessageEvent.friendListener1(){
        /**
         * 获取消息体中的文本消息
         */
        val msg = messageContent.plainText

        friend().send(msg)
    }


    @Listener
    suspend fun GroupMessageEvent.groupListener(){
        /**
         * 打印消息日志
         */
        logger.info(messageContent.plainText)

        val author = author()   //发送消息的成员
        val authorCode = author().id    //该成员QQ号
//        val id = "".ID    可以将任意类型转换为ID
//        val id = 123.ID
        val authorName = author().nickOrUsername //该成员的群名片（如果有的话）或者QQ名字
        /**
         * 该成员的权限状态
         */
        val authorPermission = if (author().isAdmin()) "管理员" else if (author().isOwner()) "群主" else "普通成员"

        /**
         * 使用消息构建器
         */
        val msg = buildMessages {
            + At(authorCode)
            + "Hello，"
            + "".toText()
        }
        group().send(msg)

    }

    /**
     * 通用消息事件监听
     * 可同时监听FriendMessageEvent和GroupMessageEvent
     * @receiver MessageEvent
     */
    @Listener
    suspend fun MessageEvent.listener(){
        /**
         * 可以通过 when 或者 if...else判断接收器类型
         */
        when(this){
            is GroupMessageEvent -> {
                // when块内可以只能转换接收器类型
                group().send("这是群聊消息")
            }
            is FriendMessageEvent -> {
                friend().send("这是私聊消息")
            }
        }
        if (this is GroupMessageEvent){
            logger.info("this is GroupMessage")
        }
    }
}