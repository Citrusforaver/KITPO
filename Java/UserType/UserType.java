import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public interface UserType {
    public String typeName(); // Имя типа
    public Object create(); // Создает объект ИЛИ
    public Object clone(); // Клонирует текущий
    public Object readValue(InputStreamReader in) throws IOException; // Создает и читает объект
    public void writeValue(OutputStreamWriter out, Object o) throws IOException; // Создает и читает объект
    public Object parseValue(String ss); // Создает и парсит содержимое из строки
    public Comparator getTypeComparator(); // Возвращает компаратор для сравнения
}

