
import generator.*;
import model.*;
import export.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {

            Attribute nameAttr = new Attribute("Nom", "String", null, null, null);
            Attribute ageAttr = new Attribute("Age", "Integer", 18, 65, null);
            Attribute cityAttr = new Attribute("Ville", "String", null, null, Arrays.asList("Paris", "Lyon", "Marseille", "Bordeaux"));

            Entity userEntity = new Entity("Utilisateur", Arrays.asList(nameAttr, ageAttr, cityAttr));

            Project project = new Project("Population_FR", 10, Arrays.asList(userEntity));

            DataGenerator rndGen = new RandomDataGenerator();
            DatasetGenerator generator = new DatasetGenerator(rndGen);
            Map<String, List<List<Object>>> dataset = generator.generate(project);

            String outDir = "output";
            Exporter csv = new CsvExporter();
            csv.export(project, dataset, outDir);

            Exporter json = new JsonExporter();
            json.export(project, dataset, outDir);

            Exporter xml = new XmlExporter();
            xml.export(project, dataset, outDir);

            Exporter sql = new SqlExporter();
            sql.export(project, dataset, outDir);

            System.out.println("Process finished. Vérifier le dossier 'output'.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
