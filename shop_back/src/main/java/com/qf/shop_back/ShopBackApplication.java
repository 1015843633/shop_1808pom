package com.qf.shop_back;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

//配置fastdfs的中的一个类
@Import(FdfsClientConfig.class)
@SpringBootApplication(scanBasePackages = "com.qf")
public class ShopBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopBackApplication.class, args);
	}

}

