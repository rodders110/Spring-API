package com.daly_roddy.API.Development;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class LicenseKeyController {

    @Autowired
    private EncryptionService encryptionService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String landingPage(@RequestParam("name") String name){
        return name;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String page(){
        return "Welcome.html";
    }

    @RequestMapping(value = "/getkey", method = RequestMethod.POST)
    @ResponseBody
    public String getKey(@RequestBody EncryptionDetails encryptionDetails){
        if(true){
            return encryptionService.encrypt(encryptionDetails.getFirstName(), encryptionDetails.getLastname(), encryptionDetails.getProgram());
        }

        return null;
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
