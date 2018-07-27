package com.example.annotationlib.exception;

import javax.lang.model.element.Element;

/**
 * @author huangjian
 * @create 2018/7/25 0025
 * @Description
 */
public class ProcessingException extends Exception {
    Element element;

    public ProcessingException(Element element, String msg, Object... args) {
        super(String.format(msg, args));
        this.element = element;
    }

    public Element getElement() {
        return element;
    }
}
