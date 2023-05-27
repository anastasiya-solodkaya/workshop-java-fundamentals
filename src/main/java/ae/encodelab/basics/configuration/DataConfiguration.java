package ae.encodelab.basics.configuration;

import ae.encodelab.basics.data.csv.management.StudentStorageManager;
import ae.encodelab.basics.data.csv.storage.CsvStudentsStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.nio.file.Paths;

@Configuration
public class DataConfiguration {

    @Bean
    public CsvStudentsStorage studentStorage() throws Exception {
        ClassPathResource resource = new ClassPathResource("data", this.getClass().getClassLoader());
        StudentStorageManager manager = new StudentStorageManager(Paths.get(resource.getFile().toURI()));
        CsvStudentsStorage storage = manager.load();
        return storage;
    }

}
