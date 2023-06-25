import org.example.Education;
import org.example.Person;
import org.example.Sex;
import org.junit.jupiter.api.*;
import java.util.*;
import java.util.stream.Collectors;

public class MainTest {

    private Collection<Person> persons;

    @Test
    @BeforeEach // определяю, что метод должен быть запущен перед каждым тестом
    public void Settings(){
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");

        persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())), // выбор случайного имени из списка names
                    families.get(new Random().nextInt(families.size())), // выбор случайной фамилии из списка families
                    new Random().nextInt(100), // генерирую случайный возраст от 0 до 99
                    Sex.values()[new Random().nextInt(Sex.values().length)], //выбор случайного пола
                    Education.values()[new Random().nextInt(Education.values().length)])); // выбор случайного образования
        }
    }
    @Test
    public void testUnderageCount() {
        long underageCount = persons.stream()
                .filter(person -> person.getAge() < 18)
                .count();
        List<String> recruits = persons.stream()
                .filter(person -> person.getAge() >= 18 && person.getAge() <= 27)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        Assertions.assertTrue(underageCount > 0, "Найдены несовершеннолетние в коллекции Persons");
        Assertions.assertTrue(recruits.size() > 0, "Не найдено призывников в коллекции Persons");
    }
    // данным тестом проверяю, что список recruits не пустой, а также что количество несовершеннолетних underageCount больше нуля


    @Test
    public void testRecruits() {
        List<String> recruits = persons.stream()
                .filter(person -> person.getAge() >= 18 && person.getAge() <= 27) // фильтр элементов по возрасту
                .map(Person::getFamily) // преобразование каждого элемента стрима
                .collect(Collectors.toList()); // сбор элементов в новую коллекцию
        Assertions.assertNotNull(recruits); // переменная не является null
        Assertions.assertTrue(recruits.size() > 0); // размер коллекции больше 0
    }
    // Данным тестом проверяю, что в коллекции persons есть хотя бы один объект, у которого возраст между 18 и 27.



    @Test
    public void testWorkableList() {
        List<Person> workable = persons.stream()
                .filter(person -> person.getAge() >= 18 && person.getEducation() == Education.HIGHER &&
                        ((person.getSex() == Sex.MAN && person.getAge() <= 65) ||
                                (person.getSex() == Sex.WOMAN && person.getAge() <= 60)))
                // фильтрация элементов коллекции по условиям возраста, образование и пола.
                .sorted(Comparator.comparing(Person::getFamily)) // сортировка по алфавиту
                .collect(Collectors.toList()); // сбор элементов в новую коллекцию
        Assertions.assertNotNull(workable); // переменная не является null
        Assertions.assertTrue(workable.size() > 0); // размер коллекции больше 0
    }
}






