package Project.Investment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import Project.Investment.domain.Comment;
import Project.Investment.domain.CommentRepository;
import Project.Investment.domain.Metal;
import Project.Investment.domain.MetalRepository;
import Project.Investment.domain.Reply;
import Project.Investment.domain.ReplyRepository;






@SpringBootApplication
public class InvestmentApplication {
	private static final Logger log = LoggerFactory.getLogger(InvestmentApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(InvestmentApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner comment(CommentRepository crepository, ReplyRepository rrepository, MetalRepository mrepository) {
		return (args)->{
			log.info("pari kommentia");
			
			//testi metallit
			mrepository.save(new Metal(12));
			mrepository.save(new Metal(3));
			
			
			//testi kommentit
			crepository.save(new Comment("Pekka","aaaaaaaaaaaaaaaaaaaaaa"));
			crepository.save(new Comment("Vilma","bbbbbbbbbbbbbb"));
			crepository.save(new Comment("Juho","ccccccccccccccccccccc"));
			
			
			
			
			log.info("nouda kommentit");
			for (Comment comment : crepository.findAll()) {
				log.info(comment.toString());
			}
		};
		
	}

}
