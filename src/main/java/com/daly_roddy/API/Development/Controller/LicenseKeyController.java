package com.daly_roddy.API.Development.Controller;

import com.daly_roddy.API.Development.Services.AuthenticationService;
import com.daly_roddy.API.Development.Details.DecryptionDetails;
import com.daly_roddy.API.Development.Details.EncryptionDetails;
import com.daly_roddy.API.Development.Services.EncryptionService;
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
    private EncryptionService encryptionService;

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
            return encryptionService.encrypt(encryptionDetails.getFullName(), encryptionDetails.getProgram());
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return "Password incorrect";
    }

    @RequestMapping(value = "/verifyKey", method = RequestMethod.POST)
    public void checkLicenseKey(@RequestBody DecryptionDetails decryptionDetails, HttpServletResponse response){



        if(encryptionService.verify(decryptionDetails.getFullName(), decryptionDetails.getProgram(), decryptionDetails.getLicense())){
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
