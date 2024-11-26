package org.example;

import java.util.Properties;

abstract public class EmailSender {
    String to;
    String from;
    String host;
    String files="users.txt";
    Properties properties;
    public abstract void init(String to);
    public abstract String sendmail();
    public abstract boolean check1(String email);
}