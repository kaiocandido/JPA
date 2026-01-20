package oi.githubkaiocandido.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataBaseConfiguration {


    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;

    @Bean
    public DataSource hikariDataSource(){
        HikariConfig hk = new HikariConfig();
        hk.setJdbcUrl(url);
        hk.setUsername(username);
        hk.setPassword(password);
        hk.setDriverClassName(driver);

        hk.setMaximumPoolSize(10); // maximo de conexões liberadas
        hk.setMinimumIdle(1); // minimo de conexões liberadas
        hk.setPoolName("Library"); // nome
        hk.setMaxLifetime(600000); // 600 mil ms (tempo de duração de conexão)
        hk.setConnectionTimeout(100000); // tempo para obter conexão
        hk.setConnectionTestQuery("select 1"); // teste de conexão no banco

        return new HikariDataSource(hk);

    }

}
