package com.example.divichart;

import com.example.divichart.entity.Account;
import com.example.divichart.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DivichartApplication {

	private static final Logger log = LoggerFactory.getLogger(DivichartApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DivichartApplication.class, args);
	}

	/*
	 * DB操作のデモ、使い方に慣れたら削除する
	 */
	@Bean
	public CommandLineRunner demo(AccountRepository repository) {
		return (args) -> {
			// insert
			repository.save(new Account("Jack", "Bauer"));

			// select all
			log.info("findAll()");
			log.info("-------------------------------");
			for (Account user : repository.findAll()) {
				log.info(user.toString());
			}
			log.info("");

			// select by id
			Account user = repository.findById(2L);
			log.info("findById(2L)");
			log.info("--------------------------------");
			log.info(user.toString());
			log.info("");

			// select by name
			log.info("select by name('Bauer')");
			log.info("--------------------------------------------");
			repository.findByName("Bauer").forEach(bauer -> {
				log.info(bauer.toString());
			});
			// for (Customer bauer : repository.findByLastName("Bauer")) {
			//  log.info(bauer.toString());
			// }
			log.info("");

			// delete by id
			repository.deleteById(2L);
			log.info("deleteById(2L)");
			log.info("--------------------------------");
			log.info(user.toString());
			log.info("");

		};
	}

}
