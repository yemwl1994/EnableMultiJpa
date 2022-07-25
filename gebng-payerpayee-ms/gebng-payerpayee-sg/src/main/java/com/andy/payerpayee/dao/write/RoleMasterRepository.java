package com.andy.payerpayee.dao.write;

import com.andy.payerpayee.model.LookupOptionMaster;
import com.andy.payerpayee.model.RoleMaster;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
@Qualifier("roleMasterRepoWrite")
public interface RoleMasterRepository extends JpaRepository<RoleMaster,Integer> {


}

