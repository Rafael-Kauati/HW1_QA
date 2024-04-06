package deti.traveler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Slf4j
@SpringBootApplication
@EnableJpaRepositories
@AllArgsConstructor
public class TravelerApplication implements CommandLineRunner {

	@Autowired
	private final SetupDatabaseContent setupH2DatabaseContent;

		public static void main(String[] args) {SpringApplication.run(TravelerApplication.class, args);}

	@Override
	public void run(String... args) throws Exception {
		setupH2DatabaseContent.setup();
	}
}
