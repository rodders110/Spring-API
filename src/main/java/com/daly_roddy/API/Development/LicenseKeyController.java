package com.daly_roddy.API.Development;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
            return encryptionService.encrypt(encryptionDetails.getFirstName(), encryptionDetails.getLastname(), encryptionDetails.getProgram());
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return "Password incorrect";
    }

    @RequestMapping(value = "/verifyKey", method = RequestMethod.POST)
    public void checkLicenseKey(@RequestBody DecryptionDetails decryptionDetails, HttpServletResponse response){



        if(encryptionService.verify(decryptionDetails.getFirstName(), decryptionDetails.getLastName(), decryptionDetails.getProgram(), decryptionDetails.getLicense())){
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
