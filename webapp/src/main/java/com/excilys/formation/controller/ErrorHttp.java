package com.excilys.formation.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorHttp {
    @GetMapping(value = "errors")
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {
         
        ModelAndView errorPage = new ModelAndView();
        int httpErrorCode = getErrorCode(httpRequest);
 
        switch (httpErrorCode) {
            case 403: {
                errorPage.setViewName(ViewName.FORBIDDEN.toString());
                break;
            }
            case 404: {
                errorPage.setViewName(ViewName.NOTFOUND.toString());
                break;
            }
            case 500: {
                errorPage.setViewName(ViewName.INTERNALSERVERERROR.toString());
                break;
            }
            default:
            	errorPage.setViewName(ViewName.NOTFOUND.toString());
            	break;
        }        
        return errorPage;
    }
     
    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
          .getAttribute("javax.servlet.error.status_code");
    }
}
