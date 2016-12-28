package box;

import box.config.DefaultProfileUtil;
import box.repository.GreenHouseManagerRepository;
import box.service.GreenHouseManagerServiceService;
import box.service.impl.GreenHouseManagerServiceServiceImpl;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.IntervalTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * This is a helper Java class that provides an alternative to creating a
 * web.xml. This will be invoked only when the application is deployed to a
 * servlet container like Tomcat, Jboss etc.
 */
@Configuration
@EnableScheduling
@ComponentScan("box.service")
public class ApplicationWebXml extends SpringBootServletInitializer /*implements SchedulingConfigurer*/ {

//    @Bean
//    public GreenHouseManagerRunner greenHouseManagerRunner(){
//        return new GreenHouseManagerRunner();
//    }
//    @Bean
//    public GreenHouseManagerServiceService greenHouseManagerServiceService() {
//        return new GreenHouseManagerServiceServiceImpl();
//    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        /**
         * set a default to use when no profile is configured.
         */
        DefaultProfileUtil.addDefaultProfile(application.application());
        return application.sources(BachelorApp.class);
    }
    
//    @Bean(destroyMethod = "shutdown")
//    public Executor taskExecutor() {
//        return Executors.newScheduledThreadPool(5);
//    }
//    
//    @Override
//    public void configureTasks(ScheduledTaskRegistrar str) {
//        str.setScheduler(taskExecutor());
//        str.addFixedRateTask(new IntervalTask(new Runnable() {
//            @Override
//            public void run() {
//                greenHouseManagerServiceService().run();
//            }
//        },0));
//    }
}
