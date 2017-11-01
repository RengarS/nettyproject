package nettytimeserver.domain;

import java.util.List;

/**
 * @author Aries
 */
public class Request {
    /**
     * request的类型
     */
    private String action;
    /**
     * 发送时间
     */
    private String time;
    /**
     * 发送者
     */
    private String sender;
    /**
     * 接受者
     */
    private List<String> receiver;
    /**
     * 消息内容
     */
    private String content;

    public Request() {
    }

    public Request(String action, String sender, List<String> receiver, String content, String time) {
        this.time = time;
        this.action = action;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public List<String> getReceiver() {
        return receiver;
    }

    public void setReceiver(List<String> receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
