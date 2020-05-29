package com.lin.karley.Server;

import org.springframework.stereotype.Component;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description Description
 * @Author Karley LIn
 * @Date Created in 2020/5/29
 */
@Component
//@ServerEndpoint("/websocket/{id}/{xm}")
public class WebSocketServer {

    /**
     * 所有在线会话
     */
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();

    /**
     * 连接成功
     */
    @OnOpen
    public void onOpen(@PathParam(value = "id")String id,Session session){
        onlineSessions.put(id,session);
        System.out.println("用户"+id+" 上线！当前在线 "+onlineSessions.size()+" 人");
    }

    /**
     * 发送消息给所有人
     */
    private static void sendMessageToAll(String msg,String xm){
        onlineSessions.forEach((id,session) ->{
            try {
                session.getBasicRemote().sendText(xm+":"+msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 发送信息给指定ID用户
     * @Param id:用户id
     * @Param sendUserId:发送给用户id
     */
    public void sendToUser(String msg,String id,String sendUserId,String xm) throws IOException {
        if (onlineSessions.get(sendUserId) != null){
            onlineSessions.get(sendUserId).getBasicRemote().sendText(xm+":"+msg);
            onlineSessions.get(id).getBasicRemote().sendText(xm+":"+msg);
        }
    }
}
