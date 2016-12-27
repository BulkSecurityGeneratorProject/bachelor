package box;

import box.config.DefaultProfileUtil;
import box.repository.GreenHouseManagerRepository;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

/**
 * This is a helper Java class that provides an alternative to creating a web.xml.
 * This will be invoked only when the application is deployed to a servlet container like Tomcat, Jboss etc.
 */
public class ApplicationWebXml extends SpringBootServletInitializer {

//    @Bean
//    public GreenHouseManagerRunner greenHouseManagerRunner(){
//        return new GreenHouseManagerRunner();
//    }
    

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        /**
         * set a default to use when no profile is configured.
         */
        DefaultProfileUtil.addDefaultProfile(application.application());
        return application.sources(BachelorApp.class);
    }
}
