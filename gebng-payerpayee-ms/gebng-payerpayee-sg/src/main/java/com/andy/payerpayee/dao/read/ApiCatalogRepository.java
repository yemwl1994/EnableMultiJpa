package com.andy.payerpayee.dao.read;


import com.andy.payerpayee.model.LookupOptionMaster;
import com.andy.payerpayee.model.RoleMaster;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("ApiCatalogRepositoryRead")
public interface ApiCatalogRepository extends JpaRepository<RoleMaster, Integer> {

    //List<LookupOptionMaster> findByAttribute(String attribute);
    RoleMaster findOneByRoleId(Integer id);
}
