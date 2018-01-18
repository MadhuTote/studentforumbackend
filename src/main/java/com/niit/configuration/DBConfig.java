package com.niit.configuration;



import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.niit.model.BlogComment;
import com.niit.model.BlogPost;
import com.niit.model.Chat;
import com.niit.model.Friend;
import com.niit.model.Job;
import com.niit.model.JobApplication;
import com.niit.model.ProfilePicture;
//import com.niit.model.BlogComment;
//import com.niit.model.BlogPost;
//import com.niit.model.Chat;
//import com.niit.model.Friend;
//import com.niit.model.Job;
//import com.niit.model.JobApplication;
//import com.niit.model.ProfilePicture;
import com.niit.model.User;

@Configuration
@EnableTransactionManagement
public class DBConfig 
{
public DBConfig(){
	System.out.println("DB Configuration is instantiated");
}
	//create an instance
			@Bean
			public SessionFactory sessionFactory() {
				LocalSessionFactoryBuilder lsf=
						new LocalSessionFactoryBuilder(getDataSource());
				Properties hibernateProperties=new Properties();
				hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
			   // hibernateProperties.setProperty("hibernate.default_schema" , "ABC");
				//hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
				hibernateProperties.setProperty("hibernate.show_sql", "true");
				lsf.addProperties(hibernateProperties);
				Class classes[]=new Class[]{User.class,Job.class,JobApplication.class, BlogComment.class,BlogPost.class,ProfilePicture.class,Friend.class,Error.class,Chat.class};
				
			    return lsf.addAnnotatedClasses(classes).buildSessionFactory();
			}
			@Bean
			public DataSource getDataSource() {
			    BasicDataSource dataSource = new BasicDataSource();
			    dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
			    dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
			    dataSource.setUsername("PROJECT");
			    dataSource.setPassword("123");
			    return dataSource;
			}
			@Bean
			public HibernateTransactionManager hibTransManagement(){
				return new HibernateTransactionManager(sessionFactory());
			}

}
