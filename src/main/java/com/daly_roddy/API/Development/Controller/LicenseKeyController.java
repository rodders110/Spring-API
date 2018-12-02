package com.daly_roddy.API.Development.Controller;

import com.daly_roddy.API.Development.Service.AuthenticationService;
import com.daly_roddy.API.Development.Model.DecryptionDetails;
import com.daly_roddy.API.Development.Model.EncryptionDetails;
import com.daly_roddy.API.Development.Service.HashingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class LicenseKeyController {

    @Autowired
    private HashingService hashingService;

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void landingPage(HttpServletResponse response) throws IOException {
        response.sendRedirect("swagger-ui.html");
    }

    @RequestMapping(value = "/getkey", method = RequestMethod.POST)
    @ResponseBody
    public String getKey(@RequestBody EncryptionDetails encryptionDetails, HttpServletResponse response){
        if(authenticationService.authenticate(encryptionDetails.getPassword())){
            response.setStatus(HttpServletResponse.SC_OK);
            return hashingService.encrypt(encryptionDetails.getFullName(), encryptionDetails.getSoftware());
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return "Password incorrect";
    }

    @RequestMapping(value = "/verifyKey", method = RequestMethod.POST)
    public void checkLicenseKey(@RequestBody DecryptionDetails decryptionDetails, HttpServletResponse response){
        if(hashingService.verify(decryptionDetails.getFullName(), decryptionDetails.getSoftware(), decryptionDetails.getLicense())){
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
