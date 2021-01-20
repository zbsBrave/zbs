package zbs.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zbs
 * @since 2021/1/10
 */
@ServerEndpoint("/ws/{id}")
@Component
public class WebsocketServer {
    private static final Logger logger = LoggerFactory.getLogger(WebsocketServer.class);

    /** 用于存放客户端对应的 WebsocketServer 对象 */
    private static final ConcurrentHashMap<String,WebsocketServer> SERVER_MAP = new ConcurrentHashMap<>();
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;
    /**接收 id */
    private String id;
    /** 在线人数 */
    private static final AtomicInteger onlineCount = new AtomicInteger(0);

    /**
     * 建立websocket连接
     * @param id 客户端id
     * @param session 会话
     */
    @OnOpen
    public void onOpen(@PathParam("id") String id, Session session){
        logger.info("onOpen ===> id={}的客户端开启了websocket",id);
        this.id = id;
        this.session = session;
        if(!SERVER_MAP.containsKey(id)){
            onlineCount.incrementAndGet();
        }
        SERVER_MAP.put(id,this);
        sendMessage("websocket 连接成功");
    }

    /**
     * 接收到客户端发送的消息
     * @param message 客户端发送的消息
     * @param session 会话
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("onMessage ===> 用户id: {} ,消息: {}",id,message);
        sendMessage("收到");
    }

    /**
     * 关闭 websocket 连接
     */
    @OnClose
    public void onClose(){
        SERVER_MAP.remove(this.id);
        onlineCount.decrementAndGet();
        logger.info("onClose ===> 用户：{} 端口了连接，当前在线人数为：{}",id,onlineCount.get());
    }

    /**
     * 连接异常
     * @param error 异常
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("onError ===> id=" +id+ " 的用户连接发送异常",error);
    }

    /**
     * 服务器发送消息
     * @param message 消息内容
     */
    public void sendMessage(String message){
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.error("发送消息失败",e);
        }
    }

    /**
     * 给指定用户发送消息
     * @param message 消息
     * @param userId 用户id
     * @throws IOException
     */
    public void sendMessageToUser(String message,String userId) throws IOException {
        logger.info("发送消息到：{}，消息内容：{}",userId,message);
        if(userId!=null && !"".equals(userId) && SERVER_MAP.containsKey(userId)){
            SERVER_MAP.get(userId).sendMessage(message);
        }else {
            sendMessage("用户"+userId+",不在线！");
        }
    }

}
