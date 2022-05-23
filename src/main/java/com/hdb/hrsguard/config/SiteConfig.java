package com.hdb.hrsguard.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/*网站基础信息配置
 * @author Administrator
 * */
@Component
@PropertySource(value = "classpath:site.properties")
public class SiteConfig {
    @Value("${hospital.site.name}")
    private String SiteName;
    @Value("${hospital.site.url}")
    private String SiteUrl;
    @Value("填写病历")
    private String AddPatient;

    public String getSiteName() {
        return SiteName;
    }

    public void setSiteName(String siteName) {
        SiteName = siteName;
    }

    public String getSiteUrl() {
        return SiteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        SiteUrl = siteUrl;
    }

    public String getAddPatient() {
        return AddPatient;
    }

    public void setAddPatient(String addPatient) {
        AddPatient = addPatient;
    }
}
