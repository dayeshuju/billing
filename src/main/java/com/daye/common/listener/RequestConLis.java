package com.daye.common.listener;

import org.springframework.web.context.request.RequestContextListener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;

@WebListener()
public class RequestConLis extends RequestContextListener {

}
