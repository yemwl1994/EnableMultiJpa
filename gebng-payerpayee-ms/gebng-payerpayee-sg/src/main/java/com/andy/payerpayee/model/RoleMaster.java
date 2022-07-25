package com.andy.payerpayee.model;


import javax.persistence.*;

@Entity
@Table(name="ROLE_MASTER")
public class RoleMaster {

    @Id
    @Column(name="ROLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    @Column(name="ROLE_NAME")
    private String roleName;

    @Column(name="IS_ACTIVE")
    private Integer isActive;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }
}
