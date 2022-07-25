package com.andy.payerpayee.api;


import com.andy.payerpayee.api.response.PayerAccount;
import com.andy.payerpayee.dao.read.ApiCatalogRepository;
import com.andy.payerpayee.dao.write.RoleMasterRepository;
import com.andy.payerpayee.model.LookupOptionMaster;
import com.andy.payerpayee.model.RoleMaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
public class PayerPayeeController {

    private final Logger logger  = LoggerFactory.getLogger(PayerPayeeController.class);

    @Autowired
    @Qualifier("ApiCatalogRepositoryRead")
    ApiCatalogRepository apiCatalogRepositoryRead;

    @Autowired
    @Qualifier("roleMasterRepoWrite")
    RoleMasterRepository roleMasterRepoWrite;

    @GetMapping(value="/list")
    public PayerAccount getAccount() {
        logger.debug("Enter to PayerAccount");
        logger.info("Info Enter to PayerAccount");
        PayerAccount account = new PayerAccount();
        account.setAccountNo("123124124");
        account.setActive(true);
        account.setType("CASA");
        account.setVirtual(true);

        return account;
    }


//    @GetMapping(value="/category")
//    public LookupOptionMaster getOptions() {
//        System.out.println("-------Read Connection ----");
//        List<LookupOptionMaster> option = apiCatalogRepositoryRead.findByAttribute("Category");
//        if(option.size() > 0) {
//            return option.get(0);
//        }
//        return null;
//
//    }

    @GetMapping(value="/role/{id}")
    public ResponseEntity<?> getRoleMaster(@PathVariable("id") Integer id) {
//
        System.out.println("-----Write Connection ----");
        if(id == 2) {
            HashMap<String,Object> map =  new HashMap<>();
            map.put("status", 2);
            map.put("statusCode", "not_working");

            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        RoleMaster option = apiCatalogRepositoryRead.findOneByRoleId(id);

        //RoleMaster newRoleMaster = new RoleMaster();
        System.out.println("RoleName: " + option.getRoleName());
        option.setRoleName("Portal Admin");
        System.out.println(option.getRoleName());
        try {
            roleMasterRepoWrite.save(option);
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
//        if(option.size() > 0) {
//            return option.get(0);
//        }
        return new ResponseEntity<>(option, HttpStatus.OK);

    }



}
