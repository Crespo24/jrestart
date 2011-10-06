package com.example.jrestart.data;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Data {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}