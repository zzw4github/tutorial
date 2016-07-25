package me.zzw.app.nutz.service;

/**
 * Created by Administrator on 2016-07-25.
 */
public interface EmailService {

    boolean send(String to, String subject, String html);

}
