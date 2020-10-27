package com.timon.rhouse.bean;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Scope(value = "session")
@Component(value = "pageJsfService")
@ELBeanName(value = "pageJsfService")
public class PageBean {


    private String thispage;


    private String previousPage;



//    public void toPrevPage(){
//        return
//                thispage+"";
//    }

    public Boolean isUser(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object name = auth.getPrincipal();
        if(name=="anonymousUser"){
            return false;
        }else {
            return true;
        }
    }
    public String getThispage() {
        return thispage;
    }

    public void setThispage(String thispage) {
        this.thispage = thispage;
    }
}
